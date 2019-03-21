package com.sandu.api.springFestivalActivity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxUserInviteRecord {
    private Long id;

    private Long activityId;

    private String openId;

    private String nickName;

    private String headPic;

    private Long userId;

    private Long inviteUserId;

    private Byte status;

    private Byte cardFlag;

    private Long cardId;

    private String remark;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;
}