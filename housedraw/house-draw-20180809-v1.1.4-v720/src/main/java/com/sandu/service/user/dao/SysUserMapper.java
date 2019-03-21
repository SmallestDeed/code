package com.sandu.service.user.dao;

import com.sandu.api.volume.room.model.VolumeRoomData;
import com.sandu.api.user.model.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserMapper {

    SysUser getSysUserByMobile(VolumeRoomData volumeRoomData);
}
