package com.nork.mobile.service;

import com.nork.mobile.model.search.MobileRenderingModel;

public interface MobileSearchDesignPlanProductService {

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
