package com.sandu.api.volume.room.service;

import com.sandu.api.house.model.DrawBaseHouse;
import com.sandu.api.volume.room.model.DrawVolumeRoom;
import com.sandu.api.user.model.SysUser;
import com.sandu.util.http.RequestBody;
import org.springframework.web.multipart.MultipartFile;

public interface DrawVolumeRoomService {

    /**
     * 保存量房数据
     *
     * @param requestBody
     * @param zipFile
     */
    void saveVolumeRoom(RequestBody requestBody, MultipartFile zipFile);

    Integer handlerVolumeRoom(DrawBaseHouse drawHouse, Integer updateStatus, Integer applyStatus);

    DrawBaseHouse addDrawHouseByVolumeRoom(DrawVolumeRoom drawVolumeRoom, SysUser sysUser);
}
