package com.sandu.api.system.model;

import lombok.Data;

@Data
public class BaseMobileArea {

    private Long id;

    /**
     * 电话号码前7位
     */
    private String mobilePrefix;

    /**
     * 运营商
     */
    private String corp;

    /**
     * 省份编码
     */
    private String provinceCode;

    /**
     * 省份名称
     */
    private String proviceName;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 电话区号0755,020
     */
    private String areaCode;

}
