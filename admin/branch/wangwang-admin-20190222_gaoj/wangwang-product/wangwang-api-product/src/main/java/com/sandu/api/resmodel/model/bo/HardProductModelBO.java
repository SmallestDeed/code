package com.sandu.api.resmodel.model.bo;

import com.sandu.api.product.model.bo.ProductPropBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HardProductModelBO extends ModelBO implements Serializable {
    /**
     * 硬装属性
     */
    private List<ProductPropBO> props;
}
