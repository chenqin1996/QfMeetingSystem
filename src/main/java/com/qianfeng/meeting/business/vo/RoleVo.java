package com.qianfeng.meeting.business.vo;

import lombok.Data;

/**
 * @author Allen
 * @date 2020/10/13
 **/
@Data
public class RoleVo {
    private String roleName;
    private String roleDescription;
    private String[] permIds;

}
