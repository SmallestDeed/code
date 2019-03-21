package com.sandu.api.act.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * award
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-20 14:25
 */
@Data
public class WxActBargainAwardAdd implements Serializable {

            
    @ApiModelProperty(value = "报名id")
    private String regId;
    
            
    @ApiModelProperty(value = "收货人")
    private String receiver;
        
            
    @ApiModelProperty(value = "手机号")
    private String mobile;
        
    @ApiModelProperty(value = "验证码")
    private String validationCode;
    
    @ApiModelProperty(value = "详细地址")
    private String address;
    
        
}
