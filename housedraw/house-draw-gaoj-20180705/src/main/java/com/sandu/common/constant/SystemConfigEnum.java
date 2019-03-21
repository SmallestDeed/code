package com.sandu.common.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * Description: 系统常量属性类
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2018/1/6
 */
public enum SystemConfigEnum {

	// TODO 本地模拟
	UPLOAD_DOMAIN("upload.domain"),

	/** 资源文件地址前缀 */
	UPLOAD_ROOT_DIR("upload.root.dir"),

	/** 户型图片上传路径 */
	HOUSE_PIC_UPLOAD("house.pic.upload.path"),
	
	/** 户型截图 */
	HOUSE_SNAP_PIC_UPLOAD("house.pic.upload.path"),

	/** 户型还原文件上传路径 */
	/*HOUSE_FILE_UPLOAD("house.file.restore.upload.path"),*/
	HOUSE_FILE_UPLOAD("draw.baseHouse.file.restore.upload.path"),
	
	/** 空间图片 */
	SPACE_PIC_UPLOAD("draw.spaceCommon.pic.upload.path"),

	/** 空间文件 */
	/*SPACE_FILE_UPLOAD("space.file.upload.path"),*/
	SPACE_FILE_UPLOAD("draw.spaceCommon.file.restore.upload.path"),

	/** 空间模型（灯光模型） */
	/*SPACE_MODEL_UPLOAD("space.model.upload.path"),*/
	SPACE_MODEL_UPLOAD("draw.spaceCommon.model.restore.upload.path"),

	/** 产品文件 */
	/*PRODUCT_FILE_UPLOAD("product.file.upload.path"),*/
	PRODUCT_FILE_UPLOAD("draw.baseProduct.file.restore.upload.path"),

	/** 产品模型 */
	/*PRODUCT_MODEL_UPLOAD("product.model.upload.path"),*/
	PRODUCT_MODEL_UPLOAD("draw.baseProduct.model.restore.upload.path"),

	/** 样板房文件 */
	/*DESIGN_TEMPLET_FILE_UPLOAD("design.templet.file.upload.path"),*/
	DESIGN_TEMPLET_FILE_UPLOAD("draw.designTemplet.file.restore.upload.path"),

	/** 样板房模型文件 */
	/*DESIGN_TEMPLET_MODEL_UPLOAD("design.templet.model.upload.path"),*/
	DESIGN_TEMPLET_MODEL_UPLOAD("draw.designTemplet.model.restore.upload.path"),

	DESIGN_TEMPLET_PIC("raw.designTemplet.pic.restore.upload.path"),
	
	/** 白天灯光模型上传路径 */
	SPACE_MODEL_DAYLIGHT_UPLOAD("home.spaceCommon.u3dmodel.daylight.upload.path"),
	
	/** 黄昏灯光模型上传路径 */
	SPACE_MODEL_DUSKLIGHT_UPLOAD("home.spaceCommon.u3dmodel.dusklight.upload.path"),
	
	/** 夜晚灯光模型上传路径 */
	SPACE_MODEL_NIGHTLIGHT_UPLOAD("home.spaceCommon.u3dmodel.nightlight.upload.path"),
	
	/**
	 * 产品模型文件
	 */
	BASEPRODUCT_U3DMODEL_WINDOWSPC_UPLOAD("product.baseProduct.u3dmodel.windowsPc.upload.path"),
	
	/**
	 * 样板房模型文件
	 */
	DESIGNTEMPLET_U3DMODEL_WINDOWSPC_UPLOAD("design.designTemplet.u3dmodel.windowsPc.upload.path"),
	
	/**
	 * 样板房配置文件
	 */
	DESIGNTEMPLET_CONFIGFILE_UPLOAD("design.designTemplet.configFile.upload.path"),

	/**
	 * 量房文件存储路径
	 */
	DRAW_VOLUME_ROOM_FILE_UPLOAD("draw.volume.room.file.upload.path"),
	
	/**
	 * 文件加密密匙
	 */
	AES_RESOURCES_ENCRYPT_KEY("aes.resources.encrypt.key"),
	
	DESIGN_TEMPLET_PIC_UPLOAD("draw.designTemplet.pic.upload.path");

	private String key;

	SystemConfigEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	/**
	 * 获取对应fileKey
	 * 
	 * @author huangsongbo
	 * @return
	 */
	public String getFileKey() {
		String key = this.getKey();
		if(StringUtils.isEmpty(key)){
			return "";
		}else{
			return key.replace(".upload.path", "");
		}
	}
	
}
