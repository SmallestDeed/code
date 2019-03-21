package com.sandu.api.resmodel.model.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author bvvy
 * @date 2018/4/9
 */
@Data
public class ModelListBO implements Serializable {
    /**
     * 对应产品ID
     */
    @ApiModelProperty("队形产品id")
    private Integer concerProductId;

    /**
     * 对应产品的名称
     */
    @ApiModelProperty("对应产品名称")
    private String concerProductName;
    /**
     * 对应产品的编码
     */
    @ApiModelProperty("对应产品名称")
    private String concerProductCode;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 模型编码
     */
    @ApiModelProperty("编码")
    private String modelCode;

    /**
     * 模型名称
     */
    @ApiModelProperty("名称")
    private String modelName;

    /**
     * 缩略图的文件路径
     */
    @ApiModelProperty("缩略图地址")
    private String thumbPicPath;
    /**
     * 分类ids
     */
    @ApiModelProperty("类型ids")
    private String categoryIds;
    /**
     * 分类names
     */
    @ApiModelProperty("类型")
    private String categoryNames;

    /**
     * 转换状态 ING 转换中 SUCCESS 成功 FAIL 失败
     */
    @ApiModelProperty("转化状态")
    private String transStatus;

    /**
     * 修改时间
     */
    @ApiModelProperty("创建时间")
    private Date gmtModified;

    /**
     * 模型描述
     */
    @ApiModelProperty("模型描述")
    private String modelDesc;
    /**
     * 转换后的文件
     **/
    @ApiModelProperty("模型文件地址")
    private String modelPath;

    @ApiModelProperty("模型长")
    private Integer length;

    @ApiModelProperty("模型宽")
    private Integer width;

    @ApiModelProperty("模型高")
    private Integer height;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("模型来源")
    private String originId;

    @ApiModelProperty("模型来源公司的名称")
    private String modelOrigin;

    @ApiModelProperty("模型型号")
    private String modelModelNum;

    @ApiModelProperty("模型小类")
    private String smallTypeMark;

    @ApiModelProperty("模型大类")
    private String TypeMark;

    @ApiModelProperty("模型各区域贴图详情")
    private List<ModelAreaBO> modelTextureInfo;

    @ApiModelProperty(value = "模型材质数量")
    private Integer textureCount;

    @ApiModelProperty(value = "主模型标志  1:主模型,0:非主模型")
    private Integer mainModelFlag;
}
