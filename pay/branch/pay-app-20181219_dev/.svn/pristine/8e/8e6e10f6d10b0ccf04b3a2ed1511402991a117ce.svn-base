package com.sandu.common.objectconvert.payOrder;

import com.sandu.pay.order.vo.Mobile2bRechargeIntegralVo;
import com.sandu.pay.order.vo.RechargeIntegralVo;
import com.sandu.system.model.SysDictionary;

/**
 * @author 20171017
 * @desc 充值度币对象
 * @auth xiaoxc
 */
public class RechargeIntegralObject {


    /**
     * 将系统字典数据对象转换为充值度币VO对象
     *
     * @param sysDictionary 系统字典数据
     * @return RechargeIntegralVo 充值度币VO
     */
    public static RechargeIntegralVo parseToRechargeIntegralVoBySysDictionary(SysDictionary sysDictionary) {

        //创建对象
        RechargeIntegralVo rechargeIntegralVo = null;

        if (null != sysDictionary) {
            //初始化对象
            rechargeIntegralVo = new RechargeIntegralVo();
            rechargeIntegralVo.setRechargeId(sysDictionary.getId());
            rechargeIntegralVo.setRechargeMoney(sysDictionary.getValue() / 100);
            rechargeIntegralVo.setRechargeIntegral(sysDictionary.getName());
            rechargeIntegralVo.setPresentIntegral(sysDictionary.getAtt1());
            rechargeIntegralVo.setDiscountName(sysDictionary.getAtt2());
        }

        return rechargeIntegralVo;
    }

    /**
     * 将系统字典数据对象转换为充值度币VO对象（2b专用）
     *
     * @param sysDictionary
     * @return
     */
    public static Mobile2bRechargeIntegralVo parseToMobile2bRechargeIntegralVo(SysDictionary sysDictionary) {
        Mobile2bRechargeIntegralVo mobile2bRechargeIntegralVo = null;
        if (null != sysDictionary) {
            mobile2bRechargeIntegralVo = new Mobile2bRechargeIntegralVo();
            mobile2bRechargeIntegralVo.setId(sysDictionary.getId()); // 产品id
            mobile2bRechargeIntegralVo.setType(sysDictionary.getType()); // 产品类型
            mobile2bRechargeIntegralVo.setValuekey(sysDictionary.getValuekey()); // 唯一标识
            mobile2bRechargeIntegralVo.setValue(sysDictionary.getValue() / 100); //购买需要的金额
            mobile2bRechargeIntegralVo.setName(sysDictionary.getName());// 优惠说明，比如：充值30000度币送8000度币
            mobile2bRechargeIntegralVo.setOrdering(sysDictionary.getOrdering()); // 排序,序号越大，排序越后
            mobile2bRechargeIntegralVo.setPicId(sysDictionary.getPicId());
            mobile2bRechargeIntegralVo.setPicPath(sysDictionary.getPicPath());
            mobile2bRechargeIntegralVo.setAtt1(sysDictionary.getAtt1());// 赠送的度币（要除以10），如果为0表示没有赠送
            mobile2bRechargeIntegralVo.setAtt2(sysDictionary.getAtt2());//显示的度币（要除以10）
        }
        return mobile2bRechargeIntegralVo;
    }
}
