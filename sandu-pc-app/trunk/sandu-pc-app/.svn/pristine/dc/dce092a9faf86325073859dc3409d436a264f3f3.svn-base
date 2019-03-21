package com.sandu.design.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sandu.design.model.po.DesignPlanProductPO;
import com.sandu.product.model.BaseProduct;
import com.sandu.product.model.dto.SplitTextureDTO;
import com.sandu.product.model.result.CategoryProductResult;

/**   
 * @Title: DesignPlanProduct.java 
 * @Package com.nork.onekeydesign.model
 * @Description:设计方案-设计方案产品库
 * @createAuthor pandajun 
 * @CreateDate 2015-06-26 11:26:11
 * @version V1.0   
 */
public class DesignPlanProduct extends DesignPlanProductPO implements Serializable {

	private static final long serialVersionUID = 7056547447867912098L;

	/**  设计方案产品ID  **/
	private Integer designPlanProductId;
	
	/**  设计方案id  **/
	private Integer designPlanId;
	
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
	
	/**  样板房列表产品id  **/
	private Integer templatePlanProductId;
	
	/** 产品类型名称 **/
	private String productTypeName;
	
	/** 用户ID **/
	private Integer userId;
	
	/** 品牌名称  **/
	private String brandName;
	
	/** 产品价格 **/
	private Double salePrice;
	
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
	
	
	private String rootType;
	private String productLength;
	private String productWidth;
	private String productHeight;
	/**  u3d模型  **/
	private String u3dModelPath;
	
	/*判断是否是可拆分材质产品:0:普通产品;1:可拆分材质产品(橱柜)*/
	private Integer isSplit=new Integer(0);
	
	/*可拆分材质信息*/
	private List<SplitTextureDTO> splitTexturesChoose;
	
	/*过滤条件:序列号里的品牌,大类,小类,产品ids(序列号条件设置到BaseProduct类中)*/
	private List<BaseProduct> baseProduct;
	
	/**携带得白模产品集合*/
	private Map<String,CategoryProductResult> basicModelMap;
	
	/** 产品属性 **/
	private Map<String,String> propertyMap;
 
	private String isInternalUser;
	
	//0、实体墙  1、背景墙 2、门框
	private Integer bgWall;
	
	private List<Integer>resIdList;

	/*产品的状态*/
	private Integer productPutawayState;
 
	
	public Integer getProductPutawayState() {
		return productPutawayState;
	}

	public void setProductPutawayState(Integer productPutawayState) {
		this.productPutawayState = productPutawayState;
	}

