package com.sandu.api.house.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sandu.util.Utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/28
 */
@Data
public class DrawBaseHouseBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("户型id")
	private Long id;

	@ApiModelProperty("户型编码")
	private String houseCode;

	@ApiModelProperty("户型名称")
	private String houseName;

	@ApiModelProperty("小区名字")
	private String livingName;
	
	private String detailAddress;

	@ApiModelProperty("最后修改时间")
	@JsonFormat(pattern = Utils.YYYY_MM_DD_HH_MM_SS)
	private Date gmtModified;

	@ApiModelProperty("还原文件id")
	private Long restoreFileId;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	@ApiModelProperty("还原文件路径")
	private String filePath;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	@ApiModelProperty("户型图片地址")
	private String picPath;

	@ApiModelProperty("户型图片id")
	private Long baseHousePicId;

	@ApiModelProperty("小区地理位置编码")
	private String areaLongCode;
	
	@ApiModelProperty("空间列表")
	private List<DrawSpaceCommonBO> drawSpaceCommonBOList;

	@ApiModelProperty("用户Id")
	private Long userId;

	@ApiModelProperty("用于用户是否绘制过改户型") // 0 表示没有  1 表示有
	private Integer checkOwnHouse;

	@ApiModelProperty("正式表中判断户型是否公开")
	private Integer isPublic;

	@ApiModelProperty("户型面积")
	private String totalArea;

	@ApiModelProperty("户型简称")
	private String houseShortName;

	@ApiModelProperty("小区对应的区areaCode")
	private String areaId;

	@ApiModelProperty("小区Id")
	private  Long livingId;

	@ApiModelProperty("户型处理状态")
	private String dealStatus;

	@ApiModelProperty("是否标准")
	private Integer isStandard;

	/**
	 * 户型状态:保存未提交的户型
	 * </p>
	 * 草稿(1), 待审核(2),审核驳回(3)
	 * </p>
	 * 待烘焙(4), 烘焙中(5), 烘焙成功(6),
	 * </p>
	 * 烘焙失败(7)、完善户型(8)、完善中(9)
	 */
	private String houseStatus;

	@JsonInclude(JsonInclude.Include.ALWAYS)
	@ApiModelProperty("快照截图")
	private String snapshotPic;
	
	private String userName;
	private String userMobile;
	
	/**
	 * 截图ID
	 */
	private Long snapPicId;
	
	/**
	 * 审核驳回原因
	 */
	private String rejectReason;

	/**
	 * 平台类型
	 * {@see com.sandu.common.constant.kechuang.PlatformType}
	 */
	private Integer platformType;

	private Long houseId;

	private Long picRes1Id;
}
