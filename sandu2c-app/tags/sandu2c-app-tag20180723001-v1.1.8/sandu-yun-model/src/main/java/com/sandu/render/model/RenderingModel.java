package com.sandu.render.model;

import java.io.Serializable;

import com.sandu.common.model.Mapper;


/**
 * 我的设计 效果图 model
 * @author yangzhun
 *
 */
public class RenderingModel extends Mapper implements Serializable {
    /**
     * 缩略图id
     */
    private Integer thumbId;
    /**
     * 缩略图封面
     */
    private String pic;
    /**
     * 缩略图对应方案的名称
     */
    private String name;
    /**
     * 创建时间
     */
    private String ctime;
    /**
     * 空间类型
     */
    private String type;
    /**
     * 面积
     */
    private String area;
    /**
     * 对应的设计方案id
     */
    private Integer planId; 
    /**
     * 创建人id
     */
    private Integer userId;
    /**
     * 一键发布状态
     */
    private Integer oneKeySt;
    /**
     * 公开状态
     */
    private Integer pubSt;
    /**
     * 设计方案副本id
     */
    private Integer cpId;
    private Integer isSort;
    private String areaValue;
    private String spaceStyleId;
    
    private String planName;
    
    public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getAreaValue() {
		return areaValue;
	}
	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}
	public String getSpaceStyleId() {
		return spaceStyleId;
	}
	public void setSpaceStyleId(String spaceStyleId) {
		this.spaceStyleId = spaceStyleId;
	}
	public Integer getIsSort() {
		return isSort;
	}
	public void setIsSort(Integer isSort) {
		this.isSort = isSort;
	}
	private String remark;
    private Integer houseId;
    
    public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String token;
    
   	public String getToken() {
   		return token;
   	}
   	public void setToken(String token) {
   		this.token = token;
   	}
	public Integer getThumbId() {
		return thumbId;
	}
	public void setThumbId(Integer thumbId) {
		this.thumbId = thumbId;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getOneKeySt() {
		return oneKeySt;
	}
	public void setOneKeySt(Integer oneKeySt) {
		this.oneKeySt = oneKeySt;
	}
	public Integer getPubSt() {
		return pubSt;
	}
	public void setPubSt(Integer pubSt) {
		this.pubSt = pubSt;
	}
	public Integer getCpId() {
		return cpId;
	}
	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}
	/**
     * 风格
     */
    private Integer designStyleId;
	public Integer getDesignStyleId() {
		return designStyleId;
	}

	public void setDesignStyleId(Integer designStyleId) {
		this.designStyleId = designStyleId;
	}
	/**
	 * 缩略图id
	 */
	private String smallPicId;
	/**
	 * 查看渲染效果来源
	 * //推荐1效果图2 设计3
	 */
	private String detailsSeeType;

	public String getSmallPicId() {
		return smallPicId;
	}
	public void setSmallPicId(String smallPicId) {
		this.smallPicId = smallPicId;
	}
	public String getDetailsSeeType() {
		return detailsSeeType;
	}
	public void setDetailsSeeType(String detailsSeeType) {
		this.detailsSeeType = detailsSeeType;
	}
	/**
	 * 方案來源类型  space_function_id
	 * 就是   对应   空间类型的value（客餐厅等）
	 */
	public Integer spaceFunctionId;
	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}
	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}
	

}
