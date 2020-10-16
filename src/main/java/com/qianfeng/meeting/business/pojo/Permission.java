package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:28
 * @Description:权限
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {
    private Integer perId;     //权限id
    private String perName;    //权限名字
    private String perDes;     //权限的描述
    private Integer selectState;  //选中的状态
}
