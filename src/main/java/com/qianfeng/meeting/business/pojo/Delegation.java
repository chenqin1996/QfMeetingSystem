package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:16
 * @Description:代表团这个实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delegation implements Serializable {
    private static final long serialVersionUID = 8352351598184417355L;
    private Integer delegationId;      //代表团的id
    private String delegationName;     //代表团的名字
    private String delegationDes;      //代表团的描述
    private List<Staff> staffs=new ArrayList<>();
}
