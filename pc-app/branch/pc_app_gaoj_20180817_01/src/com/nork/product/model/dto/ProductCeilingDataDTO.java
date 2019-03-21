package com.nork.product.model.dto;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:00 2018/7/16 0016
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/16 0016PM 3:00
 */
public class ProductCeilingDataDTO implements Serializable{

    private String msgId;

    private String uuId;

    private String keyName;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
