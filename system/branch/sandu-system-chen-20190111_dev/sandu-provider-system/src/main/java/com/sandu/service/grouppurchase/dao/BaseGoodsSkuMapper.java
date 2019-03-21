package com.sandu.service.grouppurchase.dao;


import com.sandu.api.grouppurchase.model.BaseGoodsSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseGoodsSkuMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(BaseGoodsSku record);

	int insertSelective(BaseGoodsSku record);

	BaseGoodsSku selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(BaseGoodsSku record);

	int updateByPrimaryKey(BaseGoodsSku record);

	List<BaseGoodsSku> selectBySpuId(@Param("spuId") Long spuId);
}