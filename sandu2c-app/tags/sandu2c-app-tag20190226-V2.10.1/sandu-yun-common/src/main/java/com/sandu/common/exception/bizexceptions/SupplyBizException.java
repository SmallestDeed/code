/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcBizException.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.sandu.common.exception.bizexceptions;

import com.sandu.common.enums.ErrorCodeEnum;
import com.sandu.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;


/**
 * The class Supply biz exception.
 * 供求信息业务异常
 * @author weis
 */
@Slf4j
public class SupplyBizException extends BusinessException {

	private static final long serialVersionUID = -6552248511084911254L;

	/**
	 * Instantiates a new Supply exception.
	 */
	public SupplyBizException() {
	}

	/**
	 * Instantiates a new Supply exception.
	 *
	 * @param code      the code
	 * @param msgFormat the msg format
	 * @param args      the args
	 */
	public SupplyBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
		log.info("<== SupplyException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Supply exception.
	 *
	 * @param code the code
	 * @param msg  the msg
	 */
	public SupplyBizException(int code, String msg) {
		super(code, msg);
		log.info("<==SupplyException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Supply exception.
	 *
	 * @param codeEnum the code enum
	 */
	public SupplyBizException(ErrorCodeEnum codeEnum) {
		super(codeEnum.code(), codeEnum.msg());
		log.info("<==SupplyException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Supply exception.
	 *
	 * @param codeEnum the code enum
	 * @param args     the args
	 */
	public SupplyBizException(ErrorCodeEnum codeEnum, Object... args) {
		super(codeEnum, args);
		log.info("<==SupplyException, code:{}, message:{}", this.code, super.getMessage());
	}
}
