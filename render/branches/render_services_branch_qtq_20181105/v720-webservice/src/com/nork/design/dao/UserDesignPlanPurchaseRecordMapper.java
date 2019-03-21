package com.nork.design.dao;

import com.nork.design.model.UserDesignPlanPurchaseRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDesignPlanPurchaseRecordMapper {

    int isExistBuyDesignPlanCopyRight(@Param("userId") Integer userId, @Param("designPlanId") Integer designPlanId, @Param("tradeStatus") int tradeStatus);

    void insert(UserDesignPlanPurchaseRecord userDesignPlanPurchaseRecord);

    void updateTradeStatusByTradeNo(@Param("tradeNo") String tradeNo, @Param("tradeStatus") int tradeStatus);

    UserDesignPlanPurchaseRecord getRecordByUserIdAndDesignPlanId(@Param("userId")Integer userId, @Param("planRecommendedId")Integer planRecommendedId);

    UserDesignPlanPurchaseRecord getByTradeNo(@Param("tradeNo") String tradeNo, @Param("tradeStatus")Integer tradeStatus);

    Integer getRecommendedPlanId(Integer designPlanRenderSceneId);
}
