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
public class HardProductLibraryUpdate extends ProductLibraryUpdate implements Serializable {
    @ApiModelProperty(value = "硬装产品信息")
    private List<ProductSKUInfo> productSKUInfos;
}
