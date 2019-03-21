package com.sandu.api.resmodel.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author bvvy
 * @date 2018/4/9
 */
@Data
@Builder
public class ResModelUpdate implements Serializable {

    /** */
    @ApiModelProperty("id")
    @NotNull(message = "不能为空")
    @Min(value = 1, message = "id最小值 {min}")
    private Long modelId;

    /**
     * 模型名称
     */
    @ApiModelProperty(value = "模型名称", required = true)
    @NotEmpty(message = "模型名称不能为空")
    @Length(max = 50, message = "模型名称不能超过{max}个字")
    private String modelName;


    /**
     * 模型路径
     */
    @ApiModelProperty("模型地址")
    @Pattern(regexp = ".+(\\.)((?i)(assetbundle)|(3du))$", message = "请选择正确的文件格式")
    @Length(max = 200, message = "模型地址长度不能超过{max}")
    private String modelPath;

    /**
     * 模型描述
     */
    @ApiModelProperty("描述")
    @Length(max = 200, message = "描述长度不能超过{max}")
    @Builder.Default
    private String modelDesc = "";

    @ApiModelProperty(value = "模型型号")
    @Length(max = 30, message = "模型型号长度不能超过{max}")
    private String modelModelNum;

    @ApiModelProperty(value = "模型缩略图路径")
    @Length(max = 200, message = "模型缩略图路径长度不能超过{max}")
    private String thumbPicPath;

    @ApiModelProperty(value = "模型分类")
    @Length(max = 30, message = "模型分类长度不能超过{max}")
    private String categoryIds;

    @ApiModelProperty(value = "模型分类")
    @Length(max = 30, message = "模型分类长度不能超过{max}")
    private String categoryNames;

    @ApiModelProperty(value = "转化状态")
    @Length(max = 30, message = "转化状态长度不能超过{max}")
    private String transStatus;

    @ApiModelProperty(value = "企业ID")
    @NotNull(message = "企业ID不能为空")
    @Min(value = 0, message = "企业ID最小{min}")
    private Integer companyId;

    @ApiModelProperty(value = "被关联的产品ID", hidden = true)
    @Min(value = 1, message = "被关联的产品ID最小{min}")
    private Integer productId;

    @ApiModelProperty(value = "模型小类编码")
    private String smallType;

    @ApiModelProperty(value = "模型区域信息编码")
    private List<ModelAreaTextureRelAdd> modelAreaTextureRelAdd;

    @ApiModelProperty(value = "主模型标志 1:为主模型,0:副模型 ")
    private Integer mainModelFlag;

    @ApiModelProperty(value = "模型打包机IP", hidden = true)
    private String transMachineIp;
}
