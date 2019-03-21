package com.sandu.service.grouppurchase.service.impl;

import com.sandu.api.grouppurchase.bo.PurchaseGoodSkuBO;
import com.sandu.api.grouppurchase.model.BaseGoodsSku;
import com.sandu.api.grouppurchase.model.BaseGoodsStockLog;
import com.sandu.api.grouppurchase.model.GroupPurchaseGoods;
import com.sandu.api.grouppurchase.model.MallBaseOrder;
import com.sandu.api.grouppurchase.service.GroupPurchaseGoodsService;
import com.sandu.service.grouppurchase.dao.BaseGoodsSkuMapper;
import com.sandu.service.grouppurchase.dao.BaseGoodsStockLogMapper;
import com.sandu.service.grouppurchase.dao.GroupPurchaseGoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Sandu
 * @ClassName GroupPurchaseGoodsServiceImpl
 * @date 2018/11/6
 */
@Service
@Slf4j
public class GroupPurchaseGoodsServiceImpl implements GroupPurchaseGoodsService {

	@Autowired
	private GroupPurchaseGoodsMapper groupPurchaseGoodsMapper;

	@Autowired
	private BaseGoodsStockLogMapper baseGoodsStockLogMapper;

	@Autowired
	private BaseGoodsSkuMapper baseGoodsSkuMapper;


	@Override
	public List<PurchaseGoodSkuBO> getSkuInfoBySpuId(Long spuId, Long activityId) {

		return groupPurchaseGoodsMapper.getSkuInfoBySpuId(spuId, activityId);
	}

	@Deprecated
	@Override
	public void updateQty(GroupPurchaseGoods good, Boolean isOut, Integer qtyChange, MallBaseOrder order) {
		good.setGmtModified(new Date());
		groupPurchaseGoodsMapper.updateByPrimaryKeySelective(good);

		if (isOut == null) {
			return;
		}

		//维护sku库存
		BaseGoodsSku sku = baseGoodsSkuMapper.selectByPrimaryKey(good.getSkuId().intValue());
		Integer method = isOut ? -1 : 1;
		int inventory = sku.getInventory() + qtyChange * method;
		sku.setInventory(inventory);
		baseGoodsSkuMapper.updateByPrimaryKey(sku);


		BaseGoodsStockLog build = BaseGoodsStockLog.builder()
				.purchaseGoodsId(good.getId())
				.orderId(order.getId().longValue())
				.orderNo(order.getOrderCode())
				.spuId(good.getSpuId())
				.skuId(good.getSkuId())
				.qty(good.getQty())
				.outQty(isOut ? qtyChange : 0)
				.inQty(isOut ? 0 : qtyChange)
				.operateType((byte) (isOut ? 0 : 1))
				.gmtCreate(good.getGmtModified())
				.gmtModified(good.getGmtModified())
				.isDeleted(0)
				.build();
		baseGoodsStockLogMapper.insert(build);
	}

	@Override
	public void updateQty(BaseGoodsSku sku, Boolean isOut, Integer qtyChange, MallBaseOrder order) {
		if (isOut == null) {
			return;
		}

		Integer method = isOut ? -1 : 1;
		int inventory = sku.getInventory() + qtyChange * method;
		sku.setInventory(inventory);
		sku.setGmtModified(new Date());
		log.debug("sync qty param : {},  add : {}", sku, method);
		baseGoodsSkuMapper.updateByPrimaryKey(sku);

		BaseGoodsStockLog build = BaseGoodsStockLog.builder()
				.purchaseGoodsId(-1L)
				.orderId(order.getId().longValue())
				.orderNo(order.getOrderCode())
				.spuId(sku.getSpuId().longValue())
				.skuId(sku.getId().longValue())
				.qty(new BigDecimal(sku.getInventory()))
				.outQty(isOut ? qtyChange : 0)
				.inQty(isOut ? 0 : qtyChange)
				.operateType((byte) (isOut ? 0 : 1))
				.gmtCreate(sku.getGmtModified())
				.gmtModified(sku.getGmtModified())
				.isDeleted(0)
				.build();
		baseGoodsStockLogMapper.insert(build);
	}
}
