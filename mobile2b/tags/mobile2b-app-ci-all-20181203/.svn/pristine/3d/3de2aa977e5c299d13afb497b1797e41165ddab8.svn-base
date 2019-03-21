package com.nork.decorateOrder.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 装修客户表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-10-18 13:56:05.205
 */

@Data
public class DecorateCustomer implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
    /**
     * 客户ID<p>
     * decorate_customer.id
     */
    private Long id;

    /**
     * 客户类型(0-客户店铺预约;1-抢单;2-平台派单)<p>
     * decorate_customer.customer_type
     */
    private Byte customerType;

    /**
     * 装修业主Id(proprietor_info的主键ID)<p>
     * decorate_customer.proprietor_info_id
     */
    private Integer proprietorInfoId;

    /**
     * 店铺预约装企ID<p>
     * decorate_customer.company_id
     */
    private Long companyId;

    /**
     * 内部指派装企ID<p>
     * decorate_customer.inner_company_id
     */
    private Long innerCompanyId;

    /**
     * 第一家抢单企业ID<p>
     * decorate_customer.first_seckill_company
     */
    private Long firstSeckillCompany;

    /**
     * 第二家抢单企业ID<p>
     * decorate_customer.second_seckill_company
     */
    private Long secondSeckillCompany;

    /**
     * 方案ID<p>
     * decorate_customer.design_plan_id
     */
    private Long designPlanId;

    /**
     * 回访时间<p>
     * decorate_customer.revisit_time
     */
    private Date revisitTime;

    /**
     * 装修类型<p>
     * decorate_customer.decorate_type
     */
    private Byte decorateType;

    /**
     * 签约装企<p>
     * decorate_customer.contract_company
     */
    private Long contractCompany;

    /**
     * 签约价格<p>
     * decorate_customer.contract_price
     */
    private BigDecimal contractPrice;

    /**
     * 备注<p>
     * decorate_customer.remark
     */
    private String remark;

    /**
     * 创建者<p>
     * decorate_customer.creator
     */
    private String creator;

    /**
     * 创建时间<p>
     * decorate_customer.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * decorate_customer.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * decorate_customer.gmt_modified
     */
    private Date gmtModified;

    /**
     * 是否删除<p>
     * decorate_customer.is_deleted
     */
    private Integer isDeleted;
    
    /**
     * 报价截止时间
     */
    private Date bidEndTime;
    
    /**
     * 平台派单.报价状态 
     * 0-报价中; 
     * 1-完成报价; 
     * 2-完成了报价但是订单未指派出去
     */
    private Integer bidStatus;
    
}