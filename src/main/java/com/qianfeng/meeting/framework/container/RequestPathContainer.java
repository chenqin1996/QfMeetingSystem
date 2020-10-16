package com.qianfeng.meeting.framework.container;

import com.qianfeng.meeting.framework.pojo.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来存放请求与类/方法的对应关系的容器
 */
public class RequestPathContainer {
    private static Map<String, BeanDefinition> requestMap = new ConcurrentHashMap<>();

    public static Map<String, BeanDefinition> getRequestMap() {
        return requestMap;
    }

    /**
     * set方法应该是给map的元素赋值
     */
    public static void setRequestMap(String key, BeanDefinition value) {
        requestMap.put(key, value);
    }
}
