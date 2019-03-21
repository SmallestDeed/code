package com.sandu.goods.model.query;

import java.util.List;

import com.sandu.base.model.query.BaseQuery;
import io.swagger.annotations.ApiModel;

/***
 * 商品查询条件
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "商品查询条件", description = "商品查询条件")
public class GoodsQuery extends BaseQuery<GoodsQuery>{
	private static final long serialVersionUID = 5356411343953745510L;

	private Integer companyId;
	private Integer couponId;
	private Integer bizType=1;
	private String typeCode;
	private String childTypeCode;
	private Integer isPutaway;
	private Integer isPresell;
	private String spuName;
	private String spuCode;
	
	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getSpuName() {
		return spuName;
	}

	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getChildTypeCode() {
		return childTypeCode;
	}

	public void setChildTypeCode(String childTypeCode) {
		this.childTypeCode = childTypeCode;
	}

	public Integer getIsPutaway() {
		return isPutaway;
	}

	public void setIsPutaway(Integer isPutaway) {
		this.isPutaway = isPutaway;
	}

	public Integer getIsPresell() {
		return isPresell;
	}

	public void setIsPresell(Integer isPresell) {
		this.isPresell = isPresell;
	}

	
	
}
