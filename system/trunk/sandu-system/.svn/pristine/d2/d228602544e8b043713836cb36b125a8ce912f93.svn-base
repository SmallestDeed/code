package com.sandu.service.imallOrderPayment.dao;

import com.sandu.api.imallOrderPayment.model.ImallOrderPaymentItem;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发货单明细
 */
@Repository
public interface ImallOrderPaymentItemDao {

    /**
     * 添加发货单明细
     * @param model
     * @return
     */
    int insertImallOrderPaymentItem(ImallOrderPaymentItem model);

    /**
     * 通过id获取发货单明细信息
     * @param id
     * @return
     */
    ImallOrderPaymentItem getImallOrderPaymentItem(@Param("id") long id);

    /**
     * 通过paymentId获取发货单明细信息
     * @return
     */
    List<ImallOrderPaymentItem> getImallOrderPaymentItemList(@Param("paymentId") long paymentId);
}
