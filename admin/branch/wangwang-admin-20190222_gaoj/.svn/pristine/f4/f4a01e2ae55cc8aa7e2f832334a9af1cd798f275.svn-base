package com.sandu.annotation;
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
    HSSFAFont font() default @HSSFAFont();
    // 列样式
    HSSFStyle style() default @HSSFStyle;
}
