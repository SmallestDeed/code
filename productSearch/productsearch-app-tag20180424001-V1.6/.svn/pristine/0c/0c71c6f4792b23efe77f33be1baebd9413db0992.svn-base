package com.nork.mobile.service;

import javax.servlet.http.HttpServletRequest;

import com.nork.mobile.model.search.MobileRenderingModel;
import com.nork.mobile.model.search.MobileSearchProductModel;

public interface MobileSearchDesignPlanProductService {

	/**
	 * 移动端搜索单品的接口
	 * @param model
	 * @return
	 */
	Object searchProduct(MobileSearchProductModel model, String mediaType);
	
	/**
	 * 获取产品list
	 * @param model
	 * @return
	 */
	public Object getDesignPlanProductList(MobileRenderingModel model);
	
	/**
	 * 分类查询接口供移动端调用
	 * @return
	 */
	public Object searchProCategory(String type, Integer cid, String msgId);
}
