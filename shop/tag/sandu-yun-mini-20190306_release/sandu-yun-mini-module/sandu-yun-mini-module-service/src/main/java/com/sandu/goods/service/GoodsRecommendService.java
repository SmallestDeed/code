package com.sandu.goods.service;

import java.util.List;
import java.util.Map;

import com.sandu.goods.model.GoodsRecommend;
import com.sandu.goods.model.query.GoodsRecommendQuery;
import com.sandu.goods.model.vo.GoodsRecommendVo;
import com.sandu.matadata.Page;

/***
 * 商品推荐服务接口
 * 
 * @author Administrator
 *
 */
public interface GoodsRecommendService {
	/***
	 * 添加商品推荐
	 * 
	 * @param recommend
	 * @return
	 */
	boolean add(GoodsRecommend recommend);

	/***
	 * 批量添加商品推荐
	 * @param lstGoods
	 * @return
	 */
	boolean batchAdd(List<GoodsRecommend> lstGoods, long companyId);
	/***
	 * 根据ID删除商品推荐
	 * @param id
	 * @param companyId
	 * @return
	 */
	boolean delete(long id,long companyId);
	
	/***
	 * 批量删除商品推荐
	 * @param query
	 * @return
	 */
	boolean batchDelete(GoodsRecommendQuery query);

	/****
	 * 分页查询商品推荐信息
	 * 
	 * @param query
	 * @return
	 */
	Page<GoodsRecommendVo> getList(GoodsRecommendQuery query);

	/***
	 * 根据企业ID获取排名前几名的商品推荐信息
	 * @param query
	 * @return
	 */
	Map<String,Object> getTopList(GoodsRecommendQuery query);
}
