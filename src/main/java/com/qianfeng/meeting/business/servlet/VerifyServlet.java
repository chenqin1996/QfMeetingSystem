package com.qianfeng.meeting.business.servlet;

import com.qianfeng.meeting.business.utils.VerifyCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class VerifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VerifyCode verifyCode = new VerifyCode();
        BufferedImage image = verifyCode.getImage();
        String text = verifyCode.getText();
        req.getSession().setAttribute("code", text);
        VerifyCode.output(image, resp.getOutputStream());
    }
}
