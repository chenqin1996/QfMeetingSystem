package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingNotice implements Serializable {
    private static final long serialVersionUID = -5945908615518328526L;
    private Integer meetingNoticeId;    //会议通知id
    private String  meetingName;        //会议名字
    private Date meetingStartTime;      //会议开始的时间
    private String meetingAddress;      //会议地址
}
