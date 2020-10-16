package com.qianfeng.meeting.framework.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanDefinition {
    private String typeName;//类名
    private Class typeClass;//类对象
    private Class annotationClass;//注解的类对象
    private String annotationMappingName;//注解的映射路径
    private List<MethodDefinition> methodDefinitions;//类包含的所有方法

}
