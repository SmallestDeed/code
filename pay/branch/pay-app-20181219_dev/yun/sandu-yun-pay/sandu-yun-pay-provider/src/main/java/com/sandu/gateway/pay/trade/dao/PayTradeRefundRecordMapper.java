package com.sandu.gateway.pay.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.gateway.pay.input.PayTradeRefundQueryVo;
import com.sandu.gateway.pay.trade.model.PayTradeRefundRecord;

@Repository
public interface PayTradeRefundRecordMapper {
	
    int insertSelective(PayTradeRefundRecord record);
    
    int updateById(PayTradeRefundRecord record);

	List<PayTradeRefundRecord> selectList(PayTradeRefundQueryVo queryVo);

	int updateToProcessStatus(@Param("payRefundNo") String payRefundNo);
}
