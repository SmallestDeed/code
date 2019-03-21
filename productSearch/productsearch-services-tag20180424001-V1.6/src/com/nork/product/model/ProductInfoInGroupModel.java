/**
 * 
 */
package com.nork.product.model;

import java.util.Map;

/**
 * 
 * 产品组合中每个产品的一些常用信息，比如：产品编码、产品类型值、产品小类型值、售卖价格、长宽高等等
 * @author louxinhua
 *
 */
public class ProductInfoInGroupModel {

	/*
	 gpd.group_id, gpd.product_id, gpd.is_main, gpd.is_deleted,
			bp.brand_id, bp.product_code, bp.product_type_value, bp.product_small_type_value, bp.parent_id,
   			bp.sale_price,
   			bp.product_height, bp.product_width, bp.product_length, bp.material_pic_ids,
   			parent_bp.product_type_value as parent_product_type_value,
   			rm.model_path 
	 */
	
	private Integer groupID;
	
	private Integer productID;
	
	private Integer isMain;
	
	private Integer isDeleted;
	
	private Integer brandID;
	
	private String productCode;
	
	private String productTypeValue;
	
	private String productSmallTypeValue;
	
	private Integer parentID;
	
	private Double salePrice;
	
	private String productHeight;
	
	private String productWidth;
	
	private String productLength;
	
	private String materialPicIds;
	
	private String modelPath;
	
	private String parentProductTypeValue;

	/** windows使用u3d模型 **/
	private Integer windowsU3dModelId;
	/** 5.6版本windows使用u3d模型 **/
//	private Integer newWindowsU3dModelId;

	// :各个 type mark
	/**  产品大分类标示 **/
	private String productTypeMark;
	/**  产品小分类标示  **/
	private String productSmallTypeMark;
	
	// 产品属性
	/** 产品属性 **/
	private Map<String,String> propertyMap;
	
	private String brandName; // 品牌名字
	
	
	private Integer minHeight; // 最低高度
	
	private String splitTexturesInfo;
	
	// 2017-01-18
	private Long productStructureId;
	private Integer chartletProductModelId;
	// 2017-02-10
	/**
	 * 相机位置
	 */
	private String cameraView;
	/**
	 * 产品远景位置
	 */
	private String cameraLook;

	//产品序号
	private Integer productIndex;

	/**
	 * 组合产品中默认的多材质信息
	 */
	private String splitTexturesChooseInfo;
	
	//==========================================
	// Getter and Setter
	//==========================================

	
	public Integer getProductIndex() {
		return productIndex;
	}

	public String getSplitTexturesChooseInfo() {
		return splitTexturesChooseInfo;
	}

	public void setSplitTexturesChooseInfo(String splitTexturesChooseInfo) {
		this.splitTexturesChooseInfo = splitTexturesChooseInfo;
	}

	public void setProductIndex(Integer productIndex) {
		this.productIndex = productIndex;
	}

	public Integer getWindowsU3dModelId() {
		return windowsU3dModelId;
	}

	public void setWindowsU3dModelId(Integer windowsU3dModelId) {
		this.windowsU3dModelId = windowsU3dModelId;
	}

//	public Integer getNewWindowsU3dModelId() {
//		return newWindowsU3dModelId;
//	}
//
//	public void setNewWindowsU3dModelId(Integer newWindowsU3dModelId) {
//		this.newWindowsU3dModelId = newWindowsU3dModelId;
//	}

	public String getCameraView() {
		return cameraView;
	}

	public void setCameraView(String cameraView) {
		this.cameraView = cameraView;
	}

	public String getCameraLook() {
		return cameraLook;
	}

	public void setCameraLook(String cameraLook) {
		this.cameraLook = cameraLook;
	}
	
	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getBrandID() {
		return brandID;
	}

	public void setBrandID(Integer brandID) {
		this.brandID = brandID;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public String getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(String productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(String productHeight) {
		this.productHeight = productHeight;
	}

	public String getProductWidth() {
		return productWidth;
	}

	public void setProductWidth(String productWidth) {
		this.productWidth = productWidth;
	}

	public String getProductLength() {
		return productLength;
	}

	public void setProductLength(String productLength) {
		this.productLength = productLength;
	}

	public String getMaterialPicIds() {
		return materialPicIds;
	}

	public void setMaterialPicIds(String materialPicIds) {
		this.materialPicIds = materialPicIds;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public String getParentProductTypeValue() {
		return parentProductTypeValue;
	}

	public void setParentProductTypeValue(String parentProductTypeValue) {
		this.parentProductTypeValue = parentProductTypeValue;
	}

	public String getProductTypeMark() {
		return productTypeMark;
	}

	public void setProductTypeMark(String productTypeMark) {
		this.productTypeMark = productTypeMark;
	}

	

	public String getProductSmallTypeMark() {
		return productSmallTypeMark;
	}

	public void setProductSmallTypeMark(String productSmallTypeMark) {
		this.productSmallTypeMark = productSmallTypeMark;
	}

	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}

	public String getSplitTexturesInfo() {
		return splitTexturesInfo;
	}

	public void setSplitTexturesInfo(String splitTexturesInfo) {
		this.splitTexturesInfo = splitTexturesInfo;
	}

	public Long getProductStructureId() {
		return productStructureId;
	}

	public void setProductStructureId(Long productStructureId) {
		this.productStructureId = productStructureId;
	}

	public Integer getChartletProductModelId() {
		return chartletProductModelId;
	}

	public void setChartletProductModelId(Integer chartletProductModelId) {
		this.chartletProductModelId = chartletProductModelId;
	}
	
	
	
	
	
	
}
