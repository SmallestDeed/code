package com.sandu.api.decorateorder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * decorate_order
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-18 14:00
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecorateOrder implements Serializable {

    public final static Integer ORDER_TYPE_COMPANY = 0;
    public final static Integer ORDER_TYPE_AUTO_SEDKILL = 1;
    public final static Integer ORDER_TYPE_AUTO_DISPATCH = 2;
    public final static Integer ORDER_TYPE_INNER_SET = 3;

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 订单id */
    private Integer id;
    /** 客户id */
    private Integer customerId;
    /** 签约装企 */
    private Integer companyId;
    /** 装修业主Id(proprietor_info的主键ID) */
    private Integer proprietorInfoId;
    /** 订单类型(0-客户店铺预约;1-抢单;2-平台自动派单;3-内部推荐) */
    private Integer orderType;
    /** 订单状态(0-待支付;1-订单超时;2-待沟通;3-有意向;3-无意向;5-已签约;6-已完成) */
    private Integer orderStatus;
    /** 装修报价ID(关联decorate_price表) */
    private Integer decoratePriceId;
    /** 抢购订单ID(关联decorate_seckill_order) */
    private Integer decorateSeckillOrderId;
    /** 抢购用户ID/报价用户ID/内部推荐用户ID/店铺用户ID */
    private Integer userId;
    /** 订单超时时间 */
    private Date orderTimeout;
    /** 订单支付时间 */
    private Date payTime;
    /** 合同状态 */
    private Integer contractStatus;
    /** 合同查看 */
    private Integer contractId;
    /** 签约价格 */
    private String contractFee;
    /** 服务费（签约价格*5%） */
    private String serviceFee;
    /** 客单价 */
    private String price;
    /** 合同审核时间 */
    private Date contractApproveTime;
    /** 合同审核操作人 */
    private Integer contractApproveUserId;
    /** 合同驳回时间 */
    private Date contractRejectTime;
    /** 合同驳回原因 */
    private String contractRejectReason;
    /** 合同驳回操作人 */
    private Integer contractRejectUserId;
    /** 财务收款状态 */
    private Integer financeReceiptsStatus;
    /** 财务收款操作人 */
    private Integer financeReceiptsUserId;
    /** 财务收款时间 */
    private Date financeReceiptsTime;
    /** 退款类型(0-用户申请;1-系统自动退款) */
    private Integer refundType;
    /** 退款状态 */
    private Integer refundStatus;
    /** 退款申请时间 */
    private Date refundApplyTime;
    /** 退款原因 */
    private String refundReason;
    /** 退款驳回时间 */
    private Date refundRejectTime;
    /** 退款驳回原因 */
    private String refundRejectReason;
    /** 退款驳回操作人 */
    private Integer refundRejectUserId;
    /** 退款审核通过时间 */
    private Date refundApproveTime;
    /** 退款审核通过操作人 */
    private Integer refundApproveUserId;
    /** 备注1(客服) */
    private String remark1;
    /** 备注2(装企) */
    private String remark2;
    /** 创建者 */
    private String creator;
    /** 创建时间 */
    private Date gmtCreate;
    /** 修改人 */
    private String modifier;
    /** 修改时间 */
    private Date gmtModified;
    /** 是否删除 */
    private Integer isDeleted;
    /** 合同上传操作人 */
    private Integer contractUploadUserId;
    /** 合同上传时间 */
    private Date contractUploadTime;
    /**
     * 退款状态
     */
    private String refundStatusStr;
    /**
     * 财务收款状态
     */
    private String financeReceiptsStatusStr;
    /**
     * 合同状态
     */
    private String contractStatusStr;
    
    /**
     * 省份+城市名称
     */
    private String cityName;
    
    private String userName;
    
    private String mobile;
    
    private String companyName;
    
    
}
