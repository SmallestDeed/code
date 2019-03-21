package com.sandu.house.model.output;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseHouseVo implements Serializable {

    @ApiModelProperty(value = "ID", dataType = "Integer", hidden = true)
    private Integer id;
    @ApiModelProperty(value = "户型ID", dataType = "Integer")
    private Integer livingId;
    @ApiModelProperty(value = "户型名称", dataType = "String")
    private String houseCommonCode;
    @ApiModelProperty(value = "户型面积", dataType = "String")
    private String totalArea;
    @ApiModelProperty(value = "户型图ID(户型绘制工具产生的户型图，snap_pic_id)", dataType = "Integer")
    private Integer housePicId;
    @ApiModelProperty(value = "户型图ID(老的户型图，res_pic1_id)", dataType = "Integer")
    private Integer oldHousePicId;
    @ApiModelProperty(value = "户型图(户型绘制工具产生的户型图)", dataType = "String")
    private String picPath;
    @ApiModelProperty(value = "各房型空间数量", dataType = "String")
    private String houseSpaceNumStr;
    @ApiModelProperty(value = "户型所属区域长编码", dataType = "String", hidden = true)
    private String areaLongCode;
    @ApiModelProperty(value="户型所在省",dataType = "String")
    private String province;
    @ApiModelProperty(value="户型所在市",dataType = "String")
    private String city;
    @ApiModelProperty(value ="户型所在区域",dataType = "String")
    private String district;
    /**
     * 户型最后使用时间
     * 取户型下最后一个效果图方案的创建时间
     */
    @ApiModelProperty(value="最后使用时间", dataType = "String")
    private String lastUseTime;
    @ApiModelProperty(value="户型所在小区",dataType = "String")
    private String livingName;
}
