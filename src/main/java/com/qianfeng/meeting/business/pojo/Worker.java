package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:36
 * @Description:工作人员实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker implements Serializable {
    private static final long serialVersionUID = 2356333548383064322L;
    private Integer workerId ;   //工作人员id
    private String workerName ;  //工作人员名字
    private String photo ;       //头像
    private Integer gender;
    private String telNum;       //电话号码
    private Integer deptId;      //部门id
}
