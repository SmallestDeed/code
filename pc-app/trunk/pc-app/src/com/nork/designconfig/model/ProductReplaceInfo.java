package com.nork.designconfig.model;

import com.nork.product.model.CategoryProductResult;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/12/11 0011 15:13
 * @Modified By
 */
public class ProductReplaceInfo implements Serializable {
    private static final long serialVersionUID = -411733011800274217L;

    private Map<String,String> rulesSecondary;      //产品规则信息

    private CategoryProductResult productDetails;   //产品详情

    private Integer isMultidimensional = 0;         //0:非多维、1:多维

    public Map<String, String> getRulesSecondary() {
        return rulesSecondary;
    }

    public void setRulesSecondary(Map<String, String> rulesSecondary) {
        this.rulesSecondary = rulesSecondary;
    }

    public CategoryProductResult getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(CategoryProductResult productDetails) {
        this.productDetails = productDetails;
    }

    public Integer getIsMultidimensional() {
        return isMultidimensional;
    }

    public void setIsMultidimensional(Integer isMultidimensional) {
        this.isMultidimensional = isMultidimensional;
    }
}
