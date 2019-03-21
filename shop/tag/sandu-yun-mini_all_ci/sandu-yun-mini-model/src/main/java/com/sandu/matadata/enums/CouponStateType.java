package com.sandu.matadata.enums;

public enum CouponStateType {
	/***
	 * 未开始
	 */
	New(1),
	/***
	 * 进行中
	 */
	Progress(2),
	/***
	 * 已失效
	 */
	Expired(3);
	private int value = 0;

	private CouponStateType(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}
