package com.sandu.service.decoratecustomer.dao;

import com.sandu.api.decoratecustomer.input.DecorateCustomerQuery;
import com.sandu.api.decoratecustomer.model.DecorateCustomer;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerBO;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerListBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
@Repository
public interface DecorateCustomerMapper {
    int insert(DecorateCustomer decorateCustomer);

    int updateByPrimaryKey(DecorateCustomer decorateCustomer);

    int deleteByPrimaryKey(@Param("decorateCustomerIds") Set<Integer> decorateCustomerIds);

    DecorateCustomer selectByPrimaryKey(@Param("decorateCustomerId") int decorateCustomerId);

    List<DecorateCustomerListBO> findAll(DecorateCustomerQuery query);

    List<DecorateCustomer> findByOption(@Param("query") DecorateCustomer query);

    DecorateCustomerBO getByIdWithProprietorInfo(@Param("decorateCustomerId") int decorateCustomerId);

    List<DecorateCustomer> listForDispatchDecoratePrice();

    List<DecorateCustomer> findWithNullCompanyId();

    DecorateCustomer getByProprietorInfoId(@Param("proprietorInfoId") Integer id);

    List<DecorateCustomer> listCustomerWithInnerCompanyAndNoneOrder();

    List<DecorateCustomer> getCustomerByProprietorIds(@Param("propIds") List<Integer> propId);
}
