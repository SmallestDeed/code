package com.nork.sync.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.nork.product.model.ProductRecommendation;

/**
 * Created by Administrator on 2016/5/21.
 */
@XmlRootElement(name="SyncProductRecommendation")
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncProductRecommendation {

    private ProductRecommendation productRecommendation;

    private SyncBaseProduct syncBaseProduct;

    public ProductRecommendation getProductRecommendation() {
        return productRecommendation;
    }

    public void setProductRecommendation(ProductRecommendation productRecommendation) {
        this.productRecommendation = productRecommendation;
    }

    public SyncBaseProduct getSyncBaseProduct() {
        return syncBaseProduct;
    }

    public void setSyncBaseProduct(SyncBaseProduct syncBaseProduct) {
        this.syncBaseProduct = syncBaseProduct;
    }
}
