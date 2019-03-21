package com.sandu.api.user.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author chenqiang
 * @Description 经销商用户编辑 入参
 * @Date 2018/6/7 0007 17:03
 * @Modified By
 */
@Data
public class SysEiuUserUpdate implements Serializable{

    @ApiModelProperty(value = "用户" , required = true)
    @NotNull(message = "用户不能为空")
    private Long id;

    @ApiModelProperty(value = "账号",required = true)
    @NotBlank(message = "账号不能为空")
    private String userName;

    @ApiModelProperty(value = "密码")
    @Size(min = 6,max = 20,message = "密码长度{min}-{max}~可字母、数字和任意字符")
    private String password;

    @ApiModelProperty(value = "确认密码")
    @Size(min = 6,max = 20,message = "确认密码长度{min}-{max}~可字母、数字和任意字符")
    private String passwordNew;

    @ApiModelProperty(value = "昵称")
    @Size(min = 0,max = 20,message = "昵称长度{min}-{max}~任意字符")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    //@Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-9]))|(17[0-3,5-8])|(18[0-9])|166|198|199)\\d{8}$",message = "手机号格式错误")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮件格式错误")
    private String eMail;

    @ApiModelProperty(value = "头像Id")
    private Long headPicId;

    @ApiModelProperty(value = "用户类型",required = true)
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    /**add by wanghl**/
    @ApiModelProperty(value = "激活时间(首次登录时间)")
    private Date firstLoginTime;

    @ApiModelProperty(value = "省地区编码")
    private String provinceCode;

    @ApiModelProperty(value = "市地区编码")
    private String cityCode;

    @ApiModelProperty(value = "区地区编码")
    private String areaCode;

    @ApiModelProperty(value = "街道地区编码")
    private String streetCode;

    @ApiModelProperty(value = "详细地址")
    @Size(min = 0,max = 50,message = "详细地址应输入{min}-{max}个字符")
    private String address;



}
