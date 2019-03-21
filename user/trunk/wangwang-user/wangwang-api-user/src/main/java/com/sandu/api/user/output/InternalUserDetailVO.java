package com.sandu.api.user.output;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业内部账号详情输出
 * @author WangHaiLin
 * @date 2018/6/4  19:13
 */
@Data
public class InternalUserDetailVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long userId;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "电话")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String eMail;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;
    @ApiModelProperty(value = "用户类型名称")
    private String userTypeStr;

    @ApiModelProperty(value = "头像Id")
    private String headPicId;

    @ApiModelProperty(value = "头像path")
    private String headPicPath;

    @ApiModelProperty(value = "激活时间(首次登录时间)")
    private Date firstLoginTime;

    @ApiModelProperty(value = "激活时间")
    private String firstLoginTimeStr;

    @ApiModelProperty(value = "省地区编码")
    private String provinceCode;
    @ApiModelProperty(value = "省名称")
    private String provinceCodeName;

    @ApiModelProperty(value = "市地区编码")
    private String cityCode;
    @ApiModelProperty(value = "市名称")
    private String cityCodeName;

    @ApiModelProperty(value = "区地区编码")
    private String areaCode;
    @ApiModelProperty(value = "区名称")
    private String areaCodeName;

    @ApiModelProperty(value = "街道地区编码")
    private String streetCode;
    @ApiModelProperty(value = "街道名称")
    private String streetCodeName;

    @ApiModelProperty(value = "详细地址")
    private String address;

}
