package com.sandu.common.constant;

/**
 * CopyRight (c) 2017 Sandu Technology Inc.
 * <p>
 *
 * house-draw
 *
 * @author songjianming@sanduspace.cn
 *         <p>
 *         2018年1月17日
 */

public enum FileKey {


	DESIGIN_TEMPLET_U3DMODEL("design.designTemplet.u3dmodel."), 
	BASE_PRODUCT_U3DMODEL_WINDOWSPC("product.baseProduct.u3dmodel.windowsPc"),
	SPACE_COMMON_U3DMODEL("home.spaceCommon.u3dmodel."), 
	DESIGN_TEMPLET_CONFIG_FILE("design.designTemplet.configFile"),
	BASE_HOUSE_PIC("home.baseHouse.pic"),
	HOUSE_DRAW_FILE("house.draw.file"),
	HOUSE_DRAW_PIC("house.draw.pic"),
	// 空间户型图
	SPACE_COMMON_PIC("home.spaceCommon.pic"),
	// 灯光白天pc_u3d模型
	SPACECOMMON_U3DMODEL_DAYLIGHT("home.spaceCommon.u3dmodel.daylight"),
	// 灯光黄昏pc_u3d模型
	SPACECOMMON_U3DMODEL_DUSKLIGHT("home.spaceCommon.u3dmodel.dusklight"),
	// 灯光黑夜pc_u3d模型
	SPACECOMMON_U3DMODEL_NIGHTLIGHT("home.spaceCommon.u3dmodel.daylight"),
	// (样板房)空间布局图
	DESIGN_DESIGNTEMPLET_PIC("design.designTemplet.pic"),
	// (样板房)顶视图
	DESIGN_DESIGNTEMPLET_EFFECTPLAN("design.designTemplet.effectPlan"),
	// 样板模型文件
	DESIGN_DESIGNTEMPLET_U3DMODEL_WINDOWSPC("design.designTemplet.u3dmodel.windowsPc"),

	KECHUANG_LF_FILE("draw.kechuang.lf.file"),
	KECHUANG_ZIP_FILE("draw.kechuang.zip.file"),
	KECHUANG_PPNG_FILE("draw.kechuang.ppng.file");

	private String fileKey;
	private String remark;

	public String getFileKey() {
		return fileKey;
	}

	public String getRemark() {
		return remark;
	}

	FileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	FileKey(String fileKey, String remark) {
		this.remark = remark;
		this.fileKey = fileKey;
	}
}
