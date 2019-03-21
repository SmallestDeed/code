package com.sandu.gateway.pay.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.gateway.pay.input.PayTradeQueryVo;
import com.sandu.gateway.pay.trade.model.PayTradeRecord;

@Repository
public interface PayTradeRecordMapper {
	
    int insertSelective(PayTradeRecord record);

	PayTradeRecord getByTradeNos(@Param("payTradeNo") String payTradeNo);

	int updateExtenalTradeNoAndStatusById(@Param("id") Long id, @Param("extenalTradeNo") String extenalTradeNo,@Param("status") Integer status);

	void updateNotifyResultById(@Param("id")Long id, @Param("notifyResult")Integer notifyResult);

	/**
	 * 通过交易号将 状态从'开始'修改成'回调处理中'
	 * @param payTradeNo
	 */
	int updateToProcessStatus(@Param("payTradeNo")String payTradeNo);

	List<PayTradeRecord> selectList(PayTradeQueryVo queryVo);
}
