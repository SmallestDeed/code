package com.sandu.home.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.sandu.home.model.po.SpaceCommonPO;

/**   
 * @Title: SpaceCommon.java 
 * @Package com.nork.home.model
 * @Description:户型房型-通用空间表
 * @createAuthor pandajun 
 * @CreateDate 2015-06-17 15:48:39
 * @version V1.0   
 */
public class SpaceCommon extends SpaceCommonPO implements Serializable{
	
	private static final long serialVersionUID = 1L;

    private Integer hideId;

	//空间户型缩略图id
	private Integer picSmallId;
	//空间户型图原图
    private String picPath;
    //空间户型图缩略图
    private String picSmallPath;

	/**  3d俯视图  **/
	private String view3dPic;
	//3d俯视图缩略图id
	private Integer view3dPicSmallId;
	/**  3d俯视图原图  **/
	private String view3dPicPath;
	/**  3d俯视图缩略图  **/
	private String view3dSmallPicPath;

	/*平面图对应缩略图id*/
	private Integer viewPlanSmallId;
	/*空间俯视平面图path*/
	private String viewPlanPath;
	/*空间俯视平面图对应缩略图path*/
	private String viewPlanSmallPath;
	
	private String cadPicPath;
	
	private String cadPicSmallPath;
	
	/*条件查询上架状态(多个)*/
	private List<Integer> putawayStates;

	private List<Integer>  statusList;
	
	/*空间类型黑名单:查询过滤用*/
	private Set<Integer> blackList;
	/*查询过滤条件:houseId*/
	private Integer houseId;
	/*查询过滤条件:userType*/
	private Integer userType;
	private Integer livingId;

	/*1为外网  2  为内网    默认为外网*/
	private String versionType;

	private Integer spaceShapeInt;

	//空间编码，用于按编码模糊查询
	private String sch_spaceCode;
	
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
	
	public Integer getHideId() {
		return hideId;
	}

	public void setHideId(Integer hideId) {
		this.hideId = hideId;
	}

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

	public String getSch_spaceCode() {
		return sch_spaceCode;
	}

	public void setSch_spaceCode(String sch_spaceCode) {
		this.sch_spaceCode = sch_spaceCode;
	}

	public Integer getSpaceShapeInt() {
		return spaceShapeInt;
	}

	public void setSpaceShapeInt(Integer spaceShapeInt) {
		this.spaceShapeInt = spaceShapeInt;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getHouseId() {
		return houseId;
	}

	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}

	public Set<Integer> getBlackList() {
		return blackList;
	}

	public void setBlackList(Set<Integer> blackList) {
		this.blackList = blackList;
	}

	public List<Integer> getPutawayStates() {
		return putawayStates;
	}
	
	public void setPutawayStates(List<Integer> putawayStates) {
		this.putawayStates = putawayStates;
	}
	
	public String getCadPicPath() {
		return cadPicPath;
	}

	public void setCadPicPath(String cadPicPath) {
		this.cadPicPath = cadPicPath;
	}

	public String getCadPicSmallPath() {
		return cadPicSmallPath;
	}

	public void setCadPicSmallPath(String cadPicSmallPath) {
		this.cadPicSmallPath = cadPicSmallPath;
	}

	public String getViewPlanPath() {
		return viewPlanPath;
	}

	public void setViewPlanPath(String viewPlanPath) {
		this.viewPlanPath = viewPlanPath;
	}

	public String getViewPlanSmallPath() {
		return viewPlanSmallPath;
	}

	public void setViewPlanSmallPath(String viewPlanSmallPath) {
		this.viewPlanSmallPath = viewPlanSmallPath;
	}

	public Integer getViewPlanSmallId() {
		return viewPlanSmallId;
	}

	public void setViewPlanSmallId(Integer viewPlanSmallId) {
		this.viewPlanSmallId = viewPlanSmallId;
	}

	//空间功能类型名称
	private String spaceFunctionName;
	//门位置
    private String doorLocationName;
  
    //面积范围
    private String areaRange;
    //空间类型
    private String type;
    
    private Integer totalSpaceNum;
    
    //统计子空间数量
    private Integer subspaceCount;
    //子空间归属空间编码
    private String standardSpaceCode;
    // 标准空间类型
    private String standardSpaceType;
    //空间形状info
    private String spaceShapeName;
    //子标准空间的子空间数量
    private Integer subStandardSpaceCount;
    //是否有白模产品 1 有 0 无
    private Integer isBmProduct;
    
    
	public Integer getLivingId() {
		return livingId;
	}

	public void setLivingId(Integer livingId) {
		this.livingId = livingId;
	}

	public Integer getIsBmProduct() {
		return isBmProduct;
	}

	public void setIsBmProduct(Integer isBmProduct) {
		this.isBmProduct = isBmProduct;
	}

