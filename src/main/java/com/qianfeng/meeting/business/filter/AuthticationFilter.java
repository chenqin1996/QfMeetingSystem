package com.qianfeng.meeting.business.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 定义过滤器
 放行的规则：
 * 静态资源
 * controller相关的方法-->验证码、登录
 * html-->login.html
 * 其他资源：登录了就放行，否则重定向到登录界面
 **/
public class AuthticationFilter implements Filter {
    //定义静态资源
    private String[] staticResource = {".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".ico", "/login.html"};
    //定义可被放行的controller资源
    private String[] controllerResourceNoIntercept = {"/users/verify", "/users/login"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /*
    定义过滤的规则
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //1.将request、response强转为HttpXxx
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        //2.获取用户访问的资源路径
        String uri = req.getRequestURI();
        //判断是否是get请求，是就截取？号前面的资源
        if (uri.contains("?")){
            uri = uri.substring(0, uri.indexOf("?"));
        }

        //3.判断访问的资源是否是静态资源，如果是，则放行
        Boolean result1 = isRequestStaticResource(uri);
        if (result1){
            chain.doFilter(request,response);
            return;
        }

        //4.判断访问的资源是否是与controller相关的可放行资源
        Boolean result2 = isControllerResourceNoIntercept(uri);
        if (result2){
            chain.doFilter(request,response);
            return;
        }

        /*
        如果上面两个if都没有执行，则表示访问的资源是不可直接放行的资源
        需要判断是否已登录，如果登录则放行，否则重定向
         */
        //判断是否是登录状态
        Cookie[] cookies = req.getCookies();
        Boolean result3 = isLogin(cookies);
        if (result3){
            chain.doFilter(req,resp);
        }else {
            resp.sendRedirect("/html/login.html");
        }
    }

    /**
     * 判断是否是登陆状态，从cookie里面取值
     * @param cookies
     * @return
     */
    private Boolean isLogin(Cookie[] cookies) {
        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("userInfo")){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是可放行的Controller的资源
     * @param uri
     * @return
     */
    private Boolean isControllerResourceNoIntercept(String uri) {
        for (String resource : controllerResourceNoIntercept){
            if (uri.endsWith(resource)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是静态资源
     * @param uri
     * @return
     */
    private Boolean isRequestStaticResource(String uri) {
        for (String resource : staticResource){
            if (uri.endsWith(resource)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
