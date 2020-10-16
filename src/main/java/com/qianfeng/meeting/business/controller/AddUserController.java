package com.qianfeng.meeting.business.controller;

import com.qianfeng.meeting.business.pojo.Delegation;
import com.qianfeng.meeting.business.pojo.Staff;
import com.qianfeng.meeting.business.pojo.User;
import com.qianfeng.meeting.business.response.DataResult;
import com.qianfeng.meeting.business.response.ResponseCode;
import com.qianfeng.meeting.business.service.AddUserService;
import com.qianfeng.meeting.business.service.impl.AddUserServiceImpl;
import com.qianfeng.meeting.business.vo.AddUserVo;
import com.qianfeng.meeting.business.vo.UserVo;
import com.qianfeng.meeting.framework.annotation.QfController;
import com.qianfeng.meeting.framework.annotation.QfRequestBody;
import com.qianfeng.meeting.framework.annotation.QfRequestMapping;
import org.omg.CORBA.Request;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;


@QfController("/addUsers")
public class AddUserController {
private AddUserService addUserService = new AddUserServiceImpl();

    @QfRequestMapping("/addUser")
    public DataResult addUser(@QfRequestBody AddUserVo addUserVo){
        DataResult dataResult = null;
        try {
            addUserService.addUser(addUserVo);
            dataResult = new DataResult();
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/findAllStaff")
    public DataResult findAllStaff(){
        DataResult dataResult = null;
        try {
            List<Staff> staffList = addUserService.findAllStaff();
            dataResult = new DataResult(staffList);
        }catch (Exception e){
            if (e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/findAllDeputation")
    public DataResult findAllDeputation(@QfRequestBody Staff staff){
        DataResult dataResult = null;
        try {
            List<Delegation> deputationList = addUserService.findAllDeputation(staff.getStaffId());
            dataResult = new DataResult(deputationList);
        }catch (Exception e){
            if (e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else {
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/checkUserStaff")
    public DataResult checkUserStaff(){
        DataResult dataResult=null;
        try{
            List<AddUserVo> userVoList = addUserService.checkUserStaff();
            dataResult=new DataResult(userVoList);
        }catch (Exception e){
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/checkUserStaffByuId")
    public DataResult checkUserStaffByuId(@QfRequestBody AddUserVo userId, HttpServletRequest req){
        DataResult dataResult=null;
        try{
            AddUserVo userVo = addUserService.checkUserStaffByuId(userId.getUId());
            req.getSession().setAttribute("userVo",userVo);
            dataResult=new DataResult();
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }

    @QfRequestMapping("/checkUserStaffByuId2")
    public DataResult checkUserStaffByuId2(@QfRequestBody HttpServletRequest req){
        DataResult dataResult=null;
        try{
            AddUserVo userVo = (AddUserVo) req.getSession().getAttribute("userVo");
            dataResult=new DataResult(userVo);
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof SQLException){
                dataResult = new DataResult(ResponseCode.SQLEXCEPTION);
            }else{
                dataResult = new DataResult(ResponseCode.SYSTEMEXCEPTION);
            }
        }
        return dataResult;
    }



    @QfRequestMapping("/updateUser")
    public DataResult updateUser(@QfRequestBody AddUserVo addUserVo){
        DataResult dataResult = null;
        try{
            addUserService.updateUser(addUserVo);
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

    @QfRequestMapping("/deleteUserVo")
    public DataResult deleteUserVo(Integer userId){
        DataResult dataResult = null;
        try {
            addUserService.deleteUserVo(userId);
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
