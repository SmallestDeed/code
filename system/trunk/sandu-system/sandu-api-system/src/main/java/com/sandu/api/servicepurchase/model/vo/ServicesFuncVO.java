package com.sandu.api.servicepurchase.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ServicesFuncVO implements Serializable {
    @ApiModelProperty(value = "功能 ")
    private List<String> funcNames;

    @ApiModelProperty(value = "平台名称")
    private String platformName;
}
