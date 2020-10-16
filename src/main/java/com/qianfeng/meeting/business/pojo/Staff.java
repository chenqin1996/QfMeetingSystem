package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:31
 * @Description:职位实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {
    private Integer staffId;      //职位id
    private String staffName;    //职位名字
    private String staffDes;      //职位描述
    private Integer delegationId;  //代表团id
}
