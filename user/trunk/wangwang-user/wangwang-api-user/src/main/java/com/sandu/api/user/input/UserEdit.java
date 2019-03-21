package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserEdit implements Serializable {

    private Long id;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户类型")
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户来源")
    private Integer userSource;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "街道编码")
    private String streetCode;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "套餐id")
    private Long servicesId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "经销商id")
    private Long franchiserId;

    @ApiModelProperty(value = "调用对象")
    @NotNull(message = "企业类型不能为空")
    private String userMethod;

    @ApiModelProperty(value = "套餐年限id")
    private Integer priceId;
}
