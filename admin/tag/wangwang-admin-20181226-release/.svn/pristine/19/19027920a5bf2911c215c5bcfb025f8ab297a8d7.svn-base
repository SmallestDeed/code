package com.sandu.service.customer.dao;


import com.sandu.api.customer.input.query.CustomerFollowLogQuery;
import com.sandu.api.customer.model.CustomerFollowLog;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author sandu-lipeiyuan
 */
@Repository
public interface CustomerFollowLogMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerFollowLog record);

    CustomerFollowLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerFollowLog record);

    List<CustomerFollowLog> selectByOption(CustomerFollowLogQuery query);

    int countByOption(CustomerFollowLogQuery query);
}