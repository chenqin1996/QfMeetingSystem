package com.qianfeng.meeting.business.dao;

import com.qianfeng.meeting.business.pojo.Permission;

import java.util.List;

/**
 * @author Allen
 * @date 2020/10/13
 **/
public interface PermDao {
    void addPerm(Permission permission) throws Exception;

    List<Permission> findAllPerms() throws Exception;

    void deletePerm(Integer perId) throws Exception;
}
