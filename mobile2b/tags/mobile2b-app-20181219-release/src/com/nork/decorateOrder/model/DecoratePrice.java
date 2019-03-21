package com.nork.decorateOrder.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 装修报价表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-10-18 13:56:05.207
 */

@Data
public class DecoratePrice implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * 自增id<p>
     * decorate_price.id
     */
    private Long id;

    /**
     * 报价企业ID<p>
     * decorate_price.company_id
     */
    private Long companyId;

    /**
     * 客户ID(关联decorate_customer的主键ID)<p>
     * decorate_price.customer_id
     */
    private Long customerId;

    /**
     * 装修业主Id(proprietor_info的主键ID)<p>
     * decorate_price.proprietor_info_id
     */
    private Integer proprietorInfoId;

    /**
     * 状态(0-待报价;1-已报价;2-未选中;3-超时取消报价)<p>
     * decorate_price.status
     */
    private Integer status;

    /**
     * 报价用户ID<p>
     * decorate_price.bid_user_id
     */
    private Long bidUserId;

    /**
     * 材料费<p>
     * decorate_price.material_fee
     */
    private BigDecimal materialFee;

    /**
     * 质检费<p>
     * decorate_price.check_fee
     */
    private BigDecimal checkFee;

    /**
     * 人工费<p>
     * decorate_price.labour_fee
     */
    private BigDecimal labourFee;

    /**
     * 设计费<p>
     * decorate_price.design_fee
     */
    private BigDecimal designFee;

    /**
     * 报价提交时间<p>
     * decorate_price.submit_time
     */
    private Date submitTime;

    /**
     * 报价开始时间<p>
     * decorate_price.start_time
     */
    private Date startTime;

    /**
     * 截止时间<p>
     * decorate_price.end_time
     */
    private Date endTime;

    /**
     * 创建者<p>
     * decorate_price.creator
     */
    private String creator;

    /**
     * 创建时间<p>
     * decorate_price.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * decorate_price.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * decorate_price.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * decorate_price.is_deleted
     */
    private Integer isDeleted;
    
    /**
     * 报价被选中后,需要扣款price元
     */
    private BigDecimal price;
    
    // ------非数据库字段 ->start
    
    /**
     * 报价总价
     */
    private BigDecimal totalFee;
    
    /**
     * 支付状态; 和decorate_order.order_status保持一致
     * 0-待支付
     * 1-已支付,待沟通
     */
    private Integer payStatus;
    
    // ------非数据库字段 ->end
    
}