package com.sandu.api.grouppurchase.service.biz;

import com.sandu.api.grouppurchase.bo.GroupPurchaseOrderBO;
import com.sandu.api.grouppurchase.bo.UserPurchaseListBO;
import com.sandu.api.grouppurchase.input.*;
import com.sandu.api.grouppurchase.model.GroupPurchaseOpenDetail;
import com.sandu.api.grouppurchase.model.MallBaseOrder;
import com.sandu.api.grouppurchase.output.*;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;

import java.util.List;

/**
 * @author Sandu
 * @ClassName GroupPurchaseBizService
 * @date 2018/11/6
 */
public interface GroupPurchaseBizService {


	ResponseEnvelope<GroupPurchaseGoodsDetailVO> getGoodsDetail(Long id,Long activityId, Long groupId, LoginUser loginUser);


    GroupPurchaseActivityVO getGoodsActivity(Long activityId,Long groupId);

    ResponseEnvelope<GoodsSkuVO> getGoodsAttr(Long id, Long activityId);

    ResponseEnvelope<GoodsSkuVO> getSkuInfoByAttrs(GoodsSkuQuery query);

    ResponseEnvelope<GroupPurchaseGatherVO> listGather(GroupPurchaseGatherQuery query);

	List<UserPurchaseListBO> listPurchaseByUserId(GroupPurchaseOpenQuery query);

	UserPurchaseListBO getPurchaseDetail(Long purchaseId);

	/**
	 * 生成团购订单
	 *
	 * @param query
	 * @return
	 */
    List<GoodsSkuVO> getActivitySku(GoodsSkuQuery query);

    int createActivitySku(GroupPurchaseGoodsAdd input);

	MallBaseOrder initGroupPurchaseOrder(GroupPurchaseOrderBO o);

    List<GroupPurchaseGoodsVO> getActivitySkuDetail(Integer purchaseActivityId, Integer spuId);

    int updateActivitySku(GroupPurchaseGoodsUpdate input);

	/**
	 * 完成支付后,回调同步相关数据状态
	 * 需要同步
	 * 		open_details 加入状态
	 * 		open 的参团状态(满员时,同步订单(mall_order)状态)
	 *		base_goods_stock_log 库存变化
	 *		groupPurchaseGoods 库存变化
	 *
	 *
	 * @param payTradeNo 订单
	 * @param payStatus  支付状态: 0,取消订单 1,支付失败 2,支付成功
	 */
	void callBackSyncPurchaseData(String payTradeNo, Integer payStatus);

	/**
	 * 同步到期团购活动
	 * 处理activity、groupPurchaseOpen 的状态
	 * 获取需要的退款用户信息
	 * 		完成退款之后,在退款回调中处理库存状态stock_log 和 groupPurchaseGoods , mallOrder
	 */
	List<GroupPurchaseOpenDetail> syncGroupPurchaseStatus();

	/**
	 * @param purchaseOpenId 开团Id
	 * @return 满团返回false, 反之true
	 */
	boolean checkOpenStatus(Long purchaseOpenId);

	public static void main(String[] args) {
		int i = 10;
		int i1 = i + '-';
		System.out.println(i1);
	}
}
