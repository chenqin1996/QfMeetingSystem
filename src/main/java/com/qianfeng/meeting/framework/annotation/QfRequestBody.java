package com.qianfeng.meeting.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 修饰参数的注解，被此注解修饰的参数将自动由前端发送的json数据转换成java对象
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QfRequestBody {
}
