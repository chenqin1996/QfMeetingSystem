package com.qianfeng.meeting.business.service;

import com.qianfeng.meeting.business.pojo.User;

public interface UserService {
    User login(User loginUser) throws Exception;

    void logout(User logoutUser) throws Exception;

    User findUserByUserName(String userName) throws Exception;

    void verifyPassword(User user) throws Exception;

    void updatePwd(User user) throws Exception;

    void updateUser(User user) throws Exception;

    void sendEmail(String userRealName);
}
