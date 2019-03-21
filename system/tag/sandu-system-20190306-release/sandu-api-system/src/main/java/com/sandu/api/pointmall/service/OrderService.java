/**
 * 礼品服务接口
 *
 */
package com.sandu.api.pointmall.service;

import com.sandu.api.pointmall.model.ImallOrder;
import com.sandu.api.pointmall.model.ImallOrderAddress;
import com.sandu.api.pointmall.model.ImallOrderItem;
import com.sandu.api.pointmall.model.ImallOrderLog;
import com.sandu.api.pointmall.model.vo.OrderGiftDetailVo;

import java.util.List;

public interface OrderService {

    /**
     * 获取我的礼品列表
     * @return
     */
    List<OrderGiftDetailVo> getOrderGiftDetailVoList(int uid);

    /**
     * 更新订单状态
     * 后期还需完善功能，根据传入状态值判断订单业务逻辑等
     * 参数：id 订单id
     * 参数：status 要更新的状态
     * @return
     */
    int updateOrderStatus(int id,int status);

    ImallOrder selectOrderId(String orderNo);

    int insertSelective(ImallOrder record) ;

    int insertImallOrderAddressSelective(ImallOrderAddress record) ;

    int inserImallOrderItemSelective(ImallOrderItem record) ;

    int inserImallOrderLogSelective(ImallOrderLog record) ;
}
