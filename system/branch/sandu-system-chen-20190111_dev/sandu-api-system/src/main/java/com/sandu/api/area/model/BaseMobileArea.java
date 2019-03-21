package com.sandu.api.area.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 电话归属地址表
 * @author WangHaiLin
 * @date 2018/7/19  10:57
 */
@Data
public class BaseMobileArea implements Serializable {
    /**
     * 主键Id
     */
    private Integer id;
    /**
     * 电话号码前七位
     */
    private String mobilePrefix;
    /**
     *运营商
     */
    private String corp;
    /**
     *省份编码
     */
    private String provinceCode;
    /**
     *省份名称
     */
    private String provinceName;
    /**
     *城市编码
     */
    private String cityCode;
    /**
     *城市名称
     */
    private String cityName;
    /**
     *邮编
     */
    private String zipCode;
    /**
     *电话区号0755,020
     */
    private String areaCode;

}
