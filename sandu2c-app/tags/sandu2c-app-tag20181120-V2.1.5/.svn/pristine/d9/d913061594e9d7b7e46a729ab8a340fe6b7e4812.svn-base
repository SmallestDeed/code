package com.sandu.common.objectconvert.payOrder;

import com.sandu.pay.order.vo.RechargeIntegralVo;
import com.sandu.system.model.SysDictionary;

/**
 * @author 20171017
 * @desc 充值积分对象
 * @auth xiaoxc
 */
public class RechargeIntegralObject {


    /**
     * 将系统字典数据对象转换为充值积分VO对象
     *
     * @param sysDictionary 系统字典数据
     * @return RechargeIntegralVo 充值积分VO
     */
    public static RechargeIntegralVo parseToRechargeIntegralVoBySysDictionary(SysDictionary sysDictionary) {

        //创建对象
        RechargeIntegralVo rechargeIntegralVo = null;

        if (null != sysDictionary) {
            //初始化对象
            rechargeIntegralVo = new RechargeIntegralVo();
            rechargeIntegralVo.setRechargeId(sysDictionary.getId());
            rechargeIntegralVo.setRechargeMoney(sysDictionary.getValue()/100);
            rechargeIntegralVo.setRechargeIntegral(sysDictionary.getName());
            rechargeIntegralVo.setPresentIntegral(sysDictionary.getAtt1());
            rechargeIntegralVo.setDiscountName(sysDictionary.getAtt2());
        }

        return rechargeIntegralVo;
    }
}
