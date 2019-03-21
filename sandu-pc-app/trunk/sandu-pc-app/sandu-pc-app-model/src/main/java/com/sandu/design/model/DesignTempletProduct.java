package com.sandu.design.model;

import java.io.Serializable;

import com.sandu.design.model.po.DesignTempletProductPO;

/**   
 * @Title: DesignTempletProduct.java 
 * @Package com.nork.onekeydesign.model
 * @Description:设计模块-方案产品表
 * @createAuthor pandajun 
 * @CreateDate 2015-07-07 11:04:44
 * @version V1.0   
 */
public class DesignTempletProduct extends DesignTempletProductPO implements Serializable{
	private static final long serialVersionUID = 1L;

	//产品名称
	private String productName;
	//产品编码
	private String productCode;
	//方案名称
	private String designName;
	/**  品牌名称  **/
	private String brandName;
	/**  产品品牌id  **/
	private Integer brandId;
	//大类名称
	private String ProductTypeName;
	//小类名称
	private String productSmallTypeName;
	
	//产品推荐数量
	private Integer recommendProCount;
	//历史产品推荐数量
	private Integer historyRecommendProCount;
	
	private Integer productTypeValue;
	private Integer productSmallTypeValue;

    
	/*款式id 得到的款式名*/
	private String styleName;
	
	/*结构类型*/
	private String structureType;
	/*结构编码*/
	private String structureCode;
	
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
	 * 背景墙全铺长度
	 */
	private String fullPaveLength;
	
	/**
	 * 产品布局标识(天花)
	 */
	private String productSmallpoxIdentify;

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

	public String getProductSmallpoxIdentify() {
		return productSmallpoxIdentify;
	}

	public void setProductSmallpoxIdentify(String productSmallpoxIdentify) {
		this.productSmallpoxIdentify = productSmallpoxIdentify;
	}

	public String getFullPaveLength() {
		return fullPaveLength;
	}

	public void setFullPaveLength(String fullPaveLength) {
		this.fullPaveLength = fullPaveLength;
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

	public String getStructureCode() {
		return structureCode;
	}

	public void setStructureCode(String structureCode) {
		this.structureCode = structureCode;
	}

	public String getStructureType() {
		return structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public Integer getProductSmallTypeValue() {
		return productSmallTypeValue;
	}

	public void setProductSmallTypeValue(Integer productSmallTypeValue) {
		this.productSmallTypeValue = productSmallTypeValue;
	}

	public Integer getProductTypeValue() {
		return productTypeValue;
	}

	public void setProductTypeValue(Integer productTypeValue) {
		this.productTypeValue = productTypeValue;
	}

	public Integer getHistoryRecommendProCount() {
		return historyRecommendProCount;
	}

	public void setHistoryRecommendProCount(Integer historyRecommendProCount) {
		this.historyRecommendProCount = historyRecommendProCount;
	}

	public Integer getRecommendProCount() {
		return recommendProCount;
	}

	public void setRecommendProCount(Integer recommendProCount) {
		this.recommendProCount = recommendProCount;
	}

	public String getProductTypeName() {
		return ProductTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		ProductTypeName = productTypeName;
	}


	public String getProductSmallTypeName() {
		return productSmallTypeName;
	}

	public void setProductSmallTypeName(String productSmallTypeName) {
		this.productSmallTypeName = productSmallTypeName;
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

    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

    public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

    public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
        DesignTempletProduct other = (DesignTempletProduct) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLocationFileId() == null ? other.getLocationFileId() == null : this.getLocationFileId().equals(other.getLocationFileId()))
            && (this.getProductSequence() == null ? other.getProductSequence() == null : this.getProductSequence().equals(other.getProductSequence()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getDesignTempletId() == null ? other.getDesignTempletId() == null : this.getDesignTempletId().equals(other.getDesignTempletId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getAtt1() == null ? other.getAtt1() == null : this.getAtt1().equals(other.getAtt1()))
            && (this.getAtt2() == null ? other.getAtt2() == null : this.getAtt2().equals(other.getAtt2()))
            && (this.getNuma1() == null ? other.getNuma1() == null : this.getNuma1().equals(other.getNuma1()))
            && (this.getNuma2() == null ? other.getNuma2() == null : this.getNuma2().equals(other.getNuma2()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLocationFileId() == null) ? 0 : getLocationFileId().hashCode());
        result = prime * result + ((getProductSequence() == null) ? 0 : getProductSequence().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getDesignTempletId() == null) ? 0 : getDesignTempletId().hashCode());
        result = prime * result + ((getSysCode() == null) ? 0 : getSysCode().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAtt1() == null) ? 0 : getAtt1().hashCode());
        result = prime * result + ((getAtt2() == null) ? 0 : getAtt2().hashCode());
        result = prime * result + ((getNuma1() == null) ? 0 : getNuma1().hashCode());
        result = prime * result + ((getNuma2() == null) ? 0 : getNuma2().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
;
        return result;
    }
    
}
