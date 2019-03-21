package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserUpdatePasswordAndMobile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String msgId;
    
    @NotNull(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String password;
    
    @NotNull(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @NotNull(message = "短信验证码不能为空")
    @ApiModelProperty(value = "短信验证码", required = true)
    private String phoneCode;
    
    /**
     * 账号存在多个时,如果前端提示用户后,用户确认修改,则此值等于1
     */
    private Integer comfirmFlag;

    /**
     * 用户选择修改类型:1.修改当前企业 2.修改所有关联企业
     */
    private Integer enterpriseFlag;
    
   
}
