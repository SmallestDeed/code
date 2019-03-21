package com.sandu.service.account.dao;

import com.sandu.api.account.model.PayOrder;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PayOrderDao {
    List<PayOrder> selectExpensesRecordList(@Param("userId") Long userId, @Param("bizType") String bizType, @Param("platformId") Long platformId, @Param("start") Integer start, @Param("limit") Integer limit);

    int countExpensesRecordList(@Param("userId") Long userId, @Param("bizType") String bizType, @Param("platformId") Long platformId);

    List<PayOrder> selectPayOrdersByOrderNos(@Param("orderNos") Set<String> orderNos);

    int countUserRechargeRecord(Long userId);

    List<PayOrder> getUserRechargeRecords(@Param("userId")Long userId, @Param("start")Integer start, @Param("limit")Integer limit);
}
