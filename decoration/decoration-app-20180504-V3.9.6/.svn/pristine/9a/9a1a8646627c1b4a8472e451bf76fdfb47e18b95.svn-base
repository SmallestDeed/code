package com.nork.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;
import com.nork.productprops.model.ProductPropsSimple;

/**   
 * @Title: BaseProduct.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品库
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 17:01:37
 * @version V1.0   
 */
public class BaseProduct  extends Mapper implements Serializable{
private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer productId;
    private Integer hideId;
	/**  系统编码  **/
	private String sysCode;
	/**  创建者  **/
	private String creator;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  修改人  **/
	private String modifier;
	/**  修改时间  **/
	private Date gmtModified;
	/**  是否删除  **/
	private Integer isDeleted;
	/**  产品编码  **/
	private String productCode;
	/**  产品名称  **/
	private String productName;
	/**   饰品名称  **/
	private String decorationName;
	/**  产品品牌  **/
	private Integer brandId;
	/**  产品风格  **/
	private Integer proStyleId;
	/**  产品规格  **/
	private String productSpec;
	/**  产品颜色  **/
	private Integer colorId;
	/**  产品长度  **/
	private String productLength;
	/**  产品宽度  **/
	private String productWidth;
	/**  产品高度  **/
	private String productHeight;
	/**  销售价格  **/
	private Double salePrice;
	/**  图片缩略图id  **/
	private Integer picId;
	
	private String picPath;
	/**  3D模型id  **/
	private Integer modelId;
	
	private String filePath;
	
	private String maxModelPath;
	/**  产品描述  **/
	private String productDesc;
	/**  产品小图片  **/
	private String picIds;
	
	private String rootType;
	
	//0、实体墙  1、背景墙 2、门框
	private Integer bgWall;
	//模型path
	private String productModelPath;
	/** 产品属性 **/
	private Map<String,String> propertyMap;
	
	/** 全铺长度  **/
	private String fullPaveLength;
	/** fbx状态 **/
	private Integer fbxState;
	/**  fbx文件ID  **/
	private Integer fbxFileId;
	/**  fbx贴图多个  **/
	private String fbxTexture;
	
	/** 产品价格单位 */
	private Integer salePriceValue;
	
	/**
	 * 前段传来的 拼花 json 传
	 */
	private Integer spellingFlowerFileId;
	
	/**产品价格单位名称*/
	private String salePriceValueName;
	
	//产品材质描述
	private String productMaterialDesc;
	//产品色系
	private String colorsLongCode;
	//色系排序
	private Integer colorsOrdering;
	/**携带得白模产品集合*/
	private Map<String,CategoryProductResult> basicModelMap;
	/**产品风格*/
	private String productStyleIdInfo;
	/**产品风格(list)*/
	private List<String> productStyleInfoList;
	
 
	private List<String> list;
	
	private List<Integer> resIdList;
	
	private Integer dicSmallTypeValue;

	/*  是否显示U3D模型 0:不显示,1:显示  */
	private Integer showU3dModel;
	
	/*  天花布局标识 */
	private String productSmallpoxIdentify;
	
	/**
	 * 墙体类型
	 */
	private String wallType;
	
	/**
	 * 过滤属性信息(用于组合/单品匹配过滤属性)
	 */
	private List<ProductPropsSimple> productFilterPropList;

	//系列ID
	private Integer seriesId = new Integer(0);

	/**
	 * 设置款式排序
	 */
	private Integer orderStyleId;
	
	/**
	 * 设置产品型号排序
	 */
	private String orderProductModelNumber;
	
	/**
	 * 设置产品小类排序
	 */
	private Integer orderProductSmallTypeValue;
	
	public Integer getOrderStyleId() {
		return orderStyleId;
	}

	public void setOrderStyleId(Integer orderStyleId) {
		this.orderStyleId = orderStyleId;
	}

	public String getOrderProductModelNumber() {
		return orderProductModelNumber;
	}

	public void setOrderProductModelNumber(String orderProductModelNumber) {
		this.orderProductModelNumber = orderProductModelNumber;
	}

	public Integer getOrderProductSmallTypeValue() {
		return orderProductSmallTypeValue;
	}

