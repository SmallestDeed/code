package com.sandu.commons;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/6/9 0009 10:49
 * @Modified By
 */
public class Kav implements Serializable{

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
