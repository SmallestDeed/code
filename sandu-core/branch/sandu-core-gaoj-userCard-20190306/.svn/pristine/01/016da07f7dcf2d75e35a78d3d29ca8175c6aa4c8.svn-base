package com.sandu.api.base.input;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserCardAccessRecordAdd implements Serializable{

    private static final long serialVersionUID = 6347847594937514880L;
    /**
     * 用户名片ID
     */
    @NotNull(message = "用户名片ID")
    @Min(value = 0, message = "非法的用户名片ID")
    private Integer userCardId;


    /**
     * 1:转发微信好友,2:转发微信朋友圈,3:名片海报
     */
    @NotNull(message = "访问类型")
    @Min(value = 0, message = "非法的访问类型")
    private Integer accessType;


}