package com.sandu.goods.model;

import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 商品推荐
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "商品推荐", description = "商品推荐")
public class GoodsRecommend extends DataEntity<GoodsRecommend>{
	private static final long serialVersionUID = -9076321547988512690L;
	@ApiModelProperty(value = "商品ID")
    private long goodsId;
	@ApiModelProperty(value = "公司ID")
    private long companyId;
	@ApiModelProperty(value = "店铺ID")
    private long shopId;
 
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
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
    
    
}
