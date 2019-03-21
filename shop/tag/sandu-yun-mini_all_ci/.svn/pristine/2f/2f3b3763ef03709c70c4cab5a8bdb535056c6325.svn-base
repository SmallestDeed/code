package com.sandu.company.model;

import java.util.Date;
import com.sandu.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/****
 * 门店优惠活动
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "门店优惠活动", description = "门店优惠活动")
public class CompanyShopActivity extends DataEntity<CompanyShopActivity> {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "门店ID")
	private long shopId;
	@ApiModelProperty(value = "活动标题")
	private String activityName;
	@ApiModelProperty(value = "活动描述")
	private String activityDesc;
	@ApiModelProperty(value = "活动开始时间")
	private Date startDate;
	@ApiModelProperty(value = "活动结束时间")
	private Date endDate;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}

