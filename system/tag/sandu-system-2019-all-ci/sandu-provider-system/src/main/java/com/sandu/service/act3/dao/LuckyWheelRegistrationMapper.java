package com.sandu.service.act3.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sandu.api.act3.model.LuckyWheelRegistration;

public interface LuckyWheelRegistrationMapper {
  
    int insert(LuckyWheelRegistration record);

    LuckyWheelRegistration selectById(String id);

    int updateById(LuckyWheelRegistration record);
    
    List<LuckyWheelRegistration> selectList(LuckyWheelRegistration record);

    /**
     * //更新任务的相关计数字段
     * @param regId
     * @param isAward
     */
    void updateToIncreaseLotteryRefCount(@Param("regId") String regId,@Param("isAward") boolean isAward);

}