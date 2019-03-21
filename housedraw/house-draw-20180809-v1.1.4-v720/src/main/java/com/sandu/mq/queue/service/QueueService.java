package com.sandu.mq.queue.service;

import com.sandu.mq.queue.SyncMessage;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/22 14:22
 */
public interface QueueService {

    boolean send(SyncMessage sync);
}
