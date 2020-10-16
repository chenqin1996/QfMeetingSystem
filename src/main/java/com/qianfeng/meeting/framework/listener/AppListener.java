package com.qianfeng.meeting.framework.listener;

import com.qianfeng.meeting.business.config.AppConfig;
import com.qianfeng.meeting.framework.annotation.QfController;
import com.qianfeng.meeting.framework.annotation.QfRequestBody;
import com.qianfeng.meeting.framework.annotation.QfRequestMapping;
import com.qianfeng.meeting.framework.annotation.QfScanner;
import com.qianfeng.meeting.framework.container.RequestPathContainer;
import com.qianfeng.meeting.framework.pojo.BeanDefinition;
import com.qianfeng.meeting.framework.pojo.MethodDefinition;
import com.qianfeng.meeting.framework.pojo.ParameterDefinition;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class AppListener implements ServletContextListener {
    /**
     * 此方法在服务器启动的时候执行
     * 我们就可以使用它来加载处理请求的类对象
     * @param sce
     */
    public void contextInitialized(ServletContextEvent sce) {
        //把AppConfig类的注解取到，将注解的basePackage属性值取出
//        Class<AppConfig> appConfigClass = AppConfig.class;
        String config = sce.getServletContext().getInitParameter("config");
        Class appConfigClass = null;
        try {
            appConfigClass = Class.forName(config);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //取出该类的QfScanner注解
        QfScanner qfScanner = (QfScanner)appConfigClass.getAnnotation(QfScanner.class);
        //判断该注解是否存在
        if(qfScanner != null){
            String basePackage = qfScanner.basePackage();
            //调用getRealPath获取需要创建Class对象的.class文件所在的真实路径
            String realPath = getRealPath(basePackage, sce);
            //调用getFullPath 得到要创建类对象的全路径
            List<String> fullPaths = getFullPath(basePackage, realPath);
            try {
                //将全路径代表的java类转换成Class对象
                List<Class> classes = convertPathToClass(fullPaths);
                for(Class clazz : classes){//遍历classes，取出每一个类对象

                    QfController qfController = (QfController)clazz.getAnnotation(QfController.class);
                    if(qfController != null) {
                        //通过反射将clazz解析为BeanDefinition对象
                        BeanDefinition beanDefinition = convertClazzToBeanDefinition(clazz, qfController);
                        //将解析好的BeanDefinition存入container中，以该类的注解的值作为key
                        RequestPathContainer.setRequestMap(beanDefinition.getAnnotationMappingName(),beanDefinition);
                    }


                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 通过反射取出clazz对象中的信息，封装成beanDefinition
     * @param clazz
     * @return
     */
    private BeanDefinition convertClazzToBeanDefinition(Class clazz, QfController qfController) {
        BeanDefinition beanDefinition = new BeanDefinition();
        //clazz.getName() 获取全路径名     getSimpleName()获取不包含包名的类名
        beanDefinition.setTypeName(clazz.getName());//设置类名
        beanDefinition.setTypeClass(clazz);//这是类对象
        beanDefinition.setAnnotationClass(QfController.class);//设置注解类对象
        beanDefinition.setAnnotationMappingName(qfController.value());//设置注解的值

        //获取该类的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        List<MethodDefinition> methodDefinitions = new ArrayList<>();
        for(Method m : methods){
            //获取方法对象的QfRequestMapping注解
            QfRequestMapping qfRequestMapping = m.getAnnotation(QfRequestMapping.class);
            if(qfRequestMapping != null){
                //qfRequestMapping不为空，说明该方法一定是被此注解修饰
                //则将方法对象解析为MethodDefinition对象
                MethodDefinition methodDefinition = convertMethodToMethodDefinition(m,qfRequestMapping);
                methodDefinitions.add(methodDefinition);
            }
        }
        beanDefinition.setMethodDefinitions(methodDefinitions);//设置方法描述对象的集合
        System.out.println(beanDefinition);
        return beanDefinition;
    }

    /**
     * 解析为methodDefinition
     * @param m
     * @param qfRequestMapping
     * @return
     */
    private MethodDefinition convertMethodToMethodDefinition(Method m, QfRequestMapping qfRequestMapping) {
        MethodDefinition methodDefinition = new MethodDefinition();
        methodDefinition.setMethodName(m.getName());
        methodDefinition.setMethodClass(m);
        methodDefinition.setAnnotationClass(QfRequestMapping.class);
        methodDefinition.setAnnotationMappingName(qfRequestMapping.value());


        List<ParameterDefinition> parameterDefinitions = new ArrayList<>();
        Parameter[] parameters = m.getParameters();//获取该方法所有的参数对象的数组
        Class[] parameterTypes = m.getParameterTypes();//获取所有的参数类型  类型的顺序与参数的顺序是对应的
        for(int i=0;i<parameters.length;i++){//遍历出每一个参数
            Parameter p = parameters[i];//取出每一个Parameter对象
            //将每一个Parameter转换成ParameterDefinition对象
            ParameterDefinition parameterDefinition = convertParamToParamDefinition(p, parameterTypes[i], i);
            parameterDefinitions.add(parameterDefinition);
        }
        methodDefinition.setParameterDefinitions(parameterDefinitions);//设置方法描述对象的属性描述对象集合
        return methodDefinition;
    }

    /**
     * 根据Parameter对象装配出一个ParameterDefinition对象
     * @param p
     * @param paramType
     * @param i
     * @return
     */
    private ParameterDefinition convertParamToParamDefinition(Parameter p,Class paramType, int i) {
        ParameterDefinition parameterDefinition = new ParameterDefinition();
        parameterDefinition.setParaClass(p);
        parameterDefinition.setParaName(p.getName());
        parameterDefinition.setParaType(paramType);
        parameterDefinition.setParaIndex(i);
        //判断当前参数是否被 QfRequestBody注解修饰
        QfRequestBody qfRequestBody = p.getAnnotation(QfRequestBody.class);
        if(qfRequestBody != null){
            //如果被修饰了，在parameterDefinition对象中将HaveRequestBodyAnnotation值设置为true
            parameterDefinition.setHaveRequestBodyAnnotation(true);
        }
        return parameterDefinition;
    }

    /**
     * 将fullPaths中的每一个全路径都转换成Class对象
     * @param fullPaths
     * @return
     */
    private List<Class> convertPathToClass(List<String> fullPaths) throws ClassNotFoundException {

        List<Class> classes = new ArrayList<>();
        for (int i = 0; i < fullPaths.size(); i++) {
            Class clazz = Class.forName(fullPaths.get(i));
            classes.add(clazz);
        }
        return classes;
    }

    /**
     * 把realPath代表的路径下的每一个.class文件的文件名获取到
     * 把.class去掉
     * 与basePackage拼成全路径
     * @param basePackage
     * @param realPath
     * @return
     */
    private List<String> getFullPath(String basePackage, String realPath) {
        //创建代表realPath路径的File对象
        File file = new File(realPath);
        List<String> fullPaths = getAllFullPath(file,basePackage);
        return fullPaths;
    }

    /**
     * 获取指定目录下的所有文件并解析成  类的全路径格式
     * 包括子文件夹中的文件
     * @param file
     * @param basePackage
     * @return
     */
    List<String> fullPaths = new ArrayList<>();
    private List<String> getAllFullPath(File file, String basePackage) {
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isFile()){
                /*
                getAbsolutePath是该文件的绝对路径
                然后将绝对路径中的 \ 替换为 .
                从绝对路径中以basePackage的内容为起点开始截取，一直截取到.结束
                那么就得到了每一个类的全路径
                 */
                String absolutePath = f.getAbsolutePath();
                absolutePath = absolutePath.replaceAll("\\\\", "\\.");
                int beginIndex = absolutePath.indexOf(basePackage);
                int endIndex = absolutePath.lastIndexOf(".");
                String fullPas = absolutePath.substring(beginIndex, endIndex);
                fullPaths.add(fullPas);

            }else{
                getAllFullPath(f, basePackage);
            }
        }
        return fullPaths;
    }

    /**
     * 根据提供的basePackage 来拼成指定Java类的全路径
     * @param basePackage
     * @param sce
     * @return
     */
    private String getRealPath(String basePackage, ServletContextEvent sce) {
        /*
        servletContext.getRealPath("/") 获取部署到服务器中的项目的真实路径
        例如：本项目拿到的就是 C:\java2006分享\day16\代码\cd2006MeetingSystem\target\cd2006MeetingSystem-1.0-SNAPSHOT\
         */
        String realPath = sce.getServletContext().getRealPath("/");
        /*
         拼接realPath
         拼接的是项目下真实存放.class文件的目录
         */
        realPath += "WEB-INF\\classes\\";
        /*
        为realPath拼接basePackage
        拼之前需要将basePackage中的 . 替换为  \
         */
        realPath += basePackage.replaceAll("\\.", "\\\\");

        return realPath;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
