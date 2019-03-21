package com.sandu.goods.model.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sandu.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;

/***
 * 商品信息
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "商品信息", description = "商品信息")
public class GoodsVo extends BaseVo<GoodsVo> {
	private static final long serialVersionUID = -6659777844782328855L;
	
	private String spuCode;
	private String spuName;
	private int totalInventory;
	private int isPresell;
	private Date getTime;
	private float minPrice;
	private float maxPrice;
	private String bigType;
	private String smallType;
	private String picPath;
   
	public String getBigType() {
		return bigType;
	}

	public void setBigType(String bigType) {
		this.bigType = bigType;
	}

	public String getSmallType() {
		return smallType;
	}

	public void setSmallType(String smallType) {
		this.smallType = smallType;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}

	public String getSpuName() {
		return spuName;
	}

	public void setSpuName(String spuName) {
		this.spuName = spuName;
	}

	public int getTotalInventory() {
		return totalInventory;
	}

	public void setTotalInventory(int totalInventory) {
		this.totalInventory = totalInventory;
	}

	public int getIsPresell() {
		return isPresell;
	}

	public void setIsPresell(int isPresell) {
		this.isPresell = isPresell;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}


}
