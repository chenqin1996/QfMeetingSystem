package com.qianfeng.meeting.framework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Parameter;

/**
 * 描述参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterDefinition {
    private String paraName;//参数名称
    private Object paraType;//参数类型
    private Parameter paraClass;//参数类对象
    private int paraIndex;//参数的位置
    private boolean isHaveRequestBodyAnnotation;//是否有其他注解
}
