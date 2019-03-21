package com.sandu.company.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sandu.base.model.DataEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/****
 * 店铺信息
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "店铺信息", description = "店铺信息")
public class CompanyShop extends DataEntity<CompanyShop> {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "公司ID")
	private long companyId;
	@ApiModelProperty(value = "店铺名称")
	private String shopName;
	@ApiModelProperty(value = "店铺Logo图片资源ID")
	private long logoId;
	@ApiModelProperty(value = "店铺商品分类ID集合")
	private String productCategoryIds;
	@ApiModelProperty(value = "店铺所在区域Code")
	private int longAreaCode;
	@ApiModelProperty(value = "店铺所在县城或城区ID")
	private int areaId;
	@ApiModelProperty(value = "访问次数")
	private String shopAddress;
	@ApiModelProperty(value = "访问次数")
	private int visitCount;
	@ApiModelProperty(value = "好评率")
	private float praiseRate;
	@ApiModelProperty(value = "销量")
	private int salesVolume;
	@ApiModelProperty(value = "收藏数量")
	private int collectionCount;
	@ApiModelProperty(value = "点赞数量")
	private int likeCount;
	@ApiModelProperty(value = "评论数量")
	private int commentCount;

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@NotNull(message = "店铺名称不能为空")
	@Length(min = 2, max = 150, message = "店铺名称的长度必须介于 2 和 150 之间")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getProductCategoryIds() {
		return productCategoryIds;
	}

	public void setProductCategoryIds(String productCategoryIds) {
		this.productCategoryIds = productCategoryIds;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public float getPraiseRate() {
		return praiseRate;
	}

	public void setPraiseRate(float praiseRate) {
		this.praiseRate = praiseRate;
	}

	public long getLogoId() {
		return logoId;
	}

	public void setLogoId(long logoId) {
		this.logoId = logoId;
	}

	public int getLongAreaCode() {
		return longAreaCode;
	}

	public void setLongAreaCode(int longAreaCode) {
		this.longAreaCode = longAreaCode;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public int getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

}
