package com.nork.render.model.input;

import com.nork.pay.metadata.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName RenderPaySearch
 * @Description 渲染支付方式信息查询类
 * @Author chenm
 * @Date 2019/1/15 16:06
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RenderPayInput implements Serializable{
    private static final long serialVersionUID = 1L;
    /** 支付方式**/
    private Integer payMethodNum;
    /** 渲染方式 **/
    private Integer renderingType;
    /** 设计方案id **/
    private Integer designPlanId;
    /** 请求平台id **/
    private Integer platformId;
    /** 请求平台类型 **/
    private String platformCode;
    /** **/
    private String authorization;
    /** 支付方式枚举值 **/
    private PayMethodEnum methodEnum;
    /**
     * 支付方式
     * @author: chenm
     * @date: 2019/1/15 18:00
     */
    @Getter
    @AllArgsConstructor
    public enum  PayMethodEnum{
        /**
         * 微信支付
         */
        WX(1, PayType.WXPAY,"wxScanCodePay"),
        /**
         * 支付宝支付
         */
        ALI(2, PayType.ALIPAY,"aliScanCodePay"),
        /**
         * 度币支付
         */
        DUBI(0, PayType.PREDEPOSIT,"balance");
        //前端给的值
        private Integer payMethodNum;
        //存入订单表的支付方式标识
        private String payType;
        //支付时的标识
        private String payMethodValue;
    }
    /** 渲染相关参数（是一个json字段,包含视角、场景、清晰度等级等参数,存储后直接返回给前端） **/
    private String renderParamsStr ;

}
