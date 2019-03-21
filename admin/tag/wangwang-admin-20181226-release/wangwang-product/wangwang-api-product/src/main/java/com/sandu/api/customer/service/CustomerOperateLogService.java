package com.sandu.api.customer.service;


import com.sandu.api.customer.input.query.CustomerOperateLogQuery;
import com.sandu.api.customer.model.CustomerOperateLog;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Component
public interface CustomerOperateLogService {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerOperateLog record);

    CustomerOperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerOperateLog record);


    List<CustomerOperateLog> selectByOption(CustomerOperateLogQuery query);

    int countByOption(CustomerOperateLogQuery query);
}