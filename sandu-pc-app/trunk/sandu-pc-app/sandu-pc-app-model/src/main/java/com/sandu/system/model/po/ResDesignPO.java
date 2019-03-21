package com.sandu.system.model.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ResDesignPO implements Serializable{

	private static final long serialVersionUID = 809887458261881921L;

	Integer  id;
	
	/**
	 * 原始表id
	 */
	Integer oldId;
	
	/**
	 * 文件编码
	 */
	String fileCode;
	
	/**
	 * 文件名称
	 */
	String fileName;
	
	/**
	 * 文件原名称
	 */
	String fileOriginalName;
	
	/**
	 * 文件类别
	 */
	String fileType;
	
	/**
	 * 文件大小
	 */
	String fileSize;
	
	/**
	 * 图片长、模型宽
	 */
	String fileWeight;
	
	/**
	 * 图片高、模型高
	 */
	String fileHigh;
	
	/**
	 * 长（模型）
	 */
	Integer fileLength;
	
	/**
	 * 文件后缀
	 */
	String fileSuffix;
	
	/**
	 * 文件格式（图片）
	 */
	String fileFormat;
	
	/**
	 * 文件级别
	 */
	String fileLevel;
	
	/**
	 * 文件路径
	 */
	String filePath;
	
	/**
	 * 文件描述
	 */
	String fileDesc;
	
	/**
	 * 文件排序
	 */
	Integer fileOrdering;
	
	/**
	 * 系统编码
	 */
	String sysCode;
	
	/**
	 * 创建者
	 */
	String creator;
	
	/**
	 * 创建时间
	 */
	Date gmtCreate;
	
	/**
	 * 修改人
	 */
	String modifier;
	
	/**
	 * 修改时间
	 */
	Date gmtModified;
	
	/**
	 * 是否删除
	 */
	Integer isDeleted;
	
	String fileKey;
	
	/**
	 * 业务id
	 */
	Integer businessId;
	
	/**
	 * 缩略图（图片）
	 */
	String smallPicInfo;
	
	/**
	 * 观察点（渲染图）
	 */
	Integer viewPoint;
	
	/**
	 * 场景（渲染图）
	 */
	Integer scene;
	
	/**
	 * 顺序（图片）
	 */
	Integer sequence;
	
	/**
	 * 渲染类型（渲染图）
	 */
	Integer renderingType;
	
	/**
	 * 720度渲染图路径（渲染图）
	 */
	String panoPath;
	
	/**
	 * 最小高度（模型）
	 */
	Integer minHeight;
	
	/**
	 * 是否共享（模型）
	 */
	Integer isModelShare;
	
	String att1;
	
	String att2;
	
	String att3;
	
	String att4;
	
	String att5;
	
	String att6;
	
	Integer numa1;
	
	Integer numa2;
	
	Integer numa3;
	
	Integer numa4;
	
	Integer numa5;
	
	Integer numa6;
	
	/*备注*/
	String remark;
	
	String source;
	
}
