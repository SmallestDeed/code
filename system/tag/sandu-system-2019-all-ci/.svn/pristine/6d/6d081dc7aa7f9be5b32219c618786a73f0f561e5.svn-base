package com.sandu.api.act3.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动奖品列表中实体
 * @author WangHaiLin
 * @date 2019/1/16  19:23
 */
@Data
public class LuckyWheelPrizeListBO implements Serializable {
    @ApiModelProperty(value = "奖品ID")
    private String id;
    @ApiModelProperty(value = "活动ID")
    private String actId;
    @ApiModelProperty(value = "奖品名称")
    private String name;
    @ApiModelProperty(value = "数量")
    private Integer num;
    @ApiModelProperty(value = "总的剩余数量")
    private Integer remainNum;
    @ApiModelProperty(value = "当天剩余数量(新增时不用传值)")
    private Integer todayRemainNum;
    @ApiModelProperty(value = "每日最大数量")
    private Integer numPerDay;
    @ApiModelProperty(value = "概率")
    private double probability;
    @ApiModelProperty(value = "排序")
    private Integer orderSeq;
    @ApiModelProperty(value = "奖品类型(0:未中奖,1:度币,2:现金,3:话费,4:资料,5:物品(需要邮寄))")
    private Integer type;
    @ApiModelProperty(value = "单个额度:度币值(10度币,100度币之类的),话费(10,100)")
    private Double value;
    @ApiModelProperty(value = "奖品图片")
    private String img;
}
