package com.qianfeng.meeting.business.vo;

import lombok.Data;

/**
 * @author Allen
 * @date 2020/10/14
 **/
@Data
public class AddUserVo {
    private Integer uId;
    private String userName;
    private String password;
    private String userRealName;
    private Integer gender;
    private String telPhone;
    private Integer staffId;
    private String staffName;
    private Integer delegationId;
    private String delegationName;
}
