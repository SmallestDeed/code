package com.sandu.api.basewaterjet.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * base_waterjet
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-11-09 10:03
 */
@Data
public class BasewaterjetUpdate implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer basewaterjetId;

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

    @ApiModelProperty(value = "水刀模版形状")
    private Integer templateShapeValue;

    @ApiModelProperty(value = "CAD源文件")
    @NotNull(message = "水刀CAD文件不能为空")
    private Integer cadSourceFileId;

    @ApiModelProperty(value = "水刀模版描述")
    private String templateDescribe;
    
}
