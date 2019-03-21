package com.sandu.api.proprietorinfo.output;

import lombok.Data;

import java.io.Serializable;

@Data
public class CityVo implements Serializable {
    /**
     * 市编码
     */
    private String cityCode;
    /**
     * 市
     */
    private String city;
}
