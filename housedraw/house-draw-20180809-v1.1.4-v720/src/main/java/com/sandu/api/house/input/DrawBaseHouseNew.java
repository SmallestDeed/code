package com.sandu.api.house.input;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 * 户型绘制保存
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/21
 */
@Data
public class DrawBaseHouseNew implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("户型id")
    private Long houseId;
    
    /** base_house ==> id */
    private Long baseHouseId;

    @ApiModelProperty("户型名称")
    @NotNull(message = "户型名称不能为空")
    private String houseName;

    @ApiModelProperty("小区id")
    @NotNull(message = "小区不能为空")
    private Long livingId;
    
    @ApiModelProperty("小区名")
    @NotNull(message = "小区名不能为空")
    private String livingName;

    @ApiModelProperty("户型总面积")
    @NotNull(message = "户型总面积不能为空")
    private String totalArea;

//    @ApiModelProperty("地理区域id")
//    @NotNull(message = "地理位置不能为空")
//    private Long areaId;

    @ApiModelProperty("一级区域编码")
    private String firstAreaCode;

    @ApiModelProperty("二级区域编码")
    private String secondAreaCode;

    @ApiModelProperty("三级区域编码")
    private String thirdAreaCode;

    @ApiModelProperty("户型下的文件 此处为还原文件")
    private List<DrawFileDataNew> datas;

    @ApiModelProperty("户型下的空间")
    private List<DrawSpaceCommonDataNew> spaceCommon;
    
    /**
     * 1：通过绘制工具绘制的户型
     * 2：临摹老数据的户型
     */
    private Integer drawType;
}
