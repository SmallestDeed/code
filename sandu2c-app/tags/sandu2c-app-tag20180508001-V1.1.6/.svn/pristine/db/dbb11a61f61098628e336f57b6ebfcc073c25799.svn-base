package com.sandu.user.model;

import com.sandu.common.model.PageModel;

import java.io.Serializable;
import java.util.Date;

public class UserPrivateMessage implements Serializable{
    private static final long serialVersionUID = -807503105237540698L;
    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 发送者Id
     */
    private Integer userId;

    /**
     * 接受者Id
     */
    private Integer friendId;

    /**
     * 发送者id
     */
    private Integer senderId;

    /**
     * 接受者Id
     */
    private Integer receiverId;

    /**
     * 消息类型,1：普通消息 2：系统消息
     */
    private Integer messageType;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 消息发送时间
     */
    private Date sendTime;

    /**
     * 消息状态 1：未读 2：已读 3：删除
     */
    private Byte status;

    /**
     * 是否删除（0:否，1:是）
     */
    private Integer isDeleted;

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
     * 发送者Id
     * @return user_id 发送者Id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 发送者Id
     * @param userId 发送者Id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 接受者Id
     * @return friend_id 接受者Id
     */
    public Integer getFriendId() {
        return friendId;
    }

    /**
     * 接受者Id
     * @param friendId 接受者Id
     */
    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    /**
     * 发送者id
     * @return sender_id 发送者id
     */
    public Integer getSenderId() {
        return senderId;
    }

    /**
     * 发送者id
     * @param senderId 发送者id
     */
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    /**
     * 接受者Id
     * @return receiver_id 接受者Id
     */
    public Integer getReceiverId() {
        return receiverId;
    }

    /**
     * 接受者Id
     * @param receiverId 接受者Id
     */
    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    /**
     * 消息类型,1：普通消息 2：系统消息
     * @return message_type 消息类型,1：普通消息 2：系统消息
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * 消息类型,1：普通消息 2：系统消息
     * @param messageType 消息类型,1：普通消息 2：系统消息
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    /**
     * 消息内容
     * @return message_content 消息内容
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 消息内容
     * @param messageContent 消息内容
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    /**
     * 消息发送时间
     * @return send_time 消息发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 消息发送时间
     * @param sendTime 消息发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 消息状态 1：未读 2：已读 3：删除
     * @return status 消息状态 1：未读 2：已读 3：删除
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 消息状态 1：未读 2：已读 3：删除
     * @param status 消息状态 1：未读 2：已读 3：删除
     */
    public void setStatus(Byte status) {
        this.status = status;
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
}