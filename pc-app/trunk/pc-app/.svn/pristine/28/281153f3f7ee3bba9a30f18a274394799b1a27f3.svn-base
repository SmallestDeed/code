package com.nork.pay.dao;


import com.nork.pay.model.UserDesignPlanPurchaseRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDesignPlanPurchaseRecordMapper {
    /**
     * 物理删除
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 选择新增
     */
    int insertSelective(UserDesignPlanPurchaseRecord record);

    /**
     * 主键查询
     */
    UserDesignPlanPurchaseRecord selectByPrimaryKey(Long id);

    /**
     * 选择修改
     */
    int updateByPrimaryKeySelective(UserDesignPlanPurchaseRecord record);

    /**
     * 根据交易单号 修改交易状态
     * @param tradeNo       交易单号
     * @param recordStatus  交易状态
     * @return
     */
    int updateObjByTradeNo(@Param("tradeNo") String tradeNo,@Param("recordStatus") Integer recordStatus);
    

    /**
     * 校验用户购买方案
     * @author chenqiang
     * @param userId            用户id
     * @param designPlanId      方案id
     * @return
     * @date 2018/9/10 0010 11:55
     */
    int selectPlanZFCheck(@Param("userId") Integer userId,@Param("designPlanId") Integer designPlanId);


    /**
     * 根据交易单号获取对象
     * @author chenqiang
     * @param tradeNo 交易单号
     * @return
     * @date 2018/9/11 0011 16:40
     */
    UserDesignPlanPurchaseRecord selectUserDesignPlanPurchaseRecordByNO(@Param("tradeNo") String tradeNo);

}