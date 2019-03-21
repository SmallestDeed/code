package com.nork.decorateOrder.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 装修抢单表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-10-18 13:56:05.208
 */

@Data
public class DecorateSeckill implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * 自增id<p>
     * decorate_seckill.id
     */
    private Long id;

    /**
     * 客户ID(decorate_customer主键ID)<p>
     * decorate_seckill.customer_id
     */
    private Long customerId;

    /**
     * 装修业主Id(proprietor_info的主键ID)<p>
     * decorate_seckill.proprietor_info_id
     */
    private Integer proprietorInfoId;

    /**
     * 状态(0-待抢购;1-已抢购)<p>
     * decorate_seckill.status
     */
    private Integer status;

    /**
     * 客单价<p>
     * decorate_seckill.price
     */
    private BigDecimal price;

    /**
     * 创建者<p>
     * decorate_seckill.creator
     */
    private String creator;

    /**
     * 创建时间<p>
     * decorate_seckill.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * decorate_seckill.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * decorate_seckill.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * decorate_seckill.is_deleted
     */
    private Integer isDeleted;
    
    /**
     * 抢购用户id
     */
    private Long userId;

    // ------非数据库字段 ->start
    
    /**
     * 抢单对应的发起人信息
     */
    private ProprietorInfo proprietorInfo;
    
    /**
     * 订单支付状态:
     * orderStatus = 0: 待支付
     * orderStatus = 1: 订单超时
     * orderStatus = 2: 待沟通
     * orderStatus = 3: 有意向
     * orderStatus = 4: 无意向
     * orderStatus = 5: 已签约
     * orderStatus = 6: 已完成
     */
    private Integer orderStatus;
    
    /**
     * 订单支付超时时间
     */
    private Date orderTimeout;
    
    /**
     * 结合status + orderStatus + orderTimeout判断出来的状态, 用于返回给客户端
	 * resultStatus = 0: 待抢购
	 * resultStatus = 1: 已抢购
	 * resultStatus = 2: 待支付
     */
    private Integer resultStatus;
    
    /**
	 * 剩余支付时间, 
	 * 格式: 毫秒数
	 */
	private Long remainingTime = 0L;
    
    // ------非数据库字段 ->start
    
}