package com.sandu.api.customer.service;


import com.sandu.api.customer.input.query.CustomerFollowLogQuery;
import com.sandu.api.customer.model.CustomerFollowLog;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Component
public interface CustomerFollowLogService {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerFollowLog record);

    CustomerFollowLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerFollowLog record,Integer loginUserId,Integer alotStatus);

    List<CustomerFollowLog> selectByOption(CustomerFollowLogQuery query);

    int countByOption(CustomerFollowLogQuery query);

    int complete(CustomerFollowLog record,Integer alotStatus,Integer loginUserId);
}