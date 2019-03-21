package com.sandu.service.volume.room.dao;

import com.sandu.api.house.bo.DrawBaseHouseBO;
import com.sandu.api.volume.room.model.DrawVolumeRoom;
import com.sandu.api.volume.room.model.VolumeRoomData;
import com.sandu.api.volume.room.model.VolumeRoomHouseBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DrawVolumeRoomMapper {
    int insertSelective(DrawVolumeRoom record);

    DrawVolumeRoom selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrawVolumeRoom record);

    DrawVolumeRoom getDrawVolumeRoomByKey(VolumeRoomData volumeRoomData);

    Integer updateVolumeRoomByLock(@Param("drawHouseId") Long drawHouseId,
                                   @Param("updateStatus") Integer updateStatus,
                                   @Param("applyStatus") Integer applyStatus);

    List<VolumeRoomHouseBO> listVolumeRoomByHouseId(@Param("list") List<DrawBaseHouseBO> volumeRooms,
                                                    @Param("status") Integer status);

}