package com.sandu.api.product.model.po;

import com.sandu.api.product.model.bo.ProductPropBO;
import com.sandu.api.product.model.Product;
import com.sandu.api.resmodel.model.bo.ModelAreaTextureRelBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
/**
 * @author Sandu
 */
@Data
public class ProductUpdatePO implements Serializable{
    @ApiModelProperty(value = "产品id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;
    @ApiModelProperty(value = "产品编码", required = true)
    @NotNull(message = "产品编码不能为空")
    private String code;
    @NotNull(message = "产品名称不能为空")
    @ApiModelProperty(value = "产品名称", required = true)
    private String name;
    @ApiModelProperty(value = "品牌ID", required = true)
    @NotNull(message = "品牌ID不能为空")
    private Integer brandId;
    @ApiModelProperty(value = "所属分类编码", required = true)
    @NotNull(message = "所属分类编码不能为空")
    private String categoryCode;    //作为小类
    @ApiModelProperty(value = "产品款式")
    private Integer modelingId;
    @ApiModelProperty(value = "产品系列")
    private Integer seriesId;
    @ApiModelProperty(value = "售价单位")
    private Integer saleUnionId;
    @ApiModelProperty(value = "编辑类型:产品库编辑:libraryEdit;线上产品编辑:onlineEdit;渠道产品编辑:channelEdit")
    private String editType;
    @ApiModelProperty(value = "产品型号", required = true)
    @NotNull(message = "产品型号不能为空")
    private String modelNumber;
    @ApiModelProperty(value = "产品宽")
    private String width;
    @ApiModelProperty(value = "产品长")
    private String length;
    @ApiModelProperty(value = "产品高")
    private String height;
    @ApiModelProperty(value = "产品的属性")
    private List<ProductPropBO> props;


    @ApiModelProperty(value = "产品缩略图ID")
    private String defaultPicId;
    @ApiModelProperty(value = "产品所有图片ID,以逗号分隔")
    private String picIds;
    @ApiModelProperty(value = "建议售价")
    private Double advicePrice;
    @ApiModelProperty(value = "售价")
    private Double price;
    @ApiModelProperty(value = "产品风格")
    private List<Integer> baseStyleId;
    @ApiModelProperty(value = "产品描述")
    private String desc;

    private Product product;

    @ApiModelProperty(value = "模型产品的贴图详情")
    private List<ModelAreaTextureRelBO> modelTextureInfos;
    @ApiModelProperty(value = "贴图产品的贴图ID")
    private Integer textureId;
    @ApiModelProperty(value = "模型产品的模型ID")
    private Integer modelId;

    @ApiModelProperty("用户ID")
    @Min(value = 1, message = "请输入正确的用户ID")
    private Integer userId;

    @ApiModelProperty("属性分类编码集合")
    private List<String> categoryAttrs;

    private List<Integer> categoryIds;

    @ApiModelProperty("产品规格")
    @Size(max = 50,message = "产品规格休息不能超过50个字符")
    private String productSpec;

    @ApiModelProperty("产品材质")
    private String materialPicIds;



}
