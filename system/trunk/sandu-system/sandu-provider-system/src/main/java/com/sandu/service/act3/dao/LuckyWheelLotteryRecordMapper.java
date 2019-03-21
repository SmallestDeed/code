package com.sandu.service.act3.dao;

import java.util.List;

import com.sandu.api.act3.model.LuckyWheelLotteryRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface LuckyWheelLotteryRecordMapper {

    int insert(LuckyWheelLotteryRecord record);

    LuckyWheelLotteryRecord selectById(String id);

    int updateById(LuckyWheelLotteryRecord record);
    
    List<LuckyWheelLotteryRecord> selectList(LuckyWheelLotteryRecord record);
}