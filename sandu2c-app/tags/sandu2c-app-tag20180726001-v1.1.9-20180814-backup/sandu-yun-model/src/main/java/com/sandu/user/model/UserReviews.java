package com.sandu.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserReviews implements Serializable{
    private static final long serialVersionUID = 7505484469177850022L;
    /**
     * 
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 业务ID
     */
    private Integer businessId;

    /**
     * 评论详情
     */
    private String reviewsMsg;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 是否删除（0:否，1:是）
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户ID
     * @return user_id 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 业务ID
     * @return business_id 业务ID
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 业务ID
     * @param businessId 业务ID
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 评论详情
     * @return reviews_msg 评论详情
     */
    public String getReviewsMsg() {
        return reviewsMsg;
    }

    /**
     * 评论详情
     * @param reviewsMsg 评论详情
     */
    public void setReviewsMsg(String reviewsMsg) {
        this.reviewsMsg = reviewsMsg == null ? null : reviewsMsg.trim();
    }

    /**
     * 创建者
     * @return creator 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 创建时间
     * @return gmt_create 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 创建时间
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 是否删除（0:否，1:是）
     * @return is_deleted 是否删除（0:否，1:是）
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除（0:否，1:是）
     * @param isDeleted 是否删除（0:否，1:是）
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 备注
     * @return remark 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}