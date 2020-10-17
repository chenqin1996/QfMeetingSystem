package com.qianfeng.meeting.business.service.impl;


import com.qianfeng.meeting.business.dao.RoleDao;
import com.qianfeng.meeting.business.dao.impl.RoleDaoImpl;
import com.qianfeng.meeting.business.pojo.Role;
import com.qianfeng.meeting.business.service.RoleService;
import com.qianfeng.meeting.business.utils.QfDbUtils;
import com.qianfeng.meeting.business.vo.RoleVo;

import java.util.List;


public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao = new RoleDaoImpl();

    @Override
    public void addRole(RoleVo roleVo) throws Exception {
        try {
            //开启事物
            QfDbUtils.beginTransaction();
            //添加角色
            Role role = new Role();
            role.setRoleName(roleVo.getRoleName());
            role.setRoleDescription(roleVo.getRoleDescription());
            roleDao.addRole(role);
            //添加角色与权限的关联关系
            Role r = roleDao.findRoleByRoleName(role.getRoleName());
            int roleId = r.getRoleId();//获取角色id
            String[] permIds = roleVo.getPermIds();//获取所有的权限id
            addRole2PermRelation(roleId, permIds);//添加角色与权限的关联关系
            //确认事物
            QfDbUtils.commitTransaction();
        }catch (Exception e){
            //回滚事物
            QfDbUtils.rollbackTransaction();
            throw new Exception(e);
        }
    }

    //添加角色与权限的关联关系
    private void addRole2PermRelation(int roleId, String[] permIds) throws Exception {
        for(String permId : permIds){
            roleDao.addRole2PermRelation(roleId, Integer.parseInt(permId));
        }
    }

    @Override
    public List<Role> findAllRole() throws Exception {
        return roleDao.findAllRole();
    }

    @Override
    public void deleteRole(Integer roleId) throws Exception {
        roleDao.deleteRole(roleId);
    }
}
