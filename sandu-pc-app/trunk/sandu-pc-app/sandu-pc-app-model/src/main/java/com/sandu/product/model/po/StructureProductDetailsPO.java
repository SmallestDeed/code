package com.sandu.product.model.po;

import java.util.Date;

import lombok.Data;

@Data
public class StructureProductDetailsPO {

	private Integer id;
	
	/**
	 * 结构id
	 */
	private Integer structureId;
	
	/**
	 * 关联产品的id
	 */
	private Integer productId;
	
	/**
	 * 排序
	 */
	private Integer sorting;
	
	/**
	 * 系统编码
	 */
	private String sysCode;
	
	/**
	 * 创建者
	 */
	private String creator;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 修改人
	 */
	private String modifier;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * 字符备用1
	 */
	private String att1;
	
	/**
	 * 字符备用2
	 */
	private String att2;
	
	/**
	 * 整数备用1
	 */
	private Integer numa1;
	
	/**
	 * 整数备用2
	 */
	private Integer numa2;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 相机位置
	 */
	private String cameraView;
	
	/**
	 * 产品远景位置
	 */
	private String cameraLook;

	/**
	 * 区域标识
	 * 尺寸代码
	 * 款式ID
	 * 描述(区域、尺寸代码)
	 * add by xiaoxc
	 */
	private String regionMark;
	
	private Integer styleId;
	
	private String measureCode;
	
	private String describeInfo;
	
}
