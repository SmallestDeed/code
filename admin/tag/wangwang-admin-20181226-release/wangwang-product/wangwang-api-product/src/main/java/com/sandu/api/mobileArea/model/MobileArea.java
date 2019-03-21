package com.sandu.api.mobileArea.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * mobileArea
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-07-31 14:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileArea implements Serializable {

    
    /** 主键 */
    private Integer id;
    /** 电话号码前7位 */
    private String mobilePrefix;
    /** 运营商 */
    private String corp;
    /** 省份编码 */
    private String provinceCode;
    /** 省份名称 */
    private String provinceName;
    /** 城市编码 */
    private String cityCode;
    /** 城市名称 */
    private String cityName;
    /** 邮编 */
    private String zipCode;
    /** 电话区号0755,020 */
    private String areaCode;
    
}
