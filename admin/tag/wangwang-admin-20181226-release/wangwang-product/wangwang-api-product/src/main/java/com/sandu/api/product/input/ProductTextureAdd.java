package com.sandu.api.product.input;

import com.sandu.api.product.model.bo.ProductPropBO;
import com.sandu.api.resmodel.model.bo.ModelAreaTextureRelBO;
import com.sandu.api.restexture.input.ResTextureAdd;
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
public class ProductTextureAdd implements Serializable {
    public static final String PRODUCT_TYPE_SOFT = "soft";
    public static final String PRODUCT_TYPE_HARD = "hard";

    @ApiModelProperty(value = "产品信息对象")
    private ProductAdd productAdd;

    @ApiModelProperty(value = "产品信息对象")
    private ResTextureAdd resTextureAdd;
}
