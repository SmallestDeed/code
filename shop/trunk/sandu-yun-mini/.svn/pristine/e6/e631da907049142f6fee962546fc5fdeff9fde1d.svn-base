package com.sandu.company.model.query;

import java.util.Date;
import com.sandu.base.model.query.BaseQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/***
 * 店铺优惠活动查询条件
 * @author Administrator
 *
 */

@ApiModel(value = "店铺优惠活动查询条件", description = "店铺优惠活动查询条件")
public class ShopActivityQuery extends BaseQuery<ShopActivityQuery>{
	private static final long serialVersionUID = 798484153986262764L;
	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "店铺ID")
	private String shopId;
	@ApiModelProperty(value = "活动开始时间")
	private Date startdate;
	@ApiModelProperty(value = "活动结束时间")
	private Date enddate;
	private String orderBySql;
	public long getCompanyId() {
		return companyId;
	}
	public String getShopId() {
		return shopId;
	}
	public Date getStartdate() {
		return startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public String getOrderBySql() {
		return orderBySql;
	}
	public void setOrderBySql(String orderBySql) {
		this.orderBySql = orderBySql;
	}
	
}
