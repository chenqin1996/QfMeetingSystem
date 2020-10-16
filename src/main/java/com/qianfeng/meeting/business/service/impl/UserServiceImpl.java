package com.qianfeng.meeting.business.service.impl;

import com.qianfeng.meeting.business.dao.UserDao;
import com.qianfeng.meeting.business.dao.impl.UserDaoImpl;
import com.qianfeng.meeting.business.exception.BusinessException;
import com.qianfeng.meeting.business.pojo.User;
import com.qianfeng.meeting.business.response.ResponseCode;
import com.qianfeng.meeting.business.service.UserService;
import com.qianfeng.meeting.business.utils.CommonsUtils;
import com.qianfeng.meeting.business.utils.MailUtils;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * 先查找用户名是否存在
     * 再比较密码是否相等
     *
     * 登录成功后，为用户添加一个token
     * @return
     */
    public User login(User loginUser) throws Exception {
        User user = userDao.findUserByUserName(loginUser.getUserName());
        if(user == null){
            throw new BusinessException(ResponseCode.USERNAMEERROR);
        }
        if(!loginUser.getPassword().equals(user.getPassword())){
            throw new BusinessException(ResponseCode.PASSWORDERROR);
        }

        /*
        给用户一个令牌
        使用uuid作为令牌
         */
        String token = CommonsUtils.getUUID();
        user.setToken(token);
        //跟新最后登录时间
        user.setLastLoginTime(loginUser.getLastLoginTime());
        userDao.update(user);
        return user;
    }

    /**
     * 退出登录
     * 把用户token字段清空
     * @param logoutUser
     * @throws Exception
     */
    public void logout(User logoutUser) throws Exception {
        logoutUser.setToken("");
        userDao.update(logoutUser);
    }

    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    public User findUserByUserName(String userName) throws Exception {
        return userDao.findUserByUserName(userName);
    }

    /**
     * 验证指定用户名对应的密码是否正确
     * @param user
     * @throws Exception
     */
    public void verifyPassword(User user) throws Exception {
        User u = userDao.findUserByUserName(user.getUserName());
        if(!u.getPassword().equals(user.getPassword())){
            throw new BusinessException(ResponseCode.PASSWORDERROR);
        }
    }

    /**
     * 更新密码
     * @param user
     * @throws Exception
     */
    public void updatePwd(User user) throws Exception {
        userDao.updatePwd(user);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void updateUser(User user) throws Exception{
        User u = userDao.findUserByUserUid(user.getUId());
        u.setUserRealName(user.getUserRealName());
        u.setUserName(user.getUserName());
        u.setGender(user.getGender());
        u.setTelPhone(user.getTelPhone());
        u.setLastLoginTime(user.getLastLoginTime());
        userDao.update(u);
    }

    /**
     * 向运维的邮箱发送邮件
     * @param userRealName
     */
    public void sendEmail(String userRealName) {
        String to = "1025226466@qq.com";
        String text = "千锋：" + userRealName +"请你喝茶！";
        String title = "千锋会议系统呼叫运维";

        MailUtils.sendMail(to,text,title);
    }
}
