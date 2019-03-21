package com.sandu.matadata.enums;

/**
 * 店铺封面资源类型
 *
 * @auth xiaoxc
 * @data 2018-06-23
 */
public enum ShopCoverResType {

    /***
     * 图片列表
     */
    PICLIST(1),

    /***
     * 全景图
     */
    PANORAMA(2),

    /***
     * 视频
     */
    VIDEO(3);
    private int value = 0;

    ShopCoverResType(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
