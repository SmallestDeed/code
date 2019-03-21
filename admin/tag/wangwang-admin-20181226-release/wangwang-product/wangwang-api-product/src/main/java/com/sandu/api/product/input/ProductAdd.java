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
public class ProductAdd implements Serializable {
    public static final String PRODUCT_TYPE_SOFT = "soft";
    public static final String PRODUCT_TYPE_HARD = "hard";

    @ApiModelProperty(value = "产品编码", hidden = true)
    private String code;

    @ApiModelProperty(value = "产品名称", required = true)
    @NotEmpty(message = "产品名称不能为空")
    @Size(min = 1, max = 40, message = "长度应在{min}-{max}之间")
    private String name;

    @ApiModelProperty(value = "品牌ID", required = true)
    @NotNull(message = "品牌ID不能为空")
    @Min(value = 1, message = "请输入正确的品牌ID")
    private Integer brandId;

    @ApiModelProperty(value = "所属分类编码", required = true)
//    @NotEmpty(message = "所属分类编码不能为空")
    private String categoryCode;

    @ApiModelProperty("属性分类编码集合")
    private List<String> categoryAttrs;

    @ApiModelProperty("运营分类ID集合")
    @NotNull(message = "至少选中一个运营分类")
    @Size(min = 1,message = "至少选中一个运营分类")
    private List<Integer> categoryIds;

    @ApiModelProperty(value = "产品小类编码")
    @NotEmpty(message = "小类编码不能为空")
    private String productSmallType;

    @ApiModelProperty(value = "产品的属性")
    private List<ProductPropBO> props;

    @ApiModelProperty(value = "产品风格")
    private List<Integer> baseStyleId;

    @ApiModelProperty(value = "产品款式")
    @Min(value = 1, message = "请输入正确的产品款式")
    private Integer modelingId;

    @ApiModelProperty(value = "产品系列")
    @Min(value = 1, message = "请输入正确的产品系列ID")
    private Integer seriesId;

    @ApiModelProperty(value = "建议售价")
    @Min(value = 0, message = "请输入正确的数值")
    @Max(value = 100000, message = "数值超出限制,100000")
    private Double advicePrice;

    @ApiModelProperty(value = "售价")
    @Min(value = 0, message = "请输入正确的数值")
    @Max(value = 100000, message = "数值超出限制,100000")
    private Double price;

    @ApiModelProperty(value = "售价单位")
    @Min(value = 1, message = "售价单位ID有误")
    private Integer saleUnionId;

    @ApiModelProperty(value = "产品描述")
    @Length(max = 5000, message = "描述长度超过{max}")
    private String desc;

    @ApiModelProperty(value = "(多选框)要分配的平台类型;渠道:2b/线上:2c")
    @Pattern(regexp = "^([2][bc])?(,[2][bc])?$", message = "平台类型有错误")
    private String platformType;

    @ApiModelProperty(value = "产品型号", required = true)
    @NotEmpty(message = "产品型号不能为空")
    private String modelNumber;

    @ApiModelProperty(value = "产品宽")
    @Size(max = 10, message = "请输入正确的宽度")
    private String width;

    @ApiModelProperty(value = "产品长")
    @Size(max = 10, message = "请输入正确的长度")
    private String length;

    @ApiModelProperty(value = "产品高")
    @Size(max = 10, message = "请输入正确的高度")
    private String height;

    @ApiModelProperty(value = "产品缩略图ID")
    @NotEmpty(message = "请选择产品缩略图")
    private String defaultPicId;

    @ApiModelProperty(value = "产品所有图片ID,以逗号分隔")
    @Pattern(regexp = "^[1-9]\\d{0,11}(,[1-9]\\d{0,11})*$", message = "请输入有效的图片集合")
    private String picIds;


    @ApiModelProperty(value = "模型产品的贴图详情")
    private List<ModelAreaTextureRelBO> modelTextureInfos;

    @ApiModelProperty(value = "贴图产品的贴图ID")
    @Min(value = 1, message = "贴图ID有误")
    private Integer textureId;

    @ApiModelProperty(value = "模型产品的模型ID")
    @Min(value = 1, message = "请输入正确的模型ID")
    private Integer modelId;

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "请输入正确的用户ID")
    private Integer userId;

    @ApiModelProperty(value = "产品sku信息")
    @Size(min = 1,message = "产品属性异常,请至少选择一个属性")
    private List<ProductSKUInfo> productSKUInfos;


    @ApiModelProperty("新增产品类型    软装:soft 硬装:hard")
    @Pattern(regexp = "(soft)|(hard)",message = "请输入正确的产品类型")
    private String addProductType;

    @ApiModelProperty("产品规格")
    @Size(max = 50,message = "产品规格休息不能超过50个字符")
    private String productSpec;

    @ApiModelProperty("是否单独创建")
    private Integer isCreatedTexture;

    @ApiModelProperty("材质id")
    private String materialPicIds;

    @ApiModelProperty("是否一石多面，0：不是，1：是")
    private Integer isComplexParquet;
}
