package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    private Integer roleId;  //角色id
    private String roleName;  //角色名字
    private String roleDescription; //角色描述
}
