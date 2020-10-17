package com.qianfeng.meeting.business.dao.impl;

import com.qianfeng.meeting.business.dao.PermDao;
import com.qianfeng.meeting.business.pojo.Permission;
import com.qianfeng.meeting.business.utils.QfDbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;


public class PermDaoImpl implements PermDao {
    private QueryRunner qr = QfDbUtils.getQr();
    /**
     * 添加权限
     * @param perm
     * @throws Exception
     */
    public void addPerm(Permission perm) throws Exception {
        String sql = "INSERT INTO t_perm VALUES(?,?,?)";
        Object[] params = {null, perm.getPerName(), perm.getPerDes()};
        qr.update(sql, params);
    }

    /**
     * 查询所有权限
     */
    public List<Permission> findAllPerms() throws Exception {
        String sql = "SELECT * FROM t_perm";
        List<Permission> permList = qr.query(sql, new BeanListHandler<Permission>(Permission.class));
        return permList;
    }

    @Override
    public void deletePerm(Integer perId) throws Exception {
        String sql = "delete from t_perm where perId=?";
        Object[] params = {perId};
        qr.update(sql, params);
    }
}
