package com.sandu.api.decorateorder.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.decorateorder.input.DecorateOrderQuery;
import com.sandu.api.decorateorder.model.DecorateOrder;
import com.sandu.api.decorateorder.model.DecorateOrderScore;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * decorate_order
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
public interface DecorateOrderService {

    /**
     * 插入
     *
     * @param decorate_order
     * @return
     */
    int insert(DecorateOrder decorate_order);

    /**
     * 更新
     *
     * @param decorate_order
     * @return
     */
    int update(DecorateOrder decorate_order);

    /**
     * 删除
     *
     * @param decorate_orderIds
     * @return
     */
    int delete(Set<Integer> decorate_orderIds);

    /**
     * 通过ID获取详情
     *
     * @param decorate_orderId
     * @return
     */
     DecorateOrder getById(int decorate_orderId);

    /**
     * 查询列表
     *
     * @return
     */
    PageInfo<DecorateOrder> findAll(DecorateOrderQuery query);

    /**
     * 修改
     * @return
     */
	int uploadOrder(DecorateOrder decorateOrder);

	List<DecorateOrder> queryContractOrderList(DecorateOrderQuery query);

	List<DecorateOrder> queryRefundOrderList(DecorateOrderQuery query);

	List<DecorateOrder> listOrderByCompanyId(Integer companyPid);

	List<DecorateOrderScore> listOrderScoreByCompanyId(List<Integer> asList);

	List<DecorateOrder> listOrderByCustomerId(Integer id);

	DecorateOrder findOrderByPriceId(Integer id);
}
