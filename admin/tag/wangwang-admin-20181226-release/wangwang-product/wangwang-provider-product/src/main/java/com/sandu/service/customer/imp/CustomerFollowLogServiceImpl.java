package com.sandu.service.customer.imp;


import com.sandu.api.customer.constant.CustomerBaseInfoConstant;
import com.sandu.api.customer.input.query.CustomerFollowLogQuery;
import com.sandu.api.customer.model.CustomerBaseInfo;
import com.sandu.api.customer.model.CustomerFollowLog;
import com.sandu.api.customer.service.CustomerFollowLogService;
import com.sandu.service.customer.dao.CustomerBaseInfoMapper;
import com.sandu.service.customer.dao.CustomerFollowLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author sandu-lipeiyuan
 */
@Service("customerFollowLogService")
public class CustomerFollowLogServiceImpl implements CustomerFollowLogService {

    @Resource
    private CustomerFollowLogMapper customerFollowLogMapper;
    @Resource
    private CustomerBaseInfoMapper  customerBaseInfoMapper;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return customerFollowLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(CustomerFollowLog record) {
        return customerFollowLogMapper.insertSelective(record);
    }

    @Override
    public CustomerFollowLog selectByPrimaryKey(Long id) {
        return customerFollowLogMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int updateByPrimaryKeySelective(CustomerFollowLog record,Integer loginUserId,Integer alotStatus) {
        if(record.getUserId() == null || loginUserId == null ){
            return 0;
        }
        int update = customerFollowLogMapper.updateByPrimaryKeySelective(record);
        if(update > 0){
            // 更新跟进人和跟进状态
            CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
            customerBaseInfo.setUserId(record.getUserId());
            customerBaseInfo.setFollowUpUserId(loginUserId);
            customerBaseInfo.setAlotStatus(alotStatus);
            update = customerBaseInfoMapper.updateByUserId(customerBaseInfo);
            if(update > 0){
                return update;
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return update;
            }
        }else{
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return update;
        }
    }

    @Override
    public List<CustomerFollowLog> selectByOption(CustomerFollowLogQuery query) {
        return customerFollowLogMapper.selectByOption(query);
    }

    @Override
    public int countByOption(CustomerFollowLogQuery query) {
        Integer count = customerFollowLogMapper.countByOption(query);
        if (count == null) {
            return 0;
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int complete(CustomerFollowLog record,Integer alotStatus,Integer loginUserId) {
        if(record.getUserId() == null || loginUserId == null ){
            return 0;
        }
        int insert = customerFollowLogMapper.insertSelective(record);
        if(insert > 0){
            CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
            customerBaseInfo.setUserId(record.getUserId());
            customerBaseInfo.setAlotStatus(alotStatus);
            customerBaseInfo.setFollowUpUserId(loginUserId);
            int update = customerBaseInfoMapper.updateByUserId(customerBaseInfo);
            if(update > 0){
                return update;
            }else{
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return update;
            }
        }else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }
}