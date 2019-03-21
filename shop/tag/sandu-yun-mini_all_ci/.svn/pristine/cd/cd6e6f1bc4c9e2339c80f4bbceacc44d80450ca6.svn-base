package com.sandu.designplan.model.query;

import java.math.BigInteger;
import java.util.List;

import com.sandu.base.model.query.BaseQuery;
import com.sandu.interaction.model.query.CommentQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 设计方案查询条件
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "设计方案查询条件", description = "设计方案查询条件")
@Data
public class DesignPlanQuery extends BaseQuery<CommentQuery> {
	private static final long serialVersionUID = 1130988917573392567L;
	/**
	 * 企业ID
	 */
	@ApiModelProperty(value = "企业ID")
	private BigInteger companyId;
	@ApiModelProperty(value = "当前用户ID")
	private BigInteger userId;
	/***
	 * 推荐方案类别:1.样板间 2:一键方案
	 */
	@ApiModelProperty(value = "推荐方案类别:1.样板间 2:一键方案")
	private Integer recommendedType;

	@ApiModelProperty(value = "平台类型ID")
	private Integer platformId;
	@ApiModelProperty(value = "企业IDs")
	List<Integer> companyIds;
	@ApiModelProperty(value = "店铺ID")
	private Integer shopId;

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public List<Integer> getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(List<Integer> companyIds) {
		this.companyIds = companyIds;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public BigInteger getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigInteger companyId) {
		this.companyId = companyId;
	}

	public Integer getRecommendedType() {
		return recommendedType;
	}

	public void setRecommendedType(Integer recommendedType) {
		this.recommendedType = recommendedType;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
}
