package com.sandu.api.imallOrder.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ImallOrderVO implements Serializable {

    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "总积分")
    private int totalPoint;

    @ApiModelProperty(value = "状态")
    private int status;

    @ApiModelProperty(value="购买人名称")
    private String buyerNickName;

    @ApiModelProperty(value="商品名称")
    private String itemName;

    @ApiModelProperty(value="商品积分")
    private int itemPoint;

    @ApiModelProperty(value="商品数量")
    private int itemCount;

    @ApiModelProperty(value="商品缩略图")
    private String itemSmall;

    @ApiModelProperty(value="创建时间")
    private Date gmtCreate;
}
