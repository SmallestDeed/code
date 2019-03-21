package com.sandu.api.base.input;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserCardAccessOperationLogAdd implements Serializable{
    private static final long serialVersionUID = -3398574977620562954L;

    /**
     * 用户名片ID
     */
    @NotNull(message = "用户名片ID")
    @Min(value = 0, message = "非法的用户名片ID")
    private Integer userCardId;

    /**
     * 用户访问记录id
     */
    @NotNull(message = "用户访问记录id")
    @Min(value = 0, message = "非法的用户访问记录id")
    private Integer userCardAccessRecordId;

    /**
     * 操作类型:1:获取微信号 2: 获取手机号 3 : 获取邮箱 4 :获取地址 5 :留下联系方式
     */
    @NotNull(message = "操作类型")
    @Min(value = 0, message = "非法的操作类型")
    private Integer operationType;


    /**
     * 联系人手机号
     */
    private String phone;

    /**
     * 手机验证码
     */
    private String code;
}