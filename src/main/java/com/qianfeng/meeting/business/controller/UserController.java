package com.qianfeng.meeting.business.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianfeng.meeting.business.exception.BusinessException;
import com.qianfeng.meeting.business.pojo.User;
import com.qianfeng.meeting.business.response.DataResult;
import com.qianfeng.meeting.business.response.ResponseCode;
import com.qianfeng.meeting.business.service.UserService;
import com.qianfeng.meeting.business.service.impl.UserServiceImpl;
import com.qianfeng.meeting.business.utils.VerifyCode;
import com.qianfeng.meeting.business.vo.UserVo;
import com.qianfeng.meeting.framework.annotation.QfController;
import com.qianfeng.meeting.framework.annotation.QfRequestBody;
import com.qianfeng.meeting.framework.annotation.QfRequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;

@QfController("/users")
public class UserController {

    private UserService userService = new UserServiceImpl();

    @QfRequestMapping("/verify")
    public void verify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        //图片的内容应该存到session
        req.getSession().setAttribute("code", text);
        //响应图片
        VerifyCode.output(image, resp.getOutputStream());
    }

    @QfRequestMapping("/login")
    public DataResult<User> login(@QfRequestBody UserVo userVo, HttpServletRequest req){
        DataResult<User> dataResult = null;

        String userName = userVo.getUserName();
        String password = userVo.getPassword();
        String inputCode = userVo.getCode();


        String code = (String)req.getSession().getAttribute("code");


        if(!inputCode.toUpperCase().equals(code.toUpperCase())){
            dataResult = new DataResult<>(ResponseCode.VERIFYERROR);
            return dataResult;
        }

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setLastLoginTime(new Date());//设置用户登录时间
        try {
            User u = userService.login(user);
            dataResult = new DataResult<>(u);
        } catch (Exception e) {
            if(e instanceof BusinessException){
                BusinessException be = (BusinessException)e;
                /*
                ResponseCode.values()
                取出该枚举中所有的值
                 */
                for(ResponseCode rc : ResponseCode.values()){
                    if(rc.getCode() == be.getCode()){
                       dataResult = new DataResult<>(rc);
                    }
                }

            }else if (e instanceof SQLException){
                dataResult = new DataResult<>(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult<>(ResponseCode.SYSTEMEXCEPTION);
            }

        }

        return dataResult;
    }

    @QfRequestMapping("/logout")
    public DataResult logout(HttpServletRequest request, HttpServletResponse resp){
        DataResult dataResult = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("userInfo")){
                    //取出名称为userInfo的cookie的内容
                    String value = cookie.getValue();
                    try {
                        //该内容不能直接使用，因为该内容被URL编码了，使用之前需要URL解码
                        value = URLDecoder.decode(value, "UTF-8");
                        //value是符合json格式的字符串。将它转换成java对象
                        ObjectMapper objectMapper = new ObjectMapper();
                        //将cookie的内容转换成DataResult对象
                        DataResult dr = (DataResult) objectMapper.readValue(value, DataResult.class);
                        //取出DataResult中的属性t
                        Object t = dr.getT();
                        //t的类型是LinkedHashMap,所以将t强转为LinkedHashMap
                        LinkedHashMap map = (LinkedHashMap)t;
                        //取出该map中的key为userName的值
                        Object userName = map.get("userName");
                        //从数据库中取出指定userName的User对象。 该对象就一定是我们存在cookie中的对象
                        User user = userService.findUserByUserName((String)userName);
                        // 调用业务层的退出登录功能
                        userService.logout(user);
                        //将cookie清除
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);

                        dataResult = new DataResult();
                    } catch (SQLException e) {
                        dataResult = new DataResult((ResponseCode.SQLEXCEPTION));
                    } catch (JsonMappingException e) {
                        dataResult = new DataResult(ResponseCode.JSONERROR);
                    } catch (JsonProcessingException e) {
                        dataResult = new DataResult(ResponseCode.JSONERROR);
                    } catch (Exception e) {
                        dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
                    }
                }
            }
        }
        return dataResult;
    }

    /**
     * 验证密码是否正确
     * @param user
     * @return
     */
    @QfRequestMapping("/verifyPwd")
    public DataResult verifyPassword(@QfRequestBody User user){
        DataResult dataResult = null;
        try {
            userService.verifyPassword(user);
            dataResult = new DataResult();
        }catch (Exception e){
            //异常有可能是 业务异常 ，还有可能是 SQL异常，或者其他异常
            if(e instanceof BusinessException){
                BusinessException be = (BusinessException)e;
                for(ResponseCode rc : ResponseCode.values()){
                    if(rc.getCode() == be.getCode()){
                        dataResult = new DataResult(rc);
                    }
                }
            }else if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/updatePwd")
    public DataResult updatePwd(@QfRequestBody User user){
        DataResult dataResult = null;
        try {
            userService.updatePwd(user);
            dataResult = new DataResult();
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);

            }
        }
        return dataResult;
    }

    @QfRequestMapping("/updateUser")
    public DataResult updateUser(@QfRequestBody User user){
        DataResult dataResult = null;
        try {
            userService.updateUser(user);
            dataResult = new DataResult();
        } catch (Exception e) {
            if (e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }

        }
        return dataResult;
    }

    @QfRequestMapping("/sendEmail")
    public DataResult sendEmail(@QfRequestBody User user){
        DataResult dataResult = null;
        try {
            userService.sendEmail(user.getUserRealName());
            dataResult = new DataResult();
        }catch (Exception e){
            dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
        }
        return  dataResult;
    }
}
