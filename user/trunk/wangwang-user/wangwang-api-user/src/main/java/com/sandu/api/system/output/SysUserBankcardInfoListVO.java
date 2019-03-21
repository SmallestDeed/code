package com.sandu.api.system.output;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysUserBankcardInfoListVO implements Serializable {

	private static final long serialVersionUID = 3240917921823080553L;

	/**
	 * 属于哪家银行
	 * eg: 建设银行/农业银行
	 */
	private String issuingBank;
	
	/**
	 * 卡种类
	 * eg: 储蓄卡/信用卡
	 */
	private String cardType;
	
	/**
	 * 卡号(会隐藏部分数字)
	 */
	private String cardNumberHide;
	
	/**
	 * 
	 */
	private Long id;
	
	/**
	 * 输出格式: 中国建设银行 **** **** **** 1111
	 */
	private String info;
	
}
