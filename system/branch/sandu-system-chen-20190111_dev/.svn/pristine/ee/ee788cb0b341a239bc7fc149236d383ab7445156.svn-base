package com.sandu.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HSSFRow {
    // 正则表达式
    String pattern();
    // 行字体
    HSSFFont font() default @HSSFFont;
    // 行样式
    HSSFStyle style() default @HSSFStyle;
}
