package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description 经销商用户 确认 经销商企业 入参
 * @Date 2018/6/15 0015 10:32
 * @Modified By
 */
@Data
public class FranchiserAccountUpdate implements Serializable{

    @ApiModelProperty(value = "密码" , required = true)
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "账号",required = true)
    @NotBlank(message = "账号不能为空")
    private String userName;

    @ApiModelProperty(value = "经销商",required = true)
    @NotNull(message = "经销商不能为空")
    private Long franchiserId;

    @ApiModelProperty(value = "经销商",required = true)
    @NotNull(message = "企业id不能为空")
    private Long companyId;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "u3d标识",required = true)
    private String msgId;
}
