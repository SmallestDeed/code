package com.sandu.api.act2.input;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ActRedPacketQuery implements Serializable {

	@ApiModelProperty(value = "活动名称")
	private String name;

	@ApiModelProperty(value = "活动状态")
	private Integer statusCode;
	
	private Integer pageNum=1;
	
	private Integer pageSize=20;
	
    private String appId="wx42e6b214e6cdaed3"; 

}
