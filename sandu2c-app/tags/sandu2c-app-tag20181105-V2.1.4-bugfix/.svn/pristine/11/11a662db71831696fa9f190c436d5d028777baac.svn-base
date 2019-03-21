package com.sandu.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderManage implements Serializable{

    private Long id;

    @ApiModelProperty(value = "中介id")
    private Long intermediaryId;

    @ApiModelProperty(value = "中介名称")
    private String intermediaryName;

    @ApiModelProperty(value = "成交对象")
    private String tradePartner;

    @ApiModelProperty(value = "成交金额")
    private BigDecimal tradePrice;

    @ApiModelProperty(value = "成交时间")
    private Date tradeDate;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
}
