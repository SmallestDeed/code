package com.sandu.api.company.input;

import com.sandu.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author Sandu
 */
@Data
public class CompanyShopQuery extends BaseQuery implements Serializable {

	private String cityCode;

	private String goodStyle;

	private Integer costRage;

	private String companyName;

	private Integer houseAcreage;

	private String orderMethod;

	private String orderBy;


	@ApiModelProperty(value = "最低价格", hidden = true)
	private Double designFeeStarting;

	@ApiModelProperty(value = "最高价格", hidden = true)
	private Double designFeeEnding;


	@ApiModelProperty(value = "是否接收平台派单 (0:不接收;1:接收)", hidden = true)
	private Integer isReceivePlatformDispatch;


	@ApiModelProperty(value = "是否接收内部推荐(0:不接收;1:接收)", hidden = true)
	private Integer isReceiveInteriorDispatch;
}
