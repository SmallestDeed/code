package com.sandu.api.basewaterjet.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * base_waterjet
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-09 10:03
 */
@Data
public class BasewaterjetVO implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer basewaterjetId;

    @ApiModelProperty(value = "水刀模版编码")
    private String templateCode;
        
    @ApiModelProperty(value = "水刀模版名称")
    private String templateName;
        
    @ApiModelProperty(value = "水刀模版品牌")
    private Integer brandId;
        
    @ApiModelProperty(value = "水刀模版默认长度(cm)")
    private Integer templateLength;
        
    @ApiModelProperty(value = "水刀模版默认宽度(cm)")
    private Integer templateWidth;

    @ApiModelProperty(value = "水刀模版模型文件")
    private Integer templateFileId;
        
    @ApiModelProperty(value = "水刀模版展示图片")
    private Integer templatePicId;
        
    @ApiModelProperty(value = "水刀模版形状value(关联数据字典value)")
    private Integer templateShapeValue;

    @ApiModelProperty(value = "水刀模版描述")
    private String templateDescribe;

    @ApiModelProperty(value = "水刀模版状态（0：未上架、1：已上架、2：已下架）")
    private Integer templateStatus;

    @ApiModelProperty(value = "CAD源文件")
    private Integer cadSourceFileId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "图片路径")
    private String picPath;

    @ApiModelProperty(value = "形状名称")
    private String shapeName;

    @ApiModelProperty(value = "文件名称")
    private String fileName;
}
