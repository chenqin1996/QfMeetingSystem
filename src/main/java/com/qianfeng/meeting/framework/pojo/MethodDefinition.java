package com.qianfeng.meeting.framework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 描述方法
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodDefinition {
    private String methodName;//方法名
    private Method methodClass;//方法类对象
    private Class annotationClass;//注解类对象
    private String annotationMappingName;//注解映射的值
    private Object result;//返回值
    private List<ParameterDefinition> parameterDefinitions;//方法的所有参数
}
