package com.sandu.api.house.output;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 户型输出类
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/21
 */
@Data
@ApiModel
public class DrawBaseHouseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("户型id")
	private Long houseId;

	@ApiModelProperty("户型编码")
	private String houseCode;

	@ApiModelProperty("户型名称")
	private String houseName;

	@ApiModelProperty("小区详细地址")
	private String detailAddress;

	@ApiModelProperty("小区名字")
	private String livingName;

	@ApiModelProperty("小区Id")
	private Long livingId;

	@ApiModelProperty("最后修改时间")
	private Date lastUpdateTime;

	@ApiModelProperty("户型图片")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String housePic;

	@ApiModelProperty("还原文件")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	private String restoreFile;

	@ApiModelProperty("户型下的空间")
	private List<DrawSpaceCommonDataVO> spaceCommon;

	@ApiModelProperty("用户Id")
	private Long userId;

	@ApiModelProperty("用于用户是否绘制过改户型") // 0 表示没有  1 表示有
	private Integer checkOwnHouse;

	@ApiModelProperty("正式表中判断户型是否公开")
	private Integer IsPublic;

	@ApiModelProperty("户型面积")
	private String totalArea;

	@ApiModelProperty("户型简称")
	private String houseShortName;

	@ApiModelProperty("draw_base_house表中的id")
	private Long relDrawId;

	@ApiModelProperty("户型处理状态")
	private String dealStatus;
	
	@ApiModelProperty("是否标准")
	private Integer isStandard;

	@ApiModelProperty("快照截图")
	private String snapshotPic;
	
	private String userName;
	private String userMobile;
	
	private String houseStatus;
	
	private String rejectReason;

	private Integer platformType;
	
}
