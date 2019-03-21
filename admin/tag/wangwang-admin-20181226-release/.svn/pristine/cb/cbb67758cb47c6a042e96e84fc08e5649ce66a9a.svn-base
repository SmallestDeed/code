package com.sandu.api.decoratecustomer.model.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecorateCustomerListBO implements Serializable {

    public static final String FUNC_EDIT = "edit";
    public static final String FUNC_INNER_RECOMMEND = "recommend";
    public static final String FUNC_COMMIT = "commit";


    private Integer id;

    @ApiModelProperty("客户名称")
    private String userName;

    @ApiModelProperty("客户手机")
    private String mobile;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("客户预约企业ID")
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
	private List<SimpleDecoratePriceInfo> allPriceList;

    @ApiModelProperty("选定装企报价")
	private List<SimpleDecoratePriceInfo> choosePriceList;


    @ApiModelProperty("内部推荐企业")
    private Integer innerCompanyId;
    @ApiModelProperty("内部推荐企业名称")
    private String innerCompanyName;


    @ApiModelProperty("装修类型")
    private Integer decorateType;
    @ApiModelProperty("装修类型名称")
    private String decorateTypeName;


    @ApiModelProperty("回访时间")
    private Date revisitTime;

    @ApiModelProperty("备注")
    private String remark;


    @ApiModelProperty("客户列表提交订单按钮权限")
    private boolean commitOrderFunc;

    private DecorateCustomerBO proprietorInfo;

    /**
     * 签约装企
     */
    private Integer contractCompany;
    /**
     * 签约价格
     */
    private String contractPrice;

	@Data
	@Builder
	public static class SimpleDecoratePriceInfo implements Serializable {
		private String name;
		private Double price;
	}

}





