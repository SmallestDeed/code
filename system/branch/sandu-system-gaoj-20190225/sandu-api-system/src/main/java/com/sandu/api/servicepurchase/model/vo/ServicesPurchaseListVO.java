package com.sandu.api.servicepurchase.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class ServicesPurchaseListVO implements Serializable {

    @ApiModelProperty(value = "服务ID")
    private Integer serviceId;
    @ApiModelProperty(value = "服务编码")
    private String servicesCode;
    @ApiModelProperty(value = "服务名称")
    private String servicesName;
    @ApiModelProperty(value = "服务描述")
    private String serviceDesc;
    @ApiModelProperty(value = "时长")
    private String duration;
    @ApiModelProperty(value = "使用状态(0-未使用;1-使用)")
    private String status;
    @ApiModelProperty(value = "账户")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "购买时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "用户类型")
    private String userType;
    @ApiModelProperty(value = "用户类型名称")
    private String userTypeName;

    @ApiModelProperty(value = "账户创建者")
    private String creator;
}
