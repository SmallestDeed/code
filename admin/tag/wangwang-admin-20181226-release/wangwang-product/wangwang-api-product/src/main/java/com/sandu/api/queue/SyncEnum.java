package com.sandu.api.queue;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-wangwang
 *
 * @author Yoco (yocome@gmail.com)
 * @date 2018/3/22 14:30
 */
public enum SyncEnum {

    TOB("2b", "2B"),
    TOC("2c", "2C");

    SyncEnum(String value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    private String value;

    private String remark;

    public static enum Action {
        CREATE("create", "create"),
        UPDATE("create", "update"),
        DELETE("create", "delete");

        private String value;

        private String remark;

        Action(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }

        public String getValue() {
            return value;
        }

        public String getRemark() {
            return remark;
        }
    }
}
