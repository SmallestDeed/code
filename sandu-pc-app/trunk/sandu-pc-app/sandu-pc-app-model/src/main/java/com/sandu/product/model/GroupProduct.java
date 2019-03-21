package com.sandu.product.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sandu.product.model.po.GroupProductPO;

/**   
 * @Title: GroupProduct.java 
 * @Package com.nork.product.model
 * @Description:产品模块-产品组合主表
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:52:57
 * @version V1.0   
 */
public class GroupProduct extends GroupProductPO implements Serializable{
	
	private static final long serialVersionUID = 1L;

    /**  坐标txt 路径  **/
    private String locationPath;

    private Integer structureConfigFileId;
    
    private Integer productStructureId ;
    
    private String  structureName;
    
    private String  structureCode;
    
    private String  structureConfig;

	/** 户型归属  **/
	private String houseTypeValues;

	/**
	 * 封面url
	 */
	private String picPath;
	
	/**产品风格(list)*/
	private List<String> productStyleInfoList;
	
	private String productStyle;

	/**
	 * 主产品的过滤属性信息
	 */
	private List<ProductPropsSimple> productFilterPropList;
	
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

	public List<ProductPropsSimple> getProductFilterPropList() {
		return productFilterPropList;
	}

	public void setProductFilterPropList(List<ProductPropsSimple> productFilterPropList) {
		this.productFilterPropList = productFilterPropList;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getStructureConfig() {
		return structureConfig;
	}

	public void setStructureConfig(String structureConfig) {
		this.structureConfig = structureConfig;
	}

	public Integer getStructureConfigFileId() {
		return structureConfigFileId;
	}

	public void setStructureConfigFileId(Integer structureConfigFileId) {
		this.structureConfigFileId = structureConfigFileId;
	}

	public Integer getProductStructureId() {
		return productStructureId;
	}

	public void setProductStructureId(Integer productStructureId) {
		this.productStructureId = productStructureId;
	}

	public String getStructureName() {
		return structureName;
	}

	public void setStructureName(String structureName) {
		this.structureName = structureName;
	}

	public String getStructureCode() {
		return structureCode;
	}

	public void setStructureCode(String structureCode) {
		this.structureCode = structureCode;
	}
	
	public String getLocationPath() {
		return locationPath;
	}

	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}

	/*空间面积*/
	private Integer spaceCommonArea;

	public Integer getSpaceCommonArea() {
		return spaceCommonArea;
	}

	public void setSpaceCommonArea(Integer spaceCommonArea) {
		this.spaceCommonArea = spaceCommonArea;
	}

	public String getHouseTypeValues() {
		return houseTypeValues;
	}

	public void setHouseTypeValues(String houseTypeValues) {
		this.houseTypeValues = houseTypeValues;
	}

	//组合产品收藏状态1和0
    private Integer collectState;
    //组合产品列表
  	List<BaseProduct> groupProductList = new ArrayList<BaseProduct>();
	
  	private String brandName;
  	/**
  	 * 风格名称
  	 */
  	private String styleName;
  	
	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getCollectState() {
		return collectState;
	}

	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}

	public List<BaseProduct> getGroupProductList() {
		return groupProductList;
	}

	public void setGroupProductList(List<BaseProduct> groupProductList) {
		this.groupProductList = groupProductList;
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
        GroupProduct other = (GroupProduct) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getGroupCode() == null ? other.getGroupCode() == null : this.getGroupCode().equals(other.getGroupCode()))
            && (this.getGroupName() == null ? other.getGroupName() == null : this.getGroupName().equals(other.getGroupName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getSorting() == null ? other.getSorting() == null : this.getSorting().equals(other.getSorting()))
            && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
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
        result = prime * result + ((getGroupCode() == null) ? 0 : getGroupCode().hashCode());
        result = prime * result + ((getGroupName() == null) ? 0 : getGroupName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getSorting() == null) ? 0 : getSorting().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
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
    
    /**获取对象的copy**/
    /*public GroupProduct copy(){
       GroupProduct obj =  new GroupProduct();
       obj.setGroupCode(this.groupCode);
       obj.setGroupName(this.groupName);
       obj.setType(this.type);
       obj.setState(this.state);
       obj.setSorting(this.sorting);
       obj.setPicId(this.picId);
       obj.setCode(this.code);
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setAtt1(this.att1);
       obj.setAtt2(this.att2);
       obj.setNuma1(this.numa1);
       obj.setNuma2(this.numa2);
       obj.setRemark(this.remark);
       return obj;
    }*/
    
     /**获取对象的map**/
    /*public Map toMap(){
       Map map =  new HashMap();
       map.put("groupCode",this.groupCode);
       map.put("groupName",this.groupName);
       map.put("type",this.type);
       map.put("state",this.state);
       map.put("sorting",this.sorting);
       map.put("picId",this.picId);
       map.put("code",this.code);
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("att1",this.att1);
       map.put("att2",this.att2);
       map.put("numa1",this.numa1);
       map.put("numa2",this.numa2);
       map.put("remark",this.remark);
       return map;
    }*/

	public List<String> getProductStyleInfoList() {
		return productStyleInfoList;
	}

	public void setProductStyleInfoList(List<String> productStyleInfoList) {
		this.productStyleInfoList = productStyleInfoList;
	}

	public String getProductStyle() {
		return productStyle;
	}

	public void setProductStyle(String productStyle) {
		this.productStyle = productStyle;
	}

    
}
