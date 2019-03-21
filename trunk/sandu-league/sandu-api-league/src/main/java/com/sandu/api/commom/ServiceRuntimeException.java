/**
 * Copyright 2013 Ruckus Wireless, Inc. All rights reserved.
 *
 * RUCKUS WIRELESS, INC. CONFIDENTIAL - 
 * This is an unpublished, proprietary work of Ruckus Wireless, Inc., and is 
 * fully protected under copyright and trade secret laws. You may not view, 
 * use, disclose, copy, or distribute this file or any information contained 
 * herein except pursuant to a valid license from Ruckus. 
 */
 
package com.sandu.api.commom;


public class ServiceRuntimeException extends SanduRuntimeException {

	private static final long serialVersionUID = 6539767464960471812L;
	
	public ServiceRuntimeException(String msg) {
		super(msg);
	}
	
	public ServiceRuntimeException(String msg, Throwable ex) {
		super(msg, ex);
	}
	
	public ServiceRuntimeException(Throwable ex) {
		super(ex);
	}
	
	public ServiceRuntimeException() {
		super();
	}

	public ServiceRuntimeException(String message, Throwable cause,
			String msgKey, Object... msgArguments) {
		super(message, cause, msgKey, msgArguments);
		
	}

	public ServiceRuntimeException(String message, Throwable cause,
			String msgKey) {
		super(message, cause, msgKey);
		
	}

}