	public List<Integer> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<Integer> resIdList) {
		this.resIdList = resIdList;
	}

	

	

	List<Integer>designPlanProductIds = new ArrayList<Integer>();

	

	

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

	public List<Integer> getDesignPlanProductIds() {
		return designPlanProductIds;
	}

	public void setDesignPlanProductIds(List<Integer> designPlanProductIds) {
		this.designPlanProductIds = designPlanProductIds;
	}

	public Integer getBgWall() {
		return bgWall;
	}

	public void setBgWall(Integer bgWall) {
		this.bgWall = bgWall;
	}

	public String getIsInternalUser() {
		return isInternalUser;
	}

	public void setIsInternalUser(String isInternalUser) {
		this.isInternalUser = isInternalUser;
	}

	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public Map<String, CategoryProductResult> getBasicModelMap() {
		return basicModelMap;
	}

	public void setBasicModelMap(Map<String, CategoryProductResult> basicModelMap) {
		this.basicModelMap = basicModelMap;
	}

	public List<BaseProduct> getBaseProduct() {
		return baseProduct;
	}

	public void setBaseProduct(List<BaseProduct> baseProduct) {
		this.baseProduct = baseProduct;
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

	public String getU3dModelPath() {
		return u3dModelPath;
	}
	
	public void setU3dModelPath(String u3dModelPath) {
		this.u3dModelPath = u3dModelPath;
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

	public String getMaterialConfigPath() {
		return materialConfigPath;
	}
	public void setMaterialConfigPath(String materialConfigPath) {
		this.materialConfigPath = materialConfigPath;
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

	public Integer getDesignPlanId() {
		return designPlanId;
	}
	public void setDesignPlanId(Integer designPlanId) {
		this.designPlanId = designPlanId;
	}
	public Integer getTemplatePlanProductId() {
		return templatePlanProductId;
	}
	public void setTemplatePlanProductId(Integer templatePlanProductId) {
		this.templatePlanProductId = templatePlanProductId;
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

	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public Integer getDesignPlanProductId() {
		return designPlanProductId;
	}
	public void setDesignPlanProductId(Integer designPlanProductId) {
		this.designPlanProductId = designPlanProductId;
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
        DesignPlanProduct other = (DesignPlanProduct) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getPlanId() == null ? other.getPlanId() == null : this.getPlanId().equals(other.getPlanId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getLocationFileId() == null ? other.getLocationFileId() == null : this.getLocationFileId().equals(other.getLocationFileId()))
            && (this.getProductSequence() == null ? other.getProductSequence() == null : this.getProductSequence().equals(other.getProductSequence()))
            && (this.getMaterialPicId() == null ? other.getMaterialPicId() == null : this.getMaterialPicId().equals(other.getMaterialPicId()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getAtt3() == null ? other.getAtt3() == null : this.getAtt3().equals(other.getAtt3()))
            && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
            && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
            && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
            && (this.getDateAtt1() == null ? other.getDateAtt1() == null : this.getDateAtt1().equals(other.getDateAtt1()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
            && (this.getPlanProductId() == null ? other.getPlanProductId() == null : this.getPlanProductId().equals(other.getPlanProductId()))
            && (this.getDisplayStatus() == null ? other.getDisplayStatus() == null : this.getDisplayStatus().equals(other.getDisplayStatus()))
            && (this.getNumAtt3() == null ? other.getNumAtt3() == null : this.getNumAtt3().equals(other.getNumAtt3()))
            && (this.getNumAtt4() == null ? other.getNumAtt4() == null : this.getNumAtt4().equals(other.getNumAtt4()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
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
        result = prime * result + ((getPlanId() == null) ? 0 : getPlanId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getLocationFileId() == null) ? 0 : getLocationFileId().hashCode());
        result = prime * result + ((getProductSequence() == null) ? 0 : getProductSequence().hashCode());
        result = prime * result + ((getMaterialPicId() == null) ? 0 : getMaterialPicId().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getAtt3() == null) ? 0 : getAtt3().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getDateAtt1() == null) ? 0 : getDateAtt1().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getPlanProductId() == null) ? 0 : getPlanProductId().hashCode());
        result = prime * result + ((getDisplayStatus() == null) ? 0 : getDisplayStatus().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
    /**获取对象的copy**/
    /*public DesignPlanProduct copy(){
       DesignPlanProduct obj =  new DesignPlanProduct();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setPlanId(this.planId);
       obj.setProductId(this.productId);
       obj.setLocationFileId(this.locationFileId);
       obj.setProductSequence(this.productSequence);
       obj.setMaterialPicId(this.materialPicId);
       obj.setAtt2(this.att2);
       obj.setAtt3(this.att3);
       obj.setAtt4(this.att4);
       obj.setAtt5(this.att5);
       obj.setAtt6(this.att6);
       obj.setDateAtt1(this.dateAtt1);
       obj.setDateAtt2(this.dateAtt2);
       obj.setPlanProductId(this.planProductId);
       obj.setDisplayStatus(this.displayStatus);
       obj.setNumAtt3(this.numAtt3);
       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);

       return obj;
    }*/
    
    
    
    /**获取对象的copy**/
	public DesignPlanRecommendedProduct recommendedCopy() {
		DesignPlanRecommendedProduct obj = new DesignPlanRecommendedProduct();
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
		return obj;
	}
    
     /**获取对象的map**/
    /*public Map toMap(){
       Map map =  new HashMap();
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("planId",this.planId);
       map.put("productId",this.productId);
       map.put("locationFileId",this.locationFileId);
       map.put("productSequence",this.productSequence);
       map.put("materialPicId",this.materialPicId);
       map.put("att2",this.att2);
       map.put("att3",this.att3);
       map.put("att4",this.att4);
       map.put("att5",this.att5);
       map.put("att6",this.att6);
       map.put("dateAtt1",this.dateAtt1);
       map.put("dateAtt2",this.dateAtt2);
       map.put("planProductId",this.planProductId);
       map.put("displayStatus",this.displayStatus);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);

       return map;
    }*/
    
    //用户产品配置三种情况（1品牌，2品牌大类，3品牌大类小类）
    private String brands;
    private String bigType;
    private String smallType;
    
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
    
}
