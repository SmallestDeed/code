package com.sandu.api.house.bo;

import com.sandu.api.house.input.DrawFileDataNew;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description:
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2018/1/3
 */
@Data
public class DrawBaseHouseDetailBO {

    @ApiModelProperty("户型id")
    private Long houseId;

    @ApiModelProperty("户型名称")
    @NotNull(message = "户型名称不能为空")
    private String houseName;

    @ApiModelProperty("小区id")
    @NotNull(message = "小区不能为空")
    private Long livingId;

    @ApiModelProperty("户型总面积")
    @NotNull(message = "户型总面积不能为空")
    private String totalArea;

    @ApiModelProperty("地理区域id")
    @NotNull(message = "地理位置不能为空")
    private Long areaId;

    @ApiModelProperty("一级地理区域")
    private String firstAreaCode;

    @ApiModelProperty("二级地理区域")
    private String secondAreaCode;

    @ApiModelProperty("三级地理区域")
    private String thridAreaCode;

    @ApiModelProperty("户型下的文件 此处为还原文件")
    private List<DrawFileDataNew> datas;

    @ApiModelProperty("户型下的空间")
    private List<DrawSpaceCommonBO> spaceCommon;
}
