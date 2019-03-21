package com.sandu.api.solution.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author by bvvy
 * @date 2018/4/11
 */
@Data
public class SecrecyFlagVO implements Serializable{
    @ApiModelProperty("方案")
    private Integer planId;
    @ApiModelProperty("保密 0 1 ")
    private Integer secrecyFlag;
}
