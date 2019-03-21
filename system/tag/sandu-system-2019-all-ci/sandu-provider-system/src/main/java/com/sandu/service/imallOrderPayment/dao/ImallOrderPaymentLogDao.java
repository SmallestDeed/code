package com.sandu.service.imallOrderPayment.dao;

import com.sandu.api.imallOrderPayment.model.ImallOrderPaymentLog;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发货单日志
 */
@Repository
public interface ImallOrderPaymentLogDao {

    /**
     * 新增发货单日志
     * @return
     */
    int insertOrderPaymentLog(ImallOrderPaymentLog model);

    /**
     * 通过id获取日志信息
     * @param id
     * @return
     */
    ImallOrderPaymentLog getImallOrderPaymentLog(@Param("id") long id);

    /**
     * 通过paymentId获取日志列表
     * @param paymentId
     * @return
     */
    List<ImallOrderPaymentLog> getImallOrderPaymentLogList(@Param("paymentId") long paymentId);
}
