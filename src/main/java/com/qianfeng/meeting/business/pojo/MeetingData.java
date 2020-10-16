package com.qianfeng.meeting.business.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: xiaobobo
 * @Date: 2020/6/3 10:27
 * @Description:会议资料实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingData implements Serializable {
    private static final long serialVersionUID = -4794166298682737765L;
    private Integer meetingDataId;    //会议资料的id
    private Integer meetingNoticeId;  //会议通知的id
    private String meetingDataDetail; //会议详情id
}
