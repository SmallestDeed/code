package com.nork.design.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.Mapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.SplitTextureDTO;

/**   
 * @Title: DesignPlanProductRenderScene.java 
 * @Package com.nork.design.model
 * @Description:设计方案产品副本表
 * @createAuthor huangsongbo
 * @CreateDate 2017.7.17
 * @version V1.0
 */
public class DesignPlanProductRenderScene  extends Mapper implements Serializable{
	
	private static final long serialVersionUID = 436429272373692613L;
	
	private Integer id;
	/**  设计方案产品ID  **/
	private Integer designPlanProductId;
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
	/**  方案id  **/
	private Integer planId;
	/**  设计方案id  **/
	private Integer designPlanId;
	/** 产品挂节点路径 **/
	private String posIndexPath;
	/**  产品id  **/
	private Integer productId;
	/**初始产品ID**/
	private Integer initProductId;
	/**  产品编码  **/
	private String productCode;
	/**  产品名称  **/
	private String productName;
	/**  产品缩略图  **/
	private String  productSmallPicPath;
	/**  产品缩略图  **/
	private String  productU3dModelPath;
	/**  产品编码  **/
	private Integer productType;
	/**  相机位置文件id  **/
	private Integer locationFileId;
	/**  产品顺序  **/
	private String productSequence;
	/**  字符备用1  **/
	private String materialPicId;
	/**  样板房列表主键1  **/
	private Integer planProductId;
	/**  样板房列表产品id  **/
	private Integer templatePlanProductId;
	/**  显示状态  **/
	private Integer displayStatus;
	/**  备注  **/
	private String remark;
	/** 是否隐藏 **/
	private Integer isHide;
	/** 产品类型名称 **/
	private String productTypeName;
	/** 用户ID **/
	private Integer userId;
	/** 品牌名称  **/
	private String brandName;
	/** 产品价格 **/
	private Double salePrice;
	//记录物体是否被移动过
	private Integer isDirty;
	
	private String productTypeCode;
	private String productSmallTypeCode;
	private String productSmallTypeName;
	private String materialConfigPath;
	private Integer productSmallTypeValue;
	private Integer parentProductId;
	//材质图片s
	private String[] materialPicPaths;
	/** 规则json **/
	private Map<String,String> rulesMap;
	//组合产品ＩＤ
	private Integer productGroupId;
	//是否是主产品（１，是；0，否）
	private Integer isMainProduct;
	
	private String rootType;
	private String productLength;
	private String productWidth;
	private String productHeight;
	/**  u3d模型  **/
	private String u3dModelPath;
	/*组合id+时间戳(区分同groupId组)*/
	private String planGroupId;
	/*有模型的产品id*///平吊天花换模型在换贴图用到，换贴图用当前产品模型
	private Integer modelProductId;
	/** 挂节点名称 **/
	private String posName;
	/**  绑定父产品ID  **/
	private String bindParentProductId;
	/*设计方案产品材质信息*/
	private String splitTexturesChooseInfo;
	/*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
	private Integer isSplit=new Integer(0);
	/*可拆分材质信息*/
	private List<SplitTextureDTO> splitTexturesChoose;
	/*是组合还是结构,0:组合,1:结构*/
	private Integer groupType;
	/*过滤条件:序列号里的品牌,大类,小类,产品ids(序列号条件设置到BaseProduct类中)*/
	private List<BaseProduct> baseProduct;
	/**携带得白模产品集合*/
	private Map<String,CategoryProductResult> basicModelMap;
	/** 产品属性 **/
	private Map<String,String> propertyMap;
 
	private String isInternalUser;

	//产品同小分类序号
	private Integer sameProductTypeIndex;
	//0、实体墙  1、背景墙 2、门框
	private Integer bgWall;
	
	private List<Integer>resIdList;

	/*产品的状态*/
	private Integer productPutawayState;
	
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

	List<Integer>designPlanProductIds = new ArrayList<Integer>();

	/*墙体类型*/
	private String wallOrientation;
	/*墙体方位*/
	private String wallType;

    //用户产品配置三种情况（1品牌，2品牌大类，3品牌大类小类）
    private String brands;
    private String bigType;
    private String smallType;
    
	private List<AuthorizedConfig> authorizedConfigList = new ArrayList<AuthorizedConfig>();
	
	/**
	 * 组合中产品的唯一标识
	 */
	private String groupProductUniqueId;
	
	public String getGroupProductUniqueId() {
		return groupProductUniqueId;
	}

	public void setGroupProductUniqueId(String groupProductUniqueId) {
		this.groupProductUniqueId = groupProductUniqueId;
	}

	public Integer getProductPutawayState() {
		return productPutawayState;
	}

