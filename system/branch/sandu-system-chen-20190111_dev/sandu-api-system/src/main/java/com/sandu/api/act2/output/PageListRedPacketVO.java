package com.sandu.api.act2.output;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * bargain
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-20 14:24
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
@AllArgsConstructor
public class PageListRedPacketVO implements Serializable {

	private String id;
	
	private String name;
    
	private String beginTime;
	
	private String endTime;
	
	private Integer statusCode;
	
	private Double totalAmount;
	
    private String copyUrl;
}
