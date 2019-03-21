package com.sandu.pay.mgrRecharge.dao;

import com.sandu.pay.mgrRecharge.model.MgrRechargeLog;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**   
 * @Title: MgrRechargeLogMapper.java 
 * @Package com.sandu.mgrRecharge.dao
 * @Description:日常工作-充值记录Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-03-26 05:15:26
 * @version V1.0   
 */
@Repository
@Transactional
public interface MgrRechargeLogMapper {

	/**
	 * 添加充值记录
	 * @param record
	 * @return
	 */
	int insertSelective(MgrRechargeLog record);

	/**
	 * 更新充值记录
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(MgrRechargeLog record);

	/**
	 * 通过订单NO获取充值信息
	 * @param orderNo
	 * @return
	 */
	MgrRechargeLog getReChargeByOrderNo(String orderNo);


}
