package com.sandu.authz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsFilter {
	
	/**查询名称**/
    String filterCode();
    
    /**需要过滤的属性名称:
     *     "/":返回对象本身
     *  "/obj":返回对象的obj属性
     * "/list":返回对象的list对象
     **/
    String filterPath() default "/";
}

