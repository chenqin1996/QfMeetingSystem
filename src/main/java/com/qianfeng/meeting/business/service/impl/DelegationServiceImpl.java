package com.qianfeng.meeting.business.service.impl;

import com.qianfeng.meeting.business.dao.DelegationDao;
import com.qianfeng.meeting.business.dao.impl.DelegationDaoImpl;
import com.qianfeng.meeting.business.pojo.Delegation;
import com.qianfeng.meeting.business.service.DelegationService;

import java.util.List;

public class DelegationServiceImpl implements DelegationService {
    DelegationDao delegationDao = new DelegationDaoImpl();
    @Override
    public void addDelegation(Delegation delegation) throws Exception {
        delegationDao.addDelegation(delegation);
    }

    @Override
    public List<Delegation> findAllDelegations() throws Exception {
        return delegationDao.findAllDelegations();
    }

    @Override
    public List<Delegation> findAllDelegationsByStaffId(Integer staffId) throws Exception {
        return delegationDao.findAllDelegationsByStaffId(staffId);
    }


    @Override
    public void deleteDelegation(Integer delegationId) throws Exception {
        delegationDao.deleteDelegation(delegationId);
    }

}
