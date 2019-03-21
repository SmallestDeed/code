package com.sandu.company.model.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "店铺活动信息", description = "店铺活动信息")
public class ShopActivityVo extends BaseVo<ShopActivityVo>{
	
	private static final long serialVersionUID = -4350293478239218059L;
	
	@ApiModelProperty(value = "公司ID")
	private long companyid;
	@ApiModelProperty(value = "店铺ID")
	private long shopid;
	@ApiModelProperty(value = "活动名称")
	private String activityname;
	@ApiModelProperty(value = "活动描述")
	private String activitydesc;
	@ApiModelProperty(value = "活动开始时间")
	private Date startdate;
	@ApiModelProperty(value = "活动结束时间")
	private Date enddate;
	
	public long getCompanyid() {
		return companyid;
	}
	public long getShopid() {
		return shopid;
	}
	public String getActivityname() {
		return activityname;
	}
	public String getActivitydesc() {
		return activitydesc;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartdate() {
		return startdate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEnddate() {
		return enddate;
	}
	public void setCompanyid(long companyid) {
		this.companyid = companyid;
	}
	public void setShopid(long shopid) {
		this.shopid = shopid;
	}
	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}
	public void setActivitydesc(String activitydesc) {
		this.activitydesc = activitydesc;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
}
