package com.sandu.api.resmodel.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author  bvvy
 * @date 2018/4/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResModelAdd implements Serializable {

    /**
     * 模型名称
     */
    @ApiModelProperty(value = "模型名称", required = true)
    @NotEmpty(message = "模型名称不能为空")
    @Length(max = 100, message = "模型名称不能超过{max}个字")
    private String modelName;

    /**
     * 模型路径
     */
    @ApiModelProperty(value = "模型路径")
    @Pattern(regexp = ".+(\\.)((?i)(assetbundle)|(3du))$",message = "请选择正确的文件格式")
    @Length(max = 200, message = "模型路径长度不能超过{max}")
    private String modelPath;

    /**
     * 模型描述
     */
    @ApiModelProperty("备注")
    @Length(max = 200, message = "长度不能超过{max}")
    private String modelDesc;

    @ApiModelProperty(value = "模型缩略图路径")
    @Length(max = 200, message = "模型缩略图路径长度不能超过{max}")
    private String thumbPicPath;

    @ApiModelProperty(value = "模型分类")
    @Length(max = 30, message = "模型分类长度不能超过{max}")
    private String categoryIds;

    @ApiModelProperty(value = "模型分类")
    @Length(max = 30, message = "模型分类长度不能超过{max}")
    private String categoryNames;

    @ApiModelProperty(value = "模型型号")
    @Length(max = 30, message = "模型型号长度不能超过{max}")
    private String modelModelNum;

    @ApiModelProperty(value = "模型转化状态")
    @Length(max = 30, message = "模型转化状态长度不能超过{max}")
    private String transStatus;

    @ApiModelProperty(value = "企业ID")
    @NotNull(message = "企业ID不能为空")
    private Integer companyId;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "用户id不合法")
    private Integer userId;

    @ApiModelProperty("长")
    @Range(min = 0,max = 100000,message = "长度 最长 {max} 最短{min}")
    private Integer length;

    @ApiModelProperty("宽")
    @Range(min = 0,max = 100000,message = "宽度 最长 {max} 最短{min}")
    private Integer width;

    @ApiModelProperty("高")
    @Range(min = 0,max = 100000,message = "高度 最长 {max} 最短{min}")
    private Integer height;

    @ApiModelProperty(hidden = true)
    private Long originId;

    @ApiModelProperty(value = "模型FileKey",hidden = true)
    private String fileKey;

    @ApiModelProperty(value = "材质对应材质球ID,仅在模型编辑器上传模型使用",hidden = true)
    private Integer ball2TexturePicId;

    @ApiModelProperty(value = "区域编码")
    private String areaCode;

    @ApiModelProperty(value = "模型小类编码")
    private String smallType;

    @ApiModelProperty(value = "模型区域信息编码")
    private List<ModelAreaTextureRelAdd> modelAreaTextureRelAdd;
    /**
     * 硬装产品主模型标志(1:主模型,0:副模型)
     */
    @ApiModelProperty(value = "硬装产品主模型标志(1:主模型,0:副模型)" ,hidden = true)
    private Integer mainModelFlag;

    @ApiModelProperty(value = "模型打包机IP", hidden = true)
    private String transMachineIp;
}
