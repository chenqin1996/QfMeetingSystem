package com.qianfeng.meeting.business.controller;

import com.qianfeng.meeting.business.pojo.Role;
import com.qianfeng.meeting.business.response.DataResult;
import com.qianfeng.meeting.business.response.ResponseCode;
import com.qianfeng.meeting.business.service.RoleService;
import com.qianfeng.meeting.business.service.impl.RoleServiceImpl;
import com.qianfeng.meeting.business.vo.RoleVo;
import com.qianfeng.meeting.framework.annotation.QfController;
import com.qianfeng.meeting.framework.annotation.QfRequestBody;
import com.qianfeng.meeting.framework.annotation.QfRequestMapping;

import java.sql.SQLException;
import java.util.List;


@QfController("/roles")
public class RoleController {
    private RoleService roleService = new RoleServiceImpl();

    @QfRequestMapping("/addRole")
    public DataResult addRole(@QfRequestBody RoleVo roleVo){
        DataResult dataResult = null;
        try{
            roleService.addRole(roleVo);
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

    @QfRequestMapping("/findAllRole")
    public DataResult findAllRole(){
        DataResult dataResult = null;
        try{
            List<Role> roleList = roleService.findAllRole();
            dataResult = new DataResult(roleList);
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/deleteRole")
    public DataResult deletePerm(Integer roleId){
        DataResult dataResult = null;
        try {
            roleService.deleteRole(roleId);
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
