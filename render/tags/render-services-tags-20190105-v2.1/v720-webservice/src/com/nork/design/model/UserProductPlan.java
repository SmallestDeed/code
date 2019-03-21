package com.nork.design.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class UserProductPlan implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private Integer userId;
	private String userName;
	private String productId;
	private String productName;
	private String productDesc;
	private double salePrice;
	private Integer planId;
	private String productPath;
	/**  创建时间  **/
	private Date gmtCreate;
	/** 产品大类 **/
	private String productTypeValue;
	/** 是否收藏 **/
	private Integer collectState;
	/** 收藏ID **/
	private Integer collectId;
	/** 产品code **/
	private String productCode;
	/** 产品大类code **/
	private String productType;
	/** 产品大类（唯一标识） **/
	private String productTypeKey;
	/**  产品类型（小类）  **/
	private Integer productSmallTypeValue;
	/**  产品类型code（小类）  **/
	private String productSmallTypeCode;
	/**  产品类型name（小类）  **/
	private String productSmallTypeName;
	/**  产品模型  **/
	private String u3dModelPath;
	/** 样板房配置文件文本 **/
	private String configContext;
	/** 品牌名称 **/
	private String brandName;
	/** 风格 **/
	private String proStyleId;
	private Integer colorId;
	private String productSpec;
	private String productTypeName;
	private String productTypeCode;
	private String productLength;
	private String productWidth;
	private String productHeight;
	private String materialPicId;
	private Integer spaceCommonId;
	private String materialPicPath;
	//材质图片s
	private String[] materialPicPaths;
	private String materialConfigPath;
	private Integer parentProductId;
	/** 规则json **/
	private Map<String,String> rulesMap;
	
	private String rootType;
	/*材质属性*/
	private Integer textureAttrValue; 
	/*铺设方式*/
	private String laymodes;
	/*状态:0:普通;1:该产品已删除*/
	private Integer status;
	/**材质宽*/
	private String textureWidth;
	/**材质高*/
	private String textureHeight;
	
	public String getTextureWidth() {
		return textureWidth;
	}
	public void setTextureWidth(String textureWidth) {
		this.textureWidth = textureWidth;
	}
	public String getTextureHeight() {
		return textureHeight;
	}
	public void setTextureHeight(String textureHeight) {
		this.textureHeight = textureHeight;
	}
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getTextureAttrValue() {
		return textureAttrValue;
	}
	public void setTextureAttrValue(Integer textureAttrValue) {
		this.textureAttrValue = textureAttrValue;
	}
	public String getLaymodes() {
		return laymodes;
	}
	public void setLaymodes(String laymodes) {
		this.laymodes = laymodes;
	}
	public String getRootType() {
		return rootType;
	}
	public void setRootType(String rootType) {
		this.rootType = rootType;
	}
	public String getProStyleId() {
		return proStyleId;
	}
	public void setProStyleId(String proStyleId) {
		this.proStyleId = proStyleId;
	}
	public Integer getColorId() {
		return colorId;
	}
	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}
	public String getProductSpec() {
		return productSpec;
	}
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public String getProductLength() {
		return productLength;
	}
	public void setProductLength(String productLength) {
		this.productLength = productLength;
	}
	public String getProductWidth() {
		return productWidth;
	}
	public void setProductWidth(String productWidth) {
		this.productWidth = productWidth;
	}
	public String getProductHeight() {
		return productHeight;
	}
	public void setProductHeight(String productHeight) {
		this.productHeight = productHeight;
	}
	
	public String getMaterialPicId() {
		return materialPicId;
	}
	public void setMaterialPicId(String materialPicId) {
		this.materialPicId = materialPicId;
	}
	public Integer getSpaceCommonId() {
		return spaceCommonId;
	}
	public void setSpaceCommonId(Integer spaceCommonId) {
		this.spaceCommonId = spaceCommonId;
	}
	public String getMaterialPicPath() {
		return materialPicPath;
	}
	public void setMaterialPicPath(String materialPicPath) {
		this.materialPicPath = materialPicPath;
	}
	public String[] getMaterialPicPaths() {
		return materialPicPaths;
	}
	public void setMaterialPicPaths(String[] materialPicPaths) {
		this.materialPicPaths = materialPicPaths;
	}
	public String getMaterialConfigPath() {
		return materialConfigPath;
	}
	public void setMaterialConfigPath(String materialConfigPath) {
		this.materialConfigPath = materialConfigPath;
	}
	
	public Integer getParentProductId() {
		return parentProductId;
	}
	public void setParentProductId(Integer parentProductId) {
		this.parentProductId = parentProductId;
	}
	public Map<String, String> getRulesMap() {
		return rulesMap;
	}
	public void setRulesMap(Map<String, String> rulesMap) {
		this.rulesMap = rulesMap;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	private String planName;

	private String gmtCreateStr;

	public String getGmtCreateStr() {
		return gmtCreateStr;
	}
	public void setGmtCreateStr(String gmtCreateStr) {
		this.gmtCreateStr = gmtCreateStr;
	}
	public String getProductPath() {
		return productPath;
	}
	public void setProductPath(String productPath) {
		this.productPath = productPath;
	}
	public String getProductTypeValue() {
		return productTypeValue;
	}
	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}
	public Integer getCollectState() {
		return collectState;
	}
	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}
	public Integer getCollectId() {
		return collectId;
	}
	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
	}

	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductTypeKey() {
		return productTypeKey;
	}
	public void setProductTypeKey(String productTypeKey) {
		this.productTypeKey = productTypeKey;
	}
	public String getProductSmallTypeName() {
		return productSmallTypeName;
	}
	public void setProductSmallTypeName(String productSmallTypeName) {
		this.productSmallTypeName = productSmallTypeName;
	}
	public String getProductSmallTypeCode() {
		return productSmallTypeCode;
	}

	public void setProductSmallTypeCode(String productSmallTypeCode) {
		this.productSmallTypeCode = productSmallTypeCode;
	}

	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
	}
	public String getU3dModelPath() {
		return u3dModelPath;
	}
	public void setU3dModelPath(String u3dModelPath) {
		this.u3dModelPath = u3dModelPath;
	}
	public String getConfigContext() {
		return configContext;
	}
	public void setConfigContext(String configContext) {
		this.configContext = configContext;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
