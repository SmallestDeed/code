package com.sandu.service.account.dao;

import com.sandu.api.account.model.MgrRechargeLog;
import com.sandu.api.account.model.PayAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/6/12  18:23
 */
@Repository
public interface PayAccountDao {
    /**
     * 新增支付账户
     * @param payAccount 入参 支付账户实体
     * @return 返回结果
     */
    int addPayAccount(PayAccount payAccount);

    /**
     * 根据用户Id查询账号
     * @param userId
     * @return
     */
    PayAccount selectByUserId(Long userId);

    int update(PayAccount p);

    PayAccount getInfoByUserIdAndPlatformBussinessType(@Param("userId") Integer userId, @Param("paltformBussinssType") String paltformBussinssType);

    List<MgrRechargeLog> getUserRechargeRecord(@Param("userId")Long userId, @Param("start")Integer start, @Param("limit")Integer limit);

    int countUserRechargeRecord(Long userId);

    int updateUserBalanceAmount(@Param("userId")Long userId,@Param("dubi")Double dubi);
}
