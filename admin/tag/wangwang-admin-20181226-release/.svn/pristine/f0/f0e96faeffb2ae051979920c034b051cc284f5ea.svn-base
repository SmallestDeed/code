package com.sandu.service.decoratecustomer.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.api.decoratecustomer.input.DecorateCustomerQuery;
import com.sandu.api.decoratecustomer.model.DecorateCustomer;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerBO;
import com.sandu.api.decoratecustomer.model.bo.DecorateCustomerListBO;
import com.sandu.api.decoratecustomer.service.DecorateCustomerService;
import com.sandu.service.decoratecustomer.dao.DecorateCustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-20 14:27
 */
@Slf4j
@Service("decorateCustomerService")
public class DecorateCustomerServiceImpl implements DecorateCustomerService {

    @Autowired
    private DecorateCustomerMapper decorateCustomerMapper;

    @Override
    public int insert(DecorateCustomer decorateCustomer) {
        int result = decorateCustomerMapper.insert(decorateCustomer);
        if (result > 0) {
            return decorateCustomer.getId();
        }
        return 0;
    }

    @Override
    public int update(DecorateCustomer decorateCustomer) {
        return decorateCustomerMapper.updateByPrimaryKey(decorateCustomer);
    }

    @Override
    public int delete(Set<Integer> decorateCustomerIds) {
        return decorateCustomerMapper.deleteByPrimaryKey(decorateCustomerIds);
    }

    @Override
    public DecorateCustomer getById(int decorateCustomerId) {
        return decorateCustomerMapper.selectByPrimaryKey(decorateCustomerId);
    }

    @Override
    public PageInfo<DecorateCustomerListBO> findAllWithBiz(DecorateCustomerQuery query) {

        PageHelper.startPage(query.getPage(), query.getLimit());
        return new PageInfo<>(decorateCustomerMapper.findAll(query));
    }

    @Override
    public List<DecorateCustomer> findByOption(DecorateCustomer query) {
        if (Objects.isNull(query)) {
            return Collections.emptyList();
        }
        return decorateCustomerMapper.findByOption(query);
    }

    @Override
    public DecorateCustomerBO getByIdWithProprietorInfo(int decorateCustomerId) {
        return decorateCustomerMapper.getByIdWithProprietorInfo(decorateCustomerId);
    }

    @Override
    public void insertList(List<DecorateCustomer> decorateCustomers) {
        for (DecorateCustomer decorateCustomer : decorateCustomers) {
            //
            insert(decorateCustomer);
        }
    }

    @Override
    public List<DecorateCustomer> listForDispatchDecoratePrice() {
        return decorateCustomerMapper.listForDispatchDecoratePrice();
    }

    @Override
    public List<DecorateCustomer> findWithNullCompanyId() {
        return decorateCustomerMapper.findWithNullCompanyId();
    }

    @Override
    public DecorateCustomer getByProprietorInfoId(Integer id) {
        return decorateCustomerMapper.getByProprietorInfoId(id);
    }

    @Override
    public List<DecorateCustomer> listCustomerWithInnerCompanyAndNoneOrder() {
        return decorateCustomerMapper.listCustomerWithInnerCompanyAndNoneOrder();
    }

    @Override
    public List<DecorateCustomer> getCustomerByProprietorIds(List<Integer> propId) {
        if (propId == null || propId.isEmpty()) {
            return Collections.emptyList();
        }
        return decorateCustomerMapper.getCustomerByProprietorIds(propId);
    }
}
