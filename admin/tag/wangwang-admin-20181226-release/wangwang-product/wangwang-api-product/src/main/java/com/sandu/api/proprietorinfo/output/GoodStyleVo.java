package com.sandu.api.proprietorinfo.output;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodStyleVo implements Serializable {
    /**
     * 风格
     */
    private String goodStyle;
    /**
     * 风格
     */
    private Integer goodStyleValue;
}
