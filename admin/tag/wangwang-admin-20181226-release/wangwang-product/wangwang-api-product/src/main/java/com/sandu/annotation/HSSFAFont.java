package com.sandu.annotation;

import org.apache.poi.hssf.util.HSSFColor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HSSFAFont {
    // 字符编码
    byte charset() default org.apache.poi.hssf.usermodel.HSSFFont.DEFAULT_CHARSET;
    // 字体粗细
    short bold() default org.apache.poi.hssf.usermodel.HSSFFont.BOLDWEIGHT_NORMAL;
    // 字体颜色
    short color() default HSSFColor.BLACK.index;
    // 是否斜体
    boolean italic() default false;
    // 是否有下划线
    byte underline () default org.apache.poi.hssf.usermodel.HSSFFont.U_NONE;
    // 字体大小
    short height() default 10;
    // 字体名称
    String name() default org.apache.poi.hssf.usermodel.HSSFFont.FONT_ARIAL;
}
