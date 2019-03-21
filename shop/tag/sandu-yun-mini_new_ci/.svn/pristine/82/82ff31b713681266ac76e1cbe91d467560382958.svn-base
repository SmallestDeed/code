package com.sandu.company.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 预约服务新增入参
 * @author WangHaiLin
 * @date 2018/9/28  14:52
 */
@Data
public class AppointmentAdd implements Serializable {

    @ApiModelProperty(value = "预约店铺Id")
    private Integer shopId;

    @ApiModelProperty(value = "服务类型", required = true)
    private Integer serviceType;

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "用户电话", required = true)
    private String userPhone;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "预约方案Id")
    private Integer designplanId;
    
    @ApiModelProperty(value = "预约用户ID")
    private Integer appointUserId;

    @ApiModelProperty(value = "小区名称")
    private String areaName;

}
