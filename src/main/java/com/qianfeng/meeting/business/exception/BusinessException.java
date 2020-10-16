package com.qianfeng.meeting.business.exception;

import com.qianfeng.meeting.business.response.ResponseCode;
import lombok.Getter;

/**
 * 自定义的异常
 */
@Getter
public class BusinessException extends RuntimeException{
    private int code;
    private String msg;

    public BusinessException(ResponseCode rc){
        super(rc.getMsg());
        this.code = rc.getCode();
        this.msg = rc.getMsg();
    }

}
