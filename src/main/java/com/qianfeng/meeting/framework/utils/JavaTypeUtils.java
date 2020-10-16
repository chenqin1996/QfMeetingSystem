package com.qianfeng.meeting.framework.utils;

import java.util.List;

/**
 * 罗列哪些是java普通类型
 */
public class JavaTypeUtils {
    private static final String[] javaTypeList = {
            int.class.toString(),
            Integer.class.toString(),
            String.class.toString(),
            double.class.toString(),
            Double.class.toString()
    };

    public static String[] getJavaTypeList() {
        return javaTypeList;
    }
}
