package com.sandu.service.imallOrderShipment.dao;

import com.sandu.api.imallOrder.model.ImallOrder;
import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发货单地址信息
 */
@Repository
public interface ImallOrderShipmentAddressDao {

    /**
     * 新增发货单地址信息
     * @param model
     * @return
     */
    int insertImallOrderShipmentAddress(ImallOrderShipmentAddress model);

    /**
     * id查询订单地址
     * @param id
     * @return
     */
    ImallOrderShipmentAddress getImallOrderShipmentAddress(@Param("id") long id);

    /**
     * 通过OrderId查询订单地址列表
     * @param shipmentId
     * @return
     */
    List<ImallOrderShipmentAddress> getImallOrderShipmentAddressList(@Param("shipmentId") long shipmentId);


}
