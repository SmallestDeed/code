package com.sandu.api.grouppurchase.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Sandu
 * @ClassName UserPurchaseListBO
 * @date 2018/11/6
 */
@Data
public class UserPurchaseListBO implements Serializable {
	@ApiModelProperty("开团ID")
	private Long purchaseOpenId;

	@ApiModelProperty("拼团活动ID")
	private Double activityId;

	@ApiModelProperty("商品名称")
	private String spuName;

	@ApiModelProperty("开团价格")
	private Double activityPrice;
	@ApiModelProperty("原价格")
	private Double basePrice;

	@ApiModelProperty("商品图片")
	private String picPath;
	@ApiModelProperty(hidden = true)
	private Integer picId;

	@ApiModelProperty("满团人数")
	private Integer totalNumber;

	@ApiModelProperty("团购状态-0-等待成团;1-拼团成功;2-拼团失败")
	private Integer openStatus;

	@ApiModelProperty("已经参团人数")
	private Integer joinNumber;

	@ApiModelProperty("所有用户信息")
	private List<PurchaseInnerUserInfo> innerUserInfoList;

	@ApiModelProperty(value = "参与此团的所有用户", hidden = true)
	private List<Long> userIds;

	@ApiModelProperty("团购产品spu")
	private Long spuId;

	private Long isMaster;

	@ApiModelProperty(value = "到期时间", hidden = true)
	private Date expireDate;
	@ApiModelProperty(value = "开团时间", hidden = true)
	private Date startDate;

	@ApiModelProperty(value = "开团时间")
	private Long startTime;

	@ApiModelProperty(value = "到期时间")
	private Long expireTime;

}
