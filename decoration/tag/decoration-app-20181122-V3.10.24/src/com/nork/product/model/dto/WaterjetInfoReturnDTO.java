package com.nork.product.model.dto;

import java.io.Serializable;
import java.util.List;

import com.nork.product.model.SplitTextureDTO;

/**
 * 进入设计方案,返回的水刀配置信息bean
 * 
 * @author huangsongbo
 * @date 2018.6.8
 */
public class WaterjetInfoReturnDTO implements Serializable {

	private static final long serialVersionUID = -90605773803581230L;

	/**
	 * 水刀模版id
	 */
	private Integer waterjetTemplateId;
	
	/**
	 * 水刀模板文件url
	 */
	private String waterjetTemplateFileUrl;

	/**
	 * 水刀模板CAD文件URL
	 *
	 * */
	private String waterCadPath;
	
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
	 * 水刀材质信息
	 */
	List<SplitTextureDTO> splitTexturesChoose;

	public String getWaterCadPath() {
		return waterCadPath;
	}

	public void setWaterCadPath(String waterCadPath) {
		this.waterCadPath = waterCadPath;
	}

	public String getWaterjetTemplateFileUrl() {
		return waterjetTemplateFileUrl;
	}

	public void setWaterjetTemplateFileUrl(String waterjetTemplateFileUrl) {
		this.waterjetTemplateFileUrl = waterjetTemplateFileUrl;
	}

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

	public List<SplitTextureDTO> getSplitTexturesChoose() {
		return splitTexturesChoose;
	}

	public void setSplitTexturesChoose(List<SplitTextureDTO> splitTexturesChoose) {
		this.splitTexturesChoose = splitTexturesChoose;
	}
	
}