	public void setOrderProductSmallTypeValue(Integer orderProductSmallTypeValue) {
		this.orderProductSmallTypeValue = orderProductSmallTypeValue;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	/**
	 * 是否显示无品牌开关(0:默认显示无品牌,1:不显示无品牌)
	 * add by huangsongbo 2017.11.4
	 */
	private Integer statusShowWu;

	/*品牌行业list*/
	private List<BrandIndustryModel> brandIndustryModelList;

	public List<BrandIndustryModel> getBrandIndustryModelList() {
		return brandIndustryModelList;
	}

	public void setBrandIndustryModelList(List<BrandIndustryModel> brandIndustryModelList) {
		this.brandIndustryModelList = brandIndustryModelList;
	}

	private Boolean isShrink;
	public Boolean getIsShrink() {
		return isShrink;
	}
	public void setIsShrink(Boolean isShrink) {
		this.isShrink = isShrink;
	}
	
	public Integer getSpellingFlowerFileId() {
		return spellingFlowerFileId;
	}

	public void setSpellingFlowerFileId(Integer spellingFlowerFileId) {
		this.spellingFlowerFileId = spellingFlowerFileId;
	}

	public Integer getStatusShowWu() {
		return statusShowWu;
	}

	public void setStatusShowWu(Integer statusShowWu) {
		this.statusShowWu = statusShowWu;
	}

	public List<ProductPropsSimple> getProductFilterPropList() {
		return productFilterPropList;
	}

	public void setProductFilterPropList(List<ProductPropsSimple> productFilterPropList) {
		this.productFilterPropList = productFilterPropList;
	}

	public String getWallType() {
		return wallType;
	}

	public void setWallType(String wallType) {
		this.wallType = wallType;
	}

	public String getProductSmallpoxIdentify() {
		return productSmallpoxIdentify;
	}

	public void setProductSmallpoxIdentify(String productSmallpoxIdentify) {
		this.productSmallpoxIdentify = productSmallpoxIdentify;
	}

	public Integer getShowU3dModel() {
		return showU3dModel;
	}

	public void setShowU3dModel(Integer showU3dModel) {
		this.showU3dModel = showU3dModel;
	}

	public Integer getDicSmallTypeValue() {
		return dicSmallTypeValue;
	}

	public void setDicSmallTypeValue(Integer dicSmallTypeValue) {
		this.dicSmallTypeValue = dicSmallTypeValue;
	}

	public List<Integer> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<Integer> resIdList) {
		this.resIdList = resIdList;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	/**属性信息(eg:BedWidth,1,99,180;size,0,99,2)*/
	private String propInfo;
	
	/*设计方案产品序号*/
	private Integer productIndex;
	/*设计方案产品id*/
	private Integer designPlanProductId;
	/*数据字典的att1*/
	private String att1;
	
	public String getAtt1() {
		return att1;
	}

	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	public Integer getProductIndex() {
		return productIndex;
	}

	public void setProductIndex(Integer productIndex) {
		this.productIndex = productIndex;
	}

	public Integer getDesignPlanProductId() {
		return designPlanProductId;
	}

	public void setDesignPlanProductId(Integer designPlanProductId) {
		this.designPlanProductId = designPlanProductId;
	}

	public String getPropInfo() {
		return propInfo;
	}

	public void setPropInfo(String propInfo) {
		this.propInfo = propInfo;
	}

	public List<String> getProductStyleInfoList() {
		return productStyleInfoList;
	}

	public void setProductStyleInfoList(List<String> productStyleInfoList) {
		this.productStyleInfoList = productStyleInfoList;
	}

	public String getProductStyleIdInfo() {
		return productStyleIdInfo;
	}

	public void setProductStyleIdInfo(String productStyleIdInfo) {
		this.productStyleIdInfo = productStyleIdInfo;
	}

	public Map<String, CategoryProductResult> getBasicModelMap() {
		return basicModelMap;
	}

	public void setBasicModelMap(Map<String, CategoryProductResult> basicModelMap) {
		this.basicModelMap = basicModelMap;
	}
	public String getColorsLongCode() {
		return colorsLongCode;
	}

	public void setColorsLongCode(String colorsLongCode) {
		this.colorsLongCode = colorsLongCode;
	}

	public Integer getColorsOrdering() {
		return colorsOrdering;
	}

	public void setColorsOrdering(Integer colorsOrdering) {
		this.colorsOrdering = colorsOrdering;
	}

	public String getProductMaterialDesc() {
		return productMaterialDesc;
	}

	public void setProductMaterialDesc(String productMaterialDesc) {
		this.productMaterialDesc = productMaterialDesc;
	}
	
	public Integer getSalePriceValue() {
		return salePriceValue;
	}

	public void setSalePriceValue(Integer salePriceValue) {
		this.salePriceValue = salePriceValue;
	}

	public String getSalePriceValueName() {
		return salePriceValueName;
	}

	public void setSalePriceValueName(String salePriceValueName) {
		this.salePriceValueName = salePriceValueName;
	}
 
	
	public Integer getFbxState() {
		return fbxState;
	}

	public void setFbxState(Integer fbxState) {
		this.fbxState = fbxState;
	}

	public Integer getFbxFileId() {
		return fbxFileId;
	}

	public void setFbxFileId(Integer fbxFileId) {
		this.fbxFileId = fbxFileId;
	}

	public String getFbxTexture() {
		return fbxTexture;
	}

	public void setFbxTexture(String fbxTexture) {
		this.fbxTexture = fbxTexture;
	}

 
	public String getFullPaveLength() {
		return fullPaveLength;
	}

	public void setFullPaveLength(String fullPaveLength) {
		this.fullPaveLength = fullPaveLength;
	}
	
	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public String getProductModelPath() {
		return productModelPath;
	}

	public void setProductModelPath(String productModelPath) {
		this.productModelPath = productModelPath;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	private String[] smallPicPath;
	/**  产品材质  **/
	private String materialPicIds;
	/**  产品房间归属  **/
	private String houseTypeValues;
	/**  产品类型  **/
	private String productTypeValue;
	/**  u3d模型  **/
	private String u3dModelId;
	/** 同类型合并（合并产品ID） **/
	private String mergeProductIds;
	/** 饰品id **/
	private String decorationId;
	/**  设计师id **/
	private Integer designerId;
	/** 建模师id **/
	private Integer modelingId;
	/** 渲染师id **/
	private Integer renderingId;
	private Integer spaceComonId;
	/** 产品短编码 **/
	private String productShortCode;
	/** 原始产品编码 **/
	private String originalProductCode;
	/** 编码序号 **/
	private Integer codeNumber;
	/** 硬装产品新增存的白模id **/
	private String bmIds;
    /**白模名称**/
	private String bmName;
    //同类型产品(标识同一类)
	private Integer parentId;
	//材质球配置文件id(关联res_file)
	private Integer materialFileId;
	
	private String materialFilePath;
	//材质图片s
	private String[] materialPicPaths;
	//材质球配置文件地址
	private String materialConfigPath;
	/**  3dMax材质图片  **/
	private String material3dPicIds;
	
	private String[] material3dPicPaths;
	/**  产品大类型  **/
	private String productTypeCode;
	//序列号配置品牌ids
	private List<String> configBrandIdList;
	//序列号配置大类values
	private List<String> configTypeValueList;
	//序列号配置小类values
	private List<String> configSmallTypeIdList;
	//序列号配置产品ids
	private List<String> configProductIdList;
	//设备终端号(查询产品时过滤产品的参数)
	private String terminalImei;
	/** 模型长度 **/
	private int modelLength;
	/** 模型宽度 **/
	private int modelWidth;
	/** 模型高度 **/
	private int modelHeight;
	/** 模型最小高度 **/
	private int modelMinHeight;
	/*材质属性*/
	private Integer textureAttrValue; 
	/*铺设方式*/
	private String laymodes;
	/** 配置文件ID **/
	private Integer configId;
	
	/**  产品型号  **/
	private String  productModelNumber;
	//产品最小高度（天花用到）
	private String minHeight;
	
	private List<SplitTextureDTO> splitTexturesChoose;
	private List<SplitTextureDTO> splitTexturesInfoList;
	/*查询条件,产品idList*/
	private List<Integer> productIdList;

	//状态集合
	private List<Integer> putawayStateList;

	//款式ID
	private Integer styleId;
	//是否是背景墙
	private boolean isBeijing;
	//是否是需拉伸产品
	private boolean isStretch;
	/**
	 * 区域款式
	 * 尺寸代码
	 * 是否是标准 1 是， 0 否
	 */
	private String regionMark;
	private String measureCode;
	private Integer isStandard;

	//伸缩产品长高过滤产品用
	private Integer startLength;
	private Integer endLength;
	private String stretchHeight;
	private List<String> attributeConditionList;
	private Integer attributeConditionSize;

	public List<SplitTextureDTO> getSplitTexturesInfoList() {
		return splitTexturesInfoList;
	}

	public void setSplitTexturesInfoList(List<SplitTextureDTO> splitTexturesInfoList) {
		this.splitTexturesInfoList = splitTexturesInfoList;
	}

	public Integer getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(Integer isStandard) {
		this.isStandard = isStandard;
	}

	public List<String> getAttributeConditionList() {
		return attributeConditionList;
	}

	public void setAttributeConditionList(List<String> attributeConditionList) {
		this.attributeConditionList = attributeConditionList;
	}

	public Integer getAttributeConditionSize() {
		return attributeConditionSize;
	}

	public void setAttributeConditionSize(Integer attributeConditionSize) {
		this.attributeConditionSize = attributeConditionSize;
	}

	public String getRegionMark() {
		return regionMark;
	}

	public void setRegionMark(String regionMark) {
		this.regionMark = regionMark;
	}

	public String getMeasureCode() {
		return measureCode;
	}

	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}

	public Integer getStartLength() {
		return startLength;
	}

	public void setStartLength(Integer startLength) {
		this.startLength = startLength;
	}

	public Integer getEndLength() {
		return endLength;
	}

	public void setEndLength(Integer endLength) {
		this.endLength = endLength;
	}

	public String getStretchHeight() {
		return stretchHeight;
	}

	public void setStretchHeight(String stretchHeight) {
		this.stretchHeight = stretchHeight;
	}

	public boolean isBeijing() {
		return isBeijing;
	}


	public void setBeijing(boolean beijing) {
		isBeijing = beijing;
	}

	public boolean isStretch() {
		return isStretch;
	}

	public void setStretch(boolean stretch) {
		isStretch = stretch;
	}

	public Integer getStyleId() {
		return styleId;
	}

	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}

