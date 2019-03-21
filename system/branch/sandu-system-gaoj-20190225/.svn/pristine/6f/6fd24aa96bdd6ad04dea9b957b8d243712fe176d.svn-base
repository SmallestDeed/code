package com.sandu.service.imallOrderShipment.dao;

import com.sandu.api.imallOrderShipment.model.ImallOrderShipmentLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 发货单日志
 */
@Repository
public interface ImallOrderShipmentLogDao {

    /**
     * 新增发货单日志
     * @return
     */
    int insertOrderShipmentLog(ImallOrderShipmentLog model);

    /**
     * 通过id获取日志信息
     * @param id
     * @return
     */
    ImallOrderShipmentLog getImallOrderShipmentLog(@Param("id") long id);

    /**
     * 通过shipmentId获取日志列表
     * @param shipmentId
     * @return
     */
    List<ImallOrderShipmentLog> getImallOrderShipmentLogList(@Param("shipmentId") long shipmentId);
}
