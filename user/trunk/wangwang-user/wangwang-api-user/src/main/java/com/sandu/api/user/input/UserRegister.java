package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserRegister implements Serializable {

    @NotNull(message = "手机号不能为空")
    private String mobile;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "验证码不能为空")
    private String authCode;

    private Integer userType;

    @ApiModelProperty(value = "所属企业")
    private Integer sourceCompany;

    @ApiModelProperty(value = "邀请码")
    private String invitationCode;

}
