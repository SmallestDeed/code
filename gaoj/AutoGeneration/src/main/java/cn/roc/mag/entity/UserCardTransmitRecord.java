package cn.roc.mag.entity;

import java.util.Date;

public class UserCardTransmitRecord {
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户id，与sys_user表中的主键关联
     */
    private Integer userId;

    /**
     * 用户电子名片id，与user_card表中的主键关联
     */
    private Integer userCardId;

    /**
     * 1:转发微信好友,2:转发微信朋友圈,3:转发名片海报
     */
    private Byte transmitType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改时间，自动更新
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 逻辑删除字段,0:正常 1:已删除
     */
    private Byte isDelete;

    /**
     * 备注
     */
    private String remark;

    /**
     * 主键Id
     * @return id 主键Id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键Id
     * @param id 主键Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户id，与sys_user表中的主键关联
     * @return user_id 用户id，与sys_user表中的主键关联
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户id，与sys_user表中的主键关联
     * @param userId 用户id，与sys_user表中的主键关联
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 用户电子名片id，与user_card表中的主键关联
     * @return user_card_id 用户电子名片id，与user_card表中的主键关联
     */
    public Integer getUserCardId() {
        return userCardId;
    }

    /**
     * 用户电子名片id，与user_card表中的主键关联
     * @param userCardId 用户电子名片id，与user_card表中的主键关联
     */
    public void setUserCardId(Integer userCardId) {
        this.userCardId = userCardId;
    }

    /**
     * 1:转发微信好友,2:转发微信朋友圈,3:转发名片海报
     * @return transmit_type 1:转发微信好友,2:转发微信朋友圈,3:转发名片海报
     */
    public Byte getTransmitType() {
        return transmitType;
    }

    /**
     * 1:转发微信好友,2:转发微信朋友圈,3:转发名片海报
     * @param transmitType 1:转发微信好友,2:转发微信朋友圈,3:转发名片海报
     */
    public void setTransmitType(Byte transmitType) {
        this.transmitType = transmitType;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * 修改时间，自动更新
     * @return update_time 修改时间，自动更新
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间，自动更新
     * @param updateTime 修改时间，自动更新
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 更新者
     * @return updater 更新者
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * 更新者
     * @param updater 更新者
     */
    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    /**
     * 逻辑删除字段,0:正常 1:已删除
     * @return is_delete 逻辑删除字段,0:正常 1:已删除
     */
    public Byte getIsDelete() {
        return isDelete;
    }

    /**
     * 逻辑删除字段,0:正常 1:已删除
     * @param isDelete 逻辑删除字段,0:正常 1:已删除
     */
    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
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