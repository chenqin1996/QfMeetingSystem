package com.qianfeng.meeting.framework.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.meeting.framework.container.RequestPathContainer;
import com.qianfeng.meeting.framework.pojo.BeanDefinition;
import com.qianfeng.meeting.framework.pojo.MethodDefinition;
import com.qianfeng.meeting.framework.pojo.ParameterDefinition;
import com.qianfeng.meeting.framework.utils.JavaTypeUtils;
import com.qianfeng.meeting.framework.utils.QfDateConvert;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有的请求都由该servlet来处理
 * 处理方式：把请求uri获取到，把对应类与方法的请求参数取出
 * 根据该参数调用对应的方法
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 获取请求uri
         * 把对应类与方法的请求参数取出
         * 根据参数去容器中查询是否存在
         *  存在：调用对应的方法
         *  不存在：提示用户路径有问题
         */
        /*
        request.getRequestURI
        获取整个请求url中：项目名之后 ， 问号之前的那一部分
         */
        String uri = req.getRequestURI();
        /*
        解析uri
        目的：将于类的映射关系   与  方法的映射关系取出
        最终调用定义对象的对应方法

        例如 :   /user/login
         */
        //把类的映射值截取出来
        int index = uri.lastIndexOf("/");
        String classMappingValue = uri.substring(0, index);
        //去容器中查找是否存在这个key
        Map<String, BeanDefinition> requestMap = RequestPathContainer.getRequestMap();
        if(requestMap.containsKey(classMappingValue)){//表示容器中存在这个映射的值
            //就把对应的BeanDefinition对象取出
            BeanDefinition beanDefinition = requestMap.get(classMappingValue);
            //获取类对象
            Class typeClass = beanDefinition.getTypeClass();
            //把这个类描述对象中所有的方法描述对象取出
            List<MethodDefinition> methodDefinitions = beanDefinition.getMethodDefinitions();
            for(MethodDefinition methodDefinition : methodDefinitions){//取出每一个方法描述对象
                if(uri.endsWith(methodDefinition.getAnnotationMappingName())){//判断方法名是否与请求的uri的最后部分匹配
                    Method methodClass = methodDefinition.getMethodClass();//取出方法的对象
                    try {
                        Object obj = typeClass.newInstance();//创建对应的调用的方法所在的类对象
                        //调用handlePramaters获取参数数组
                        Object[] args = handlePramaters(methodDefinition, req, resp);
                        Object result = methodClass.invoke(obj,args);//执行该方法 并获取返回值
                        //向前端响应内容
                        sendResponse(result, resp);
                        return;
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //执行到这里，说明请求路径是不存在的
        Map<String, Object> map = new HashMap<>();
        map.put("status", 404);
        map.put("msg", "请求的路径不存在");
        sendResponse(map,resp);
    }

    /**
     * 向前端响应指定对象的JSON格式的字符串
     * @param result
     * @param resp
     */
    private void sendResponse(Object result, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getOutputStream(), result);
    }

    /**
     * 通过methodDefinition获取ParameterDefinitions
     * 可以从通过methodDefinition获取ParameterDefinitions取出每一个ParameterDefinition
     * 每一个ParameterDefinition包含了参数名称：可以根据参数的名称从前端的请求参数中把对应的值取出来
     * 也包含了参数类型：
     *          1.为普通的Java类型  比如：String int Integer float Float double Double...
     *              这些类型的参数就可以直接把前端请求的数据复制给参数
     *          2.自定义类型   比如:User Dept
     *              就需要使用BeanUtils将强求的参数封装到对应对象中
     * @param methodDefinition
     * @return
     */
    private Object[] handlePramaters(MethodDefinition methodDefinition, HttpServletRequest req, HttpServletResponse resp) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        //取出ParameterDefinitions
        List<ParameterDefinition> parameterDefinitions = methodDefinition.getParameterDefinitions();
        //创建args数组，作为被返回的数组
        Object[] args = new Object[parameterDefinitions.size()];
        //遍历parameterDefinitions
        for (int i = 0; i < parameterDefinitions.size(); i++) {
            //取出每一个parameterDefinition
            ParameterDefinition pd = parameterDefinitions.get(i);
            String paraName = pd.getParaName();//取出参数的名称
            Object paraType = pd.getParaType();//取出参数的类型
            Parameter paraClass = pd.getParaClass();//取出参数的类对象


            /*
            如果参数是HttpServletRequest类型
            则直接把doPost的req作为实参传过去
             */
            if(paraType == HttpServletRequest.class){
                args[i] = req;
                continue;
            }
             /*
            如果参数是HttpServletResponse类型
            则直接把doPost的resp作为实参传过去
             */
            if(paraType == HttpServletResponse.class){
                args[i] = resp;
                continue;
            }

            //判断参数是否是普通java类型
            boolean isJavaType = judgeIsJavaType(paraType);
            if(isJavaType){//参数是普通java类型
                //直接把前端请求的值赋值给该参数
                //考虑形参有可能是非String类型
                String pn = req.getParameter(paraName);

                switch (paraType.toString()){
                    case "class java.lang.String":
                        args[i] = pn;
                        break;
                    case "class java.lang.Integer":
                    case "int":
                        Integer integer = Integer.parseInt(pn);
                        args[i] = integer;
                        break;
                    case "class java.lang.Double":
                    case "double":
                        double v = Double.parseDouble(pn);
                        args[i] = v;
                        break;
                }

            }else{//参数是自定义类型
                if(pd.isHaveRequestBodyAnnotation()){//判断该参数是否被 QfRequestBody修饰
                    //将请求的json字符串转换为java对象
                    String jsonData = getJsonData(req);//获取请求的json数据
                    //将数据转换为java对象
                    ObjectMapper objectMapper = new ObjectMapper();
                    /*
                    objectMapper的readValue方法可以将字符串转换成java对象
                    readValue(json字符串, 转换成的Java对象所属的类对象);
                     */
                    Object obj = objectMapper.readValue(jsonData, (Class)paraType);
                    //将Java对象赋值给参数
                    args[i] = obj;
                }else{
                    //需要使用BeanUtils来封装对象
                    //创建参数类型对应的对象
                    Object obj = paraClass.getType().newInstance();
                    //还需要考虑日期类型
                    ConvertUtils.register(new QfDateConvert(), Date.class);
                    //将前端请求的值封装到参数类型的对象中
                    BeanUtils.populate(obj, req.getParameterMap());
                    args[i] = obj;//把封装好的对象作为实参存入args数组中
                }

            }
        }
        return args;
    }

    /**
     * 获取前端以json格式传过来的数据
     * @param req
     * @return
     */
    private String getJsonData(HttpServletRequest req) throws IOException {
        //获取服务器的输入流   服务器的输入流的内容就是客户端的输出流写到内容
        //客户端写的内容就是提交的数据
        ServletInputStream inputStream = req.getInputStream();
        byte[] bytes = new byte[1024];
        inputStream.read(bytes);
        String jsonData = new String(bytes, "UTF-8");
        return jsonData;
    }

    /**
     * 判断指定的对象是否是Java普通类型
     * @param paraType
     * @return
     */
    private boolean judgeIsJavaType(Object paraType) {
        //取出包含所有java普通类型的数组
        String[] javaTypeList = JavaTypeUtils.getJavaTypeList();
        //遍历该数组，取出每一个普通类型的名称
        for(String typeName : javaTypeList){
            //如果参数的类型名称与该普通类型的名称相等
            if(typeName.equals(paraType.toString())){
                //该参数就是普通类型
                return true;
            }
        }
        //如果循环结束上面if都没有执行过，说明参数一定是非普通类型
        return false;
    }
}
