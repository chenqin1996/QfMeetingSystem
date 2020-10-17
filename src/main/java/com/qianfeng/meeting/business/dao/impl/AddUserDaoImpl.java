package com.qianfeng.meeting.business.dao.impl;

import com.qianfeng.meeting.business.dao.AddUserDao;
import com.qianfeng.meeting.business.pojo.Delegation;
import com.qianfeng.meeting.business.pojo.Staff;
import com.qianfeng.meeting.business.pojo.User;
import com.qianfeng.meeting.business.utils.QfDbUtils;
import com.qianfeng.meeting.business.vo.AddUserVo;
import com.qianfeng.meeting.business.vo.UserVo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import sun.awt.datatransfer.DataTransferer;

import javax.xml.ws.handler.Handler;
import java.sql.Connection;
import java.util.List;


public class AddUserDaoImpl implements AddUserDao{
    private QueryRunner qr = QfDbUtils.getQr();

    @Override
    public void addUser(User user) throws Exception {
        Connection conn = QfDbUtils.getConnection();
        String sql = "insert into t_user(userName,password,userRealName,gender,telPhone) values(?,?,?,?,?)";
        Object[] pasams = {user.getUserName(),user.getPassword(),user.getUserRealName(),user.getGender(),user.getTelPhone()};
        qr.update(conn,sql,pasams);
    }

    @Override
    public User finUserByUserName(String userName) throws Exception {
        Connection conn = QfDbUtils.getConnection();
        String sql = "SELECT * FROM t_user WHERE userName=?";
        Object[] params = {userName};
        User user = qr.query(conn, sql, new BeanHandler<User>(User.class), params);
        return user;
    }


    @Override
    public void addUser2StaffRelation(Integer uId, Integer staffId) throws Exception {
        Connection conn = QfDbUtils.getConnection();
        String sql = "INSERT INTO t_user_staff VALUES(?,?)";
        Object[] params = {uId, staffId};
        qr.update(conn,sql,params);
    }

    @Override
    public List<Staff> findAllStaff() throws Exception {
        String sql = "select * from t_staff";
        List<Staff> staffList = qr.query(sql, new BeanListHandler<Staff>(Staff.class));
        return staffList;
    }

    @Override
    public List<Delegation> findAllDeputation(Integer staffId) throws Exception {
        String sql = "select * from t_staff where staffId=?";
        Object[] params = {staffId};
        Staff staff = qr.query(sql, new BeanHandler<Staff>(Staff.class),params);
        String sql2 = "select * from t_delegation where delegationId=?";
        Object[] params2 = {staff.getDelegationId()};
        List<Delegation> delegations = qr.query(sql2, new BeanListHandler<Delegation>(Delegation.class),params2);
        return delegations;
    }

    @Override
    public List<AddUserVo> checkUserStaff() throws Exception {
        String sql="SELECT\n" +
                "\tt_user.uId uid,\n" +
                "\tt_user.userName userName,\n" +
                "\tt_user.userRealName userRealName,\n" +
                "\tt_user.gender gender,\n" +
                "\tt_user.telPhone telPhone,\n" +
                "\tt_staff.staffName staffName,\n" +
                "\tt_delegation.delegationName delegationName\n" +
                "FROM\n" +
                "\tt_user\n" +
                "\tINNER JOIN t_user_staff ON t_user.uid = t_user_staff.uId\n" +
                "\tINNER JOIN t_staff ON t_user_staff.staffId = t_staff.staffId\n" +
                "\tINNER JOIN t_delegation ON t_staff.delegationId = t_delegation.delegationId order by uid";

        List<AddUserVo> userVoList = qr.query(sql, new BeanListHandler<AddUserVo>(AddUserVo.class));
        return userVoList;
    }

    @Override
    public void deleteUserVo(Integer userId) throws Exception {
        String sql ="delete from t_user_staff where uId=?";
        Object[] params = {userId};
        qr.update(sql, params);
        String sql2 = "delete from t_user where uId=?";
        Object[] params2 = {userId};
        qr.update(sql2, params2);

    }

    @Override
    public AddUserVo checkUserStaffByuId(Integer uId) throws Exception{
        String sql ="SELECT\n" +
                "\tt_user.uId uid,\n" +
                "\tt_user.userName userName,\n" +
                "\tt_user.userRealName userRealName,\n" +
                "\tt_user.gender gender,\n" +
                "\tt_user.telPhone telPhone,\n" +
                "\tt_staff.staffName staffName,\n" +
                "\tt_staff.`staffId` staffId,\n" +
                "\tt_delegation.delegationName delegationName,\n" +
                "\tt_delegation.`delegationId` delegationId\n" +
                "FROM\n" +
                "\tt_user\n" +
                "\tINNER JOIN t_user_staff ON t_user.uid = t_user_staff.uId\n" +
                "\tINNER JOIN t_staff ON t_user_staff.staffId = t_staff.staffId\n" +
                "\tINNER JOIN t_delegation ON t_staff.delegationId = t_delegation.delegationId\n" +
                "WHERE t_user.uId=?\t";
        Object[] params = {uId};
        AddUserVo userVo = qr.query(sql, new BeanHandler<AddUserVo>(AddUserVo.class), params);
        return userVo;
    }

    @Override
    public void updateUser(AddUserVo addUserVo) throws Exception {
        String sql = "update t_user set userRealName=?,telPhone=?,gender=? where uId=?";
        Object[] params = {addUserVo.getUserRealName(),addUserVo.getTelPhone(),addUserVo.getGender(),addUserVo.getUId()};
        qr.update(sql,params);
        String sql2="update t_user_staff set staffId=? where uId=?";
        Object[] params2 = {addUserVo.getStaffId(), addUserVo.getUId()};
        qr.update(sql2, params2);
    }
}
