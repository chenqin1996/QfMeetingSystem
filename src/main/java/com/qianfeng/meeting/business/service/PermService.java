package com.qianfeng.meeting.business.service;

import com.qianfeng.meeting.business.pojo.Permission;

import java.util.List;


public interface PermService {
    void addPerm(Permission permission) throws Exception;

    List<Permission> findAllPerms() throws Exception;

    void deletePerm(Integer perId) throws Exception;
}
