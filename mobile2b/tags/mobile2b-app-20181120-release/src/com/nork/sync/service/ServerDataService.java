package com.nork.sync.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.nork.sync.model.SyncBaseProduct;
import com.nork.sync.model.SyncDesignTemplet;
import com.nork.sync.model.SyncSpaceCommon;

/**   
 * @Title: BaseAreaService.java 
 * @Package com.nork.system.service
 * @Description:同步-数据同步Service
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 11:43:41
 * @version V1.0   
 */
@WebService
public interface ServerDataService {
	/**
	 * 保存样板房
	 * @param syncDesignTemplet
	 * @return
	 */
	@WebMethod
	String insertDesignTemplet(@WebParam(name = "syncDesignTemplet")SyncDesignTemplet syncDesignTemplet);

	/**
	 * 保存空间
	 * @param syncSpaceCommon
	 * @return
	 */
	@WebMethod
	Object insertSpaceCommon(@WebParam(name = "syncSpaceCommon")SyncSpaceCommon syncSpaceCommon);

	/**
	 * 保存产品
	 * @param syncBaseProduct
	 * @return
	 */
	@WebMethod
	Object insertProduct(@WebParam(name = "syncBaseProduct")SyncBaseProduct syncBaseProduct);
}
