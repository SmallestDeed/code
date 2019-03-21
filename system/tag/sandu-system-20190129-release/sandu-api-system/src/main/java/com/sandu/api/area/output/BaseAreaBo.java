package com.sandu.api.area.output;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class BaseAreaBo implements Serializable {
    private static final long serialVersionUID = 3858346869780417450L;

    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String areaCode;
    private String areaName;
    private String streetCode;
    private String streetName;
}
