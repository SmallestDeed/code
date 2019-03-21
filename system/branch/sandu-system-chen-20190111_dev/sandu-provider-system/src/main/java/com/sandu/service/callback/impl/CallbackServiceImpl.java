package com.sandu.service.callback.impl;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sandu.api.callback.AdConstant;
import com.sandu.api.callback.model.RecordAdClick;
import com.sandu.api.callback.service.CallbackService;
import com.sandu.api.callback.toutiao.TouTiaoReportClick;
import com.sandu.service.callback.dao.RecordAdClickDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/23/2018 4:48 PM
 */
@Service("callbackService")
@Slf4j
public class CallbackServiceImpl implements CallbackService {

    @Autowired
    private RecordAdClickDao recordAdClickDao;

    static final Gson GSON = new GsonBuilder().create();

    @Override
    public int recordTouTiaoAdClick(TouTiaoReportClick click) {
        log.debug("TouTiao Report: {}", click);

        RecordAdClick record = RecordAdClick.builder()
                .origin(AdConstant.ORIGIN_TOUTIAO)
                .deviceMac(Strings.nullToEmpty(click.getMac()))
                .deviceImei(Strings.nullToEmpty(click.getImei()))
                .clientIp(Strings.nullToEmpty(click.getIp()))
                .reportData(GSON.toJson(click))
                .build();

        if (AdConstant.TOUTIAO_OS_ANDROID == click.getOs()) {    // Android
            record.setDeviceOs(AdConstant.OS_ANDROID);
            record.setDeviceMainId(Strings.nullToEmpty(click.getUuid()));   //取 UUID
            record.setDeviceViceId(Strings.nullToEmpty(click.getAndroidid()));  //取 Android ID
        } else if (AdConstant.TOUTIAO_OS_IOS == click.getOs()) {    // iOS
            record.setDeviceOs(AdConstant.OS_IOS);
            record.setDeviceMainId(Strings.nullToEmpty(click.getIdfa()));   //取 IDFA
            record.setDeviceViceId(Strings.nullToEmpty(click.getUdid()));   //取 UDID
        }

        return recordAdClickDao.insert(record);
    }

    @Override
    public int changeStatus(int recordId, int status) {
        RecordAdClick record = new RecordAdClick();
        record.setId(recordId);
        record.setStatus(status);
        return recordAdClickDao.update(record);
    }

    @Override
    public RecordAdClick findByDeviceInfo(RecordAdClick deviceInfo) {
        return recordAdClickDao.findByDeviceInfo(deviceInfo);
    }
}
