package com.sandu.api.resmodel.output;

import com.sandu.api.resmodel.model.bo.ModelAreaBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author bvvy
 * @date 2018/4/9
 */
@Data
@Builder
public class ResModelVO implements Serializable {

    /**
     * 模型id
     */
    @ApiModelProperty("模型id")
    private Long modelId;

    /**
     * 模型编码
     */
    @ApiModelProperty("模型编号")
    private String modelCode;

    /**
     * 模型名称
     */
    @ApiModelProperty("模型名称")
    private String modelName;

    /**
     * 模型文件名称
     */
    @ApiModelProperty("模型3d文件名")
    private String modelFileName;


    /**
     * 模型路径
     */
    @ApiModelProperty("模型文件地址")
    private String modelPath;

    /**
     * 模型描述
     */
    @ApiModelProperty("模型描述")
    private String modelDesc;


    /**
     * 创建者
     */
    @ApiModelProperty("模型作者")
    private String author;

    /** 模型创建日期*/
    @ApiModelProperty("模型创建日期")
    private Date createDate;


    /**
     * 修改时间
     */
    @ApiModelProperty("最后修改日期")
    private Date updateDate;


    /**
     * 长
     */
    @ApiModelProperty("模型长度")
    private Integer length;

    /**
     * 高
     */
    @ApiModelProperty("模型高度")
    private Integer height;

    /**
     * 宽
     */
    @ApiModelProperty("模型宽度")
    private Integer width;

    /**
     * 模型缩略图地址
     */
    @ApiModelProperty("模型缩略图地址")
    private String thumbPicPath;

    /**
     * 模型转化状态
     */
    @ApiModelProperty("模型转化状态")
    private String transStatus;

    /**
     * 模型型号
     */
    @ApiModelProperty("模型型号")
    private String modelModelNum;
    /**
     * 对应产品ID
     */
    @ApiModelProperty("对应产品ID")
    private String productName;
    /**
     * 分类Id
     */
    @ApiModelProperty("分类Id")
    private String categoryIds;
    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String categoryNames;

    /**
     * 对应产品ID
     */
    @ApiModelProperty("关联产品")
    private Integer concerProductId;
    /**
     * 对应产品的名称
     */
    @ApiModelProperty("关联产品")
    private String concerProductName;
    /**
     * 对应产品的编码
     */
    @ApiModelProperty("对应产品名称")
    private String concerProductCode;
    /**
     * 3du文件路径
     */
    @ApiModelProperty("3du文件路径")
    private String file3duPath;

    @ApiModelProperty("模型来源公司")
    private String modelOrigin;

    @ApiModelProperty("模型小类编码")
    private String smallType;

    @ApiModelProperty("模型大类编码")
    private String type;

    @ApiModelProperty("模型分类名称")
    private List<String> typeNames;

    @ApiModelProperty(value = "模型的贴图详情")
    private List<ModelAreaBO> modelTextureInfo;

    @ApiModelProperty(value = "模型材质数量")
    private Integer textureCount;

    @ApiModelProperty(value = "主模型标志  1:主模型,0:非主模型")
    private Integer mainModelFlag;

}