	public Integer getSubStandardSpaceCount() {
		return subStandardSpaceCount;
	}

	public void setSubStandardSpaceCount(Integer subStandardSpaceCount) {
		this.subStandardSpaceCount = subStandardSpaceCount;
	}

	public String getSpaceShapeName() {
		return spaceShapeName;
	}

	public void setSpaceShapeName(String spaceShapeName) {
		this.spaceShapeName = spaceShapeName;
	}

	public Integer getView3dPicSmallId() {
		return view3dPicSmallId;
	}

	public void setView3dPicSmallId(Integer view3dPicSmallId) {
		this.view3dPicSmallId = view3dPicSmallId;
	}

	public Integer getPicSmallId() {
		return picSmallId;
	}

	public void setPicSmallId(Integer picSmallId) {
		this.picSmallId = picSmallId;
	}

	public String getPicSmallPath() {
		return picSmallPath;
	}

	public void setPicSmallPath(String picSmallPath) {
		this.picSmallPath = picSmallPath;
	}

	public String getView3dPicPath() {
		return view3dPicPath;
	}

	public void setView3dPicPath(String view3dPicPath) {
		this.view3dPicPath = view3dPicPath;
	}

	public String getView3dSmallPicPath() {
		return view3dSmallPicPath;
	}

	public void setView3dSmallPicPath(String view3dSmallPicPath) {
		this.view3dSmallPicPath = view3dSmallPicPath;
	}

	public String getStandardSpaceType() {
		return standardSpaceType;
	}

	public void setStandardSpaceType(String standardSpaceType) {
		this.standardSpaceType = standardSpaceType;
	}

	public String getStandardSpaceCode() {
		return standardSpaceCode;
	}

	public void setStandardSpaceCode(String standardSpaceCode) {
		this.standardSpaceCode = standardSpaceCode;
	}

	public Integer getSubspaceCount() {
		return subspaceCount;
	}

	public void setSubspaceCount(Integer subspaceCount) {
		this.subspaceCount = subspaceCount;
	}

	public Integer getTotalSpaceNum() {
		return totalSpaceNum;
	}

	public void setTotalSpaceNum(Integer totalSpaceNum) {
		this.totalSpaceNum = totalSpaceNum;
	}

	public  String getView3dPic() {
		return view3dPic;
	}
	public void setView3dPic(String view3dPic){
		this.view3dPic = view3dPic;
	}

public String getSpaceFunctionName() {
		return spaceFunctionName;
	}

	public void setSpaceFunctionName(String spaceFunctionName) {
		this.spaceFunctionName = spaceFunctionName;
	}

   public String getDoorLocationName() {
		return doorLocationName;
	}

	public void setDoorLocationName(String doorLocationName) {
		this.doorLocationName = doorLocationName;
	}
	

   public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	

    public String getAreaRange() {
		return areaRange;
	}

