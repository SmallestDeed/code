package com.nork.decorateOrder.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 装修订单表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-10-18 13:56:05.206
 */

@Data
public class DecorateOrder implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * 订单id<p>
     * decorate_order.id
     */
    private Long id;

    /**
     * 客户id<p>
     * decorate_order.customer_id
     */
    private Long customerId;

    /**
     * 企业ID<p>
     * decorate_order.company_id
     */
    private Long companyId;

    /**
     * 装修业主Id(proprietor_info的主键ID)<p>
     * decorate_order.proprietor_info_id
     */
    private Integer proprietorInfoId;

    /**
     * 订单类型(0-客户店铺预约;1-抢单;2-平台自动派单;3-内部推荐)<p>
     * decorate_order.order_type
     */
    private Integer orderType;

    /**
     * 订单状态(0-待支付;1-订单超时;2-待沟通;3-有意向;4-无意向;5-已签约;6-已完成)<p>
     * decorate_order.order_status
     */
    private Integer orderStatus;

    /**
     * 装修报价ID(关联decorate_price表)<p>
     * decorate_order.decorate_price_id
     */
    private Long decoratePriceId;

    /**
     * 抢购订单ID(关联decorate_seckill_order)<p>
     * decorate_order.decorate_seckill_order_id
     */
    private Long decorateSeckillOrderId;

    /**
     * 抢购用户ID/报价用户ID/内部推荐用户ID/店铺用户ID<p>
     * decorate_order.user_id
     */
    private Long userId;

    /**
     * 订单超时时间<p>
     * decorate_order.order_timeout
     */
    private Date orderTimeout;

    /**
     * 订单支付时间<p>
     * decorate_order.pay_time
     */
    private Date payTime;

    /**
     * 合同状态(0-待上传;1-待审核;2-审核通过)<p>
     * decorate_order.contract_status
     */
    private Integer contractStatus;

    /**
     * 合同Id(关联res_file的主键ID)<p>
     * decorate_order.contract_id
     */
    private Long contractId;

    /**
     * 签约价格<p>
     * decorate_order.contract_fee
     */
    private BigDecimal contractFee;

    /**
     * 服务费<p>
     * decorate_order.service_fee
     */
    private BigDecimal serviceFee;

    /**
     * 客单价<p>
     * decorate_order.price
     */
    private BigDecimal price;

    /**
     * 合同审核时间<p>
     * decorate_order.contract_approve_time
     */
    private Date contractApproveTime;

    /**
     * 合同审核操作人<p>
     * decorate_order.contract_approve_user_id
     */
    private Long contractApproveUserId;

    /**
     * 合同驳回时间<p>
     * decorate_order.contract_reject_time
     */
    private Date contractRejectTime;

    /**
     * 合同驳回原因<p>
     * decorate_order.contract_reject_reason
     */
    private String contractRejectReason;

    /**
     * 合同驳回操作人<p>
     * decorate_order.contract_reject_user_id
     */
    private Long contractRejectUserId;

    /**
     * 财务收款状态(1-待收款;2已收款)<p>
     * decorate_order.finance_receipts_status
     */
    private Byte financeReceiptsStatus;

    /**
     * 财务收款操作人<p>
     * decorate_order.finance_receipts_user_id
     */
    private Long financeReceiptsUserId;

    /**
     * 财务收款时间<p>
     * decorate_order.finance_receipts_time
     */
    private Date financeReceiptsTime;

    /**
     * 退款类型(0-用户申请;1-系统自动退款)<p>
     * decorate_order.refund_type
     */
    private Integer refundType;

    /**
     * 退款状态(0-待审核;1-审核通过-;2-已驳回)<p>
     * decorate_order.refund_status
     */
    private Integer refundStatus;

    /**
     * 退款申请时间<p>
     * decorate_order.refund_apply_time
     */
    private Date refundApplyTime;

    /**
     * 退款原因<p>
     * decorate_order.refund_reason
     */
    private String refundReason;

    /**
     * 退款驳回时间<p>
     * decorate_order.refund_reject_time
     */
    private Date refundRejectTime;

    /**
     * 退款驳回原因<p>
     * decorate_order.refund_reject_reason
     */
    private String refundRejectReason;

    /**
     * 退款驳回操作人<p>
     * decorate_order.refund_reject_user_id
     */
    private Long refundRejectUserId;

    /**
     * 退款审核通过时间<p>
     * decorate_order.refund_approve_time
     */
    private Date refundApproveTime;

    /**
     * 退款审核通过操作人<p>
     * decorate_order.refund_approve_user_id
     */
    private Long refundApproveUserId;

    /**
     * 备注1(客服)<p>
     * decorate_order.remark1
     */
    private String remark1;

    /**
     * 备注2(装企)<p>
     * decorate_order.remark2
     */
    private String remark2;

    /**
     * 创建者<p>
     * decorate_order.creator
     */
    private String creator;

    /**
     * 创建时间<p>
     * decorate_order.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * decorate_order.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * decorate_order.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * decorate_order.is_deleted
     */
    private Integer isDeleted;

    /**签约时间**/
    private Date signTime;

    // ------非数据库字段 ->start
    
    /**
     * 显示快抢订单id(decorate_seckill.id)
     */
    private Long decorateSeckillId;
    
    // ------非数据库字段 ->end
    
}