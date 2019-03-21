package com.sandu.goods.dao;

import java.util.List;

import com.sandu.common.persistence.CrudDao;
import com.sandu.goods.model.GoodsRecommend;
import com.sandu.goods.model.query.GoodsRecommendQuery;
import com.sandu.goods.model.vo.GoodsRecommendVo;

public interface GoodsRecommendDao extends CrudDao<GoodsRecommend, GoodsRecommendQuery, GoodsRecommendVo> {
	int batchDelete(GoodsRecommendQuery query);
	int batchInsert(List<GoodsRecommend> lstProduct);
	/***
	 * 根据企业ID获取排名前几名的产品推荐信息
	 * 
	 * @param query
	 * @return
	 */
	List<GoodsRecommendVo> findTopList(GoodsRecommendQuery query);

    int countTopList(GoodsRecommendQuery query);
}
