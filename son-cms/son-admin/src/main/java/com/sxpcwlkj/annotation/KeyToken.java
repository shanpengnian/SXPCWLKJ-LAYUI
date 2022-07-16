package com.sxpcwlkj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.lhym.applet.config
 * @date 2020/11/2
 * 自定义注解  主要用来对外开放的接口 key验证
 * 后期可以开成token验证 其实原来一样
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyToken {
    boolean required() default true;
}
