package com.sandu.web.servicepurchase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelMark {

    String value() default "";

    String cellName() default "";

    int cellNum() default 0;
}
