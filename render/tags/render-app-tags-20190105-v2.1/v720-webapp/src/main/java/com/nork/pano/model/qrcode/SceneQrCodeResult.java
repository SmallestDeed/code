package com.nork.pano.model.qrcode;

import java.io.Serializable;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/10/27 0027 10:50
 * @Modified By
 */
public class SceneQrCodeResult implements Serializable {
    private static final long serialVersionUID = -3658176086638921286L;

    /** 标题 **/
    private String title;

    /** 描述文案 */
    private String text;

    /**图片输入流*/
    private String imgBase64;

    /** 720场景访问地址**/
    private String qrUrl;


    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }
}
