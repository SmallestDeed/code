package com.sandu.api.act3.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 运营平台抽奖记录列表输出实体
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@AllArgsConstructor
@NoArgsConstructor
public class LuckyWheelLotteryRecordListVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "抽奖记录ID")
	private String id;

	@ApiModelProperty(value = "微信ID")
	private String openId;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "用户手机号码")
	private String mobile;

	@ApiModelProperty(value = "是否中奖")
	private Integer awardsStatus;

	@ApiModelProperty(value = "奖品")
	private String prizeName;

	@ApiModelProperty(value = "是否发货")
	private Integer deliverStatus;

	@ApiModelProperty(value = "收货人")
	private String receiver;

	@ApiModelProperty(value = "区域")
	private String provinceCityAreaName;

	@ApiModelProperty(value = "详细地址")
	private String address;

	@ApiModelProperty(value = "抽奖时间")
	private String lotteryTime;

}
