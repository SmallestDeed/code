package com.nork.onekeydesign.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 设计方案水刀数据传输bean
 * 
 * @author huangsongbo
 * @date 2018.6.5
 *
 */
public class WaterJetInfoDTO implements Serializable{

	private static final long serialVersionUID = -1541278433985292485L;

	/**
	 * 水刀模版id
	 */
	private Integer waterjetTemplateId;
	
	/**
	 * 位置坐标X
	 */
	private Double posX;
	
	/**
	 * 位置坐标Y
	 */
	private Double posY;
	
	/**
	 * 位置坐标Z
	 */
	private Double posZ;
	
	/**
	 * 三维角度值X
	 */
	private Double rotX;
	
	/**
	 * 三维角度值Y
	 */
	private Double rotY;
	
	/**
	 * 三维角度值Z
	 */
	private Double rotZ;
	
	/**
	 * 拉伸比例X
	 */
	private Double scaleX;
	
	/**
	 * 拉伸比例Y
	 */
	private Double scaleY;
	
	/**
	 * 拉伸比例Z
	 */
	private Double scaleZ;
	
	/**
	 * 水刀模版材质信息
	 */
	List<WaterJetTextureInfoDTO> waterJetTextureInfoList;

	public Integer getWaterjetTemplateId() {
		return waterjetTemplateId;
	}

	public void setWaterjetTemplateId(Integer waterjetTemplateId) {
		this.waterjetTemplateId = waterjetTemplateId;
	}

	public Double getPosX() {
		return posX;
	}

	public void setPosX(Double posX) {
		this.posX = posX;
	}

	public Double getPosY() {
		return posY;
	}

	public void setPosY(Double posY) {
		this.posY = posY;
	}

	public Double getPosZ() {
		return posZ;
	}

	public void setPosZ(Double posZ) {
		this.posZ = posZ;
	}

	public Double getRotX() {
		return rotX;
	}

	public void setRotX(Double rotX) {
		this.rotX = rotX;
	}

	public Double getRotY() {
		return rotY;
	}

	public void setRotY(Double rotY) {
		this.rotY = rotY;
	}

	public Double getRotZ() {
		return rotZ;
	}

	public void setRotZ(Double rotZ) {
		this.rotZ = rotZ;
	}

	public Double getScaleX() {
		return scaleX;
	}

	public void setScaleX(Double scaleX) {
		this.scaleX = scaleX;
	}

	public Double getScaleY() {
		return scaleY;
	}

	public void setScaleY(Double scaleY) {
		this.scaleY = scaleY;
	}

	public Double getScaleZ() {
		return scaleZ;
	}

	public void setScaleZ(Double scaleZ) {
		this.scaleZ = scaleZ;
	}

	public List<WaterJetTextureInfoDTO> getWaterJetTextureInfoList() {
		return waterJetTextureInfoList;
	}

	public void setWaterJetTextureInfoList(List<WaterJetTextureInfoDTO> waterJetTextureInfoList) {
		this.waterJetTextureInfoList = waterJetTextureInfoList;
	}
	
}
