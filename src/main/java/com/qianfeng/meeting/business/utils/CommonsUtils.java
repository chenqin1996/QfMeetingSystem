package com.qianfeng.meeting.business.utils;

import java.util.UUID;

public class CommonsUtils {

    /**
     * 获取去掉-之后的UUID字符串值
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
