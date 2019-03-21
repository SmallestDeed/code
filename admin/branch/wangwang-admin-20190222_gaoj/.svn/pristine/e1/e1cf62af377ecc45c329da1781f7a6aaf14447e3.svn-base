package com.sandu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HSSARow {
    // 正则表达式
    String pattern();
    // 行字体
    HSSFAFont font() default @HSSFAFont;
    // 行样式
    HSSFStyle style() default @HSSFStyle;
}
