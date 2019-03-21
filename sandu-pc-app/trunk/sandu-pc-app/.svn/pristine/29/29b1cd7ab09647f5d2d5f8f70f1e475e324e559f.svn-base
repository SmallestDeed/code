package com.sandu.panorama.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DesignPlanStoreReleaseAdd implements Serializable {

    @ApiModelProperty(value = "分享Id",required = false,dataType = "Integer")
    private Integer id;
    @ApiModelProperty(value = "分享标题", required = true, dataType = "String")
    private String shareTitle;
    @ApiModelProperty(value = "分享类型（1:全户型分享、2:随意组合分享）", required = true, dataType = "Integer")
    private Integer shareType;
    @ApiModelProperty(value = "联盟门店ID", dataType = "Integer")
    private Integer unionGroupId;
    @ApiModelProperty(value = "优惠活动ID", dataType = "Integer")
    private Integer unionSpecialOfferId;
    @ApiModelProperty(value = "联系人ID", dataType = "Integer")
    private Integer unionContactId;
    @ApiModelProperty(value = "户型ID", dataType = "Integer")
    private Integer houseId;
    @ApiModelProperty(value = "720分享详情", dataType = "List<DesignPlanStoreReleaseDetailsAdd>")
    private List<DesignPlanStoreReleaseDetailsAdd> designPlanStoreReleaseDetailsAddList;
}
