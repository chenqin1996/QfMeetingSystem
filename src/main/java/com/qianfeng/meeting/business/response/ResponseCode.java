package com.qianfeng.meeting.business.response;

/**
 * 响应标准的具体实现
 */
public enum ResponseCode implements ResponseCodeInterface{

    SUCCESS(0, "请求成功"),
    USERNAMEERROR(101, "用户名错误"),
    PASSWORDERROR(102, "密码错误"),
    USERAUTHTICATIONERROR(103, "用户权限错误"),
    VERIFYERROR(104, "验证码错误"),
    SQLEXCEPTION(105, "SQL执行异常"),
    SYSTEMEXCEPTION(106, "系统异常"),
    JSONERROR(107, "JSON错误");

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
