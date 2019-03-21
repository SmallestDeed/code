package com.nork.render.model.vo;

import com.nork.pay.metadata.PayType;
import com.nork.render.model.input.RenderPayInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @ClassName RenderPayInfo
 * @Description 渲染付费信息返回类
 * @Author chenm
 * @Date 2019/1/15 16:00
 * @Version 1.0
 **/
@Data
public class RenderPayVo implements Serializable{
    private static final long serialVersionUID = -2946571148983626903L;
    /** 支付方式**/
    private Integer payMethodNum;
    /** 支付金额 **/
    private String totalFee;
    /** 费用(单位:分) **/
    private Long renderCost;
    /** 价格单位 **/
    private String priceUnit;
    /**  二维码地址 **/
    private String qrCodeUrl;
    /** 支付流水号? **/
   /* private String payTradeNo;*/
   /** 账户余额 **/
    private String  balanceAmount;
}
