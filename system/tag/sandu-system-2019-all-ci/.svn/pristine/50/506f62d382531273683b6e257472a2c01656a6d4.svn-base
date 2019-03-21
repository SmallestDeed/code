package com.sandu.config;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sandu.api.callback.AdConstant;
import com.sandu.api.callback.model.RecordAdClick;
import com.sandu.api.callback.service.CallbackService;
import com.sandu.api.callback.toutiao.TouTiaoReportClick;
import com.sandu.api.collect.model.RecordMachineInfoOperation;
import com.sandu.queue.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.IOException;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-system
 *
 * @author A.ddy (yocome@gmail.com)
 * @datetime 10/24/2018 6:40 PM
 */
@EnableScheduling
@Component
@Slf4j
public class AdClickSchedule {

    private static final Gson GSON = new GsonBuilder().create();

    private int num = 1;


    @Autowired
    private QueueService queueService;

    @Autowired
    private CallbackService callbackService;

    /**
     * 每 2 分钟检查用户打开、注册时上报设备信息是否匹配今日头条上报数据：
     * 有匹配，则返馈给今日头条
     */
    @Scheduled(initialDelay = 180000L, fixedRate = 180000L)
    public void checkTouTiaoAdClick() {
        log.info("Check TouTiao ad click: " + num++);
        /**
         * 1、获取队列中的设备上报信息
         */
        RecordMachineInfoOperation deviceInfo = queueService.fetchDeviceInfo(RecordMachineInfoOperation.class);
        if (deviceInfo != null) {
            log.info("Pull message: " + deviceInfo.toString());
            RecordAdClick click = RecordAdClick.builder()
                    .origin(AdConstant.ORIGIN_TOUTIAO)
                    .deviceMac(Strings.nullToEmpty(deviceInfo.getMac()))
                    .clientIp(Strings.nullToEmpty(deviceInfo.getAppIp()))
                    .build();

            if(Strings.isNullOrEmpty(deviceInfo.getImei())) {
                click.setDeviceImei("");
            }else{
                //对 IMEI 进行 MD5
                final String imeiMd5 = DigestUtils.md5DigestAsHex(deviceInfo.getImei().getBytes());
                click.setDeviceImei(imeiMd5);
            }

            int clientOs = AdConstant.TOUTIAO_OS_ANDROID;

            switch (Strings.nullToEmpty(deviceInfo.getOs()).toLowerCase()) {
                case AdConstant.OS_ANDROID:
                    click.setDeviceOs(AdConstant.OS_ANDROID);
                    click.setDeviceMainId(deviceInfo.getUuid());
                    click.setDeviceViceId(deviceInfo.getAndroidId());
                    break;
                case AdConstant.OS_IOS:
                    clientOs = AdConstant.TOUTIAO_OS_IOS;
                    click.setDeviceOs(AdConstant.OS_IOS);
                    click.setDeviceMainId(deviceInfo.getIdfa());
                    click.setDeviceViceId(deviceInfo.getUdid());
                    break;
                default:
            }

            /**
             * 2、查找广告服务器上报的点击记录
             */
            RecordAdClick record = callbackService.findByDeviceInfo(click);
            if (record != null) {
                log.info("Matched record: " + record.toString());
                /**
                 * 3、向广告服务器反馈匹配的数据
                 */
                long currentTime = System.currentTimeMillis();
                final TouTiaoReportClick report = GSON.fromJson(record.getReportData(), TouTiaoReportClick.class);
//                TouTiaoTrack track = TouTiaoTrack.builder()
//                        .conv_time(currentTime)
//                        .os(clientOs)
//                        .source("Sandu")
//                        .callback(report.getCallback())
//                        .build();


                // setting MUID
                String muid = Strings.nullToEmpty(report.getImei());

                // 触发回传事件类型
                int eventType = 0;
                switch (deviceInfo.getRecordType().intValue()) {
                    case 1: // 激活
//                        track.setEvent_type(AdConstant.TOUTIAO_EVENT_ACTIVE);
                        eventType = AdConstant.TOUTIAO_EVENT_ACTIVE;
                        break;

                    case 2: //注册
//                        track.setEvent_type(AdConstant.TOUTIAO_EVENT_ACTIVE_REGISTER);
                        eventType = AdConstant.TOUTIAO_EVENT_ACTIVE_REGISTER;
                        break;
                }

                //设置签名
                String key = "6681d0e8-6796-4e65-b8ba-fdcec269a981";
                final StringBuilder url = new StringBuilder(AdConstant.TOUTIAO_TRACK_URL);
                url.append("?callback=").append(Strings.nullToEmpty(report.getCallback()))
                        .append("&muid=").append(muid)
                        .append("&os=").append(clientOs)
                        .append("&source=").append("Sandu")
                        .append("&conv_time=").append(currentTime)
                        .append("&event_type=").append(eventType);

                //生成 Signature, 先MD5, 再 Base64
//                byte[] signature = DigestUtils.md5Digest((key + url).getBytes());
//                signature = Base64Utils.encode(signature);
//                url.append("&signature=").append(new String(signature));

                log.info("Start Callback TouTiao URL： " + url.toString());
                final OkHttpClient client = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(url.toString())
                        .get()
                        .build();

                try {
                    final Response response = client.newCall(request).execute();
                    log.info("Callback Active response: {}", response);
                    log.info("TouTiao response body: " + response.body().string());

                    if (response.isSuccessful()) {
                        // 更新记录状态为: 已匹配
                        callbackService.changeStatus(record.getId(), AdConstant.RECORD_PROCESSED);
                    }
                } catch (IOException e) {
                    log.error("Callback Active Error: " + e.getMessage());
                }

            }

        } else {
            log.info("No have message.");
        }
    }
}
