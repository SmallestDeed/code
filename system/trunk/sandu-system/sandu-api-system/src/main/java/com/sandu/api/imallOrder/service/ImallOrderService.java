package com.sandu.api.imallOrder.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.imallOrder.model.ImallOrder;
import com.sandu.api.imallOrder.model.ImallOrderAddress;
import com.sandu.api.imallOrder.model.ImallOrderPO;
import com.sandu.api.imallOrder.output.ImallOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ImallOrderService {
    /**
     * 查询列表
     * @return
     */
    PageInfo<ImallOrderVO> findAll(ImallOrderPO query);

    /**
     * 获取订单信息
     * @param id
     * @return
     */
    ImallOrder getImallOrderById(int id);

    /**
     * 确认
     * @param orderId
     * @param modifier
     * @return
     */
    String affirm(int orderId,String modifier);

    /**
     * 完成订单
     * @param orderId
     * @param modifier
     * @return
     */
    String complete(int orderId,String modifier);

    /**
     * 发货
     * @param orderId
     * @param expressCompay
     * @param expressNo
     * @param modifier
     * @return
     */
    String send(int orderId,String expressCompay,String expressNo,String modifier);

    /**
     * 修改配送信息
     * @return
     */
    String updateAddress(ImallOrderAddress model);

    /**
     * 退款
     * @param orderId
     * @param modifier
     * @return
     */
    String refundment(int orderId,String modifier);

    /**
     * 订单汇总状态数量
     * @return
     */
    List<Map<String,Integer>> collectStatusCount();

}
