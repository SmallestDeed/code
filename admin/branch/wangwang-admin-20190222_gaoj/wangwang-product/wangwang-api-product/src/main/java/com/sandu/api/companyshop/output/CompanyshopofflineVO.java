package com.sandu.api.companyshop.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * @author songjianming@snaduspace.cn
 * @date 2018/11/29 14:11
 * @since 1.8
 */

@Data
public class CompanyshopofflineVO implements Serializable {

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

    @ApiModelProperty(value = "是否发布")
    private Integer isRelease;

    @ApiModelProperty(value = "店铺logo图片")
    private Integer logoPicId;

    @ApiModelProperty(value = "店铺logo图片路径")
    private String logoPicPath;

    @ApiModelProperty(value = "店铺封面图片")
    private String coverPicId;

    @ApiModelProperty(value = "店铺封面图片路径")
    private List<String> coverPicPath;

    @ApiModelProperty(value = "地区长编码名称")
    private String longAreaName;

    @ApiModelProperty(value = "门店介绍")
    private String shopIntroduced;

}
