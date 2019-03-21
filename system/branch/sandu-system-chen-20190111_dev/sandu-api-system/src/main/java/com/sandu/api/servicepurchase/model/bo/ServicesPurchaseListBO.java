package com.sandu.api.servicepurchase.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ServicesPurchaseListBO implements Serializable {

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
    @ApiModelProperty(value = "赠送时长")
    private String giveDuration;
    @ApiModelProperty(value = "使用状态")
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
    @ApiModelProperty(value = "套餐账户userId")
    private Integer accountUserId;
    @ApiModelProperty(value = "套餐价格单位")
    private String priceUnit;
    @ApiModelProperty(value = "账户创建者")
    private String creator;

}
