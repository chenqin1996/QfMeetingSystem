package com.qianfeng.meeting.business.dao;

import com.qianfeng.meeting.business.pojo.Role;

import java.util.List;


public interface RoleDao {
    void addRole(Role role) throws Exception;
    Role findRoleByRoleName(String roleName) throws Exception;
    void addRole2PermRelation(int roleId, int permId) throws Exception;

    List<Role> findAllRole() throws Exception;

    void deleteRole(Integer roleId) throws Exception;
}
