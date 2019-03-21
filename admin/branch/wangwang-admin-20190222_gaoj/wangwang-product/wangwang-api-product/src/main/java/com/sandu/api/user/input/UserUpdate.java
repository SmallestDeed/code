package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

/**
 * @author Sandu
 */
@Data
public class UserUpdate implements Serializable{
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "昵称", required =  true)
    private String nick;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "性别:1男,2女", required =  true)
    private Integer sex;
    @ApiModelProperty(value = "Email" , required =  true)
    @Email
    private String email;
    private long id;
}
