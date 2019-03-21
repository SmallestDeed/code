package com.sandu.service.imallOrderShipment.dao;

import com.sandu.api.imallOrderShipment.model.ImallOrderShipment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 发货单
 */
@Repository
public interface ImallOrderShipmentDao {

    /**
     * 保存发货单
     * @param model
     * @return
     */
    int insertOrderShipment(ImallOrderShipment model);

    /**
     * 通过id获取发货单信息
     * @param id
     * @return
     */
    ImallOrderShipment getImallOrderShipment(@Param("id") long id);

    /**
     * 通过orderId获取发货单列表
     * @param orderId
     * @return
     */
    ImallOrderShipment getImallOrderShipmentList(@Param("orderId") long orderId);

}

