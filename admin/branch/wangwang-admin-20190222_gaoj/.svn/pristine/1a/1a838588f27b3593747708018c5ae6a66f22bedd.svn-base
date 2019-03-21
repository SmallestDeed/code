package com.sandu.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Row {
    String value();

    boolean picFlag() default false;

    int maxLength() default 0;
    
    //是否允许上传多张图片
    boolean isMultiple() default false;
}