	public void setAreaRange(String areaRange) {
		this.areaRange = areaRange;
	}
	

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
        SpaceCommon other = (SpaceCommon) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSysCode() == null ? other.getSysCode() == null : this.getSysCode().equals(other.getSysCode()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getSpaceCode() == null ? other.getSpaceCode() == null : this.getSpaceCode().equals(other.getSpaceCode()))
            && (this.getSpaceName() == null ? other.getSpaceName() == null : this.getSpaceName().equals(other.getSpaceName()))
            && (this.getSpaceFunctionId() == null ? other.getSpaceFunctionId() == null : this.getSpaceFunctionId().equals(other.getSpaceFunctionId()))
            && (this.getMainLength() == null ? other.getMainLength() == null : this.getMainLength().equals(other.getMainLength()))
            && (this.getMainWidth() == null ? other.getMainWidth() == null : this.getMainWidth().equals(other.getMainWidth()))
            && (this.getSpaceAreas() == null ? other.getSpaceAreas() == null : this.getSpaceAreas().equals(other.getSpaceAreas()))
            && (this.getSpaceShape() == null ? other.getSpaceShape() == null : this.getSpaceShape().equals(other.getSpaceShape()))
            && (this.getDoorLocationType() == null ? other.getDoorLocationType() == null : this.getDoorLocationType().equals(other.getDoorLocationType()))
            && (this.getDoorLocationId() == null ? other.getDoorLocationId() == null : this.getDoorLocationId().equals(other.getDoorLocationId()))
            && (this.getSpaceDesc() == null ? other.getSpaceDesc() == null : this.getSpaceDesc().equals(other.getSpaceDesc()))
            && (this.getLocationArrays() == null ? other.getLocationArrays() == null : this.getLocationArrays().equals(other.getLocationArrays()))
            && (this.getIsIncludeWay() == null ? other.getIsIncludeWay() == null : this.getIsIncludeWay().equals(other.getIsIncludeWay()))
            && (this.getWayLocation() == null ? other.getWayLocation() == null : this.getWayLocation().equals(other.getWayLocation()))
            && (this.getUserNum() == null ? other.getUserNum() == null : this.getUserNum().equals(other.getUserNum()))
            && (this.getSearchNum() == null ? other.getSearchNum() == null : this.getSearchNum().equals(other.getSearchNum()))
            && (this.getSpaceNum() == null ? other.getSpaceNum() == null : this.getSpaceNum().equals(other.getSpaceNum()))
            && (this.getSpacePercent() == null ? other.getSpacePercent() == null : this.getSpacePercent().equals(other.getSpacePercent()))
            && (this.getDatasourceId() == null ? other.getDatasourceId() == null : this.getDatasourceId().equals(other.getDatasourceId()))
            && (this.getPicId() == null ? other.getPicId() == null : this.getPicId().equals(other.getPicId()))
            && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getModel3dId() == null ? other.getModel3dId() == null : this.getModel3dId().equals(other.getModel3dId()))
            && (this.getModelU3dId() == null ? other.getModelU3dId() == null : this.getModelU3dId().equals(other.getModelU3dId()))
            && (this.getView3dPic() == null ? other.getView3dPic() == null : this.getView3dPic().equals(other.getView3dPic()))
            && (this.getAtt4() == null ? other.getAtt4() == null : this.getAtt4().equals(other.getAtt4()))
            && (this.getAtt5() == null ? other.getAtt5() == null : this.getAtt5().equals(other.getAtt5()))
            && (this.getAtt6() == null ? other.getAtt6() == null : this.getAtt6().equals(other.getAtt6()))
            && (this.getPublishModified() == null ? other.getPublishModified() == null : this.getPublishModified().equals(other.getPublishModified()))
            && (this.getDateAtt2() == null ? other.getDateAtt2() == null : this.getDateAtt2().equals(other.getDateAtt2()))
            && (this.getStyleId() == null ? other.getStyleId() == null : this.getStyleId().equals(other.getStyleId()))
            && (this.getCadPicId() == null ? other.getCadPicId() == null : this.getCadPicId().equals(other.getCadPicId()))
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
        result = prime * result + ((getSpaceCode() == null) ? 0 : getSpaceCode().hashCode());
        result = prime * result + ((getSpaceName() == null) ? 0 : getSpaceName().hashCode());
        result = prime * result + ((getSpaceFunctionId() == null) ? 0 : getSpaceFunctionId().hashCode());
        result = prime * result + ((getMainLength() == null) ? 0 : getMainLength().hashCode());
        result = prime * result + ((getMainWidth() == null) ? 0 : getMainWidth().hashCode());
        result = prime * result + ((getSpaceAreas() == null) ? 0 : getSpaceAreas().hashCode());
        result = prime * result + ((getSpaceShape() == null) ? 0 : getSpaceShape().hashCode());
        result = prime * result + ((getDoorLocationType() == null) ? 0 : getDoorLocationType().hashCode());
        result = prime * result + ((getDoorLocationId() == null) ? 0 : getDoorLocationId().hashCode());
        result = prime * result + ((getSpaceDesc() == null) ? 0 : getSpaceDesc().hashCode());
        result = prime * result + ((getLocationArrays() == null) ? 0 : getLocationArrays().hashCode());
        result = prime * result + ((getIsIncludeWay() == null) ? 0 : getIsIncludeWay().hashCode());
        result = prime * result + ((getWayLocation() == null) ? 0 : getWayLocation().hashCode());
        result = prime * result + ((getUserNum() == null) ? 0 : getUserNum().hashCode());
        result = prime * result + ((getSearchNum() == null) ? 0 : getSearchNum().hashCode());
        result = prime * result + ((getSpaceNum() == null) ? 0 : getSpaceNum().hashCode());
        result = prime * result + ((getSpacePercent() == null) ? 0 : getSpacePercent().hashCode());
        result = prime * result + ((getDatasourceId() == null) ? 0 : getDatasourceId().hashCode());
        result = prime * result + ((getPicId() == null) ? 0 : getPicId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getModel3dId() == null) ? 0 : getModel3dId().hashCode());
        result = prime * result + ((getModelU3dId() == null) ? 0 : getModelU3dId().hashCode());
        result = prime * result + ((getView3dPic() == null) ? 0 : getView3dPic().hashCode());
        result = prime * result + ((getAtt4() == null) ? 0 : getAtt4().hashCode());
        result = prime * result + ((getAtt5() == null) ? 0 : getAtt5().hashCode());
        result = prime * result + ((getAtt6() == null) ? 0 : getAtt6().hashCode());
        result = prime * result + ((getPublishModified() == null) ? 0 : getPublishModified().hashCode());
        result = prime * result + ((getDateAtt2() == null) ? 0 : getDateAtt2().hashCode());
        result = prime * result + ((getStyleId() == null) ? 0 : getStyleId().hashCode());
        result = prime * result + ((getCadPicId() == null) ? 0 : getCadPicId().hashCode());
        result = prime * result + ((getNumAtt3() == null) ? 0 : getNumAtt3().hashCode());
        result = prime * result + ((getNumAtt4() == null) ? 0 : getNumAtt4().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());

        return result;
    }
    
    /**获取对象的copy**/
    /*public SpaceCommon copy(){
       SpaceCommon obj =  new SpaceCommon();
       obj.setSysCode(this.sysCode);
       obj.setCreator(this.creator);
       obj.setGmtCreate(this.gmtCreate);
       obj.setModifier(this.modifier);
       obj.setGmtModified(this.gmtModified);
       obj.setIsDeleted(this.isDeleted);
       obj.setSpaceCode(this.spaceCode);
       obj.setSpaceName(this.spaceName);
       obj.setSpaceFunctionId(this.spaceFunctionId);
       obj.setMainLength(this.mainLength);
       obj.setMainWidth(this.mainWidth);
       obj.setSpaceAreas(this.spaceAreas);
       obj.setSpaceShape(this.spaceShape);
       obj.setDoorLocationType(this.doorLocationType);
       obj.setDoorLocationId(this.doorLocationId);
       obj.setSpaceDesc(this.spaceDesc);
       obj.setLocationArrays(this.locationArrays);
       obj.setIsIncludeWay(this.isIncludeWay);
       obj.setWayLocation(this.wayLocation);
       obj.setUserNum(this.userNum);
       obj.setSearchNum(this.searchNum);
       obj.setSpaceNum(this.spaceNum);
       obj.setSpacePercent(this.spacePercent);
       obj.setDatasourceId(this.datasourceId);
       obj.setPicId(this.picId);
       obj.setModelId(this.modelId);
       obj.setStatus(this.status);
       obj.setModel3dId(this.model3dId);
       obj.setModelU3dId(this.modelU3dId);
       obj.setView3dPic(this.view3dPic);
       obj.setAtt4(this.att4);
       obj.setAtt5(this.att5);
       obj.setAtt6(this.att6);
       obj.setPublishModified(this.publishModified);
       obj.setDateAtt2(this.dateAtt2);
       obj.setStyleId(this.styleId);
       obj.setCadPicId(this.cadPicId);
       obj.setNumAtt3(this.numAtt3);
       obj.setNumAtt4(this.numAtt4);
       obj.setRemark(this.remark);
       return obj;
    }*/
    
     /**获取对象的map**/
    /*public Map toMap(){
       Map map =  new HashMap();
       map.put("sysCode",this.sysCode);
       map.put("creator",this.creator);
       map.put("gmtCreate",this.gmtCreate);
       map.put("modifier",this.modifier);
       map.put("gmtModified",this.gmtModified);
       map.put("isDeleted",this.isDeleted);
       map.put("spaceCode",this.spaceCode);
       map.put("spaceName",this.spaceName);
       map.put("spaceFunctionId",this.spaceFunctionId);
       map.put("mainLength",this.mainLength);
       map.put("mainWidth",this.mainWidth);
       map.put("spaceAreas",this.spaceAreas);
       map.put("spaceShape",this.spaceShape);
       map.put("doorLocationType",this.doorLocationType);
       map.put("doorLocationId",this.doorLocationId);
       map.put("spaceDesc",this.spaceDesc);
       map.put("locationArrays",this.locationArrays);
       map.put("isIncludeWay",this.isIncludeWay);
       map.put("wayLocation",this.wayLocation);
       map.put("userNum",this.userNum);
       map.put("searchNum",this.searchNum);
       map.put("spaceNum",this.spaceNum);
       map.put("spacePercent",this.spacePercent);
       map.put("datasourceId",this.datasourceId);
       map.put("picId",this.picId);
       map.put("modelId",this.modelId);
       map.put("status",this.status);
       map.put("att1",this.model3dId);
       map.put("att2",this.modelU3dId);
       map.put("view3dPic",this.view3dPic);
       map.put("att4",this.att4);
       map.put("att5",this.att5);
       map.put("att6",this.att6);
       map.put("publishModified",this.publishModified);
       map.put("dateAtt2",this.dateAtt2);
       map.put("numAtt1",this.styleId);
       map.put("cadPicId",this.cadPicId);
       map.put("numAtt3",this.numAtt3);
       map.put("numAtt4",this.numAtt4);
       map.put("remark",this.remark);
       return map;
    }*/
}
