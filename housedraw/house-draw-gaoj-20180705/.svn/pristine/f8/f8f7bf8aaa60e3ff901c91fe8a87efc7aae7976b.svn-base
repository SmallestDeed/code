package com.sandu.api.house.input;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandu.api.house.model.DrawBaseHouse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 户型绘制,保存数据DTO
 *
 * @author huangsongbo
 */
@Data
public class DrawBaseHouseSubmitDTO implements Serializable {

    private static final long serialVersionUID = 2484409656321802400L;

    @ApiModelProperty("绘制户型id")
    private Integer houseId;

    @ApiModelProperty("户型绘制生成的空间数据")
    private List<DrawSpaceCommonDTO> spaceCommonDTOList;

    /**
     * 数据类型 </p>
     * 0、平台数据
     * 1、个人数据
     */
    @JsonIgnore
    private int dataType;

    @JsonIgnore
    private DrawBaseHouse drawHouse;
}