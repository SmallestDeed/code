package com.sandu.service.imallOrder.dao;

import com.sandu.api.imallOrder.model.*;
import com.sandu.api.imallOrder.output.ImallOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 积分订单
 */
@Repository
public interface ImallOrderDao {

    /**
     * 查询订单列表
     *
     * @param query
     * @return
     */
    List<ImallOrderVO> findAll(ImallOrderPO query);

    /**
     * 获取订单信息
     * @param orderId
     * @return
     */
    ImallOrder getImallOrderById(@Param("orderId") int orderId);

    /**
     * 获取订单明细
     * @param orderId
     * @return
     */
    List<ImallOrderItem> getImallOrderItem(@Param("orderId") int orderId);

    /**
     * 获取订单日志
     * @param orderId
     * @return
     */
    List<ImallOrderLog> getImallOrderLog(@Param("orderId") int orderId);

    /**
     * 获取订单地址信息
     * @param orderId
     * @return
     */
    List<ImallOrderAddress> getImallOrderAddress(@Param("orderId") int orderId);

    /**
     * 修改订单状态
     * @param id
     * @param status
     * @param modifier
     * @return
     */
    int updateImallOrderStatus(@Param("id") int id,@Param("status") int status,@Param("modifier") String modifier);

    /**
     * 添加订单日志
     * @param model
     * @return
     */
    int insertImallOrderLog(ImallOrderLog model);

    /**
     * 添加订单地址信息
     * @param model
     * @return
     */
    int insertImallOrderAddress(ImallOrderAddress model);

    /**
     * 删除订单地址信息
     * @param orderId
     * @return
     */
    int deleteImallOrderAddress(@Param("orderId") int orderId,@Param("modifier") String modifier);

    /**
     * 快递信息
     * @return
     */
    int updateImallOrderExpress(@Param("orderId") int orderId
            ,@Param("status") int status
            ,@Param("expressNo") String expressNo
            ,@Param("expressCompay") String expressCompay
            ,@Param("modifier") String modifier);

    /**
     * 退款
     * @return
     */
    int updateRefundment(@Param("orderId") int orderId,@Param("modifier") String modifier);

    /**
     * 添加积分操作记录
     * @param model
     * @return
     */
    int addPointInout(UserPointInout model);

    /**
     * 订单汇总状态数量
     * @return
     */
    List<Map<String,Integer>> collectStatusCount();
}
