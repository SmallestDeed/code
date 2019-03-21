package com.sandu.amqp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderMessage implements Serializable {
    private static final long serialVersionUID = 628717254009236724L;
    // 订单ID
    private Integer orderId;
    // 执行操作(1:支付提醒,2:取消订单)
    private Integer action;
}