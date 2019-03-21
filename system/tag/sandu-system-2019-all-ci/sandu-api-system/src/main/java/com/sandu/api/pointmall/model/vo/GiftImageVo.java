package com.sandu.api.pointmall.model.vo;

import com.sandu.api.base.model.vo.BaseVo;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "礼品图片对象", description = "礼品图片对象")
public class GiftImageVo extends BaseVo<GiftImageVo> {
    private static final long serialVersionUID = 201808010901018L;
    private int gift_id;
    private String filename;

    /**
     * 获取礼品Id
     * @return
     */
    public int getGift_id() {
        return gift_id;
    }

    /**
     * 设置礼品Id
     * @param gift_id
     */
    public void setGift_id(int gift_id) {
        this.gift_id = gift_id;
    }

    /**
     * 获取图片名称
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     * 设置图片名称
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }



}
