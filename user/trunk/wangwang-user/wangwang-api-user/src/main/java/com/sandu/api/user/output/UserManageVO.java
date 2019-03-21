package com.sandu.api.user.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManageVO {

    private Long id;

    @ApiModelProperty(value = "登录名")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "套餐ID")
    private Integer servicesId;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "套餐名称")
    private String servicesName;

    @ApiModelProperty(value = "套餐过期时间")
    private Date effectiveEnd;

    @ApiModelProperty(value = "套餐时长")
    private Integer duration;

    @ApiModelProperty(value = "时长单元(0-年;1-月;2-日)")
    private Integer priceUnit;

    @ApiModelProperty(value = "老用户，套餐用户标识 =>0.老账号,1.套餐用户")
    private Integer servicesFlag;

    @ApiModelProperty(value = "用户类型转换")
    private String type;

    @ApiModelProperty(value = "有效时长")
    private String effectiveTime;

    @ApiModelProperty(value = "使用类型")
    //费套餐用户类型(0.试用,1.正式)
    private Integer useType;

    @ApiModelProperty(value = "有效时长")
    private Integer validTime;

    @ApiModelProperty(value = "到期时间")
    private Date failureTime;

    //套餐用户账号类型(0-购买;1-续费;2-试用;3-代购;4-升级)
    @ApiModelProperty(value = "套餐用户账号类型")
    private Integer businessType;

    @ApiModelProperty(value = "账号类型")
    private String accountType;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司id")
    private Integer companyId;

    private Integer businessAdministrationId;
}
