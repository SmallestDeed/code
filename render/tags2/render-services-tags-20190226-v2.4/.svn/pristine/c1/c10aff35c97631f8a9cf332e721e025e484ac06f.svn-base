package com.nork.mgr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.mgr.model.MgrRechargeLog;
import com.nork.mgr.model.search.MgrRechargeLogSearch;

/**   
 * @Title: MgrRechargeLogMapper.java 
 * @Package com.nork.mgr.dao
 * @Description:日常工作-充值记录Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-03-26 05:15:26
 * @version V1.0   
 */
@Repository
@Transactional
public interface MgrRechargeLogMapper {
    int insertSelective(MgrRechargeLog record);

    int updateByPrimaryKeySelective(MgrRechargeLog record);
  
    int deleteByPrimaryKey(Integer id);
        
    MgrRechargeLog selectByPrimaryKey(Integer id);
    
    int selectCount(MgrRechargeLogSearch mgrRechargeLogSearch);
    
	List<MgrRechargeLog> selectPaginatedList(
			MgrRechargeLogSearch mgrRechargeLogSearch);
			
    List<MgrRechargeLog> selectList(MgrRechargeLog mgrRechargeLog);

	List<MgrRechargeLog> getMoreInfoBySearch(MgrRechargeLogSearch mgrRechargeLogSearch);
	
	/**
	 * 根据订单号查询充值记录
	 * @param orderNo
	 * @return
	 */
	MgrRechargeLog getByOrderNo(String orderNo);
    
}
