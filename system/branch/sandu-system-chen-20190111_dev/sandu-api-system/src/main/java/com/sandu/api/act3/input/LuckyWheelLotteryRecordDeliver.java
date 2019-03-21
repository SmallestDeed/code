package com.sandu.api.act3.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 运营平台发货/修改发货信息入参
 * @author WangHaiLin
 * @date 2019/1/16  14:03
 */
@Data
public class LuckyWheelLotteryRecordDeliver implements Serializable {

    @ApiModelProperty(value = "抽奖记录ID",required = true)
    @NotNull(message = "抽奖记录ID不能为空")
    private String id;

    @ApiModelProperty(value = "物流公司",required = true)
    @Size(min = 1,max = 20,message = "物流公司长度限{min}-{max}个字符")
    @NotNull(message = "物流公司不能为空")
    private String carrier;

    @ApiModelProperty(value = "发货单号",required = true)
    @NotNull(message = "发货单号不能为空")
    @Pattern(regexp = "^[0-9A-Za-z]{6,30}$", message = "发货单号由字母和数字构成，长度限6-30个字符")
    private String shipmentNo;

}
