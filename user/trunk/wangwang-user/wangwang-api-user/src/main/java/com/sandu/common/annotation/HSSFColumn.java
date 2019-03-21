package com.sandu.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EXCEL表格列
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HSSFColumn {
    // 列标题
    String title();
    // 宽度自适应
    boolean autoWidth() default false;
    // 列字体
    HSSFFont font() default @HSSFFont;
    // 列样式
    HSSFStyle style() default @HSSFStyle;
    // 分条件返回(只支持字符串)
    // 格式:
    // {key1:value1, key2:value2}
    String switcher() default "";
}
