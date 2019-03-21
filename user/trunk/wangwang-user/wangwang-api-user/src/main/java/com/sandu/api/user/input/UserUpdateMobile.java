package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserUpdateMobile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String msgId;
    /**
     * 手机号
     * */
    @NotNull(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;
    /**
     * 短信验证码
     * */
    @ApiModelProperty(value = "短信验证码", required = true)
    private String phoneCode;
    
    /**
     * 账号存在多个时,如果前端提示用户后,用户确认合并,则此值等于1
     */
    @ApiModelProperty(value = "账号存在多个时,如果前端提示用户后,用户确认合并,则此值等于1")
    private Integer comfirmFlag;
    /** 
     * 当值为 1的时候 仅修改当前企业;2表示修改所有关联企业
     * */
    @ApiModelProperty(value = "当值为 1的时候 仅修改当前企业;2表示修改所有关联企业")
    private Integer enterpriseFlag;
}
