package com.sandu.panorama.model.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DesignPlanStoreReleaseVo implements Serializable{

    @ApiModelProperty(value = "分享标题", dataType = "String")
    private String shareTitle;
    @ApiModelProperty(value = "720组合分享ID", dataType = "Integer")
    private Integer id;
    @ApiModelProperty(value = "720组合分享UUID", dataType = "String")
    private String uuid;
    @ApiModelProperty(value = "用户ID", dataType = "Integer", hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "创建时间", dataType = "Date")
    private Date gmtCreate;
    @ApiModelProperty(value = "组合分享类型（全户型分享、随意组合分享）", dataType = "Integer")
    private Integer shareType;
    @ApiModelProperty(value = "浏览量", dataType = "Integer")
    private Integer pv;
    @ApiModelProperty(value = "联盟门店ID", dataType = "Integer")
    private Integer unionGroupId;
    @ApiModelProperty(value = "优惠活动ID", dataType = "Integer")
    private Integer unionSpecialOfferId;
    @ApiModelProperty(value = "联系人ID", dataType = "Integer")
    private Integer unionContactId;
    @ApiModelProperty(value = "联系人名称", dataType = "String")
    private String contactName;
    @ApiModelProperty(value = "联系电话", dataType = "String")
    private String contactPhone;
    @ApiModelProperty(value = "LOGO", dataType = "String")
    private String logo;
    @ApiModelProperty(value = "户型ID", dataType = "Integer")
    private Integer houseId;
    @ApiModelProperty(value = "户型图ID", dataType = "Integer", hidden = true)
    private Integer housePicId;
    @ApiModelProperty(value = "户型缩略图", dataType = "Integer")
    private String housePicPath;
    @ApiModelProperty(value = "分享详情", dataType = "List<DesignPlanStoreReleaseDetailsVo>")
    private List<DesignPlanStoreReleaseDetailsVo> details;
    @ApiModelProperty(value = "主图方案ID", dataType = "Integer", hidden = true)
    private Integer mainDesignPlanId;
    @ApiModelProperty(value = "主图方案封面图", dataType = "String")
    private String coverPicPath;
    @ApiModelProperty(value = "户型名称", dataType = "String")
    private String houseCommonCode;
    @ApiModelProperty(value = "户型面积", dataType = "String")
    private String totalArea;
    @ApiModelProperty(value = "各房型空间数量", dataType = "String")
    private String houseSpaceNumStr;
    @ApiModelProperty(value="户型区域code",dataType = "String",hidden = true)
    private String areaLongCode;
    @ApiModelProperty(value="户型所在省",dataType = "String")
    private String province;
    @ApiModelProperty(value="户型所在市",dataType = "String")
    private String city;
    @ApiModelProperty(value ="户型所在区域",dataType = "String")
    private String district;
    @ApiModelProperty(value="户型所在小区",dataType = "String")
    private String livingName;
}
