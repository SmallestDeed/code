package com.nork.decorateOrder.dao;

import com.nork.decorateOrder.model.input.DecoratePriceListQuery;
import com.nork.decorateOrder.model.output.DecoratePriceDetail;
import com.nork.decorateOrder.model.output.DecoratePriceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nork.decorateOrder.model.DecoratePrice;
import com.nork.decorateOrder.model.search.DecoratePriceSearch;

import java.util.List;

@Repository
public interface DecoratePriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DecoratePrice record);

    int insertSelective(DecoratePrice record);

    DecoratePrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DecoratePrice record);

    int updateByPrimaryKey(DecoratePrice record);

	int getMyOrderCount(DecoratePriceSearch decoratePriceSearch);

    /**
     * 查询我的报价列表
     * @author : WangHaiLin
     * @param query 列表查询入参
     * @return
     */
	List<DecoratePriceVO> selectList(DecoratePriceListQuery query);

    /**
     * 查询我的报价详情
     * @author : WangHaiLin
     * @param priceId 报价单Id
     * @return
     */
    DecoratePriceDetail getDetail(@Param("priceId") Long priceId);

    /**
    * 查询我的报价数量
    * @param query 查询入参
    * @return int  数量
    *
    */
    int  getCount(DecoratePriceListQuery query);

	int selectCountBySearch(DecoratePriceSearch decoratePriceSearch);

	List<DecoratePrice> selectListBySearch(DecoratePriceSearch decoratePriceSearch);
	
}