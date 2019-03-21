package com.sandu.api.product.model.po;

import com.sandu.api.product.input.ProductSKUInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HardProductUpdatePO extends ProductUpdatePO implements Serializable {

    private List<ProductSKUInfo> productSKUInfos;

}
