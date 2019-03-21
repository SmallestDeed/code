package com.nork.pay;

import java.io.Serializable;

public class PayConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 度币 / 10 = 元
	 */
	public static final int MULRIPLE_BETWEEN_DUBI_AND_YUAN = 10;
	
	/**
	 * 积分 / 10 = 度币
	 */
	public static final int MULRIPLE_BETWEEN_JIFEN_AND_DUBI = 10;
	
}
