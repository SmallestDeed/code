package com.sandu.api.decoratecustomer.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.decoratecustomer.input.DecorateCustomerQuery;
import com.sandu.api.decoratecustomer.model.DecorateCustomer;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerBO;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerListBO;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 14:27
 */
public interface DecorateCustomerService {

    /**
     * 插入
     *
     * @param decorateCustomer
     * @return
     */
    int insert(DecorateCustomer decorateCustomer);

    /**
     * 更新
     *
     * @param decorateCustomer
     * @return
     */
    int update(DecorateCustomer decorateCustomer);

    /**
     * 删除
     *
     * @param decorateCustomerIds
     * @return
     */
    int delete(Set<Integer> decorateCustomerIds);

    /**
     * 通过ID获取详情
     *
     * @param decorateCustomerId
     * @return
     */
     DecorateCustomer getById(int decorateCustomerId);

    /**
     * 查询列表
     *
     * @return
     */
    PageInfo<DecorateCustomerListBO> findAllWithBiz(DecorateCustomerQuery query);

    List<DecorateCustomer> findByOption(DecorateCustomer query);

    DecorateCustomerBO getByIdWithProprietorInfo(int decorateCustomerId);

    void insertList(List<DecorateCustomer> decorateCustomers);

    List<DecorateCustomer> listForDispatchDecoratePrice();

    List<DecorateCustomer> findWithNullCompanyId();

    DecorateCustomer getByProprietorInfoId(Integer id);

    List<DecorateCustomer> listCustomerWithInnerCompanyAndNoneOrder();

    List<DecorateCustomer> getCustomerByProprietorIds(List<Integer> collect);
}
