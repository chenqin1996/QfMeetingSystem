package com.qianfeng.meeting.business.dao;

import com.qianfeng.meeting.business.pojo.User;

import java.sql.SQLException;

public interface UserDao {
    User findUserByUserName(String userName) throws Exception;

    void update(User user) throws Exception;

    void updatePwd(User user) throws Exception;

    User findUserByUserUid(Integer uId) throws Exception;
}
