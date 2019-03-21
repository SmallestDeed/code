package com.sandu.api.grouppurchase.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述:group_purchase_activity表的实体类
 *
 * @author: Sandu
 * @创建时间: 2018-12-06
 */

@Data
public class GroupPurchaseActivity implements Serializable {
	/**
	 * 活动ID
	 */
	private Long id;

	/**
	 * 活动名称
	 */
	private String activityName;

	/**
	 * 商品spu_id(关联base_goods_spu)
	 */
	private Long spuId;

	/**
	 * 商品名称
	 */
	private String spuName;

	/**
	 * 活动结束时间
	 */
	private Date activityStartTime;

	/**
	 * 活动结束时间
	 */
	private Date activityEndTime;

	/**
	 * 拼团有效期(天)
	 */
	private Integer groupValidDay;

	/**
	 * 拼团有效期(小时)
	 */
	private Integer groupValidHour;

	/**
	 * 拼团有效期(分钟)
	 */
	private Integer groupValidMinute;

	/**
	 * 成团人数
	 */
	private Integer totalNumber;

	/**
	 * 限购数量
	 */
	private Integer purchaseLimitAmount;

	/**
	 * 开启凑团标识(0-不开启;1-开启)
	 */
	private Byte gatherFlag;

	/**
	 * 虚拟成团标识(0-不虚拟成团;1-虚拟成团)
	 */
	private Byte virtualFlag;

	/**
	 * 优惠叠加标识(0-不可使用;1-可使用)
	 */
	private Byte couponFlag;

	/**
	 * 活动状态(0-草稿;1-未开始;2-进行中;3-已结束;4-已失效)
	 */
	private Byte activityStatus;

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
	 * 总订单金额
	 */
	private Double totalOrderAmount;

	/**
	 * 总订单数量
	 */
	private Integer totalOrderCount;

	/**
	 * 总订单人数
	 */
	private Integer totalOrderPerson;

	/**
	 * 商品名称
	 */
	private String productText;

	/**
	 * 商品路径
	 */
	private String productPath;

	/**
	 * 活动链接
	 */
	private String activityPath;

	/**
	 * 备注
	 */
	private String remark;

	private Integer scheduleFlag;
	
	/**
	 * 公司ID
	 */
	private Integer companyId;
}