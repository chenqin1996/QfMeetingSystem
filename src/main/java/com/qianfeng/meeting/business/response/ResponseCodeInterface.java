package com.qianfeng.meeting.business.response;

/**
 * 指定服务端向客户端响应数据的标准
 * 此标准要求响应的数据，至少需要两个东西
 * 1.状态码
 * 2.状态码的解释
 */
public interface ResponseCodeInterface {
    /**
     * 设置状态码
     * @param code
     */
    void setCode(int code);
    /**
     * 获取状态码
     * @return
     */
    int getCode();

    /**
     * 设置响应信息
     * @param msg
     */
    void setMsg(String msg);

    /**
     * 获取响应信息
     * @return
     */
    String getMsg();
}
