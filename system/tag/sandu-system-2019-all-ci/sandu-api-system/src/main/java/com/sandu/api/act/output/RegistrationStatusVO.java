package com.sandu.api.act.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * registration
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:31
 */
@Data
public class RegistrationStatusVO implements Serializable {

	
    @ApiModelProperty(value = "任务id")
    private String registrationId;
        
    @ApiModelProperty(value = "任务状态")
    private String statusCode;

	public RegistrationStatusVO(String statusCode,String registrationId) {
		super();
		this.statusCode = statusCode;
		this.registrationId = registrationId;
	}
    
    
    
}
