package com.sandu.company.service;

import com.sandu.company.model.query.ShopActivityQuery;
import com.sandu.company.model.vo.ShopActivityDetailVo;
import com.sandu.company.model.vo.ShopActivityVo;
import com.sandu.matadata.Page;

/***
 * 店铺活动服务
 * @author Administrator
 *
 */
public interface ShopActivityService {
		/***
	    * 获取店铺优惠活动详情
	    * @param shopId
	    * @return
	    */
	   ShopActivityDetailVo get(long shopId);
	   /***
	    * 店铺优惠活动
	    * @param query
	    * @return
	    */
	   Page<ShopActivityVo> list(ShopActivityQuery query);
}
