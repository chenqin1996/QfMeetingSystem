package com.qianfeng.meeting.business.dao.impl;

import com.qianfeng.meeting.business.dao.DelegationDao;
import com.qianfeng.meeting.business.pojo.Delegation;
import com.qianfeng.meeting.business.pojo.Staff;
import com.qianfeng.meeting.business.utils.QfDbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class DelegationDaoImpl implements DelegationDao {
    private QueryRunner qr = QfDbUtils.getQr();
    @Override
    public void addDelegation(Delegation delegation) throws Exception {
        String sql = "insert into t_delegation values(?,?,?)";
        Object[] params = {null,delegation.getDelegationName(),delegation.getDelegationDes()};
        qr.update(sql,params);
    }

    @Override
    public List<Delegation> findAllDelegations() throws Exception {
        String sql = "select * from t_delegation";
        List<Delegation> delegations = qr.query(sql,new BeanListHandler<Delegation>(Delegation.class));
        return delegations;
    }

    @Override
    public void deleteDelegation(Integer delegationId) throws Exception {
        String sql= "delete from t_delegation where delegationId=?";
        Object[] params = {delegationId};
        qr.update(sql,params);
    }

    @Override
    public List<Delegation> findAllDelegationsByStaffId(Integer staffId) throws SQLException {
        String sql = "select * from t_staff where staffId=?";
        Object[] params = {staffId};
        Staff staff = qr.query(sql, new BeanHandler<Staff>(Staff.class),params);
        String sql2 = "select * from t_delegation where delegationId=?";
        Object[] params2 = {staff.getDelegationId()};
        List<Delegation> delegations = qr.query(sql2, new BeanListHandler<Delegation>(Delegation.class),params2);
        return delegations;
    }
}
