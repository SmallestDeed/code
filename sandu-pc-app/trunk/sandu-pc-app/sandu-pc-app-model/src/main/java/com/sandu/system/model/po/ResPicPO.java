package com.sandu.system.model.po;

import java.io.Serializable;
import java.util.Date;

import com.sandu.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResPicPO extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1804938683799648196L;

	/**
	 * 图片编码
	 */
	private String picCode;
	
	/**
	 * 图片名称
	 */
	private String picName;
	
	/**
	 * 图片文件名称
	 */
	private String picFileName;
	
	/**
	 * 图片类型
	 */
	private String picType;
	
	/**
	 * 图片大小
	 */
	private Integer picSize;
	
	/**
	 * 图片长
	 */
	private String picWeight;
	
	/**
	 * 图片高
	 */
	private String picHigh;
	
	/**
	 * 图片后缀
	 */
	private String picSuffix;
	
	/**
	 * 图片级别
	 */
	private String picLevel;
	
	/**
	 * 图片格式
	 */
	private String picFormat;
	
	/**
	 * 图片路径
	 */
	private String picPath;
	
	/**
	 * 图片描述
	 */
	private String picDesc;
	
	/**
	 * 图片排序
	 */
	private String picOrdering;

	private String fileKey;
	
	/**
	 * key标识（多个）
	 */
	private String fileKeys;
	
	/**
	 * 标识IDs
	 */
	private String businessIds;
	
	/**
	 * 图片对应的缩略图id信息:small_pic_info
	 */
	private String smallPicInfo;
	
	/**
	 * 渲染图视角
	 */
	private Integer viewPoint;
	
	/**
	 * 渲染图场景
	 */
	private Integer scene;
	
	/**
	 * 时间备用1
	 */
	private Date dateAtt1;
	
	/**
	 * 时间备用2
	 */
	private Date dateAtt2;
	
	private Integer businessId;
	
	/**
	 * 整数备用2
	 */
	private Integer numAtt2;
	
	/**
	 * 数字备用1
	 */
	private Double numAtt3;
	
	/**
	 * 归档标志
	 */
	private Double numAtt4;

	/**
	 * 记录图片文件排序序号
	 */
	private Integer sequence;
		
	/**
	 * 渲染图片类型
	 */
	private String renderingType;
	
	/**
	 * 全景路径
	 */
	private String panoPath;
	
	private Integer sysTaskPicId;
	
}
