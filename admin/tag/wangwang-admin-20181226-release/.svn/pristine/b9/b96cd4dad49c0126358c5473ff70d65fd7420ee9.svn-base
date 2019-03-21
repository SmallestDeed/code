package com.sandu.api.decoratecustomer.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerBO;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerListBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 *
 * <p>demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 14:27
 */
@Data
public class DecorateCustomerListVO implements Serializable {
	private Integer id;

	@ApiModelProperty("客户名称")
	private String userName;

	@ApiModelProperty("客户手机")
	private String mobile;

	@ApiModelProperty("城市编码")
	private String cityCode;

	@ApiModelProperty("城市名称")
	private String cityName;

	@ApiModelProperty(name = "客户预约企业ID", hidden = true)
	private Integer companyId;

	@ApiModelProperty("客户预约企业")
	private String companyName;

	@ApiModelProperty(name = "第一家抢单企业ID", hidden = true)
	private Integer firstSecKillCompany;

	@ApiModelProperty("第一家抢单企业名称")
	private String firstSecKillCompanyName;

	@ApiModelProperty(name = "第2家抢单企业ID", hidden = true)
	private Integer secondSecKillCompany;

	@ApiModelProperty("第2家抢单企业名称")
	private String secondSecKillCompanyName;

	@ApiModelProperty("所有十家装企报价")
	private List<DecorateCustomerListBO.SimpleDecoratePriceInfo> allPriceList;

	@ApiModelProperty("选定装企报价")
	private List<DecorateCustomerListBO.SimpleDecoratePriceInfo> choosePriceList;

	@ApiModelProperty(value = "内部推荐企业", hidden = true)
	private Integer innerCompanyId;

	@ApiModelProperty("内部推荐企业名称")
	private String innerCompanyName;

	@ApiModelProperty("装修类型")
	private Integer decorateType;

	@ApiModelProperty("装修类型名称")
	private String decorateTypeName;

	@ApiModelProperty("回访时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date revisitTime;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("客户列表提交订单按钮权限")
	private boolean commitOrderFunc;


	@ApiModelProperty("签约装企")
	private Integer contractCompany;


	@ApiModelProperty("签约价格")
	private String contractPrice;

	@ApiModelProperty("业主信息")
	private DecorateCustomerBO proprietorInfo;

}
