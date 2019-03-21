package com.sandu.api.grouppurchase.bo;

import com.sandu.order.OrderProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 * @ClassName GroupPurchaseOrderBO
 * @date 2018/11/6
 */
@Data
public class GroupPurchaseOrderBO implements Serializable {
	@ApiModelProperty(value = "用户ID", required = true)
	private Long userId;

	@ApiModelProperty(value = "是否为开团人", required = true)
	private Long isMaster;

	@ApiModelProperty(value = "sku库存Id", hidden = true)
	private Long purchaseGoodsId;

	@ApiModelProperty(value = "skuId", required = true)
	private Long skuId;

	@ApiModelProperty(value = "spuID", required = true)
	private Long spuId;

	@ApiModelProperty(value = "开团ID,参团必填")
	private Long purchaseOpenId;

	@ApiModelProperty(value = "商品拼团ID", required = true)
	private Long purchaseActiveId;

	@ApiModelProperty(value = "下单数量", required = true)
	private Long skuNum;

	@ApiModelProperty(value = "收货人姓名", required = true)
	private String consignee;

	@ApiModelProperty(value = "收货人的省份", required = true)
	private String province;

	@ApiModelProperty(value = "收货人的城市", required = true)
	private String city;


	@ApiModelProperty(value = "收货人的地区", required = true)
	private String district;

	@ApiModelProperty(value = "收货人的详细地址", required = true)
	private String address;

	@ApiModelProperty(value = "配送店铺Id ", required = true)
	private Long shopId;

	@ApiModelProperty(value = "用户手机号", required = true)
	private String mobile;

	@ApiModelProperty(value = "公司ID", required = true)
	private Long companyId;

	@ApiModelProperty
	private String orderNo;

	@ApiModelProperty(value = "优惠券ID")
	private Long couponId;

	@ApiModelProperty(value = "订单产品", required = true)
	private List<OrderProduct> orderProductList;

	private String deliverAddress;
	private String deliverAreaCode;
	private String deliverAreaLongCode;
	private String deliverCityCode;
	private String deliverProvinceCode;
	private String deliverStreetCode;
	private Long franchiserId;



}
