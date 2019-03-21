package com.nork.pay.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.<p>
 * 
 * 支付账户表
 * 
 * @author songjianming@sanduspace.cn <p>
 * 2018-10-27 15:10:36.735
 */

@Data
public class PayAccount implements Serializable {
    
    /**
     * 主键id<p>
     * pay_account.id
     */
    private Integer id;

    /**
     * 消费金额<p>
     * pay_account.consum_amount
     */
    private Double consumAmount;

    /**
     * 剩余金额（账户余额）<p>
     * pay_account.balance_amount
     */
    private Double balanceAmount;

    /**
     * 用户id<p>
     * pay_account.user_id
     */
    private Integer userId;

    /**
     * 平台id(暂时不用)<p>
     * pay_account.platform_id
     */
    private Integer platformId;

    /**
     * 是否删除<p>
     * pay_account.is_deleted
     */
    private Integer isDeleted;

    /**
     * 创建者<p>
     * pay_account.creator
     */
    private String creator;

    /**
     * 创建时间<p>
     * pay_account.gmt_create
     */
    private Date gmtCreate;

    /**
     * 修改人<p>
     * pay_account.modifier
     */
    private String modifier;

    /**
     * 修改时间<p>
     * pay_account.gmt_modified
     */
    private Date gmtModified;

    /**
     * 所属平台分类(2b/2c/sandu)<p>
     * pay_account.platform_bussiness_type
     */
    private String platformBussinessType;

    private static final long serialVersionUID = 1L;
}