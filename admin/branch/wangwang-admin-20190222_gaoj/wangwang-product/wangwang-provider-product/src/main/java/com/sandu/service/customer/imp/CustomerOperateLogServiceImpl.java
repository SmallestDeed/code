package com.sandu.service.customer.imp;


import com.sandu.api.customer.input.query.CustomerOperateLogQuery;
import com.sandu.api.customer.model.CustomerOperateLog;
import com.sandu.api.customer.service.CustomerOperateLogService;
import com.sandu.service.customer.dao.CustomerOperateLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Service("customerOperateLogService")
public class CustomerOperateLogServiceImpl implements CustomerOperateLogService {

    @Resource
    private CustomerOperateLogMapper customerOperateLogMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerOperateLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(CustomerOperateLog record) {
        return customerOperateLogMapper.insertSelective(record);
    }

    @Override
    public CustomerOperateLog selectByPrimaryKey(Long id) {
        return customerOperateLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CustomerOperateLog record) {
        return customerOperateLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<CustomerOperateLog> selectByOption(CustomerOperateLogQuery query) {
        return customerOperateLogMapper.selectByOption(query);
    }

    @Override
    public int countByOption(CustomerOperateLogQuery query) {
        Integer count = customerOperateLogMapper.countByOption(query);
        if (count == null) {
            return 0;
        }
        return count;
    }
}