	public List<Integer> getPutawayStateList() {
		return putawayStateList;
	}

	public void setPutawayStateList(List<Integer> putawayStateList) {
		this.putawayStateList = putawayStateList;
	}


	public List<Integer> getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(List<Integer> productIdList) {
		this.productIdList = productIdList;
	}

	public List<SplitTextureDTO> getSplitTexturesChoose() {
		return splitTexturesChoose;
	}

	public void setSplitTexturesChoose(List<SplitTextureDTO> splitTexturesChoose) {
		this.splitTexturesChoose = splitTexturesChoose;
	}

	public String getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(String minHeight) {
		this.minHeight = minHeight;
	}

	public String getProductModelNumber() {
		return productModelNumber;
	}

	public void setProductModelNumber(String productModelNumber) {
		this.productModelNumber = productModelNumber;
	}
 
	public String getMaxModelPath() {
		return maxModelPath;
	}

	public void setMaxModelPath(String maxModelPath) {
		this.maxModelPath = maxModelPath;
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

	public String[] getMaterial3dPicPaths() {
		return material3dPicPaths;
	}

	public void setMaterial3dPicPaths(String[] material3dPicPaths) {
		this.material3dPicPaths = material3dPicPaths;
	}
	
	public String getMaterialFilePath() {
		return materialFilePath;
	}

	public void setMaterialFilePath(String materialFilePath) {
		this.materialFilePath = materialFilePath;
	}
	
	public String[] getSmallPicPath() {
		return smallPicPath;
	}

	public void setSmallPicPath(String[] smallPicPath) {
		this.smallPicPath = smallPicPath;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public Integer getBgWall() {
		return bgWall;
	}

	public void setBgWall(Integer bgWall) {
		this.bgWall = bgWall==null?0:bgWall;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public Integer getHideId() {
		return hideId;
	}

	public void setHideId(Integer hideId) {
		this.hideId = hideId;
	}
	public String getTerminalImei() {
		return terminalImei;
	}

	public void setTerminalImei(String terminalImei) {
		this.terminalImei = terminalImei;
	}

	public List<String> getConfigBrandIdList() {
		return configBrandIdList;
	}

	public void setConfigBrandIdList(List<String> configBrandIdList) {
		this.configBrandIdList = configBrandIdList;
	}

	public List<String> getConfigTypeValueList() {
		return configTypeValueList;
	}

	public void setConfigTypeValueList(List<String> configTypeValueList) {
		this.configTypeValueList = configTypeValueList;
	}

	public List<String> getConfigSmallTypeIdList() {
		return configSmallTypeIdList;
	}

	public void setConfigSmallTypeIdList(List<String> configSmallTypeIdList) {
		this.configSmallTypeIdList = configSmallTypeIdList;
	}

	public List<String> getConfigProductIdList() {
		return configProductIdList;
	}

	public void setConfigProductIdList(List<String> configProductIdList) {
		this.configProductIdList = configProductIdList;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getMaterial3dPicIds() {
		return material3dPicIds;
	}

	public void setMaterial3dPicIds(String material3dPicIds) {
		this.material3dPicIds = material3dPicIds;
	}

	public Integer getMaterialFileId() {
		return materialFileId;
	}
	//同类型产品(标识同一类)
	private Integer parentProductId;

	public void setMaterialFileId(Integer materialFileId) {
		this.materialFileId = materialFileId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	//样板房ID
	private Integer designTempletId;

	public Integer getDesignTempletId() {
		return designTempletId;
	}

	public void setDesignTempletId(Integer designTempletId) {
		this.designTempletId = designTempletId;
	}

	public String getBmName() {
		return bmName;
	}

	public void setBmName(String bmName) {
		this.bmName = bmName;
	}

	public String getBmIds() {
		return bmIds;
	}

	public void setBmIds(String bmIds) {
		this.bmIds = bmIds;
	}

	public Integer getCodeNumber() {
		return codeNumber;
	}

	public void setCodeNumber(Integer codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String getProductShortCode() {
		return productShortCode;
	}

	public void setProductShortCode(String productShortCode) {
		this.productShortCode = productShortCode;
	}

	public String getOriginalProductCode() {
		return originalProductCode;
	}

	public void setOriginalProductCode(String originalProductCode) {
		this.originalProductCode = originalProductCode;
	}

	public Integer getSpaceComonId() {
		return spaceComonId;
	}

	public void setSpaceComonId(Integer spaceComonId) {
		this.spaceComonId = spaceComonId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
	public Integer getDesignerId() {
		return designerId;
	}

	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}

	public Integer getModelingId() {
		return modelingId;
	}

	public void setModelingId(Integer modelingId) {
		this.modelingId = modelingId;
	}

	public Integer getRenderingId() {
		return renderingId;
	}

	public void setRenderingId(Integer renderingId) {
		this.renderingId = renderingId;
	}

	/**  时间备用1  **/
	private Date putawayModified;
	/**  时间备用2  **/
	private Date dateAtt2;
	/**  产品类型（小类）  **/
	private Integer productSmallTypeValue;
	/**  产品小分类标示  **/
	private String productSmallTypeMark;
	/**  产品大分类标示 **/
	private String productTypeMark;
	/**  产品类型code（小类）  **/
	private String productSmallTypeCode;
	/**  产品类型name（小类）  **/
	private String productSmallTypeName;
	/**  上架状态  0未上架，1已上架  **/
	private Integer putawayState;
	/**  数字备用1  **/
	private Double numAtt3;
	/**  数字备用2  **/
	private Double numAtt4;
	/**  备注  **/
	private String remark;
	/**  U3D模型路径  **/
	private String u3dModelPath;
	/** ios使用u3d模型 **/
	private Integer iosU3dModelId;
	/** android使用u3d模型 **/
	private Integer androidU3dModelId;
	/** macBook使用u3d模型 **/
	private Integer macBookU3dModelId;
	/** windows使用u3d模型 **/
	private Integer windowsU3dModelId;
	/** ipad使用u3d模型 **/
	private Integer ipadU3dModelId;
	private String iosU3dModelPath;
	/** android使用u3d模型 **/
	private String androidU3dModelPath;
	/** macBook使用u3d模型 **/
	private String macBookU3dModelPath;
	/** windows使用u3d模型 **/
	private String windowsU3dModelPath;
	/** ipad使用u3d模型 **/
	private String ipadU3dModelPath;

	
	/*可能是任何模型的路径  通过传入的mediaType决定  用于 getDataAndModel 方法*/
	private String mediaTypeModelPath;
	
	private String mediaType;

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaTypeModelPath() {
		return mediaTypeModelPath;
	}

	public void setMediaTypeModelPath(String mediaTypeModelPath) {
		this.mediaTypeModelPath = mediaTypeModelPath;
	}

	public String getIosU3dModelPath() {
		return iosU3dModelPath;
	}

	public void setIosU3dModelPath(String iosU3dModelPath) {
		this.iosU3dModelPath = iosU3dModelPath;
	}

	public String getAndroidU3dModelPath() {
		return androidU3dModelPath;
	}

	public void setAndroidU3dModelPath(String androidU3dModelPath) {
		this.androidU3dModelPath = androidU3dModelPath;
	}

	public String getMacBookU3dModelPath() {
		return macBookU3dModelPath;
	}

	public void setMacBookU3dModelPath(String macBookU3dModelPath) {
		this.macBookU3dModelPath = macBookU3dModelPath;
	}

	public String getWindowsU3dModelPath() {
		return windowsU3dModelPath;
	}

	public void setWindowsU3dModelPath(String windowsU3dModelPath) {
		this.windowsU3dModelPath = windowsU3dModelPath;
	}


	public String getIpadU3dModelPath() {
		return ipadU3dModelPath;
	}

	public void setIpadU3dModelPath(String ipadU3dModelPath) {
		this.ipadU3dModelPath = ipadU3dModelPath;
	}
	private Integer userId;
	
    private String brandName;
    
    private String brandReferred;
	public String getBrandReferred() {
		return brandReferred;
	}

	public void setBrandReferred(String brandReferred) {
		this.brandReferred = brandReferred;
	}
    
    private String houseType;
    
    private String productType;
    
    private String productStyle;
    
    private String productColor;
    
    private String productPicPath;
    
    private String productSmallType;
    
    List smallPiclist = new ArrayList(); 
    
    //图片列表缩略图集合
    List thumbnailList = new ArrayList(); 

    public List getThumbnailList() {
		return thumbnailList;
	}

	public void setThumbnailList(List thumbnailList) {
		this.thumbnailList = thumbnailList;
	}

	List colorlist = new ArrayList(); 
    
    List materiallist = new ArrayList();
    
    /*List attributelist = new ArrayList();*/
    
    Map<String,List<ProductAttribute>> map = new HashMap<String,List<ProductAttribute>>();
    
    
    
	public Map<String, List<ProductAttribute>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<ProductAttribute>> map) {
		this.map = map;
	}

	/**public List getAttributelist() {
		return attributelist;
	}

	public void setAttributelist(List attributelist) {
		this.attributelist = attributelist;
	}*/

	private Integer collectState;//收藏状态
    
    private String putawayStateName;//上架狀態

	/**
	 * 商品类型唯一标识
	 */
	private String productTypeKey;
	/**
	 * 商品颜色RGB值
	 */
	private String productColorKey;
	/** 品牌（多品牌逗号分隔） **/
	private List<String> brandIds;
	
	private Date tomorrow;
	/*拆分材质信息*/
	private String splitTexturesInfo = null;
	/*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
	private Integer isSplit=new Integer(0);
	/**其他小类显示全部(不经过授权码过滤)*/
	private List<Integer> smallTypeValueListForShowAll;

	public List<Integer> getSmallTypeValueListForShowAll() {
		return smallTypeValueListForShowAll;
	}

	public void setSmallTypeValueListForShowAll(List<Integer> smallTypeValueListForShowAll) {
		this.smallTypeValueListForShowAll = smallTypeValueListForShowAll;
	}

	public Integer getIsSplit() {
		return isSplit;
	}

	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}

	public String getSplitTexturesInfo() {
		return splitTexturesInfo;
	}

	public void setSplitTexturesInfo(String splitTexturesInfo) {
		this.splitTexturesInfo = splitTexturesInfo;
	}

	public Date getTomorrow() {
		return tomorrow;
	}

	public void setTomorrow(Date tomorrow) {
		this.tomorrow = tomorrow;
	}

	public String getDecorationId() {
		return decorationId;
	}

	public void setDecorationId(String decorationId) {
		this.decorationId = decorationId;
	}

	public String getMergeProductIds() {
		return mergeProductIds;
	}

	public void setMergeProductIds(String mergeProductIds) {
		this.mergeProductIds = mergeProductIds;
	}

	public  String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}
	public  String getCreator() {
		return creator;
	}
	public void setCreator(String creator){
		this.creator = creator;
	}
	public  Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}
	public  String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	public  Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}
	public  Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	public  String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode){
		this.productCode = productCode;
	}
	public  String getProductName() {
		return productName;
	}
	public void setProductName(String productName){
		this.productName = productName;
	}
	
