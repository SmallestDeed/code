package com.sandu.design.model;

import java.io.Serializable;
import java.util.List;

import com.sandu.design.model.po.DesignPlanRecommendedProductPO;

/**
 * 方案推荐产品表
 * @author Administrator
 *
 */
public class DesignPlanRecommendedProduct extends DesignPlanRecommendedProductPO implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Integer>resIdList;
	
	private String IsInternalUser;
	
	private Integer salePriceValue;
	
	private String SalePriceValueName;
 
	private Integer brandId;
	
	private String brandName;
	
	private String salePrice;
	
	private Integer collectState;
 
	private String productCode;
	/**
	 * 商品数量
	 */
	private Integer count;
	

	/**
	 * 对应产品的大类value
	 */
	private Integer productBigTypeValue;
	
	/**
	 * 对应产品的大类value
	 */
	private Integer productSmallTypeValue;
	
	/**
	 * 对应组合的配置文件相对路径
	 */
	private String filePath;
	/**
	 * 组合表的location字段(组合配置相对路径/配置json)
	 */
	private String location;

	/*组合类型*/
	private Integer compositeType;
	
	/**
	 * 产品大类valuekey
	 */
	private String bigTypeValuekey;
	
	/**
	 * 产品小类valuekey
	 */
	private String smallTypeValuekey;
	
	/**
	 * init产品(初始产品)大类valuekey
	 */
	private String bigTypeValuekeyInit;
	
	/**
	 * init产品(初始产品)小类valuekey
	 */
	private String smallTypeValuekeyInit;
	
	/**
	 * 产品长度
	 */
	private String productLength;
	
	/**
	 * 产品宽度
	 */
	private String productWidth;
	
	/**
	 * 产品高度
	 */
	private String productHeight;
	
	/**
	 * 产品布局标识(天花)
	 */
	private String productSmallpoxIdentify;
	
	/**
	 * 产品型号
	 */
	private String productModelNumber;
	
	/**
	 * 产品系列id
	 */
	private Integer seriesId;
	
	private String  deviceId = null;
	private String  msgId = null;
	private String  ids = null;
	private Integer start = 0;
	private Integer limit = 20;
	private String  order = null;
	private String  orderNum = null;
	private String  orders = null;
	/**级别限制的资源数量*/
	private int levelLimitCount=0;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public int getLevelLimitCount() {
		return levelLimitCount;
	}

	public void setLevelLimitCount(int levelLimitCount) {
		this.levelLimitCount = levelLimitCount;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getProductModelNumber() {
		return productModelNumber;
	}

	public void setProductModelNumber(String productModelNumber) {
		this.productModelNumber = productModelNumber;
	}

	public String getProductSmallpoxIdentify() {
		return productSmallpoxIdentify;
	}

	public void setProductSmallpoxIdentify(String productSmallpoxIdentify) {
		this.productSmallpoxIdentify = productSmallpoxIdentify;
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

	public String getBigTypeValuekeyInit() {
		return bigTypeValuekeyInit;
	}

	public void setBigTypeValuekeyInit(String bigTypeValuekeyInit) {
		this.bigTypeValuekeyInit = bigTypeValuekeyInit;
	}

	public String getSmallTypeValuekeyInit() {
		return smallTypeValuekeyInit;
	}

	public void setSmallTypeValuekeyInit(String smallTypeValuekeyInit) {
		this.smallTypeValuekeyInit = smallTypeValuekeyInit;
	}

	public String getBigTypeValuekey() {
		return bigTypeValuekey;
	}

	public void setBigTypeValuekey(String bigTypeValuekey) {
		this.bigTypeValuekey = bigTypeValuekey;
	}

	public String getSmallTypeValuekey() {
		return smallTypeValuekey;
	}

	public void setSmallTypeValuekey(String smallTypeValuekey) {
		this.smallTypeValuekey = smallTypeValuekey;
	}

	public Integer getCompositeType() {
		return compositeType;
	}

	public void setCompositeType(Integer compositeType) {
		this.compositeType = compositeType;
	}
	
	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getProductBigTypeValue() {
		return productBigTypeValue;
	}

	public void setProductBigTypeValue(Integer productBigTypeValue) {
		this.productBigTypeValue = productBigTypeValue;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getSalePriceValue() {
		return salePriceValue;
	}

	public void setSalePriceValue(Integer salePriceValue) {
		this.salePriceValue = salePriceValue;
	}

	public String getSalePriceValueName() {
		return SalePriceValueName;
	}

	public void setSalePriceValueName(String salePriceValueName) {
		SalePriceValueName = salePriceValueName;
	}

	/**获取对象的copy**/
    public DesignPlanProduct recommendedProductCopy(){
       DesignPlanProduct obj =  new DesignPlanProduct();
       obj.setSysCode(this.getSysCode());
       obj.setCreator(this.getCreator());
       obj.setGmtCreate(this.getGmtCreate());
       obj.setModifier(this.getModifier());
       obj.setGmtModified(this.getGmtModified());
       obj.setIsDeleted(this.getIsDeleted());
       obj.setProductId(this.getProductId());
       obj.setLocationFileId(this.getLocationFileId());
       obj.setProductSequence(this.getProductSequence());
       obj.setMaterialPicId(this.getMaterialPicId());
       obj.setAtt2(this.getAtt2());
       obj.setAtt3(this.getAtt3());
       obj.setAtt4(this.getAtt4());
       obj.setAtt5(this.getAtt5());
       obj.setAtt6(this.getAtt6());
       obj.setDateAtt1(this.getDateAtt1());
       obj.setDateAtt2(this.getDateAtt2());
       obj.setPlanProductId(this.getPlanProductId());
       obj.setDisplayStatus(this.getDisplayStatus());
       obj.setNumAtt3(this.getNumAtt3());
       obj.setNumAtt4(this.getNumAtt4());
       obj.setRemark(this.getRemark());
       obj.setIsHide(this.getIsHide());
       obj.setPosIndexPath(this.getPosIndexPath());
       obj.setInitProductId(this.getInitProductId());
       obj.setIsDirty(this.getIsDirty());
       obj.setProductGroupId(this.getProductGroupId());
       obj.setIsMainProduct(this.getIsMainProduct());
       obj.setPosName(this.getPosName());
       obj.setPlanGroupId(this.getPlanGroupId());
       obj.setModelProductId(this.getModelProductId());
       obj.setBindParentProductId(this.getBindParentProductId());
       obj.setSplitTexturesChooseInfo(this.getSplitTexturesChooseInfo());
       obj.setGroupType(this.getGroupType());
       obj.setSameProductTypeIndex(this.getSameProductTypeIndex());
       obj.setIsMainStructureProduct(this.getIsMainStructureProduct());
       obj.setIsGroupReplaceWay(this.getIsGroupReplaceWay());
       obj.setIsStandard(this.getIsStandard());
       obj.setCenter(this.getCenter());
       obj.setRegionMark(this.getRegionMark());
       obj.setStyleId(this.getStyleId());
       obj.setMeasureCode(this.getMeasureCode());
       obj.setDescribeInfo(this.getDescribeInfo());
       obj.setProductIndex(this.getProductIndex());
       obj.setWallOrientation(this.getWallOrientation());
       obj.setWallType(this.getWallType());
       return obj;
    }
    
	public String getIsInternalUser() {
		return IsInternalUser;
	}

	public void setIsInternalUser(String isInternalUser) {
		IsInternalUser = isInternalUser;
	}

	public List<Integer> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<Integer> resIdList) {
		this.resIdList = resIdList;
	}

}
