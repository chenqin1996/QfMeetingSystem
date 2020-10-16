package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:20
 * @Description:会议议程实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingAgent implements Serializable {
    private static final long serialVersionUID = 3392878243978243195L;
    private Integer meetingAgentId;  //会议议程id
    private Integer meetingNoticeId;    //会议通知id
    private String meetingAgentDetail;  //会议议程详情
}
