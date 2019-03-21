package com.sandu.util.excel;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyShopImportBean implements Serializable {
	
    @Row("序号")
    private Integer id;

    @Row(value = "*店铺名称", maxLength= 50)
    private String shopName;

    @Row(value = "*联系人姓名", maxLength= 50)
    private String contactName;

    @Row("*联系人电话")
    private String contactPhone;

    @Row(value = "*所属省")
    private String provinceName;

    @Row(value = "*所属市")
    private String cityName;

    @Row("*所属区")
    private String areaName;

    @Row("*所属街道")
    private String streetName;

    @Row(value="*详细地址", maxLength= 100)
    private String shopAddress;

    @Row(value = "LOGO设置", picFlag = true)
    private String logoPicPath;

    @Row(value= "店铺封面", picFlag = true, isMultiple = true)
    private String coverPicPath;
}
