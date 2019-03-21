package com.sandu.api.product.input;

import com.sandu.api.product.model.bo.ProductPropBO;
import com.sandu.api.resmodel.model.bo.ModelAreaTextureRelBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sandu
 */
@Data
public class ProductLibraryUpdate implements Serializable {
    @ApiModelProperty(value = "产品id", required = true)
    @Min(value = 0, message = "请输入正确的ID")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "产品编码", hidden = true)
    private String code;

    @NotNull(message = "产品名称不能为空")
    @ApiModelProperty(value = "产品名称", required = true)
    private String name;

    @ApiModelProperty(value = "品牌ID", required = true)
    @Min(value = 1, message = "请输入正确的数值")
    @NotNull(message = "品牌ID不能为空")
    private Integer brandId;

    @ApiModelProperty(value = "所属分类编码", hidden = true)
//    @NotNull(message = "所属分类编码不能为空")
    private String categoryCode;

    @ApiModelProperty("属性分类编码集合")
    private List<String> categoryAttrs;

    @ApiModelProperty(value = "产品小类编码")
//    @NotEmpty(message = "小类编码不能为空")
    private String productSmallType;

    @ApiModelProperty(value = "产品的属性")
    private List<ProductPropBO> props;

    @ApiModelProperty(value = "产品款式")
    @Min(value = 1, message = "请输入正确的产品款式ID")
    private Integer modelingId;

    @ApiModelProperty(value = "产品系列")
    @Min(value = 1, message = "请输入正确的产品系列ID")
    private Integer seriesId;

    @ApiModelProperty(value = "售价单位")
    @Min(value = 1, message = "售价单位ID有误")
    private Integer saleUnionId;

    @ApiModelProperty(value = "产品型号", required = true)
    @NotNull(message = "产品型号不能为空")
    private String modelNumber="";

    @ApiModelProperty(value = "产品宽")
    @Size(max = 10, message = "请输入正确的参数")
    private String width;

    @ApiModelProperty(value = "产品长")
    @Size(max = 10, message = "请输入正确的参数")
    private String length;

    @ApiModelProperty(value = "产品高")
    @Size(max = 10, message = "请输入正确的参数")
    private String height;

    @ApiModelProperty(value = "产品缩略图ID")
    @NotEmpty(message = "请选择产品缩略图")
    private String defaultPicId;

    @ApiModelProperty(value = "产品所有图片ID,以逗号分隔")
    @Pattern(regexp = "^[1-9]\\d{0,11}(,[1-9]\\d{0,11})*$", message = "请输入有效的图片集合")
    private String picIds;

    @ApiModelProperty(value = "建议售价")
    @Min(value = 0, message = "请输入正确的数值")
    @Max(value = 1000000, message = "数值超出限制,1000000")
    private Double advicePrice;

    @ApiModelProperty(value = "售价")
    @Min(value = 0, message = "请输入正确的数值")
    @Max(value = 1000000, message = "数值超出限制,1000000")
    private Double price;

    @ApiModelProperty(value = "产品风格")
    private List<Integer> baseStyleId;

    @ApiModelProperty(value = "产品描述")
    @Length(max = 5000, message = "请输入小于{max}个字符")
    private String desc = "";

    @ApiModelProperty(value = "模型产品的贴图详情", hidden = true)
    private List<ModelAreaTextureRelBO> modelTextureInfos;

    @ApiModelProperty(value = "贴图产品的贴图ID")
    @Min(value = -1, message = "请输入正确的贴图ID")
    private Integer textureId;

    @ApiModelProperty(value = "模型产品的模型ID")
    @Min(value = -1, message = "请输入正确的模型ID")
    private Integer modelId;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "请输入正确的用户ID")
    private Integer userId;

    @ApiModelProperty("运营分类ID集合")
    @Size(min = 1,message = "至少选中一个运营分类")
    private List<Integer> categoryIds;

    @ApiModelProperty("产品规格")
    @Size(max = 50,message = "产品规格休息不能超过50个字符")
    private String productSpec;

    @ApiModelProperty("是否一石多面，0：不是，1：是")
    private Integer isComplexParquet;
}
