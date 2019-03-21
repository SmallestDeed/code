package com.nork.onekeydesign.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DesignPlanResult implements Serializable {
	
	private Integer designId;
	
	private Integer designTempletId;
	
	private String designCode;
	
	private String designName;
	
	private Integer userId;
	
	private Integer spaceFunctionId;
	private Integer designStyleId;
	private Integer shareCount;//分享数量
	/** 分享数量  **/
	private Integer shareTotal;
    public Integer getShareTotal() {
		return shareTotal;
	}

	public void setShareTotal(Integer shareTotal) {
		this.shareTotal = shareTotal;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	
	public Integer getSpaceFunctionId() {
		return spaceFunctionId;
	}
	public void setSpaceFunctionId(Integer spaceFunctionId) {
		this.spaceFunctionId = spaceFunctionId;
	}
	public Integer getDesignStyleId() {
		return designStyleId;
	}
	public void setDesignStyleId(Integer designStyleId) {
		this.designStyleId = designStyleId;
	}
	private String userName;
	/**  设计来源  **/
	private String designSource;
	
	private String spaceCode;
	
	private String spaceName;
	
	private String spaceAreas;
	/**  渲染图  **/
	private String renderPicIds;
	/**  创建时间  **/
	private Date gmtCreate;
	/**  设计修改时间  **/
	private Date gmtModified;
	
	private String picPath;
	
	List picList = new ArrayList();
	/** 收藏状态 **/
	private Integer collectState;
	private Integer isOpen;
	public Integer getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	/** 点赞状态 **/
	private Integer likeState;
	public Integer getLikeState() {
		return likeState;
	}
	public void setLikeState(Integer likeState) {
		this.likeState = likeState;
	}
	/** 设计产品数量  **/
	private Integer designProductCount;
	
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}  
	public String getDesignCode() {
		return designCode;
	}
	public void setDesignCode(String designCode) {
		this.designCode = designCode;
	}
	public String getDesignName() {
		return designName;
	}
	public void setDesignName(String designName) {
		this.designName = designName;
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
	public String getDesignSource() {
		return designSource;
	}
	public void setDesignSource(String designSource) {
		this.designSource = designSource;
	}
	public String getSpaceCode() {
		return spaceCode;
	}
	public void setSpaceCode(String spaceCode) {
		this.spaceCode = spaceCode;
	}
	public String getSpaceName() {
		return spaceName;
	}
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	public String getSpaceAreas() {
		return spaceAreas;
	}
	public void setSpaceAreas(String spaceAreas) {
		this.spaceAreas = spaceAreas;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public List getPicList() {
		return picList;
	}
	public void setPicList(List picList) {
		this.picList = picList;
	}
	public Integer getCollectState() {
		return collectState;
	}
	public void setCollectState(Integer collectState) {
		this.collectState = collectState;
	}
	public Integer getDesignProductCount() {
		return designProductCount;
	}
	public void setDesignProductCount(Integer designProductCount) {
		this.designProductCount = designProductCount;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public String getRenderPicIds() {
		return renderPicIds;
	}
	public void setRenderPicIds(String renderPicIds) {
		this.renderPicIds = renderPicIds;
	}
	public Integer getDesignTempletId() {
		return designTempletId;
	}
	public void setDesignTempletId(Integer designTempletId) {
		this.designTempletId = designTempletId;
	}

}
