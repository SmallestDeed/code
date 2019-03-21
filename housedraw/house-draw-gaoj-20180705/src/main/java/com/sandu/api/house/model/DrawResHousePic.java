package com.sandu.api.house.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Description: 户型图片实体
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/28
 */
@Data
public class DrawResHousePic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	/** 图片编码 **/
	private String picCode;
	/** 图片名称 **/
	private String picName;
	/** 图片文件名称 **/
	private String picFileName;
	/** 图片类型 **/
	private String picType;
	/** 图片大小 **/
	private String picSize;
	/** 图片长 **/
	private String picWeight;
	/** 图片宽 **/
	private String picHigh;
	/** 图片后缀 **/
	private String picSuffix;
	/** 图片等级 **/
	private String picLevel;
	/** 图片格式 **/
	private String picFormat;
	/** 图片路径 **/
	private String picPath;
	/** 图片描述 **/
	private String picDesc;
	/** 图片排序 **/
	private String picOrdering;
	/** 无 **/
	private String fileKey;
	/** 无 **/
	private String fileKeys;
	/** 主键 **/
	private String businessIds;
	/** 主键 **/
	private Integer businessId;
	/** 无 **/
	private String smallPicInfo;
	/** 无 **/
	private Integer viewPoint;
	/** 无 **/
	private Integer scene;
	/** 无 **/
	private Integer sequence;
	/** 系统编码 **/
	private String sysCode;
	/** 创建者 **/
	private String creator;
	/** 创建时间 **/
	private Date gmtCreate;
	/** 修改人 **/
	private String modifier;
	/** 修改时间 **/
	private Date gmtModified;
	/** 是否删除 **/
	private Integer isDeleted;
	/** 字符备用1 **/
	private String att1;
	/** 字符备用2 **/
	private String att2;
	/** 整数备用1 **/
	private Integer numa1;
	/** 整数备用2 **/
	private Integer numa2;
	/** 物理文件是否存在，0为不存在，1为存在 **/
	private Double numAtt3;
	/** 归档状态 **/
	private Double numAtt4;
	/** 备注 **/
	private String remark;

	private String resIds;

	private String thumbnailPath;

	private String largeThumbnailPath;
}
