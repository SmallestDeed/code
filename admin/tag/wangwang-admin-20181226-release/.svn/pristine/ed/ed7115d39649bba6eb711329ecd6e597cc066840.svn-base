package com.sandu.service.decorateorder.dao;

import com.sandu.api.decorateorder.input.DecorateOrderQuery;
import com.sandu.api.decorateorder.model.DecorateOrder;
import com.sandu.api.decorateorder.model.DecorateOrderScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * DecorateOrder
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
@Repository
public interface DecorateOrderMapper {
    int insert(DecorateOrder DecorateOrder);

    int updateByPrimaryKey(DecorateOrder DecorateOrder);

    int deleteByPrimaryKey(@Param("DecorateOrderIds") Set<Integer> DecorateOrderIds);

    DecorateOrder selectByPrimaryKey(@Param("id") int id);

    List<DecorateOrder> findAll(DecorateOrderQuery query);

	List<DecorateOrder> selectContractOrderList(DecorateOrderQuery query);

	List<DecorateOrder> selectRefundOrderList(DecorateOrderQuery query);

	List<DecorateOrder> listOrderByCompanyId(@Param("companyId") Integer companyId);

	List<DecorateOrderScore> listOrderScoreByCompanyId(@Param("ids") List<Integer> ids);

	List<DecorateOrder> listOrderByCustomerId(@Param("id") Integer id);

	DecorateOrder findOrderByPriceId(@Param("id") Integer id);
}
