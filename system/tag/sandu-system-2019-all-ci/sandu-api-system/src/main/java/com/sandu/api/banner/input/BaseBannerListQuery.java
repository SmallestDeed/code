package com.sandu.api.banner.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础-广告 前端 列表查询入参
 * @author WangHaiLin
 * @date 2018/5/26  17:17
 */
@Data
public class BaseBannerListQuery implements Serializable{

    @ApiModelProperty(value ="使用模型Id(店铺id,企业id,平台id)")
    private Integer refModelId;

    @ApiModelProperty(value = "所属位置Id")
    private Integer positionId;
}
