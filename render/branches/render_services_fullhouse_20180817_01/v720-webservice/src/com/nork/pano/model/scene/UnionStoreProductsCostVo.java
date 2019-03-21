package com.nork.pano.model.scene;

import com.nork.design.model.ProductsCostType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public class UnionStoreProductsCostVo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 产品清单总价 **/
    private String totalPrice;
	
    /** 设计方案产品费用清单 **/
    private List<ProductsCostType> productsCostTypeList;
 
   
    public String getTotalPrice() {
      return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
      this.totalPrice = totalPrice;
    }

    public List<ProductsCostType> getProductsCostTypeList() {
        return productsCostTypeList;
    }

    public void setProductsCostTypeList(List<ProductsCostType> productsCostTypeList) {
        this.productsCostTypeList = productsCostTypeList;
    }

}
