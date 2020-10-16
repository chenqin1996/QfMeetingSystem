package com.qianfeng.meeting.business.dao.impl;

import com.qianfeng.meeting.business.dao.RoleDao;
import com.qianfeng.meeting.business.pojo.Role;
import com.qianfeng.meeting.business.utils.QfDbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

/**
 * @author Allen
 * @date 2020/10/13
 **/
public class RoleDaoImpl implements RoleDao {
    private QueryRunner qr = QfDbUtils.getQr();

    @Override
    public void addRole(Role role) throws Exception {
        Connection conn = QfDbUtils.getConnection();
        String sql = "INSERT INTO t_role VALUES(?,?,?)";
        Object[] params = {null, role.getRoleName(), role.getRoleDescription()};
        qr.update(conn,sql, params);
    }

    /**
     * 查询指定名称的角色
     */
    public Role findRoleByRoleName(String roleName) throws Exception {
        Connection conn = QfDbUtils.getConnection();
        String sql = "SELECT * FROM t_role WHERE roleName=?";
        Object[] params = {roleName};
        Role role = qr.query(conn,sql, new BeanHandler<Role>(Role.class), params);
        return role;
    }

    /**
     * 添加角色与权限的关联关系
     */
    public void addRole2PermRelation(int roleId, int permId) throws Exception {
        Connection conn = QfDbUtils.getConnection();
        String sql = "INSERT INTO t_role_perm VALUES(?,?)";
        Object[] params = {roleId, permId};
        qr.update(conn,sql,params);
    }

    @Override
    public List<Role> findAllRole() throws Exception {

        String sql = "SELECT * FROM t_role";
        List<Role> roleList = qr.query(sql, new BeanListHandler<Role>(Role.class));
        return roleList;
    }

    @Override
    public void deleteRole(Integer roleId) throws Exception {

        String sql = "delete from t_role where roleId=?";
        Object[] params = {roleId};
        qr.update(sql, params);
    }
}
