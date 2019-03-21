package com.sandu.api.servicepurchase.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ServicesFuncBO implements Serializable {
    @ApiModelProperty(hidden = true)
    private Integer platformId;

    @ApiModelProperty(hidden = true)
    private String funcName;

    @ApiModelProperty(value = "功能 ")
    private List<String> funcNames;

    @ApiModelProperty(value = "平台名称")
    private String platformName;
}
