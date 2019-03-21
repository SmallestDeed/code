package com.sandu.gateway.pay.trade.dao;

import org.springframework.stereotype.Repository;
import com.sandu.gateway.pay.trade.model.PayTradeTransfersRecord;

@Repository
public interface PayTradeTransfersRecordMapper {
	
    int insertSelective(PayTradeTransfersRecord record);
    
    int updateById(PayTradeTransfersRecord record);

	//List<PayTradeTransfersRecord> selectList(PayTradeTransfersQueryVo queryVo);

}
