package com.qianfeng.meeting.business.response;

import lombok.Getter;

/**
 * 用来作为最终响应给前端的对象
 */
@Getter
public class DataResult<T> {
    //code 与 msg是响应的标准数据
    private int code;
    private String msg;
    private T t;//扩展的数据

    /**
     * 无参构造方法表示响应成功
     */
    public DataResult() {
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = ResponseCode.SUCCESS.getMsg();
    }


    public DataResult(ResponseCode rc){
        this.code = rc.getCode();
        this.msg = rc.getMsg();
    }

    /**
     * 响应成功，同时给前端响应一个对象
     * @param t
     */
    public DataResult(T t) {
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = ResponseCode.SUCCESS.getMsg();
        this.t = t;
    }

    /**
     * 指定code与msg与响应的对象
     * @param rc
     * @param t
     */
    public DataResult(ResponseCode rc, T t) {
        this.code = rc.getCode();
        this.msg = rc.getMsg();
        this.t = t;
    }

    /**
     * 静态方法，返回值表示请求成功的响应
     * @return
     */
    public static DataResult success(){
        return new DataResult();
    }
}
