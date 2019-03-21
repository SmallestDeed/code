package com.sandu.api.solution.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * DesignPlanEffectDiagramBO class
 *
 * @author bvvy
 * @date 2018/04/02
 */
@Data
public class DesignPlanEffectDiagramBO implements Serializable{

    @ApiModelProperty("图片id")
    private Long picId;

    @ApiModelProperty("渲染类型")
    private Integer renderingType;

    @ApiModelProperty("图片地址")
    private String picPath;
    @ApiModelProperty("方案id")
    private Long planId;

    @ApiModelProperty("禁用 0 否 1 是")
    private Integer isDeleted;
}
