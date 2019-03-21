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
 * decorate
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-10-23 14:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecoratePrice implements Serializable {


	/**
	 * 自增id
	 */
	private Integer id;
	/**
	 * 报价企业ID
	 */
	private Integer companyId;
	/**
	 * 客户ID(关联decorate_customer的主键ID)
	 */
	private Integer customerId;
	/**
	 * 装修业主Id(proprietor_info的主键ID)
	 */
	private Integer proprietorInfoId;
	/**
	 * 状态(0-待报价;1-已报价;2-未选中;3-超时取消报价;4-已选中)
	 */
	private Integer status;

	private String price;
	/**
	 * 报价用户ID
	 */
	private Integer bidUserId;
	/**
	 * 材料费(元)
	 */
	private Double materialFee;
	/**
	 * 质检费(元)
	 */
	private Double checkFee;
	/**
	 * 人工费(元)
	 */
	private Double labourFee;
	/**
	 * 设计费(元)
	 */
	private Double designFee;
	/**
	 * 报价提交时间
	 */
	private Date submitTime;
	/**
	 * 报价开始时间
	 */
	private Date startTime;
	/**
	 * 截止时间
	 */
	private Date endTime;
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


}
