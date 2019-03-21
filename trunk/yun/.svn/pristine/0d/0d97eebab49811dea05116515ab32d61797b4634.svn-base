package com.sandu.pay.order.dao;

import com.sandu.pay.order.model.PayModelConfig;
import com.sandu.pay.order.model.PayOrder;
import com.sandu.pay.order.model.search.PayOrderSearch;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */
@Repository
public interface PayOrderMapper {

    PayOrder get(Integer id);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(PayOrder order);

    int updateByPrimaryKeySelective(PayOrder order);

    int selectCount(PayOrderSearch payOrderSearch);

    List<PayOrder> selectPaginatedList(PayOrderSearch payOrderSearch);

    List<PayOrder> selectList(PayOrder payOrder);

    /**
     * 通过订单NO更新订单状态
     *
     * @param order 订单NO
     * @return
     */
    int updatePayStateByOrderNo(PayOrder order);

    /**
     * 通过订单号查询订单信息
     *
     * @param orderNo
     * @return
     */
    PayOrder getOrderByOrderNo(String orderNo);

    /**
     * 查询任务对应的退款单数量
     *
     * @param taskId
     * @return
     */
    int selectRefundOrderCountByTaskId(Integer taskId);

    int selectExpenseRecordCount(PayOrderSearch payOrderSearch);

    List<PayOrder> selectExpenseRecordList(PayOrderSearch payOrderSearch);

    int updateTaskId(PayOrder payOrder);

    int updateForLockPayOrder(@Param("userId") String userId, @Param("orderNo") String orderNo);

    /**
     * 回填
     */
    int updateOrderByTaskId(PayOrder payOrder);

    PayOrder getConsumeSumByOrderNum(@Param("orderNum") String orderNum);

    int getCountByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") String status);

    void deleteByIdList(@Param("idList") List<Integer> orderIdList, @Param("remark") String remark, @Param("numa1") Integer numa1);


    /**
     * 获取度币共享的主子账号的消费记录总条数
     *
     * @param payOrderSearch
     * @return
     */
    int getShareCount(PayOrderSearch payOrderSearch);

    /**
     * 获取度币共享的主子账号的消费记录列表
     *
     * @param payOrderSearch
     * @return
     */
    List<PayOrder> getSharePaginatedList(PayOrderSearch payOrderSearch);

    /**
     * 获取度币共享的主子账号的消费记录总条数
     *
     * @param payOrderSearch
     * @return
     */
    int getShareCountPc(PayOrderSearch payOrderSearch);

    /**
     * 获取度币共享的主子账号的消费记录列表
     *
     * @param payOrderSearch
     * @return
     */
    List<PayOrder> getSharePaginatedListPc(PayOrderSearch payOrderSearch);

    /**
     * 获取退款的产品名称
     *
     * @param orderNo 订单号
     * @return
     */
    public String getRefundProductName(@Param("orderNo") String orderNo);

    /**
     * 获取退款的产品名称（pc端专用）
     *
     * @param orderNo 订单号
     * @return
     */
    public String getRefundProductNamePc(@Param("orderNo") String orderNo);

    /**
     * 通过订单号删除订单
     *
     * @param orderNo 订单号
     * @return
     */
    int deleteByOrderNo(String orderNo);

    List<PayModelConfig> selectPayModelConfig2C();

    int countHouseByPayOrder(Integer userId);

    int getExpenseRecordCountV2(PayOrderSearch payOrderSearch);

    List<PayOrder> selectExpenseRecordListV2(PayOrderSearch payOrderSearch);

    String selectPlanNameByPlanId(Integer planId);
}
