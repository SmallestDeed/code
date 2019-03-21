package com.sandu.service.imallOrderShipment.dao;

import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发货单明细
 */
@Repository
public interface ImallOrderShipmentItemDao {

    /**
     * 添加发货单明细
     * @param model
     * @return
     */
    int insertImallOrderShipmentItem(ImallOrderShipmentItem model);

    /**
     * 通过id获取发货单明细信息
     * @param id
     * @return
     */
    ImallOrderShipmentItem getImallOrderShipmentItem(@Param("id") long id);

    /**
     * 通过shipmentId获取发货单明细信息
     * @return
     */
    List<ImallOrderShipmentItem> getImallOrderShipmentItemList(@Param("shipmentId") long shipmentId);
}
