package com.sandu.design.model;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sandu.product.model.dto.SplitTextureDTO;
import com.sandu.product.model.vo.ProductCeilingVO;

public class UnityPlanProduct implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer planProductId;
	
    private String productSequence;
    
//   private String productCameraConfig;
    //软装硬装分类
    private String rootType;
    
    private Integer parentTypeValue;
    
    private String parentTypeCode;

    private String parentTypeName;
    
    private Integer smallTypeValue;
    
    private String  smallTypeCode;

    private String  smallTypeName;
    
    private Integer productTypeValue;
    
    private String productTypeCode;

    private String productTypeName;
    
    private Integer productId;
    
    private String productCode;
    
    private String productLength;
    
    private String productWidth;
    
    private String productHeight;

    private String productModelPath;
    
//    private String materialPicPath;
//    
//    private Integer materialPicId;

	/** 挂节点中白模产品ID **/
	private String templateProductId;
	
    private Integer isLeaf;
    
    private Integer leafNum;
    
    private Integer isHide;

	private String posIndexPath;

	private String posName;
	//0、实体墙  1、背景墙 2、门框
	private Integer bgWall;
	//绑定产品ID
	private String bindProductId;
	//绑定白模背景墙产品模型长
	private Integer initModelLength;
	//绑定白模背景墙产品模型宽
	private Integer initModelWidth;
	//绑定白模背景墙产品模型高
	private Integer initModelHeight;
	//全铺长度 （背景墙、窗帘、一字淋浴屏）
	private String fullPaveLength;
	/*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
	private Integer isSplit=new Integer(0);
	/*可拆分材质信息*/
	private List<SplitTextureDTO> splitTexturesChoose;
	/*结构id*/
	private Integer productStructureId;
	/*结构id(唯一)*/
	private String planStructureId;
	/*是组合还是结构,0:组合,1:结构*/
	private Integer groupType;
	/*传递数据,该结构包含的设计方案产品id*/
	private Integer [] planProductStructureIds;
	
	
	private String basicModelType; // 
	
	
	/*标准(1标准 0 非标准)*/
	private Integer isStandard;
	/*中心点*/
	private String center;
	/*区域标识*/
	private String regionMark;
	/*款式id*/
	private Integer styleId;
	/*尺寸代码*/
	private String measureCode;
	/*描述(区域、尺寸代码)*/
	private String describeInfo;
	//序号
	private Integer productIndex;
	
	/*是否为结构中的主产品标识（1是，0否）*/
	private Integer isMainStructureProduct;
	/*主产品单品是否可以作为组合方式替换（1是，0否）*/
	private Integer isGroupReplaceWay;
	//系列标志
	private String seriesSign;

	/**
	 * 系列id
	 * add by huangsongbo 2017.12.27
	 */
	private Integer seriesId;

	//天花截面数据对象
	private ProductCeilingVO productCeilingVO;

	public ProductCeilingVO getProductCeilingVO() {
		return productCeilingVO;
	}

	public void setProductCeilingVO(ProductCeilingVO productCeilingVO) {
		this.productCeilingVO = productCeilingVO;
	}
	
	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeriesSign() {
		return seriesSign;
	}

	public void setSeriesSign(String seriesSign) {
		this.seriesSign = seriesSign;
	}

	public Integer getIsMainStructureProduct() {
		return isMainStructureProduct;
	}
	public void setIsMainStructureProduct(Integer isMainStructureProduct) {
		this.isMainStructureProduct = isMainStructureProduct;
	}
	public Integer getIsGroupReplaceWay() {
		return isGroupReplaceWay;
	}
	public void setIsGroupReplaceWay(Integer isGroupReplaceWay) {
		this.isGroupReplaceWay = isGroupReplaceWay;
	}
	public Integer getIsStandard() {
		return isStandard;
	}
	public void setIsStandard(Integer isStandard) {
		this.isStandard = isStandard;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getRegionMark() {
		return regionMark;
	}
	public void setRegionMark(String regionMark) {
		this.regionMark = regionMark;
	}
	public Integer getStyleId() {
		return styleId;
	}
	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}
	public String getMeasureCode() {
		return measureCode;
	}
	public void setMeasureCode(String measureCode) {
		this.measureCode = measureCode;
	}
	public String getDescribeInfo() {
		return describeInfo;
	}
	public void setDescribeInfo(String describeInfo) {
		this.describeInfo = describeInfo;
	}
	public Integer getProductIndex() {
		return productIndex;
	}
	public void setProductIndex(Integer productIndex) {
		this.productIndex = productIndex;
	}
	public String getBasicModelType() {
		return basicModelType;
	}
	public void setBasicModelType(String basicModelType) {
		this.basicModelType = basicModelType;
	}
	
	
	
	public Integer[] getPlanProductStructureIds() {
		return planProductStructureIds;
	}
	public void setPlanProductStructureIds(Integer[] planProductStructureIds) {
		this.planProductStructureIds = planProductStructureIds;
	}
	public Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
	public Integer getProductStructureId() {
		return productStructureId;
	}
	public void setProductStructureId(Integer productStructureId) {
		this.productStructureId = productStructureId;
	}
	public String getPlanStructureId() {
		return planStructureId;
	}
	public void setPlanStructureId(String planStructureId) {
		this.planStructureId = planStructureId;
	}
	public Integer getIsSplit() {
		return isSplit;
	}
	public void setIsSplit(Integer isSplit) {
		this.isSplit = isSplit;
	}
	public List<SplitTextureDTO> getSplitTexturesChoose() {
		return splitTexturesChoose;
	}
	public void setSplitTexturesChoose(List<SplitTextureDTO> splitTexturesChoose) {
		this.splitTexturesChoose = splitTexturesChoose;
	}

	public String getFullPaveLength() {
		return fullPaveLength;
	}

	public void setFullPaveLength(String fullPaveLength) {
		this.fullPaveLength = fullPaveLength;
	}

	public Integer getInitModelLength() {
		return initModelLength;
	}

	public void setInitModelLength(Integer initModelLength) {
		this.initModelLength = initModelLength;
	}

	public Integer getInitModelWidth() {
		return initModelWidth;
	}

	public void setInitModelWidth(Integer initModelWidth) {
		this.initModelWidth = initModelWidth;
	}

	public Integer getInitModelHeight() {
		return initModelHeight;
	}

	public void setInitModelHeight(Integer initModelHeight) {
		this.initModelHeight = initModelHeight;
	}

	public String getBindProductId() {
		return bindProductId;
	}
	public void setBindProductId(String bindProductId) {
		this.bindProductId = bindProductId;
	}
	public Integer getBgWall() {
		return bgWall;
	}
	public void setBgWall(Integer bgWall) {
		this.bgWall = bgWall==null?0:bgWall;
	}
	private String[] materialPicPaths;
