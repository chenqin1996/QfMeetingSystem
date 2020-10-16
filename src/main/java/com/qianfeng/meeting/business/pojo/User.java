package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:32
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1905393547465559547L;
    private Integer uId;   //用户id
    private String userName;  //用户名字
    private String telPhone;  //电话
    private String password;  //密码
    private String photo;    //头像
    private String userRealName;  //名字
    private String token;    //token
    private long expireTime;   //过期时间
    private Date createTime;   //创建时间
    private int gender;        //性别
    private Date lastLoginTime; //最后一次的登陆时间
}
