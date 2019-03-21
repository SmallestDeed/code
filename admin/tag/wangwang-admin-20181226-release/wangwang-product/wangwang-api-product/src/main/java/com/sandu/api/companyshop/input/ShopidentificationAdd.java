package com.sandu.api.companyshop.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * shop_identification
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-22 11:50
 */
@Data
public class ShopidentificationAdd implements Serializable {

   
            
            
            
    @ApiModelProperty(value = "认证类型(0-企业;1-个体工商户;2-个人)")
    private Integer identifyType;
        
            
    @ApiModelProperty(value = "认证名称(企业名称/商户名称/个人名称)")
    private String identifyName;
        
            
    @ApiModelProperty(value = "企业法人")
    private String companyLegalPerson;
        
            
    @ApiModelProperty(value = "身份证号(法人、商户、个人身份证号)")
    private String identifyCard;
        
            
    @ApiModelProperty(value = "正面照")
    private Integer frontPicId;
        
            
    @ApiModelProperty(value = "反面照")
    private Integer backendPicId;
        
            
    @ApiModelProperty(value = "手持照")
    private Integer handPicId;
        
            
    @ApiModelProperty(value = "经营类目")
    private String categoryIds;
        
            
    @ApiModelProperty(value = "营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "创建者")
    private String creator;
            

}
