package com.sandu.api.product.input;

import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.bo.ProductPropBO;
import com.sandu.api.resmodel.model.bo.ModelAreaTextureRelBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSKUInfo implements Serializable {

    @ApiModelProperty(value = "sku属性", hidden = true)
    private List<ProductPropBO> props;

    @ApiModelProperty(value = "组合属性Ids")
    private List<Integer> propIds;

    @ApiModelProperty(value = "模型id")
    private Integer modelId;

    @ApiModelProperty(value = "材质ID")
    private Integer textureId;

    @ApiModelProperty(value = "模型产品的贴图详情", hidden = true)
    private List<ModelAreaTextureRelBO> modelTextureInfos;

    @ApiModelProperty(value = "硬装产品更新时,主产品ID")
    private Integer productId;

    @ApiModelProperty(value = "产品编码", hidden = true)
    private String productCode;

    @ApiModelProperty(value = "新增硬装产品时,主产品标志, true为主产品")
    private Boolean mainProductFlag =false;

}
