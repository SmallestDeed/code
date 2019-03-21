package com.sandu.api.mallUserAddress.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * mallUserAddress
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 15:56
 */
@Data
public class MallUserAddressVO implements Serializable {

    
        
    @ApiModelProperty(value = "")
        
    private Integer mallUserAddressId;
        
        
        
    @ApiModelProperty(value = "地址名称")
        
        
    private String addressName;
        
        
    @ApiModelProperty(value = "用户id")
        
        
    private Integer userId;
        
        
    @ApiModelProperty(value = "收货人姓名")
        
        
    private String consignee;
        
        
    @ApiModelProperty(value = "收货人的省份")
        
        
    private String province;
        
        
    @ApiModelProperty(value = "收货人的城市")
        
        
    private String city;
        
        
    @ApiModelProperty(value = "收货人的地区")
        
        
    private String district;
        
        
    @ApiModelProperty(value = "收货人的详细地址")
        
        
    private String address;
        
        
    @ApiModelProperty(value = "收货人的邮编")
        
        
    private String zipcode;
        
        
    @ApiModelProperty(value = "收货人的电话")
        
        
    private String mobile;
        
        
    @ApiModelProperty(value = "是否默认地址（0:否，1:是）")
        
        
    private Integer isDefault;
        
        
    @ApiModelProperty(value = "系统编码")
        
        
    private String sysCode;
        
        
    @ApiModelProperty(value = "创建者")
        
        
    private String creator;
        
        
    @ApiModelProperty(value = "创建时间")
        
        
    private Date gmtCreate;
        
        
    @ApiModelProperty(value = "修改人")
        
        
    private String modifier;
        
        
    @ApiModelProperty(value = "修改时间")
        
        
    private Date gmtModified;
        
        
    @ApiModelProperty(value = "是否删除（0:否，1:是）")
        
        
    private Integer isDeleted;
        
        
    @ApiModelProperty(value = "备注")
        
        
    private String remark;
        
    
}
