package com.sandu.api.grouppurchase.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sandu
 * @ClassName GroupPurchaseOpenQuery
 * @date 2018/11/6
 */
@Data
public class GroupPurchaseOpenQuery implements Serializable {
	@ApiModelProperty(required = true, name = "用户ID")
	private Long userId;

	@ApiModelProperty(required = true, name = "是否为开团人")
	private Long isMaster;

	@ApiModelProperty( name = "开团Id")
	private Long purchaseOpenId;

	@ApiModelProperty(required = true, name = "页码")
	private Integer page;

	@ApiModelProperty(required = true, name = "页面行数")
	private Integer limit;

	@ApiModelProperty(hidden = true, name = "页面行数")
	private Integer joinStatus;

}
