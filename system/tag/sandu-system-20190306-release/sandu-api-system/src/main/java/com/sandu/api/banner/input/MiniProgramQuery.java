package com.sandu.api.banner.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业小程序Banner管理端查询入参
 * @author WangHaiLin
 * @date 2018/6/22  17:39
 */
@Data
public class MiniProgramQuery implements Serializable {

    @ApiModelProperty(value = "企业Id")
    private Integer refModelId;

    @ApiModelProperty(value = "位置编码")
    private String positionCode;
}
