package com.sandu.api.user.model;

import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserUpdate {

    @ApiModelProperty("用户名")
    private String username;

    private String nick;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别:1男,2女")
    private Integer sex;

    @ApiModelProperty("Email")
    @Email
    private String email;

    @ApiModelProperty("手机短信验证码")
    private String phoneCode;

    @ApiModelProperty("图片验证码")
    private String imgCode;

    @ApiModelProperty("平台编码")
    private String sysCode;

    @ApiModelProperty("用户userId")
    private Long userId;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("省编码")
    private String provinceCode;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("区编码")
    private String areaCode;

    @ApiModelProperty("街道编码")
    private String streetCode;

    @ApiModelProperty("头像Id")
    private Long picId;

}
