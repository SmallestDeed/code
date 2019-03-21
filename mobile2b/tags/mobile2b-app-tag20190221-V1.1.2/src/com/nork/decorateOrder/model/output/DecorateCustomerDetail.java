package com.nork.decorateOrder.model.output;

import com.nork.decorateOrder.model.ProprietorInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的客户详情
 * @author WangHaiLin
 * @date 2018/10/18  18:04
 */
@Data
public class DecorateCustomerDetail extends ProprietorInfo implements Serializable {

	/**订单Id**/
    private Integer orderId;

    /**订单状态(0-待支付;1-订单超时;2-待沟通;3-有意向;4-无意向;5-已签约;6-已完成)**/
    private Integer orderStatus;

    /**客单价**/
    private BigDecimal price;

    /**服务费**/
    private BigDecimal serviceFee;

    /**签约价格（成交价格）**/
    private BigDecimal contractFee;

    /**合同上传时间**/
    private Date contractUploadTime;

    /**服务费上缴截止时间**/
    private String endOfServicePayTimeStr;

    /**三度客服备注**/
    private String remark1;

    /**装企备注**/
    private String remark2;

    /**退款状态(0-待审核;1-审核通过-;2-已驳回)**/
    private Integer refundStatus;

    /**退款原因**/
    private String refundReason;

    /**退款驳回原因**/
    private String refundRejectReason;

    /**订单类型(0-客户店铺预约;1-抢单;2-平台自动派单;3-内部推荐)**/
    private Integer orderType;

    /**合同状态(0-待上传;1-待审核;2-审核通过)**/
    private Integer contractStatus;

    /**订单生成时间**/
    private Date orderTime;

    /**支付倒计时**/
    private long payRemainingTime;

    /**方案图片**/
    private String planPicPath;

    /**方案名称**/
    private String planName;

    /**方案Id**/
    private Long planRecommendedId;

    /**全屋方案UUID**/
    private String fullHousePlanUUID;

    /**装修报价表Id**/
    private Long decoratePriceId;

    /**材料费(元)**/
    private BigDecimal materialFee;

    /**质检费(元)**/
    private BigDecimal checkFee;

    /**人工费(元)**/
    private BigDecimal labourFee;

    /**设计费(元)**/
    private BigDecimal designFee;

    /**总费用**/
    private BigDecimal totalFee;

    /**
     * 支付时间
     */
    private Date payTime;
    
}
