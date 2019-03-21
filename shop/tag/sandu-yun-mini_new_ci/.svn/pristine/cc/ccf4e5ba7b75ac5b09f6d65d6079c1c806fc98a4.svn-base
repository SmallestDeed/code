package com.sandu.designplan.model.vo;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/***
 * 设计方案
 * 
 * @author Administrator
 *
 */
@ApiModel(value = "设计方案", description = "设计方案")
public class DesignPlanVo implements Serializable{
    private static final long serialVersionUID = -4350293478239218059L;
    
    /***
     * 方案ID
     */
    @ApiModelProperty(value = "方案ID")
    private long planId;
    /***
     * 方案名称
     */
    @ApiModelProperty(value = "方案名称")
    private String planName;
    /***
     * 方案图片路径
     */
    @ApiModelProperty(value = "方案图片路径")
    private String picPath;
    /***
     * 收藏数量
     */
    @ApiModelProperty(value = "收藏数量")
    private int collectionCount;
    /***
     * 点赞数量
     */
    @ApiModelProperty(value = "点赞数量")
    private int likeCount;
    /***
     * 收藏数量
     */
    @ApiModelProperty(value = "当前用户收藏数量")
    private int currentUserLikeCount;
    /***
     * 点赞数量
     */
    @ApiModelProperty(value = "当前点赞数量")
    private int currentUserCollectionCount;

    public int getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

	public int getCurrentUserLikeCount() {
		return currentUserLikeCount;
	}

	public void setCurrentUserLikeCount(int currentUserLikeCount) {
		this.currentUserLikeCount = currentUserLikeCount;
	}

	public int getCurrentUserCollectionCount() {
		return currentUserCollectionCount;
	}

	public void setCurrentUserCollectionCount(int currentUserCollectionCount) {
		this.currentUserCollectionCount = currentUserCollectionCount;
	}
    
    
}
