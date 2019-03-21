package com.sandu.api.companyshop.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * CompanyShopOffline
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-28 18:13
 */
@Data
public class CompanyshopofflineUpdate implements Serializable {


    @ApiModelProperty(value = "id")


    private Integer id;
        
    @ApiModelProperty(value = "厂商企业ID")
        
        
    private Integer companyId;
        
        
    @ApiModelProperty(value = "门店名称")
        
        
    private String shopName;
        
        
    @ApiModelProperty(value = "省编码")
        
        
    private String provinceCode;
        
        
    @ApiModelProperty(value = "市编码")
        
        
    private String cityCode;
        
        
    @ApiModelProperty(value = "区编码")
        
        
    private String areaCode;
        
        
    @ApiModelProperty(value = "街道编码")
        
        
    private String streetCode;
        
        
        
    @ApiModelProperty(value = "联系人姓名")
        
        
    private String contactName;
        
        
    @ApiModelProperty(value = "联系电话")
        
        
    private String contactPhone;
        
        
    @ApiModelProperty(value = "联系人地址")
        
        
    private String shopAddress;
        
        
    @ApiModelProperty(value = "店铺logo图片")
        
        
    private Integer logoPicId;

    @ApiModelProperty(value = "店铺封面图片")
    private String coverPicId;


    @ApiModelProperty(value = "修改人")

    private String modifier;

    @ApiModelProperty(value = "门店介绍")
    private String shopIntroduced;


        

        
        
    
}
