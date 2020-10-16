package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:22
 * @Description:会议新闻
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingNews implements Serializable {
    private static final long serialVersionUID = 8953977684279666321L;
    private Integer meetingnewsId;      //会议新闻的id
    private String meetingnewsName;     //会议新闻的名字
    private String meetingnewsIntro;    //会议新闻的介绍
    private int type;                   // 0:表示会议新闻  1：表示图片报道
    private  String meetingnewsPic;     //会议新闻的图片
    private String meetingnewsDetail;   //会议新闻详情
}
