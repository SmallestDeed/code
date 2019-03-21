package com.sandu.api.feedback.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-feedback
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-12-13 18:05
 */
@Data
public class SysUserFeedback implements Serializable {

    /** 主键Id */
    private Integer id;
    /** 反馈用户Id */
    private Integer userId;
    /** 平台Id */
    private Integer platformId;
    /**企业微信appId**/
    private String appId;
    /** 反馈用户openId(来源是随选网和企业小程序的反馈必填) */
    private String openId;
    /** 反馈用户登录名(来源是移动端，商家管理后台，通用版必填) */
    private String userName;
    /** 企业Id(除开来源是随选网其他必填) */
    private Integer companyId;
    /** 微信昵称(来源是随选网和企业小程序的反馈必填) */
    private String wxNickName;
    /** 用户头像Id */
    private Integer headPicId;
    /** 联系电话 */
    private String phone;
    /** 反馈内容 */
    private String feedbackContent;
    /** 反馈图像Ids(逗号隔开，最多四个) */
    private String feedbackPics;
    /** 处理状态(0:未处理；1:已处理) */
    private Integer dealStatus;
    /** 答复时间 */
    private Date gmtReply;
    /** 答复者Id */
    private Integer replyUserId;
    /** 答复内容 */
    private String replyContent;
    /** 评价(0:满意；1:不满意) */
    private Integer estimate;
    /** 创建时间（也做反馈时间） */
    private Date gmtCreate;
    /** 修改时间 */
    private Date gmtModified;
    /** 备注 */
    private String remark;
    /** 是否删除(0:未删除；1:删除) */
    private Integer isDeleted;
    /**是否已读**/
    private Integer isRead;
    
}
