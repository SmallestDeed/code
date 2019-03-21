package com.sandu.service.imallOrderPayment.dao;

import com.sandu.api.imallOrderPayment.model.ImallOrderPaymentDetails;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发货单地址信息
 */
@Repository
public interface ImallOrderPaymentDetailsDao {

    /**
     * 新增发货单地址信息
     * @param model
     * @return
     */
    int insertImallOrderPaymentDetails(ImallOrderPaymentDetails model);

    /**
     * id查询订单地址
     * @param id
     * @return
     */
    ImallOrderPaymentDetails getImallOrderPaymentDetails(@Param("id") long id);

    /**
     * 通过OrderId查询订单地址列表
     * @param shipmentId
     * @return
     */
    List<ImallOrderPaymentDetails> getImallOrderPaymentDetailsList(@Param("shipmentId") long shipmentId);


}
