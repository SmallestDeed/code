package com.sandu.api.resmodel.input;

import com.sandu.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author  bvvy
 * @date 2018/4/9
 */
@Data
public class ResModelQuery extends BaseQuery implements Serializable {

    @ApiModelProperty("种类编码")
    @Length(max = 100, message = "种类编码长度不能超过{max}")
    private String categoryCodes;

    @ApiModelProperty("转换状态")
    @Length(max = 30, message = "转换状态长度不能超过{max}")
    private String transStatus;

    @ApiModelProperty("模型名称")
    @Length(max = 30, message = "模型名称长度不能超过{max}")
    private String modelName;

    @ApiModelProperty("模型编码")
    @Length(max = 30, message = "模型编码长度不能超过{max}")
    private String modelCode;

    @ApiModelProperty(value = "模型型号")
    @Length(max = 30, message = "模型型号长度不能超过{max}")
    private String modelModelNum;

    @ApiModelProperty(value = "企业ID", required = true)
    @NotNull(message = "企业ID不能为空")
    @Min(value = 1, message = "企业ID无效")
    private Integer companyId;

    @ApiModelProperty(value = "查询方式,-1全部/0未使用/1已使用", required = true)
    @NotNull(message = "查询方式参数错误")
    @Range(min = -1, max = 1, message = "查询方式限：-1全部/0未使用/1已使用")
    private Integer isUsed;

    @ApiModelProperty("模型的作者")
    @Length(min = 1, max = 30, message = "模型作者不合法")
    private String author;

    @ApiModelProperty("模型的大小类分类")
    private String modelType;

    @ApiModelProperty("是否为产品页面选择模型标志  true为产品页面, 默认为false")
    private boolean chooseModelFlag = false;

    @ApiModelProperty("需排除的模型")
    private List<Integer> existModelIds;

    @ApiModelProperty("编辑页面 已经选用的模型")
    private List<Integer> usedModelIds;


}

