package com.sandu.goods.model.query;

import java.math.BigInteger;
import java.util.List;
import com.sandu.base.model.query.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/***
 * 商品推荐查询条件
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "商品推荐查询条件", description = "商品推荐查询条件")
public class GoodsRecommendQuery extends BaseQuery<GoodsRecommendQuery> {
	private static final long serialVersionUID = 8701170575880805700L;
	@ApiModelProperty(value = "公司ID")
	private BigInteger companyId;
	@ApiModelProperty(value = "店铺ID")
	private BigInteger shopId;
	private String goodsIds;
	private List<Integer> lstGoodsIds;
	@ApiModelProperty(value = "排序SQL")
	private String orderBySql;
	private String typeCode;
	private String childTypeCode;
	private Integer isPutaway;
	private Integer isPresell;
	private String spuName;
	private String spuCode;
	
	public BigInteger getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigInteger companyId) {
		this.companyId = companyId;
	}

	public String getOrderBySql() {
		return orderBySql;
	}

	public void setOrderBySql(String orderBySql) {
		this.orderBySql = orderBySql;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}

	public List<Integer> getLstGoodsIds() {
		return lstGoodsIds;
	}

	public void setLstGoodsIds(List<Integer> lstGoodsIds) {
		this.lstGoodsIds = lstGoodsIds;
	}

	public BigInteger getShopId() {
		return shopId;
	}

	public void setShopId(BigInteger shopId) {
		this.shopId = shopId;
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
    
}
