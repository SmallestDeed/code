package com.sandu.api.mail.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sandu.api.mail.model.PerfectHouseMailMessage;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年4月3日
 */

public interface TemplateMailService {
	
	int POOL_SIZE = 2;
	ExecutorService executors = Executors.newFixedThreadPool(POOL_SIZE);

	/**
	 * 邮件推送，邮件推送不参与事务.
	 * </p>
	 * Propagation.NOT_SUPPORTED
	 * 
	 * @param msg
	 */
	void push(PerfectHouseMailMessage msg);
}
