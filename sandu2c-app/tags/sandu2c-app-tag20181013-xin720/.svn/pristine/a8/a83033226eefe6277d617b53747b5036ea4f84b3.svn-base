package com.sandu.amqp.entity.queue;

import lombok.Data;

import java.io.Serializable;

/**
 * 设计方案记录消息
 *
 * @date 2018/5/4
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Data
public class DesignPlanMessage implements Serializable {

    private static final long serialVersionUID = 30749563614995335L;

    //用户ID
    private int userId;
    //设计方案ID
    private int designPlanId;
    //操作类型
    private int actionType;
    //平台类型
    private int platformType;

    public DesignPlanMessage() {
    }

    public DesignPlanMessage(int userId, int designPlanId, int actionType, int platformType) {
        this.userId = userId;
        this.designPlanId = designPlanId;
        this.actionType = actionType;
        this.platformType = platformType;
    }
}
