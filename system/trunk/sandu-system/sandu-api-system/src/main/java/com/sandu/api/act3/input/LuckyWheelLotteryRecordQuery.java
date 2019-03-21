package com.sandu.api.act3.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 运营平台抽奖记录查询入参
 * @author WangHaiLin
 * @date 2019/1/16  10:05
 */
@Data
public class LuckyWheelLotteryRecordQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动ID")
    @NotNull(message = "活动Id不能为空")
    private String actId;

    @ApiModelProperty(value = "用户ID")
    private String openId;

    @ApiModelProperty(value = "用户手机号码")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "奖品")
    private String prizeName;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "抽奖时间起(2019-1-15 00:00:00)")
    private String lotteryTimeStart;

    @ApiModelProperty(value = "抽奖时间终(2019-1-16 00:00:00)")
    private String lotteryTimeEnd;

    @ApiModelProperty(value = "是否中奖")
    private Integer awardsStatus;

    @ApiModelProperty(value = "发货状态")
    private Integer deliverStatus;

    @ApiModelProperty(value = "页数")
    private Integer pageNum=1;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize=20;

}