	public void setProductPutawayState(Integer productPutawayState) {
		this.productPutawayState = productPutawayState;
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

	public List<Integer> getDesignPlanProductIds() {
		return designPlanProductIds;
	}

	public void setDesignPlanProductIds(List<Integer> designPlanProductIds) {
		this.designPlanProductIds = designPlanProductIds;
	}

	public String getWallOrientation() {
		return wallOrientation;
	}

	public void setWallOrientation(String wallOrientation) {
		this.wallOrientation = wallOrientation;
	}

	public String getWallType() {
		return wallType;
	}

	public void setWallType(String wallType) {
		this.wallType = wallType;
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

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

	public List<AuthorizedConfig> getAuthorizedConfigList() {
		return authorizedConfigList;
	}

	public void setAuthorizedConfigList(List<AuthorizedConfig> authorizedConfigList) {
		this.authorizedConfigList = authorizedConfigList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDesignPlanProductId() {
		return designPlanProductId;
	}

	public void setDesignPlanProductId(Integer designPlanProductId) {
		this.designPlanProductId = designPlanProductId;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getDesignPlanId() {
		return designPlanId;
	}

	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}

	public String getPosIndexPath() {
		return posIndexPath;
	}

	public void setPosIndexPath(String posIndexPath) {
		this.posIndexPath = posIndexPath;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getInitProductId() {
		return initProductId;
	}

	public void setInitProductId(Integer initProductId) {
		this.initProductId = initProductId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSmallPicPath() {
		return productSmallPicPath;
	}

	public void setProductSmallPicPath(String productSmallPicPath) {
		this.productSmallPicPath = productSmallPicPath;
	}

	public String getProductU3dModelPath() {
		return productU3dModelPath;
	}

	public void setProductU3dModelPath(String productU3dModelPath) {
		this.productU3dModelPath = productU3dModelPath;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getLocationFileId() {
		return locationFileId;
	}

	public void setLocationFileId(Integer locationFileId) {
		this.locationFileId = locationFileId;
	}

	public String getProductSequence() {
		return productSequence;
	}

	public void setProductSequence(String productSequence) {
		this.productSequence = productSequence;
	}

	public String getMaterialPicId() {
		return materialPicId;
	}

	public void setMaterialPicId(String materialPicId) {
		this.materialPicId = materialPicId;
	}

	public Integer getPlanProductId() {
		return planProductId;
	}

	public void setPlanProductId(Integer planProductId) {
		this.planProductId = planProductId;
	}

	public Integer getTemplatePlanProductId() {
		return templatePlanProductId;
	}

	public void setTemplatePlanProductId(Integer templatePlanProductId) {
		this.templatePlanProductId = templatePlanProductId;
	}

	public Integer getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(Integer displayStatus) {
		this.displayStatus = displayStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getIsDirty() {
		return isDirty;
	}

	public void setIsDirty(Integer isDirty) {
		this.isDirty = isDirty;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

	public String getProductSmallTypeCode() {
		return productSmallTypeCode;
	}

	public void setProductSmallTypeCode(String productSmallTypeCode) {
		this.productSmallTypeCode = productSmallTypeCode;
	}

	public String getProductSmallTypeName() {
		return productSmallTypeName;
	}

	public void setProductSmallTypeName(String productSmallTypeName) {
		this.productSmallTypeName = productSmallTypeName;
	}

	public String getMaterialConfigPath() {
		return materialConfigPath;
	}

	public void setMaterialConfigPath(String materialConfigPath) {
		this.materialConfigPath = materialConfigPath;
	}

	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
	}

	public Integer getParentProductId() {
		return parentProductId;
	}

	public void setParentProductId(Integer parentProductId) {
		this.parentProductId = parentProductId;
	}

	public String[] getMaterialPicPaths() {
		return materialPicPaths;
	}

	public void setMaterialPicPaths(String[] materialPicPaths) {
		this.materialPicPaths = materialPicPaths;
	}

	public Map<String, String> getRulesMap() {
		return rulesMap;
	}

	public void setRulesMap(Map<String, String> rulesMap) {
		this.rulesMap = rulesMap;
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

	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
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

	public String getU3dModelPath() {
		return u3dModelPath;
	}

	public void setU3dModelPath(String u3dModelPath) {
		this.u3dModelPath = u3dModelPath;
	}

	public String getPlanGroupId() {
		return planGroupId;
	}

	public void setPlanGroupId(String planGroupId) {
		this.planGroupId = planGroupId;
	}

	public Integer getModelProductId() {
		return modelProductId;
	}

	public void setModelProductId(Integer modelProductId) {
		this.modelProductId = modelProductId;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getBindParentProductId() {
		return bindParentProductId;
	}

	public void setBindParentProductId(String bindParentProductId) {
		this.bindParentProductId = bindParentProductId;
	}

	public String getSplitTexturesChooseInfo() {
		return splitTexturesChooseInfo;
	}

	public void setSplitTexturesChooseInfo(String splitTexturesChooseInfo) {
		this.splitTexturesChooseInfo = splitTexturesChooseInfo;
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

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public List<BaseProduct> getBaseProduct() {
		return baseProduct;
	}

	public void setBaseProduct(List<BaseProduct> baseProduct) {
		this.baseProduct = baseProduct;
	}

	public Map<String, CategoryProductResult> getBasicModelMap() {
		return basicModelMap;
	}

	public void setBasicModelMap(Map<String, CategoryProductResult> basicModelMap) {
		this.basicModelMap = basicModelMap;
	}

	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public String getIsInternalUser() {
		return isInternalUser;
	}

	public void setIsInternalUser(String isInternalUser) {
		this.isInternalUser = isInternalUser;
	}

	public Integer getSameProductTypeIndex() {
		return sameProductTypeIndex;
	}

	public void setSameProductTypeIndex(Integer sameProductTypeIndex) {
		this.sameProductTypeIndex = sameProductTypeIndex;
	}

	public Integer getBgWall() {
		return bgWall;
	}

	public void setBgWall(Integer bgWall) {
		this.bgWall = bgWall;
	}

	public List<Integer> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<Integer> resIdList) {
		this.resIdList = resIdList;
	}
 
    /**获取对象的copy**/
    public DesignPlanRecommendedProduct recommendedCopy(){
    	DesignPlanRecommendedProduct obj =  new DesignPlanRecommendedProduct();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setProductId(this.productId);
       obj.setLocationFileId(this.locationFileId);
       obj.setProductSequence(this.productSequence);
       obj.setMaterialPicId(this.materialPicId);
//       obj.setAtt2(this.att2);
//       obj.setAtt3(this.att3);
//       obj.setAtt4(this.att4);
//       obj.setAtt5(this.att5);
//       obj.setAtt6(this.att6);
//       obj.setDateAtt1(this.dateAtt1);
//       obj.setDateAtt2(this.dateAtt2);
       obj.setPlanProductId(this.planProductId);
       obj.setDisplayStatus(this.displayStatus);
//       obj.setNumAtt3(this.numAtt3);
////       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);

       obj.setIsHide(this.isHide);
       obj.setPosIndexPath(this.posIndexPath);
 
       obj.setInitProductId(this.initProductId);
       obj.setIsDirty(this.isDirty);
       obj.setProductGroupId(this.productGroupId);
       obj.setIsMainProduct(this.isMainProduct);
       obj.setPosName(this.posName);
       obj.setPlanGroupId(this.planGroupId);
       obj.setModelProductId(this.modelProductId);
       obj.setBindParentProductId(this.bindParentProductId);
       obj.setSplitTexturesChooseInfo(this.splitTexturesChooseInfo);
       obj.setGroupType(this.groupType);
       obj.setSameProductTypeIndex(this.sameProductTypeIndex);
       obj.setIsMainStructureProduct(this.isMainStructureProduct);
       obj.setIsGroupReplaceWay(this.isGroupReplaceWay);
       obj.setIsStandard(this.isStandard);
       obj.setCenter(this.center);
       obj.setRegionMark(this.regionMark);
       obj.setStyleId(this.styleId);
       obj.setMeasureCode(this.measureCode);
       obj.setDescribeInfo(this.describeInfo);
       obj.setProductIndex(this.productIndex);
       obj.setWallOrientation(this.wallOrientation);
       obj.setWallType(this.wallType);
       obj.setGroupProductUniqueId(this.groupProductUniqueId);
       return obj;
    }
    
    /**获取对象的copy**/
    public DesignPlanProduct designPlanProductCopy(){
       DesignPlanProduct obj =  new DesignPlanProduct();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setProductId(this.productId);
       obj.setLocationFileId(this.locationFileId);
       obj.setProductSequence(this.productSequence);
       obj.setMaterialPicId(this.materialPicId);
//       obj.setAtt2(this.att2);
//       obj.setAtt3(this.att3);
//       obj.setAtt4(this.att4);
//       obj.setAtt5(this.att5);
//       obj.setAtt6(this.att6);
//       obj.setDateAtt1(this.dateAtt1);
//       obj.setDateAtt2(this.dateAtt2);
       obj.setPlanProductId(this.planProductId);
       obj.setDisplayStatus(this.displayStatus);
//       obj.setNumAtt3(this.numAtt3);
//       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);

       obj.setIsHide(this.isHide);
       obj.setPosIndexPath(this.posIndexPath);
 
       obj.setInitProductId(this.initProductId);
       obj.setIsDirty(this.isDirty);
       obj.setProductGroupId(this.productGroupId);
       obj.setIsMainProduct(this.isMainProduct);
       obj.setPosName(this.posName);
       obj.setPlanGroupId(this.planGroupId);
       obj.setModelProductId(this.modelProductId);
       obj.setBindParentProductId(this.bindParentProductId);
       obj.setSplitTexturesChooseInfo(this.splitTexturesChooseInfo);
       obj.setGroupType(this.groupType);
       obj.setSameProductTypeIndex(this.sameProductTypeIndex);
       obj.setIsMainStructureProduct(this.isMainStructureProduct);
       obj.setIsGroupReplaceWay(this.isGroupReplaceWay);
       obj.setIsStandard(this.isStandard);
       obj.setCenter(this.center);
       obj.setRegionMark(this.regionMark);
       obj.setStyleId(this.styleId);
       obj.setMeasureCode(this.measureCode);
       obj.setDescribeInfo(this.describeInfo);
       obj.setProductIndex(this.productIndex);
       obj.setWallOrientation(this.wallOrientation);
       obj.setWallType(this.wallType);
       return obj;
    }
}
