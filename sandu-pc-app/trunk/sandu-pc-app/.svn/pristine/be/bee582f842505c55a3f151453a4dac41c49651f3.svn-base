package com.sandu.design.model.input;

import com.sandu.common.model.Mapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DesignPlanRenderSceneSearch extends Mapper implements Serializable {

    @ApiModelProperty(value = "方案名称", dataType = "String")
    private String planName;
    @ApiModelProperty(value = "用户ID", dataType = "Integer", hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "空间ID。传此参数表示查询该空间下我的所有方案", dataType = "Integer")
    private Integer spaceCommonId;
    @ApiModelProperty(value = "样板房ID。传此参数表示查询该样板房下我的所有方案", dataType = "Integer")
    private Integer designTemplateId;
    @ApiModelProperty(value = "分享类型（1:全户型分享、2:随意组合分享）", dataType = "Integer")
    private Integer shareType;

}