//	/** ios使用u3d模型 **/
//	private String iosU3dModelPath;
//	/** android使用u3d模型 **/
//	private String androidU3dModelPath;
//	/** macBook使用u3d模型 **/
//	private String macBookU3dModelPath;
//	/** windows使用u3d模型 **/
//	private String windowsU3dModelPath;
//	/** ipad使用u3d模型 **/
//	private String ipadU3dModelPath;
    
	private String[] decorationModelPath;

	private Integer displayStatus;

	/** 是否是白模 **/
	private Integer isBaimo;
	/** 是否是定制 **/
	private Integer isCustomized;
	
	private Integer parentProductId;
	
	//记录物体是否被移动过
	private Integer isDirty;
	//产品组合ＩＤ
	private Integer productGroupId;
	//是否是主产品（１，是；0，否）
	private Integer isMainProduct;
	//该方案组合产品IDs
	private Integer [] planProductGroupIds ; 
	//方案组合产品Id
	private String planGroupId;
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
	
	/**材质宽*/
	private String textureWidth;
	
	/**材质高*/
	private String textureHeight;
	//当前产品ID
	private Integer modelProductId;
	//产品最小高度（天花用到）
	private String minHeight;

	public String getMinHeight() {
		return minHeight;
	}
	public void setMinHeight(String minHeight) {
		this.minHeight = minHeight;
	}
	public Integer getModelProductId() {
		return modelProductId;
	}
	public void setModelProductId(Integer modelProductId) {
		this.modelProductId = modelProductId;
	}
	public Integer getIsCustomized() {
		return isCustomized;
	}
	public void setIsCustomized(Integer isCustomized) {
		this.isCustomized = isCustomized;
	}
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
	public String getPlanGroupId() {
		return planGroupId;
	}
	public void setPlanGroupId(String planGroupId) {
		this.planGroupId = planGroupId;
	}
	public Integer[] getPlanProductGroupIds() {
		return planProductGroupIds;
	}
	public void setPlanProductGroupIds(Integer[] planProductGroupIds) {
		this.planProductGroupIds = planProductGroupIds;
	}
	public Integer getProductGroupId() {
		return productGroupId;
	}
	public void setProductGroupId(Integer productGroupId) {
		this.productGroupId = productGroupId;
	}
	public Integer getIsMainProduct() {
		return isMainProduct;
	}
	public void setIsMainProduct(Integer isMainProduct) {
		this.isMainProduct = isMainProduct;
	}
	
	public Integer getIsDirty() {
		return isDirty;
	}
	public void setIsDirty(Integer isDirty) {
		this.isDirty = isDirty;
	}
	//移动方式
	private String moveWay;
	/** 规则json **/
	private Map<String,String> rulesMap;
	/** 产品属性 **/
	private Map<String,String> propertyMap;
	/** 关联的白模产品属性 **/
	private Map<String,String> basicPropertyMap;

	
	
	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}
	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}
	public String getMoveWay() {
		return moveWay;
	}

	public void setMoveWay(String moveWay) {
		this.moveWay = moveWay;
	}

	public Integer getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(Integer parentProductId) {
		this.parentProductId = parentProductId;
	}

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	public String[] getDecorationModelPath() {
		return decorationModelPath;
	}

	public void setDecorationModelPath(String[] decorationModelPath) {
		this.decorationModelPath = decorationModelPath;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public String getParentTypeCode() {
		return parentTypeCode;
	}

	public void setParentTypeCode(String parentTypeCode) {
		this.parentTypeCode = parentTypeCode;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public Integer getPlanProductId() {
		return planProductId;
	}

	public void setPlanProductId(Integer planProductId) {
		this.planProductId = planProductId;
	}

	public String getProductSequence() {
		return productSequence;
	}

	public void setProductSequence(String productSequence) {
		this.productSequence = productSequence;
	}

//	public String getProductCameraConfig() {
//		return productCameraConfig;
//	}
//
//	public void setProductCameraConfig(String productCameraConfig) {
//		this.productCameraConfig = productCameraConfig;
//	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	
//	public String getProLength() {
//		return proLength;
//	}
//
//	public void setProLength(String proLength) {
//		this.proLength = proLength;
//	}
//
//	public String getProWidth() {
//		return proWidth;
//	}
//
//	public void setProWidth(String proWidth) {
//		this.proWidth = proWidth;
//	}
//
//	public String getProHeight() {
//		return proHeight;
//	}
//
//	public void setProHeight(String proHeight) {
//		this.proHeight = proHeight;
//	}

	public String getProductModelPath() {
		return productModelPath;
	}

	public void setProductModelPath(String productModelPath) {
		this.productModelPath = productModelPath;
	}

//	public String getMaterialPicPath() {
//		return materialPicPath;
//	}
//
//	public void setMaterialPicPath(String materialPicPath) {
//		this.materialPicPath = materialPicPath;
//	}

	
	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getLeafNum() {
		return leafNum;
	}

	public void setLeafNum(Integer leafNum) {
		this.leafNum = leafNum;
	}

	public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}
//	public Integer getMaterialPicId() {
//		return materialPicId;
//	}
//
//	public void setMaterialPicId(Integer materialPicId) {
//		this.materialPicId = materialPicId;
//	}

//	public String getIosU3dModelPath() {
//		return iosU3dModelPath;
//	}
//
//	public void setIosU3dModelPath(String iosU3dModelPath) {
//		this.iosU3dModelPath = iosU3dModelPath;
//	}
//
//	public String getAndroidU3dModelPath() {
//		return androidU3dModelPath;
//	}
//
//	public void setAndroidU3dModelPath(String androidU3dModelPath) {
//		this.androidU3dModelPath = androidU3dModelPath;
//	}
//
//	public String getMacBookU3dModelPath() {
//		return macBookU3dModelPath;
//	}
//
//	public void setMacBookU3dModelPath(String macBookU3dModelPath) {
//		this.macBookU3dModelPath = macBookU3dModelPath;
//	}
//
//	public String getWindowsU3dModelPath() {
//		return windowsU3dModelPath;
//	}
//
//	public void setWindowsU3dModelPath(String windowsU3dModelPath) {
//		this.windowsU3dModelPath = windowsU3dModelPath;
//	}
//
//	public String getIpadU3dModelPath() {
//		return ipadU3dModelPath;
//	}
//
//	public void setIpadU3dModelPath(String ipadU3dModelPath) {
//		this.ipadU3dModelPath = ipadU3dModelPath;
//	}

	public String[] getMaterialPicPaths() {
		return materialPicPaths;
	}

	public void setMaterialPicPaths(String[] materialPicPaths) {
		this.materialPicPaths = materialPicPaths;
	}

	public Integer getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(Integer displayStatus) {
		this.displayStatus = displayStatus;
	}

	/**获取对象的copy**/
    public UnityPlanProduct copy(){
       UnityPlanProduct obj =  new UnityPlanProduct();
       obj.setPlanProductId(planProductId);
       obj.setProductSequence(productSequence);
       //obj.setProductCameraConfig(productCameraConfig); 
       obj.setParentTypeValue(parentTypeValue);
       obj.setParentTypeCode(parentTypeCode);
       obj.setParentTypeName(parentTypeName);
       obj.setProductTypeValue(productTypeValue);
       obj.setProductTypeCode(productTypeCode);
       obj.setProductTypeName(productTypeName);
       obj.setProductId(productId);
       obj.setProductCode(productCode);
       obj.setProductModelPath(productModelPath);
       obj.setMaterialPicPaths(materialPicPaths);
       obj.setDecorationModelPath(decorationModelPath);
//       obj.setMaterialPicPath(materialPicPath);
//       obj.setMaterialPicId(materialPicId);
       obj.setIsLeaf(isLeaf);
       obj.setLeafNum(leafNum);
	   obj.setIsHide(isHide);
	   obj.setIsDirty(isDirty);
	   obj.setPosIndexPath(posIndexPath);
//	   obj.setIpadU3dModelPath(ipadU3dModelPath);
//	   obj.setIosU3dModelPath(iosU3dModelPath);
//	   obj.setAndroidU3dModelPath(androidU3dModelPath);
//	   obj.setMacBookU3dModelPath(macBookU3dModelPath);
//	   obj.setWindowsU3dModelPath(windowsU3dModelPath);
	   obj.setTextureAttrValue(textureAttrValue);
	   obj.setLaymodes(laymodes);
	   obj.setIsMainProduct(isMainProduct);
	   obj.setIsSplit(isSplit);
	   obj.setSplitTexturesChoose(splitTexturesChoose);
	   
	   obj.setIsStandard(isStandard);
	   obj.setCenter(center);
	   obj.setRegionMark(regionMark);
	   obj.setStyleId(styleId);
	   obj.setMeasureCode(measureCode);
	   obj.setDescribeInfo(describeInfo);
	   obj.setProductIndex(productIndex);
	   obj.setIsMainStructureProduct(isMainStructureProduct);
	   obj.setIsGroupReplaceWay(isGroupReplaceWay);
       return obj;
    }

	public Integer getParentTypeValue() {
		return parentTypeValue;
	}

	public void setParentTypeValue(Integer parentTypeValue) {
		this.parentTypeValue = parentTypeValue;
	}

	public Integer getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(Integer productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public Integer getSmallTypeValue() {
		return smallTypeValue;
	}

	public void setSmallTypeValue(Integer smallTypeValue) {
		this.smallTypeValue = smallTypeValue;
	}

	public String getSmallTypeCode() {
		return smallTypeCode;
	}

	public void setSmallTypeCode(String smallTypeCode) {
		this.smallTypeCode = smallTypeCode;
	}

	public String getSmallTypeName() {
		return smallTypeName;
	}

	public void setSmallTypeName(String smallTypeName) {
		this.smallTypeName = smallTypeName;
	}

	public Integer getIsBaimo() {
		return isBaimo;
	}

	public void setIsBaimo(Integer isBaimo) {
		this.isBaimo = isBaimo;
	}

	public String getTemplateProductId() {
		return templateProductId;
	}

	public void setTemplateProductId(String templateProductId) {
		this.templateProductId = templateProductId;
	}

	public Map<String, String> getRulesMap() {
		return rulesMap;
	}

	public void setRulesMap(Map<String, String> rulesMap) {
		this.rulesMap = rulesMap;
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

	public Map<String, String> getBasicPropertyMap() {
		return basicPropertyMap;
	}

	public void setBasicPropertyMap(Map<String, String> basicPropertyMap) {
		this.basicPropertyMap = basicPropertyMap;
	}
}
