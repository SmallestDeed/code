package com.sandu.service.customer.imp;


import com.sandu.api.customer.input.query.CustomerAlotLogQuery;
import com.sandu.api.customer.model.CustomerAlotLog;
import com.sandu.api.customer.service.CustomerAlotLogService;
import com.sandu.service.customer.dao.CustomerAlotLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Service("customerAlotLogService")
public class CustomerAlotLogServiceImpl implements CustomerAlotLogService {

    @Resource
    private CustomerAlotLogMapper customerAlotLogMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerAlotLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(CustomerAlotLog record) {
        return customerAlotLogMapper.insertSelective(record);
    }

    @Override
    public CustomerAlotLog selectByPrimaryKey(Long id) {
        return customerAlotLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CustomerAlotLog record) {
        return customerAlotLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<CustomerAlotLog> selectByOption(CustomerAlotLogQuery query) {
        return customerAlotLogMapper.selectByOption(query);
    }

    @Override
    public int countByOption(CustomerAlotLogQuery query) {
        Integer count = customerAlotLogMapper.countByOption(query);
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    public Integer getAlotZoneId(List<Integer> ids) {
        return customerAlotLogMapper.getAlotZoneId(ids);
    }
}