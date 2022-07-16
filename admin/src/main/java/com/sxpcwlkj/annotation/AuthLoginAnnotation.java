package com.sxpcwlkj.annotation;

import java.lang.annotation.*;

/**
 * @author ：shanpegnian - http://www.sxpcwlkj.com
 * @version ：1.0
 * @description ： com.sxpcwlkj.a_common.annotation
 * @date ： 2019/3/9
 */

@Documented //文档生成时，该注解将被包含在javadoc中，可去掉
@Target(ElementType.METHOD)//目标是方法
@Retention(RetentionPolicy.RUNTIME) //注解会在class中存在，运行时可通过反射获取
@Inherited
public @interface AuthLoginAnnotation {
    /**
     * 检查是否已登录（注解的参数）
     * @return true-检查；默认true 进行登录验证
     */
    boolean login() default true;

    /**
     * 验证对象  user   webmember  app  ....
     *  默认拦截 后台 user对象
     * @return
     */
    String object() default "user";    //1：user：后台 2：pclm：pc联盟:3:pcgw：pc官网 4:pcjfg：5:pc集福购  6:app：app  7:xcx：小程序  8:gzh：公众号

    /**
     *  是否验证当前对象 访问权限
     *  默认 true  进行验证
     * @return
     */
    boolean authority() default true;

    /**
     *  权限标识
     *  默认 null  拦截所有
     * @return
     */
    String authorityCode() default "";

    /**
     * app是否验证key
     * 默认不验证
     * @return
     */
    boolean key() default false;
}

/*
注解方法不能有参数。
        注解方法的返回类型局限于原始类型，字符串，枚举，注解，或以上类型构成的数组。
        注解方法可以包含默认值。
        注解可以包含与其绑定的元注解，元注解为注解提供信息，有四种元注解类型：
        　　1. @Documented – 表示使用该注解的元素应被javadoc或类似工具文档化，它应用于类型声明，类型声明的注解会影响客户端对注解元素的使用。如果一个类型声明添加了Documented注解，那么它的注解会成为被注解元素的公共API的一部分。

        　　2. @Target – 表示支持注解的程序元素的种类，一些可能的值有TYPE, METHOD, CONSTRUCTOR, FIELD等等。如果Target元注解不存在，那么该注解就可以使用在任何程序元素之上。

        　　3. @Inherited – 表示一个注解类型会被自动继承，如果用户在类声明的时候查询注解类型，同时类声明中也没有这个类型的注解，那么注解类型会自动查询该类的父类，这个过程将会不停地重复，直到该类型的注解被找到为止，或是到达类结构的顶层（Object）。

        　　4. @Retention – 表示注解类型保留时间的长短，它接收RetentionPolicy参数，可能的值有SOURCE（源文件中起作用）, CLASS, 以及RUNTIME（保留到运行时起作用）。
*/
