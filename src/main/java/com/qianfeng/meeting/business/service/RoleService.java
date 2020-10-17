package com.qianfeng.meeting.business.service;

import com.qianfeng.meeting.business.pojo.Role;
import com.qianfeng.meeting.business.vo.RoleVo;

import java.util.List;


public interface RoleService {
    void addRole(RoleVo roleVo) throws Exception;

    List<Role> findAllRole() throws Exception;

    void deleteRole(Integer roleId) throws Exception;
}
