package com.sandu.amqp.entity.queue;

import lombok.Data;

import java.io.Serializable;

/**
 * 产品记录消息
 *
 * @date 2018/5/7
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Data
public class ProductMessage implements Serializable {

    private static final long serialVersionUID = -8058055217595151333L;


    //用户ID
    private int userId;
    //产品ID
    private int productId;
    //操作类型
    private int actionType;
    //平台类型
    private int platformType;

    public ProductMessage() {
        super();
    }

    public ProductMessage(int userId, int productId, int actionType, int platformType) {
        this.userId = userId;
        this.productId = productId;
        this.actionType = actionType;
        this.platformType = platformType;
    }
}
