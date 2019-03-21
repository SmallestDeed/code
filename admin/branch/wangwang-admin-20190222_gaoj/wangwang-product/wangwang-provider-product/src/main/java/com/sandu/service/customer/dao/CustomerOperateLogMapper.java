package com.sandu.service.customer.dao;


import com.sandu.api.customer.input.query.CustomerOperateLogQuery;
import com.sandu.api.customer.model.CustomerOperateLog;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author sandu-lipeiyuan
 */
@Repository
public interface CustomerOperateLogMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerOperateLog record);

    CustomerOperateLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerOperateLog record);


    List<CustomerOperateLog> selectByOption(CustomerOperateLogQuery query);

    int countByOption(CustomerOperateLogQuery query);
}