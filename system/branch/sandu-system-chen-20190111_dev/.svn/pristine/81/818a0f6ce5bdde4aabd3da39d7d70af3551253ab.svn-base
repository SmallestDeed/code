package com.sandu.api.callback.service;

import com.sandu.api.callback.model.RecordAdClick;
import com.sandu.api.callback.toutiao.TouTiaoReportClick;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/23/2018 3:55 PM
 */
public interface CallbackService {

    /**
     * 记录头条点击数据
     * @return
     */
    int recordTouTiaoAdClick(TouTiaoReportClick click);

    /**
     * 改变匹配状态
     * @param recordId
     * @return
     */
    int changeStatus(int recordId, int status);

    /**
     * 通过设备信息查找点击记录
     * @param deviceInfo
     * @return
     */
    RecordAdClick findByDeviceInfo(RecordAdClick deviceInfo);
}
