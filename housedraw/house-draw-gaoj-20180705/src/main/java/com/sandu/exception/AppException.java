package com.sandu.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sandu.common.constant.ResponseEnum;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年3月20日
 */

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** 状态标识 */
	private boolean flag;

	/** 枚举 */
	private ResponseEnum responseEnum;

	@Setter
	@Getter
	private Throwable throwable;

	public AppException(boolean flag, ResponseEnum responseEnum) {
		super(responseEnum != null ? responseEnum.getMessage() : ResponseEnum.ERROR.getMessage());
		this.flag = flag;
		this.responseEnum = responseEnum;
	}

	public AppException(boolean flag, ResponseEnum responseEnum, Throwable throwable) {
		super(throwable);
		this.flag = flag;
		this.throwable = throwable;
		this.responseEnum = responseEnum;
	}

	public ResponseEnum getResponseEnum() {
		return responseEnum;
	}

	public void setResponseEnum(ResponseEnum responseEnum) {
		this.responseEnum = responseEnum;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		if (responseEnum != null) {
			return "{flag=" + flag 
					+ ", code=" + responseEnum.getCode() 
					+ ", remark=" + responseEnum.getMessage() + "}";
		}
		
		return ToStringBuilder.reflectionToString(this);
	}
}
