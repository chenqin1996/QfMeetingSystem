package com.qianfeng.meeting.business.controller;

import com.qianfeng.meeting.business.pojo.Delegation;
import com.qianfeng.meeting.business.response.DataResult;
import com.qianfeng.meeting.business.response.ResponseCode;
import com.qianfeng.meeting.business.service.DelegationService;
import com.qianfeng.meeting.business.service.impl.DelegationServiceImpl;
import com.qianfeng.meeting.framework.annotation.QfController;
import com.qianfeng.meeting.framework.annotation.QfRequestBody;
import com.qianfeng.meeting.framework.annotation.QfRequestMapping;

import java.sql.SQLException;
import java.util.List;

@QfController("/delegations")
public class DelegationController {
    private DelegationService delegationService = new DelegationServiceImpl();
    @QfRequestMapping("/addDelegation")
    public DataResult addDelegation(@QfRequestBody Delegation delegation){
        DataResult dataResult = null;
        try {
            delegationService.addDelegation(delegation);
            dataResult = new DataResult();
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/findAllDelegations")
    public DataResult findAllDelegations(){
        DataResult dataResult = null;
        try {
            List<Delegation> delegationList = delegationService.findAllDelegations();
            dataResult = new DataResult(delegationList);
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/findAllDelegationsByStaffId")
    public DataResult findAllDelegations(Integer staffId){
        DataResult dataResult = null;
        try {
            List<Delegation> delegationList = delegationService.findAllDelegationsByStaffId(staffId);
            dataResult = new DataResult(delegationList);
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/deleteDelegation")
    public DataResult deleteDelegation(Integer delegationId){
        DataResult dataResult = null;
        try {
            delegationService.deleteDelegation(delegationId);
            dataResult = new DataResult();
        }catch (Exception e){
            if (e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }
}
