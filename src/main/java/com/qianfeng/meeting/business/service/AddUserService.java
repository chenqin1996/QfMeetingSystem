package com.qianfeng.meeting.business.service;

import com.qianfeng.meeting.business.pojo.Delegation;
import com.qianfeng.meeting.business.pojo.Staff;
import com.qianfeng.meeting.business.pojo.User;
import com.qianfeng.meeting.business.vo.AddUserVo;
import com.qianfeng.meeting.business.vo.UserVo;

import java.util.List;

/**
 * @author Allen
 * @date 2020/10/14
 **/
public interface AddUserService {
    void addUser(AddUserVo addUserVo) throws Exception;

    List<Staff> findAllStaff() throws Exception;

    List<Delegation> findAllDeputation(Integer staffId) throws Exception;

    List<AddUserVo> checkUserStaff() throws Exception;

    void deleteUserVo(Integer userId) throws Exception;


    AddUserVo checkUserStaffByuId(Integer uId) throws Exception;

    void updateUser(AddUserVo addUserVo) throws Exception;
}
