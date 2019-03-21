package com.sandu.common.annotation;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HSSFStyle {
    // 水平对齐方式
    short align() default HSSFCellStyle.ALIGN_LEFT;
    // 垂直对齐方式
    short verticalAlign() default HSSFCellStyle.VERTICAL_BOTTOM;
    // 上边框样式
    short topBorder() default HSSFCellStyle.BORDER_THIN;
    // 下边框样式
    short bottomBorder() default HSSFCellStyle.BORDER_THIN;
    // 左边框样式
    short leftBorder() default HSSFCellStyle.BORDER_THIN;
    // 右边框样式
    short rightBorder() default HSSFCellStyle.BORDER_THIN;
    // 上边框颜色
    short topBorderColor() default HSSFColor.AUTOMATIC.index;
    // 下边框颜色
    short bottomBorderColor() default HSSFColor.AUTOMATIC.index;
    // 左边框颜色
    short leftBorderColor() default HSSFColor.AUTOMATIC.index;
    // 右边框颜色
    short rightBorderColor() default HSSFColor.AUTOMATIC.index;
    // 数据格式
    String dataFormat() default "";
    // 填充样式
    short fillPattern() default HSSFCellStyle.SOLID_FOREGROUND;
    // 背景色
    short fillBackgorundColor() default HSSFColor.WHITE.index;
    // 前景色
    short fillForegroundColor() default HSSFColor.WHITE.index;
    // 是否根据内容调整边框大小
    boolean shrinkToFit() default false;
    // 自动换行
    boolean wrapText() default false;
}
