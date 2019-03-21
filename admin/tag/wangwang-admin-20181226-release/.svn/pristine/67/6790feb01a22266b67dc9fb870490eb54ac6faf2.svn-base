package com.sandu.api.decoratecustomer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:decorate_seckill表的实体类
 *
 * @author: Sandu
 * @创建时间: 2018-10-25
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DecorateSeckill implements Serializable {
	/**
	 * 自增id
	 */
	private Integer id;

	/**
	 * 客户ID(decorate_customer主键ID)
	 */
	private Integer customerId;

	/**
	 * 第一家抢单企业(客服提交抢单时写入)
	 */
	private Integer firstSeckillCompany;

	/**
	 * 抢购用户ID
	 */
	private Integer userId;

	/**
	 * 装修业主Id(proprietor_info的主键ID)
	 */
	private Integer proprietorInfoId;

	/**
	 * 状态(0-待抢购;1-已抢购)
	 */
	private Byte status;

	/**
	 * 客单价(元)
	 */
	private Double price;

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