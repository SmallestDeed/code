package com.sandu.api.grouppurchase.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:base_goods_sku表的实体类
 *
 * @author: Sandu
 * @创建时间: 2018-12-17
 */

@Data
public class BaseGoodsSku {
	/**
	 *
	 */
	private Integer id;

	/**
	 * 产品ID
	 */
	private Integer productId;

	/**
	 * spu ID
	 */
	private Integer spuId;

	/**
	 * 规格属性
	 */
	private String attributeIds;

	/**
	 * 库存
	 */
	private Integer inventory;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 列表图片ID
	 */
	private Integer listPicId;

	/**
	 * 规格图片ID
	 */
	private Integer specificationPicId;

	/**
	 * 主图ID
	 */
	private Integer mainPicId;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改者
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