package com.sandu.api.grouppurchase.service;

import com.sandu.api.grouppurchase.bo.PurchaseGoodSkuBO;
import com.sandu.api.grouppurchase.model.BaseGoodsSku;
import com.sandu.api.grouppurchase.model.GroupPurchaseGoods;
import com.sandu.api.grouppurchase.model.MallBaseOrder;

import java.util.List;

/**
 * @author Sandu
 */
public interface GroupPurchaseGoodsService {
	List<PurchaseGoodSkuBO> getSkuInfoBySpuId(Long spuId, Long activityId);

	/**
	 * @param good      商品库存信息
	 * @param isOut     ture: 出库操作, false: 入库操作, null: 无出入库操作
	 * @param qtyChange 库存变化数值
	 * @param orderId   订单ID
	 */
	@Deprecated
	void updateQty(GroupPurchaseGoods good, Boolean isOut, Integer qtyChange, MallBaseOrder orderId);

	void updateQty(BaseGoodsSku sku, Boolean isOut, Integer qtyChange, MallBaseOrder orderId);
}
