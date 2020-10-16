package com.qianfeng.meeting.business.dao.impl;

import com.qianfeng.meeting.business.dao.UserDao;

import com.qianfeng.meeting.business.pojo.User;
import com.qianfeng.meeting.business.utils.QfDbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private QueryRunner qr = QfDbUtils.getQr();

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     * @throws SQLException
     */
    public User findUserByUserName(String userName) throws SQLException {
        String sql = "SELECT * FROM t_user WHERE userName=?";
        Object[] params = {userName};
        User user = qr.query(sql, new BeanHandler<User>(User.class), params);
        return user;
    }

    /**
     * 更新用户数据
     * @param user
     */
    public void update(User user) throws SQLException {
        String sql = "UPDATE t_user SET userName=?,telPhone=?,password=?," +
                "photo=?,userRealName=?,token=?,expireTime=?,createTime=?,gender=?,lastLoginTime=? WHERE uId=?";
        Object[] params = {user.getUserName(), user.getTelPhone(), user.getPassword(), user.getPhoto(),
        user.getUserRealName(),user.getToken(),user.getExpireTime(),user.getCreateTime(),user.getGender(),user.getLastLoginTime(),user.getUId()};

        qr.update(sql, params);

    }

    /*
        更新指定用户名对应的密码
     */
    public void updatePwd(User user) throws Exception {
        String sql = "UPDATE t_user SET password=? WHERE userName=?";
        Object[] params = {user.getPassword(), user.getUserName()};
        qr.update(sql, params);
    }

    @Override
    public User findUserByUserUid(Integer uId) throws Exception {
        String sql ="select * from t_user where uId=?";
        Object[] params ={uId};
        User user = qr.query(sql, new BeanHandler<User>(User.class),params);
        return user;
    }
}
