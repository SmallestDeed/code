package com.sandu.mq.queue;

import lombok.Data;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/22 14:25
 */
@Data
public class SyncMessage implements Serializable {

    public static final int ACTION_ADD = 1;
    public static final int ACTION_DELETE = 2;
    public static final int ACTION_UPDATE = 3;

    public static final int MODULE_PRODUCT = 1;
    public static final int MODULE_SOLUTION = 2;
    public static final int MODULE_HOUSCE = 3;

    public enum PlatFormType {
        pcHouseDraw
    }


    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 动作
     */
    private int action;

    /**
     * 模块
     */
    private int module;

    /**
     * 对象
     */
    private Object object;

    /**
     * 平台类型
     */
    private String platformType;


}
