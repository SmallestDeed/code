package com.sandu.rendermachine.model.common;


import lombok.Data;

import java.io.Serializable;

@Data
public class Mapper implements Serializable{
	private static final long serialVersionUID = 1L;
	private String  deviceId = null;
	private String  msgId = null;
	private String  ids = null;
	private Integer start = 0;
	private Integer limit = 20;
	private String  order = null;
	private String  orderNum = null;
	private String  orders = null;
	/**级别限制的资源数量*/
	private int levelLimitCount=0;

}
