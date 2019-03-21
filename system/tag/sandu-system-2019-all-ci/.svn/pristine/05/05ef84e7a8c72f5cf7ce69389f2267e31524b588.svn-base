package com.sandu.api.grouppurchase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:group_activity_open_detail表的实体类
 *
 * @author: Sandu
 * @创建时间: 2018-12-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupPurchaseOpenDetail implements Serializable {
	/**
	 * 活动ID
	 */
	private Long id;

	/**
	 * 参团用户ID
	 */
	private Long userId;

	/**
	 * 活动ID(关联group_purchase_activity表)
	 */
	private Long purchaseActivityId;

	/**
	 * 参团ID(关联group_purchase_open表)
	 */
	private Long purchaseOpenId;

	/**
	 * 拼团商品ID(关联group_purchase_goods表)
	 */
	private Long purchaseGoodsId;

	/**
	 * 参团ID
	 */
	private String telephone;

	/**
	 * 用户类型(0-虚拟用户;1-真实用户)
	 */
	private Byte userType;

	/**
	 * 是否团长(0-否;1-是)
	 */
	private Byte isMaster;

	/**
	 * 订单金额
	 */
	private BigDecimal orderAmount;

	/**
	 * 购买数量
	 */
	private Long buyQuantity;

	/**
	 * 订单ID
	 */
	private Long orderId;

	/**
	 * 小程序唯一标识
	 */
	private String orderNo;

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

	private Integer joinStatus;
}