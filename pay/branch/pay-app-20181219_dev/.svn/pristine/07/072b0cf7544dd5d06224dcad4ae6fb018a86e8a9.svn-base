package com.sandu.web.task.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: RenderPicInfo 
 * @Description:渲染图路径
 * @author yanghz
 * @date 2017年3月23日 下午2:25:45
 */
public class RenderPicInfo implements Serializable{
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
	 *  二维码图片路径
	 */
	private String qrCodePath;
	
	
	public String getQrCodePath() {
		return qrCodePath;
	}
	public void setQrCodePath(String qrCodePath) {
		this.qrCodePath = qrCodePath;
	}
	public Integer getOriginalPicId() {
		return originalPicId;
	}
	public void setOriginalPicId(Integer originalPicId) {
		this.originalPicId = originalPicId;
	}
	public RenderPicInfo(String picPath, Integer renderingType, Integer originalPicId,String qrCodePath) {
		super();
		this.qrCodePath = qrCodePath;
		this.picPath = picPath;
		this.renderingType = renderingType;
		this.originalPicId = originalPicId;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public Integer getRenderingType() {
		return renderingType;
	}
	public void setRenderingType(Integer renderingType) {
		this.renderingType = renderingType;
	}
	@Override
	public String toString() {
		return "RenderPicInfo [picPath=" + picPath + ", renderingType=" + renderingType + ", originalPicId="
				+ originalPicId + "]";
	}
	
}
