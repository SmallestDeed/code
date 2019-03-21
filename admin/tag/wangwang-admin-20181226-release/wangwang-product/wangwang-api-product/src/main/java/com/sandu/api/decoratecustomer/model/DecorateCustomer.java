package com.sandu.api.decoratecustomer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * demo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-20 14:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecorateCustomer implements Serializable {


    /**
     * 客户ID
     */
    private Integer id;
    /**
     * 客户类型(0-客户店铺预约;1-抢单;2-平台派单)
     */
    private Integer customerType;
    /**
     * 装修业主Id(proprietor_info的主键ID)
     */
    private Integer proprietorInfoId;
    /**
     * 店铺预约装企ID
     */
    private Integer companyId;
    /**
     * 内部指派装企ID
     */
    private Integer innerCompanyId;
    /**
     * 第一家抢单企业ID
     */
    private Integer firstSeckillCompany;
    /**
     * 第二家抢单企业ID
     */
    private Integer secondSeckillCompany;
    /**
     * 方案ID
     */
    private Integer designPlanId;
    /**
     * 回访时间
     */
    private Date revisitTime;
    /**
     * 装修类型
     */
    private Integer decorateType;
    /**
     * 签约装企
     */
    private Integer contractCompany;
    /**
     * 签约价格
     */
    private String contractPrice;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改人
     */
    private String modifier;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 是否删除
     */
    private Integer isDeleted;

	/**
	 * 装修预算
	 */

	private Integer decorateBudget;

    /**
     * 报价截止时间
     */
    private Date bidEndTime;
    /**
     * 平台派单.报价状态 0-报价中; 1-完成报价; 2-完成了报价但是订单未指派出去
     */
    private Integer bidStatus;


}