	public String getDecorationName() {
		return decorationName;
	}

	public void setDecorationName(String decorationName) {
		this.decorationName = decorationName;
	}

	public  Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId){
		this.brandId = brandId;
	}
	public  Integer getProStyleId() {
		return proStyleId;
	}
	public void setProStyleId(Integer proStyleId){
		this.proStyleId = proStyleId;
	}
	public  String getProductSpec() {
		return productSpec;
	}
	public void setProductSpec(String productSpec){
		this.productSpec = productSpec;
	}
	public  Integer getColorId() {
		return colorId;
	}
	public void setColorId(Integer colorId){
		this.colorId = colorId;
	}
	public  String getProductLength() {
		return productLength;
	}
	public void setProductLength(String productLength){
		this.productLength = productLength;
	}
	public  String getProductWidth() {
		return productWidth;
	}
	public void setProductWidth(String productWidth){
		this.productWidth = productWidth;
	}
	public  String getProductHeight() {
		return productHeight;
	}
	public void setProductHeight(String productHeight){
		this.productHeight = productHeight;
	}
	public  Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice){
		this.salePrice = salePrice;
	}
	public  Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId){
		this.picId = picId;
	}
	public  Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId){
		this.modelId = modelId;
	}
	public  String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc){
		this.productDesc = productDesc;
	}

	public String getPicIds() {
		return picIds;
	}

	public void setPicIds(String picIds) {
		this.picIds = picIds;
	}

	public String getMaterialPicIds() {
		return materialPicIds;
	}

	public void setMaterialPicIds(String materialPicIds) {
		this.materialPicIds = materialPicIds;
	}

	public String getHouseTypeValues() {
		return houseTypeValues;
	}

	public void setHouseTypeValues(String houseTypeValues) {
		this.houseTypeValues = houseTypeValues;
	}

	public String getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(String productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
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

	public String getProductSmallTypeMark() {
		return productSmallTypeMark;
	}

	public void setProductSmallTypeMark(String productSmallTypeMark) {
		this.productSmallTypeMark = productSmallTypeMark;
	}

	public String getProductTypeMark() {
		return productTypeMark;
	}

	public void setProductTypeMark(String productTypeMark) {
		this.productTypeMark = productTypeMark;
	}

	public String getU3dModelId() {
		return u3dModelId;
	}

	public void setU3dModelId(String u3dModelId) {
		this.u3dModelId = u3dModelId;
	}

	public Date getPutawayModified() {
		return putawayModified;
	}

	public void setPutawayModified(Date putawayModified) {
		this.putawayModified = putawayModified;
	}

	public  Date getDateAtt2() {
		return dateAtt2;
	}
	public void setDateAtt2(Date dateAtt2){
		this.dateAtt2 = dateAtt2;
	}

	public Integer getPutawayState() {
		return putawayState;
	}

	public void setPutawayState(Integer putawayState) {
		this.putawayState = putawayState;
	}

	public  Double getNumAtt3() {
		return numAtt3;
	}
	public void setNumAtt3(Double numAtt3){
		this.numAtt3 = numAtt3;
	}
	public  Double getNumAtt4() {
		return numAtt4;
	}
	public void setNumAtt4(Double numAtt4){
		this.numAtt4 = numAtt4;
	}
	public  String getRemark() {
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getU3dModelPath() {
		return u3dModelPath;
	}
	public void setU3dModelPath(String u3dModelPath) {
		this.u3dModelPath = u3dModelPath;
	}
	public Integer getIosU3dModelId() {
		return iosU3dModelId;
	}
	public void setIosU3dModelId(Integer iosU3dModelId) {
		this.iosU3dModelId = iosU3dModelId;
	}
	public Integer getAndroidU3dModelId() {
		return androidU3dModelId;
	}
	public void setAndroidU3dModelId(Integer androidU3dModelId) {
		this.androidU3dModelId = androidU3dModelId;
	}
	public Integer getMacBookU3dModelId() {
		return macBookU3dModelId;
	}
	public void setMacBookU3dModelId(Integer macBookU3dModelId) {
		this.macBookU3dModelId = macBookU3dModelId;
	}
	public Integer getWindowsU3dModelId() {
		return windowsU3dModelId;
	}
	public void setWindowsU3dModelId(Integer windowsU3dModelId) {
		this.windowsU3dModelId = windowsU3dModelId;
	}
	public Integer getIpadU3dModelId() {
		return ipadU3dModelId;
	}
	public void setIpadU3dModelId(Integer ipadU3dModelId) {
		this.ipadU3dModelId = ipadU3dModelId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<String> getBrandIds() {
		return brandIds;
	}
	public void setBrandIds(List<String> brandIds) {
		this.brandIds = brandIds;
	}

	@Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BaseProduct other = (BaseProduct) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getProductCode() == null ? other.getProductCode() == null : this.getProductCode().equals(other.getProductCode()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getBrandId() == null ? other.getBrandId() == null : this.getBrandId().equals(other.getBrandId()))
            && (this.getProStyleId() == null ? other.getProStyleId() == null : this.getProStyleId().equals(other.getProStyleId()))
            && (this.getProductSpec() == null ? other.getProductSpec() == null : this.getProductSpec().equals(other.getProductSpec()))
            && (this.getColorId() == null ? other.getColorId() == null : this.getColorId().equals(other.getColorId()))
            && (this.getProductLength() == null ? other.getProductLength() == null : this.getProductLength().equals(other.getProductLength()))
            && (this.getProductWidth() == null ? other.getProductWidth() == null : this.getProductWidth().equals(other.getProductWidth()))
            && (this.getProductHeight() == null ? other.getProductHeight() == null : this.getProductHeight().equals(other.getProductHeight()))
            && (this.getSalePrice() == null ? other.getSalePrice() == null : this.getSalePrice().equals(other.getSalePrice()))
            && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
            && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()))
            && (this.getProductDesc() == null ? other.getProductDesc() == null : this.getProductDesc().equals(other.getProductDesc()))
            && (this.getPicIds() == null ? other.getPicIds() == null : this.getPicIds().equals(other.getPicIds()))
            && (this.getMaterialPicIds() == null ? other.getMaterialPicIds() == null : this.getMaterialPicIds().equals(other.getMaterialPicIds()))
            && (this.getHouseTypeValues() == null ? other.getHouseTypeValues() == null : this.getHouseTypeValues().equals(other.getHouseTypeValues()))
            && (this.getProductSmallTypeValue() == null ? other.getProductSmallTypeValue() == null : this.getProductSmallTypeValue().equals(other.getProductSmallTypeValue()))
            && (this.getProductSmallTypeMark() == null ? other.getProductSmallTypeMark() == null : this.getProductSmallTypeMark().equals(other.getProductSmallTypeMark()))
            && (this.getProductTypeMark() == null ? other.getProductTypeMark() == null : this.getProductTypeMark().equals(other.getProductTypeMark()))
            && (this.getU3dModelId() == null ? other.getU3dModelId() == null : this.getU3dModelId().equals(other.getU3dModelId()))
            && (this.getMergeProductIds() == null ? other.getMergeProductIds() == null : this.getMergeProductIds().equals(other.getMergeProductIds()))
            && (this.getPutawayModified() == null ? other.getPutawayModified() == null : this.getPutawayModified().equals(other.getPutawayModified()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
            && (this.getProductTypeValue() == null ? other.getProductTypeValue() == null : this.getProductTypeValue().equals(other.getProductTypeValue()))
            && (this.getPutawayState() == null ? other.getPutawayState() == null : this.getPutawayState().equals(other.getPutawayState()))
            && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
            && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
			&& (this.getU3dModelPath() == null ? other.getU3dModelPath() == null : this.getU3dModelPath().equals(other.getU3dModelPath()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getProductCode() == null) ? 0 : getProductCode().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getBrandId() == null) ? 0 : getBrandId().hashCode());
        result = prime * result + ((getProStyleId() == null) ? 0 : getProStyleId().hashCode());
        result = prime * result + ((getProductSpec() == null) ? 0 : getProductSpec().hashCode());
        result = prime * result + ((getColorId() == null) ? 0 : getColorId().hashCode());
        result = prime * result + ((getProductLength() == null) ? 0 : getProductLength().hashCode());
        result = prime * result + ((getProductWidth() == null) ? 0 : getProductWidth().hashCode());
        result = prime * result + ((getProductHeight() == null) ? 0 : getProductHeight().hashCode());
        result = prime * result + ((getSalePrice() == null) ? 0 : getSalePrice().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        result = prime * result + ((getProductDesc() == null) ? 0 : getProductDesc().hashCode());
        result = prime * result + ((getPicIds() == null) ? 0 : getPicIds().hashCode());
        result = prime * result + ((getMaterialPicIds() == null) ? 0 : getMaterialPicIds().hashCode());
        result = prime * result + ((getHouseTypeValues() == null) ? 0 : getHouseTypeValues().hashCode());
        result = prime * result + ((getProductSmallTypeValue() == null) ? 0 : getProductSmallTypeValue().hashCode());
        result = prime * result + ((getProductSmallTypeMark() == null) ? 0 : getProductSmallTypeMark().hashCode());
        result = prime * result + ((getProductTypeMark() == null) ? 0 : getProductTypeMark().hashCode());
        result = prime * result + ((getU3dModelId() == null) ? 0 : getU3dModelId().hashCode());
        result = prime * result + ((getMergeProductIds() == null) ? 0 : getMergeProductIds().hashCode());
        result = prime * result + ((getPutawayModified() == null) ? 0 : getPutawayModified().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getProductTypeValue() == null) ? 0 : getProductTypeValue().hashCode());
        result = prime * result + ((getPutawayState() == null) ? 0 : getPutawayState().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
		result = prime * result + ((getU3dModelPath() == null) ? 0 : getU3dModelPath().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    public BaseProduct copy(){
       BaseProduct obj =  new BaseProduct();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setProductCode(this.productCode);
       obj.setProductName(this.productName);
       obj.setBrandId(this.brandId);
       obj.setProStyleId(this.proStyleId);
       obj.setProductSpec(this.productSpec);
       obj.setColorId(this.colorId);
       obj.setProductLength(this.productLength);
       obj.setProductWidth(this.productWidth);
       obj.setProductHeight(this.productHeight);
       obj.setSalePrice(this.salePrice);
       obj.setPicId(this.picId);
       obj.setModelId(this.modelId);
       obj.setProductDesc(this.productDesc);
       obj.setPicIds(this.picIds);
       obj.setMaterialPicIds(this.materialPicIds);
       obj.setHouseTypeValues(this.houseTypeValues);
       obj.setProductTypeValue(this.productTypeValue);
       obj.setU3dModelId(this.u3dModelId);
       obj.setMergeProductIds(this.mergeProductIds);
       obj.setPutawayModified(this.putawayModified);
       obj.setDateAtt2(this.dateAtt2);
       obj.setProductSmallTypeValue(this.productSmallTypeValue);
       obj.setProductSmallTypeMark(this.productSmallTypeMark);
       obj.setProductTypeMark(this.productTypeMark);
       obj.setPutawayState(this.putawayState);
       obj.setNumAtt3(this.numAtt3);
       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);
	   obj.setU3dModelPath(this.u3dModelPath);
       return obj;
    }
    
     /**获取对象的map**/
    public Map toMap(){
       Map map =  new HashMap();
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("productCode",this.productCode);
       map.put("productName",this.productName);
       map.put("brandId",this.brandId);
       map.put("proStyleId",this.proStyleId);
       map.put("productSpec",this.productSpec);
       map.put("colorId",this.colorId);
       map.put("productLength",this.productLength);
       map.put("productWidth",this.productWidth);
       map.put("productHeight",this.productHeight);
       map.put("salePrice",this.salePrice);
       map.put("picId",this.picId);
       map.put("modelId",this.modelId);
       map.put("productDesc",this.productDesc);
       map.put("picIds",this.picIds);
       map.put("materialPicIds",this.materialPicIds);
       map.put("houseTypeValues",this.houseTypeValues);
       map.put("productTypeValue",this.productTypeValue);
       map.put("u3dModelId",this.u3dModelId);
       map.put("mergeProductIds",this.mergeProductIds);
       map.put("putawayModified",this.putawayModified);
       map.put("dateAtt2",this.dateAtt2);
       map.put("productSmallTypeValue",this.productSmallTypeValue);
       map.put("productSmallTypeMark",this.productSmallTypeMark);
       map.put("productTypeMark",this.productTypeMark);
       map.put("putawayState",this.putawayState);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);
	   map.put("u3dModelPath",this.u3dModelPath);

       return map;
    }

	public String getPutawayStateName() {
		return putawayStateName;
	}

	public void setPutawayStateName(String putawayStateName) {
		this.putawayStateName = putawayStateName;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}
    
	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	private String materialId;
    
	
	//合并数量
	private Integer mergeCount;
	
	public Integer getMergeCount() {
		return mergeCount;
	}

	public void setMergeCount(Integer mergeCount) {
		this.mergeCount = mergeCount;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductStyle() {
		return productStyle;
	}

	public void setProductStyle(String productStyle) {
		this.productStyle = productStyle;
	}

	public String getProductColor() {
		return productColor;
	}

	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}

	public String getProductPicPath() {
		return productPicPath;
	}

	public void setProductPicPath(String productPicPath) {
		this.productPicPath = productPicPath;
	}

	public List getColorlist() {
		return colorlist;
	}

	public void setColorlist(List colorlist) {
		this.colorlist = colorlist;
	}

	public List getSmallPiclist() {
		return smallPiclist;
	}

	public void setSmallPiclist(List smallPiclist) {
		this.smallPiclist = smallPiclist;
	}

	public List getMateriallist() {
		return materiallist;
	}

	public void setMateriallist(List materiallist) {
		this.materiallist = materiallist;
	}

	public String getProductTypeKey() {
		return productTypeKey;
	}

	public void setProductTypeKey(String productTypeKey) {
		this.productTypeKey = productTypeKey;
	}
	
	public String getProductColorKey() {
		return productColorKey;
	}

	public void setProductColorKey(String productColorKey) {
		this.productColorKey = productColorKey;
	}

	public String getProductSmallType() {
		return productSmallType;
	}

	public void setProductSmallType(String productSmallType) {
		this.productSmallType = productSmallType;
	}
	
	//产品推荐状态
	private Integer recommendState;
	
	public Integer getRecommendState() {
		return recommendState;
	}

	public void setRecommendState(Integer recommendState) {
		this.recommendState = recommendState;
	}
	
	/** 产品挂节点路径 **/
	private String posIndexPath;
	/**  产品序号  **/
	private String productSequence;
	/**方案Id**/
	private int planId;
	/**产品类型 （软装4，硬装1，软装白模3，硬装白模2）**/
	private String isProductType ;
	/**编辑页面编辑code权限 （0，1）**/
	private Integer editCodeState;
	/**编辑页面编辑codeList**/
	List<String> editCodeList = new ArrayList<String>();
	/** 规则json **/
	private Map<String,String> rulesMap;
	
	
	public Map<String, String> getRulesMap() {
		return rulesMap;
	}

	public void setRulesMap(Map<String, String> rulesMap) {
		this.rulesMap = rulesMap;
	}

	public List<String> getEditCodeList() {
		return editCodeList;
	}

	public void setEditCodeList(List<String> editCodeList) {
		this.editCodeList = editCodeList;
	}

	public Integer getEditCodeState() {
		return editCodeState;
	}

	public void setEditCodeState(Integer editCodeState) {
		this.editCodeState = editCodeState;
	}

	public String getIsProductType() {
		return isProductType;
	}

	public void setIsProductType(String isProductType) {
		this.isProductType = isProductType;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	public String getProductSequence() {
		return productSequence;
	}

	public void setProductSequence(String productSequence) {
		this.productSequence = productSequence;
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
	
	//产品大分类名称
	private String productTypeName;

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	
	//组合产品ID
	private Integer groupId;
	//贴图选中状态1，为选中
	private Integer selectedState;
	//是否是内部用户
	private String isInternalUser;
	
	private String bigTypeValueKey;
	
	private String smallTypeValueKey;
	
	private Integer groupCount;
	
	private String smallTypeAtt1;
	
	private String bigTypeName;
	
	private String smallTypeName;
	
	public String getBigTypeName() {
		return bigTypeName;
	}

	public void setBigTypeName(String bigTypeName) {
		this.bigTypeName = bigTypeName;
	}

	public String getSmallTypeName() {
		return smallTypeName;
	}

	public void setSmallTypeName(String smallTypeName) {
		this.smallTypeName = smallTypeName;
	}

	public String getSmallTypeAtt1() {
		return smallTypeAtt1;
	}

	public void setSmallTypeAtt1(String smallTypeAtt1) {
		this.smallTypeAtt1 = smallTypeAtt1;
	}

	public String getBigTypeValueKey() {
		return bigTypeValueKey;
	}

	public void setBigTypeValueKey(String bigTypeValueKey) {
		this.bigTypeValueKey = bigTypeValueKey;
	}

	public String getSmallTypeValueKey() {
		return smallTypeValueKey;
	}

	public void setSmallTypeValueKey(String smallTypeValueKey) {
		this.smallTypeValueKey = smallTypeValueKey;
	}

	public Integer getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Integer groupCount) {
		this.groupCount = groupCount;
	}

	public String getIsInternalUser() {
		return isInternalUser;
	}

	public void setIsInternalUser(String isInternalUser) {
		this.isInternalUser = isInternalUser;
	}

	public Integer getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(Integer selectedState) {
		this.selectedState = selectedState;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public int getModelLength() {
		return modelLength;
	}

	public void setModelLength(int modelLength) {
		this.modelLength = modelLength;
	}

	public int getModelWidth() {
		return modelWidth;
	}

	public void setModelWidth(int modelWidth) {
		this.modelWidth = modelWidth;
	}

	public int getModelHeight() {
		return modelHeight;
	}

	public void setModelHeight(int modelHeight) {
		this.modelHeight = modelHeight;
	}

	public int getModelMinHeight() {
		return modelMinHeight;
	}

	public void setModelMinHeight(int modelMinHeight) {
		this.modelMinHeight = modelMinHeight;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}
