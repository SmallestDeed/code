package com.sandu.resource.model.query;

import java.math.BigInteger;

import com.sandu.base.model.query.BaseQuery;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "图片资源查询实体类", description = "图片资源查询实体类")
public class ResPicQuery extends BaseQuery<ResPicQuery>{
	private static final long serialVersionUID = 4177749136976057652L;
	private BigInteger companyId;
    private BigInteger shopId;
	public BigInteger getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigInteger companyId) {
		this.companyId = companyId;
	}

	public BigInteger getShopId() {
		return shopId;
	}

	public void setShopId(BigInteger shopId) {
		this.shopId = shopId;
	}
}
