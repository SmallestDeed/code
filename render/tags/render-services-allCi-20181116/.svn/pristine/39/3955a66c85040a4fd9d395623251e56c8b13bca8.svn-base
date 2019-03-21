package com.nork.pay.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.pay.model.PayRefund;
import com.nork.pay.model.search.PayRefundSearch;
import com.nork.sandu.model.dto.TRefund;

/**   
 * @Title: PayRefundMapper.java 
 * @Package com.nork.pay.dao
 * @Description:支付-支付退款Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-09-22 14:41:47
 * @version V1.0   
 */
@Repository
@Transactional
public interface PayRefundMapper {
    int insertSelective(PayRefund record);

    int updateByPrimaryKeySelective(PayRefund record);
  
    int updateAuditState(PayRefund refund);
    
    int updateRefundState(PayRefund refund);
    
    int deleteByPrimaryKey(Integer id);
        
    PayRefund selectByPrimaryKey(Integer id);
    
    int findCount(PayRefundSearch payRefundSearch);
    
    int selectCount(PayRefundSearch payRefundSearch);
    
    List<TRefund> findList(PayRefundSearch payRefundSearch);
    
	List<PayRefund> selectPaginatedList(
			PayRefundSearch payRefundSearch);
			
    List<PayRefund> selectList(PayRefund payRefund);
    
	/**
	 * 其他
	 * 
	 */
}
