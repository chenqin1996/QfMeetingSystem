package com.qianfeng.meeting.business.service;

import com.qianfeng.meeting.business.pojo.Delegation;

import java.util.List;

public interface DelegationService {
    void addDelegation(Delegation delegation) throws Exception;

    List<Delegation> findAllDelegations() throws Exception;

    List<Delegation> findAllDelegationsByStaffId(Integer staffId) throws Exception;

    void deleteDelegation(Integer delegationId) throws Exception;
}
