package com.sandu.api.customer.service;


import com.sandu.api.customer.input.query.CustomerAlotLogQuery;
import com.sandu.api.customer.model.CustomerAlotLog;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Component
public interface CustomerAlotLogService {

    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerAlotLog record);

    CustomerAlotLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerAlotLog record);

    List<CustomerAlotLog> selectByOption(CustomerAlotLogQuery query);

    int countByOption(CustomerAlotLogQuery query);

    Integer getAlotZoneId(List<Integer> ids);
}