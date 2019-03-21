package com.sandu.service.imallOrderPayment.dao;

import com.sandu.api.imallOrderPayment.model.ImallOrderPayment;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 发货单
 */
@Repository
public interface ImallOrderPaymentDao {

    /**
     * 保存发货单
     * @param model
     * @return
     */
    int insertOrderPayment(ImallOrderPayment model);

    /**
     * 通过id获取发货单信息
     * @param id
     * @return
     */
    ImallOrderPayment getImallOrderPayment(@Param("id") long id);

    /**
     * 通过orderId获取发货单列表
     * @param orderId
     * @return
     */
    ImallOrderPayment getImallOrderPaymentList(@Param("orderId") long orderId);

}

