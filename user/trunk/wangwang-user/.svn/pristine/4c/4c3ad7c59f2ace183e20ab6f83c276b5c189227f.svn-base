package com.sandu.api.system.input;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SysUserBankcardInfoDTO implements Serializable {

	private static final long serialVersionUID = -3115528722732598345L;

	/**
	 * 是谁在绑定银行卡
	 */
	private Long userId;
	
	/**
	 * 姓名
	 */
	@NotNull
	@Size(max = 64)
	private String name;
	
	/**
	 * 银行卡号
	 */
	@NotNull
	@Size(max = 64)
	private String cardNumber;
	
	/**
	 * 哪家银行
	 */
	@NotNull
	private Integer issuingBankValue;
	
	/**
	 * 所属支行
	 */
	@Size(max = 128)
	private String subBranchInfo;
	
}
