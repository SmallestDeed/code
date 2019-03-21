package com.nork.design.service;

import com.nork.design.model.UserDesignPlanPurchaseRecord;
import com.nork.render.model.PayDesignPlanFree;
import com.nork.system.model.ResRenderPic;

import java.util.Map;

public interface UserDesignPlanPurchaseRecordService {

    boolean checkUserDesignIsExist(Integer userId, Integer planRecommendedId);

    String payDesignPlanCopyRight(PayDesignPlanFree payDesignPlanFree);

    void updateCallBackStatus(String intenalTradeNo, int tradeStatus);

    void updateRecommendedPlanUseCount(Integer recommendedPlanId);

    void addCompanyDesignPlanIncome(UserDesignPlanPurchaseRecord userDesignPlanPurchaseRecord,Integer useType);

    UserDesignPlanPurchaseRecord getRecordByUserIdAndDesignPlanId(Integer userId, Integer planRecommendedId);

    UserDesignPlanPurchaseRecord getByTradeNo(String tradeNo,Integer tradeStatus);

    Map<String,Object> checkReplaceDesignPlanPay(int i, Integer recommendedPlanId);
}
