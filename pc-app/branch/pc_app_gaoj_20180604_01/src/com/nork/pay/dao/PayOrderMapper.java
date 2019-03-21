package com.nork.pay.dao;

import java.util.List;

import com.nork.pay.model.RenderFailPayOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.pay.model.PayOrder;
import com.nork.pay.model.search.PayOrderSearch;
import com.nork.sandu.model.dto.TOrder;

/**   
 * @Title: PayOrderMapper.java 
 * @Package com.nork.pay.dao
 * @Description:支付-支出凭证Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-09-19 16:17:40
 * @version V1.0   
 */
@Repository
@Transactional
public interface PayOrderMapper {
    int insertSelective(PayOrder record);

    int updateByPrimaryKeySelective(PayOrder record);
    
    int updatePayStateByOrderNo(PayOrder order);
  
    int deleteByPrimaryKey(Integer id);
        
    PayOrder selectByPrimaryKey(Integer id);
    
    PayOrder selectByOrderNo(String orderNo);
    
    int findCount(PayOrderSearch payOrderSearch);
    
    int selectCount(PayOrderSearch payOrderSearch);
    
    List<TOrder> findList(PayOrderSearch payOrderSearch);
    
	List<PayOrder> selectPaginatedList(PayOrderSearch payOrderSearch);
			
    List<PayOrder> selectList(PayOrder payOrder);

	PayOrder findOneByOrderNo(@Param("orderNo") String orderNo);

	int getCountByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") String status);
	
	void deleteByIdList(@Param("idList") List<Integer> orderIdList, @Param("remark") String remark, @Param("numa1")Integer numa1);

    PayOrder getConsumeSumByOrderNum(@Param("orderNum") String orderNum);

    List<RenderFailPayOrder> findRenderFailList(@Param("times") Integer times);
}
