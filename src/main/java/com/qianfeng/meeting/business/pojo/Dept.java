package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:19
 * @Description:部门实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept implements Serializable {
    private static final long serialVersionUID = -7125255722492286260L;
    private Integer deptId;         //部门id
    private String deptName;    //部门名字
    private String deptDes;     //部门描述

    private List<Worker> workers=new ArrayList<>();
}
