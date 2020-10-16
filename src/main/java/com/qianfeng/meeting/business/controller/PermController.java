package com.qianfeng.meeting.business.controller;

import com.qianfeng.meeting.business.pojo.Permission;
import com.qianfeng.meeting.business.response.DataResult;
import com.qianfeng.meeting.business.response.ResponseCode;
import com.qianfeng.meeting.business.service.PermService;
import com.qianfeng.meeting.business.service.impl.PermServiceImpl;
import com.qianfeng.meeting.framework.annotation.QfController;
import com.qianfeng.meeting.framework.annotation.QfRequestBody;
import com.qianfeng.meeting.framework.annotation.QfRequestMapping;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Allen
 * @date 2020/10/13
 **/
@QfController("/perms")
public class PermController {
    private PermService permService = new PermServiceImpl();

    @QfRequestMapping("/addPerm")
    public DataResult addPerm(@QfRequestBody Permission permission){
        DataResult dataResult = null;
        try {
            permService.addPerm(permission);
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

    @QfRequestMapping("/findAllPerms")
    public DataResult findAllPerms(){
        DataResult dataResult = null;
        try{
            List<Permission> permList = permService.findAllPerms();
            dataResult = new DataResult(permList);
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/deletePerm")
    public DataResult deletePerm(Integer perId){
        DataResult dataResult = null;
        try {
            permService.deletePerm(perId);
            dataResult = new DataResult();
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

}
