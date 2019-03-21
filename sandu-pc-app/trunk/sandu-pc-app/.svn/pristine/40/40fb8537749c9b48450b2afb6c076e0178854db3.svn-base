package com.sandu.design.model.input;

import com.sandu.common.model.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DesignPlanRecommendedSearch extends Mapper implements Serializable{

    @ApiModelProperty(value = "当前场景方案ID", dataType = "Integer")
    private Integer planId;
    @ApiModelProperty(value = "方案风格", dataType = "Integer")
    private Integer planStyleId;
    @ApiModelProperty(value = "方案类型", dataType = "Integer")
    private Integer designPlanType;
    @ApiModelProperty(value = "当前场景方案的用户ID", dataType = "Integer")
    private Integer userId;
    @ApiModelProperty(value = "方案发布类型 ONEKEY,OPEN 一键，公开", dataType = "String")
    private String shelfStatus;
    @ApiModelProperty(value = "方案推荐类型(1分享;2一键装修)", dataType = "Integer")
    private Integer recommendedType;
    @ApiModelProperty(value = "发布状态", dataType = "Integer")
    private Integer releaseStatus;
    @ApiModelProperty(value = "当前场景方案的用户所属企业ID", dataType = "Integer")
    private Integer companyId;
    @ApiModelProperty(value = "当前场景方案的用户所属可见品牌ID", dataType = "List")
    private List<Integer> brandIds;
    @ApiModelProperty(value = "720制作场景uuid",dataType = "String")
    private String uuid;
    @ApiModelProperty(value="空间面积" , dataType = "String" ,hidden = true)
    private String spaceAreas;
    @ApiModelProperty(value="空间类型" , dataType = "String" , hidden = true)
    private Integer spaceFunctionType;
}
