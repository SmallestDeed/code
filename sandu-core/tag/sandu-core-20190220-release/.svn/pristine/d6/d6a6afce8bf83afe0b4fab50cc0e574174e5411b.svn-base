package com.sandu.api.designplan.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yanghz
 * @ClassName: RenderPicInfo
 * @Description:渲染图路径
 * @date 2017年3月23日 下午2:25:45
 */
@Data
public class RenderPicInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 图片地址
     */
    private String picPath;
    /**
     * 渲染的类型
     */
    private Integer renderingType;
    /**
     * 原图id
     */
    private Integer originalPicId;

    /**
     * 二维码图片路径
     */
    private String qrCodePath;

    //数据详情地址
    private String dateDetailUrl;

    public RenderPicInfo() {

    }

    public RenderPicInfo(String picPath, Integer renderingType, Integer originalPicId, String qrCodePath) {
        super();
        this.qrCodePath = qrCodePath;
        this.picPath = picPath;
        this.renderingType = renderingType;
        this.originalPicId = originalPicId;
    }

    @Override
    public String toString() {
        return "RenderPicInfo{" +
                "picPath='" + picPath + '\'' +
                ", renderingType=" + renderingType +
                ", originalPicId=" + originalPicId +
                ", qrCodePath='" + qrCodePath + '\'' +
                ", dateDetailUrl='" + dateDetailUrl + '\'' +
                '}';
    }
}
