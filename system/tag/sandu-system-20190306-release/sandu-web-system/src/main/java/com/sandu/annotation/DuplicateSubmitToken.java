package com.sandu.annotation;

import java.lang.annotation.*;

/**
 * @Author chenqiang
 * @Description 防止表单重复提交注解
 * @Date 2018/6/26 0026 20:08
 * @Modified By
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DuplicateSubmitToken {

    /**一次请求完成之前防止重复提交*/
    public static final int REQUEST=1;

    /**一次会话中防止重复提交*/
    public static final int SESSION=2;

    /**保存重复提交标记 默认为需要保存*/
    boolean save() default true;

    /**防止重复提交类型，默认：一次请求完成之前防止重复提交*/
    int type() default REQUEST;

}