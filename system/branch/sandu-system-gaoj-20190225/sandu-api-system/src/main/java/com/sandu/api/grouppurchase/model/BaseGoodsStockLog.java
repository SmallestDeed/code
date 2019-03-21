package com.sandu.api.grouppurchase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:base_goods_stock_log表的实体类
 *
 * @author: Sandu
 * @创建时间: 2018-12-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseGoodsStockLog implements Serializable {
	/**
	 * 主键ID
	 */
	private Long id;


	/**
	 * 拼团商品ID(关联group_purchase_goods表)
	 */
	private Long purchaseGoodsId;

	/**
	 * 订单ID(关联mall_base_order表id)
	 */
	private Long orderId;
	
	/**
	 * 订单ID(关联mall_base_order表orderCode)
	 */
	private String orderNo;

	/**
	 * 商品spu_id(关联base_goods_spu)
	 */
	private Long spuId;

	/**
	 * 商品sku_id(关联base_goods_sku)
	 */
	private Long skuId;

	/**
	 * 总库存
	 */
	private BigDecimal qty;

	/**
	 * 出库库存
	 */
	private Integer outQty;

	/**
	 * 入库库存
	 */
	private Integer inQty;

	/**
	 * 库存操作类型(0-出库;1-入库)
	 */
	private Byte operateType;

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