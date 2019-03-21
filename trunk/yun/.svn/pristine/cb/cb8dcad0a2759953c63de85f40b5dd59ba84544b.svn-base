package com.sandu.pay.order.dao;

import com.sandu.pay.order.model.PayAccount;
import org.apache.ibatis.annotations.Param;

/**
 * 支付账户
 *
 * @Author yzw
 * @Date 2018/1/4 20:38
 */
public interface PayAccountMapper {

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增
     *
     * @param record
     * @return
     */
    int insertSelective(PayAccount record);

    /**
     * 获取
     *
     * @param id
     * @return
     */
    PayAccount selectByPrimaryKey(Integer id);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PayAccount record);

    /**
     *
     * 获取支付账户信息(根据平台类型和用户id来查找)
     *
     * @param record
     * @return
     */
    public PayAccount getPayAccountInfo(PayAccount record);

    int updateByUserId(PayAccount payAccount);

    PayAccount selectByUserIdAndPlatformBussinessType(@Param("userId") Integer userId, @Param("platformBussinessType")String platformBussinessType);
}
