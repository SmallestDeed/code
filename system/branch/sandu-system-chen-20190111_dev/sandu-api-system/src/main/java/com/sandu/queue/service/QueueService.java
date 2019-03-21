package com.sandu.queue.service;


import com.sandu.api.collect.model.RecordMachineInfoOperation;
import com.sandu.queue.SyncMessage;
import org.springframework.stereotype.Component;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/22 14:22
 */
@Component
public interface QueueService {

    /**
     * 发送消息
     *
     * @param
     * @return
     */
    boolean send(byte[] messages);

    /**
     * 发送上报的设备消息
     *
     * @param info
     * @return
     */
    boolean sendDeviceInfo(RecordMachineInfoOperation info);

    /**
     * 发送同步搜索消息
     *
     * @param message
     * @return
     */
    boolean sendSyncSearch(SyncMessage message);

    String fetch();

    <T> T fetchDeviceInfo(Class<T> cls);
}
