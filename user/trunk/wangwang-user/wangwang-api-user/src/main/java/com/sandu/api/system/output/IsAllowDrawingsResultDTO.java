package com.sandu.api.system.output;

import java.io.Serializable;

import lombok.Data;

/**
 * com.sandu.web.user.controller.MyIncomeComtroller.isAllowDrawings() result DTO
 * 
 * @author huangsongbo
 *
 */
@Data
public class IsAllowDrawingsResultDTO implements Serializable {

	private static final long serialVersionUID = 4670335639228515517L;

	/**
	 * 允许提现
	 */
	private boolean isAllow;
	
	/**
	 * 如果不允许提现, 原因说明
	 */
	private String message;

	public IsAllowDrawingsResultDTO(boolean isAllow, String message) {
		super();
		this.isAllow = isAllow;
		this.message = message;
	}
	
}
