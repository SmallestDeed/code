package com.sandu.api.user.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserManageDTO implements Serializable {

    private Long id;

    @ApiModelProperty(value = "登录名")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "套餐ID")
    private Integer servicesId;

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

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    private Integer companyId;

    private Integer businessAdministrationId;
}
