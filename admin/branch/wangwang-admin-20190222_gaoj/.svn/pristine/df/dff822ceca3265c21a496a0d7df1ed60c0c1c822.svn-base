package com.sandu.api.decoratecustomer.service.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.decoratecustomer.input.DecorateCustomerAdd;
import com.sandu.api.decoratecustomer.input.DecorateCustomerQuery;
import com.sandu.api.decoratecustomer.input.DecorateCustomerUpdate;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerBO;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerListBO;

import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 14:27
 */
public interface DecorateCustomerBizService {

    /**
     * 创建
     *
     * @param input
     * @return
     */
    int create(DecorateCustomerAdd input);

    /**
     * 更新
     *
     * @param input
     * @return
     */
    int update(DecorateCustomerUpdate input);

    /**
     * 删除
     */
    int delete(String decorateCustomerId);

    /**
     * 通过ID获取详情
     *
     * @param decorateCustomerId
     * @return
     */
    DecorateCustomerBO getById(int decorateCustomerId);

    /**
     * 查询客户列表
     *
     * @param query
     * @return
     */
    PageInfo<DecorateCustomerListBO> query(DecorateCustomerQuery query);

    /**
     * 自动拍单/抢单
     */
    void dispatchDecoratePrice();

    /**
     * @return 预约装企生成订单
     */
    List<Integer> initOrderForCompanyCustomer();


    /**
     * 提交抢单
     *
     * @param customerId
     * @return
     */
    Boolean submitToSedKill(Integer customerId);
}
