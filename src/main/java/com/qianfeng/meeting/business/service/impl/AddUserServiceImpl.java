package com.qianfeng.meeting.business.service.impl;

import com.qianfeng.meeting.business.dao.AddUserDao;
import com.qianfeng.meeting.business.dao.impl.AddUserDaoImpl;
import com.qianfeng.meeting.business.pojo.Delegation;
import com.qianfeng.meeting.business.pojo.Staff;
import com.qianfeng.meeting.business.pojo.User;
import com.qianfeng.meeting.business.service.AddUserService;
import com.qianfeng.meeting.business.utils.QfDbUtils;
import com.qianfeng.meeting.business.vo.AddUserVo;
import com.qianfeng.meeting.business.vo.UserVo;

import java.util.List;


public class AddUserServiceImpl implements AddUserService{

    private AddUserDao addUserDao = new AddUserDaoImpl();

    @Override
    public void addUser(AddUserVo addUserVo) throws Exception {
        try {
            //开启事物
            QfDbUtils.beginTransaction();
            //添加用户
            User user = new User();
            user.setUserName(addUserVo.getUserName());
            user.setPassword(addUserVo.getPassword());
            user.setUserRealName(addUserVo.getUserRealName());
            user.setGender(addUserVo.getGender());
            user.setTelPhone(addUserVo.getTelPhone());
            addUserDao.addUser(user);
            User u = addUserDao.finUserByUserName(user.getUserName());
            Integer uId = u.getUId();

            //添加用户和职位的关联关系
            Integer staffId = addUserVo.getStaffId();
            addUser2StaffRelation(uId,staffId,addUserVo);

            //确认事物
            QfDbUtils.commitTransaction();
        }catch (Exception e){
            //回滚事物
            QfDbUtils.rollbackTransaction();
            throw new Exception(e);
        }
    }


    @Override
    public List<Staff> findAllStaff() throws Exception {
        return addUserDao.findAllStaff();
    }

    @Override
    public List<Delegation> findAllDeputation(Integer staffId) throws Exception {
        return addUserDao.findAllDeputation(staffId);
    }

    @Override
    public List<AddUserVo> checkUserStaff() throws Exception {
        return addUserDao.checkUserStaff();
    }

    @Override
    public void deleteUserVo(Integer userId) throws Exception {
        addUserDao.deleteUserVo(userId);
    }

    @Override
    public AddUserVo checkUserStaffByuId(Integer uId) throws Exception {
        System.out.println(addUserDao.checkUserStaffByuId(uId));
        return addUserDao.checkUserStaffByuId(uId);

    }

    @Override
    public void updateUser(AddUserVo addUserVo) throws Exception {
        addUserDao.updateUser(addUserVo);
    }


    //添加用户和职位的关联关系
    private void addUser2StaffRelation(Integer uId, Integer staffId ,AddUserVo addUserVo) throws Exception{
        addUserDao.addUser2StaffRelation(uId, staffId);
    }
}
