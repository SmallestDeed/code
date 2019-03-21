package com.nork.decorateOrder.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nork.decorateOrder.model.DecorateOrder;
import com.nork.decorateOrder.model.search.DecorateOrderSearch;
import com.nork.decorateOrder.model.input.DecorateCustomerListQuery;
import com.nork.decorateOrder.model.output.DecorateCustomerDetail;
import com.nork.decorateOrder.model.output.DecorateCustomerVO;
import org.apache.ibatis.annotations.Param;

@Repository
public interface DecorateOrderMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(DecorateOrder record);

    int insertSelective(DecorateOrder record);

    DecorateOrder selectByPrimaryKey(Long id);

    /**
     * 万能update服务
     * @author : WangHaiLin
     * @param record 客户订单实体
     * @return
     */
    int updateByPrimaryKeySelective(DecorateOrder record);

    int updateByPrimaryKey(DecorateOrder record);

	List<DecorateOrder> selectDecorateSeckillIdAndOrderOverTimeBySearch(DecorateOrderSearch decorateOrderSearch);

	int selectCountBySearch(DecorateOrderSearch decorateOrderSearch);

    /**
     * 查询我的客户列表
     * @author : WangHaiLin
     * @param query  查询入参
     * @return list
     */
    List<DecorateCustomerVO> selectList(DecorateCustomerListQuery query);

    /**
     * 查询我的客户数量
     * @author : WangHaiLin
     * @param query  查询入参
     * @return int
     */
    int selectCount(DecorateCustomerListQuery query);

    /**
     * 查看客户订单详情
     * @author : WangHaiLin
     * @param orderId 订单Id
     * @return
     */
    DecorateCustomerDetail getById(@Param("orderId") Integer orderId);

	DecorateOrder selectByDecoratePriceId(@Param("decoratePriceId") Long decoratePriceId);

	DecorateOrder selectBySeckillId(@Param("decorateSeckillId") Long decorateSeckillId);

	int updateOrderStatusByDecoratePriceId(@Param("orderStatus") Integer orderStatus, @Param("decoratePriceId") Long decoratePriceId);

	int updateOrderStatusByDecorateSeckillId(@Param("orderStatus") Integer orderStatus, @Param("decorateSeckillId") Long decorateSeckillId);

	int updateOrderStatusById(@Param("orderStatus") Integer orderStatus, @Param("id") Long id);

	List<DecorateOrder> selectOverTimeOrderList(@Param("now") Date now);

	void updateOrderStatusByIdList(@Param("orderStatus") Integer orderStatus, @Param("idList") List<Long> idList);
	
}