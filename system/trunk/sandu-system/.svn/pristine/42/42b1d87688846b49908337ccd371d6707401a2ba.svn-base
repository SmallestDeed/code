package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 内部用户 校验手机号 入参
 * @Date 2018/6/22 0022 10:33
 * @Modified By
 */
@Data
public class SysEiuUserCheckPhone implements Serializable{
    @ApiModelProperty(value = "用户id" )
    private Long id;

    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-9]))|(17[0-3,5-8])|(18[0-9])|166|198|199)\\d{8}$",message = "手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "操作类型",required = true)
    @NotBlank(message = "操作类型不能为空")
    private String oType;

    @ApiModelProperty(value = "用户类型",required = true)
    @NotBlank(message = "用户类型不能为空")
    private String userType;
}
