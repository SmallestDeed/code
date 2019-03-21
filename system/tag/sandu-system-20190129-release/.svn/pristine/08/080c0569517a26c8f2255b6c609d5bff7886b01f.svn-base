package com.sandu.service.callback.dao;

import com.sandu.api.callback.model.RecordAdClick;
import org.springframework.stereotype.Repository;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/23/2018 4:44 PM
 */
@Repository
public interface RecordAdClickDao {

    /**
     * 插入点击数据
     * @param click
     * @return
     */
    int insert(RecordAdClick click);

    /**
     * 通过设备信息查找点击记录
     * @param deviceInfo
     * @return
     */
    RecordAdClick findByDeviceInfo(RecordAdClick deviceInfo);

    int update(RecordAdClick record);
}
