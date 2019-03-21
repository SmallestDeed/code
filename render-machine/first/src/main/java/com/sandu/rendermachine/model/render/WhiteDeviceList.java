package com.sandu.rendermachine.model.render;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 渲染机白名单
 */
public class WhiteDeviceList implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String ipAddr;
	
	private String mac;
	
	private String deviceKey;

}
