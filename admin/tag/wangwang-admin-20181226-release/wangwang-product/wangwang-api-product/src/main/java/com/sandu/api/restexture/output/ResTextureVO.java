package com.sandu.api.restexture.output;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sandu
 */
@Data
@Builder
public class ResTextureVO implements Serializable {

    private Long textureId;
    /**
     * 材质编号
     */
    @ApiModelProperty("贴图编号")
    private String textureCode;

    /**
     * 贴图名称
     */
    @ApiModelProperty("贴图名称")
    private String textureName;


    /**
     * 图片宽
     */
    @ApiModelProperty("长度")
    private String fileLength;

    /**
     * 图片高
     */
    @ApiModelProperty("宽度")
    private String fileWidth;

    /**
     * 材质描述
     */
    @ApiModelProperty("备注")
    private String fileDesc;

    /**
     * 材质(材料)
     */
    @ApiModelProperty("材质(材料)")
    private String texture;

    @ApiModelProperty("材质(材料)名称")
    private String textureLabel;

    /**
     * 材质属性value
     */
    @ApiModelProperty("材质属性")
    private Integer textureAttrValue;

    @ApiModelProperty("材质属性名称")
    private String textureAttrValueName;

    /**
     * 材质缩略图
     */
    @ApiModelProperty("缩略图文件id")
    private Integer thumbnailId;

    @ApiModelProperty("缩略图地址")
    private String thumbnailPath;

    /**
     * 法线贴图
     */
    @ApiModelProperty("法线贴图文件id")
    private Integer normalPicId;

    @ApiModelProperty("法线贴图地址")
    private String normalPicPath;

    /**
     * 法线参数
     */
    @ApiModelProperty("法线参数")
    private String normalParam;

    /**
     * 材质球文件Id
     */
    @ApiModelProperty("材质球文件id, 大于0为材质球,等于0为贴图")
    private Integer textureballFileId;

    @ApiModelProperty("材质球文件地址")
    private String textureBallPath;

    /**
     * 品牌id
     */
    @ApiModelProperty("品牌id")
    private Integer brandId;


    @ApiModelProperty("品牌名称")
    private String brandName;
    /**
     * 修改时间
     */
    @ApiModelProperty("最后更新时间")
    private Date gmtModified;

    @ApiModelProperty("remark")
    private String remark;

    @ApiModelProperty("材质球转换状态")
    private String transStatus;

    @ApiModelProperty("材质球文件名称")
    private String ballName;

    @ApiModelProperty("材质型号")
    private String modelNumber;
}
