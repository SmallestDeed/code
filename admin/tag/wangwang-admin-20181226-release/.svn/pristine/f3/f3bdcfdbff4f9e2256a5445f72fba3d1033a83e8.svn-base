package com.sandu.service.customer.dao;


import com.sandu.api.customer.input.query.CustomerAlotLogQuery;
import com.sandu.api.customer.model.CustomerAlotLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author sandu-lipeiyuan
 */
@Repository
public interface CustomerAlotLogMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(CustomerAlotLog record);

    CustomerAlotLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerAlotLog record);

    List<CustomerAlotLog> selectByOption(CustomerAlotLogQuery query);

    int countByOption(CustomerAlotLogQuery query);

    Integer getAlotZoneId(@Param("ids") List<Integer> ids);
}