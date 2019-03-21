package com.sandu.common.model;

import com.sandu.common.util.ModelTable;

import java.io.Serializable;


public class Mapper implements Serializable {
    private static final long serialVersionUID = -5084759450031494382L;
    private String deviceId = null;
    private String msgId = null;
    private String ids = null;
    private Integer start = 0;
    private Integer limit = 20;
    private String order = null;
    private String orderNum = null;
    private String orders = null;
    /**
     * 级别限制的资源数量
     */
    private int levelLimitCount = 0;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOrder() {
        return order == null ? null : ModelTable.toClumn(order);
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrders() {
        return orders == null ? null : ModelTable.toOrders(orders);
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }


    public boolean limitEquals(Object that) {
        return false;
    }

    public int getLevelLimitCount() {
        return levelLimitCount;
    }

    public void setLevelLimitCount(int levelLimitCount) {
        this.levelLimitCount = levelLimitCount;
    }

}
