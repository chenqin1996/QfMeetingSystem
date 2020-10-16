package com.qianfeng.meeting.business.service.impl;

import com.qianfeng.meeting.business.dao.PermDao;
import com.qianfeng.meeting.business.dao.impl.PermDaoImpl;
import com.qianfeng.meeting.business.pojo.Permission;
import com.qianfeng.meeting.business.service.PermService;

import java.util.List;

/**
 * @author Allen
 * @date 2020/10/13
 **/
public class PermServiceImpl implements PermService {
    private PermDao permDao = new PermDaoImpl();

    /**
     * 添加权限
     * @param permission
     * @throws Exception
     */
    public void addPerm(Permission permission) throws Exception {
        permDao.addPerm(permission);
    }

    @Override
    public List<Permission> findAllPerms() throws Exception {
        return permDao.findAllPerms();
    }

    @Override
    public void deletePerm(Integer perId) throws Exception {
        permDao.deletePerm(perId);
    }
}
