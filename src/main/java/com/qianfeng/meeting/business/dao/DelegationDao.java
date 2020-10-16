package com.qianfeng.meeting.business.dao;

import com.qianfeng.meeting.business.pojo.Delegation;

import java.sql.SQLException;
import java.util.List;

public interface DelegationDao {
    void addDelegation(Delegation delegation) throws Exception;

    List<Delegation> findAllDelegations() throws Exception;


    void deleteDelegation(Integer delegationId) throws Exception;


    List<Delegation> findAllDelegationsByStaffId(Integer staffId) throws SQLException;
}
