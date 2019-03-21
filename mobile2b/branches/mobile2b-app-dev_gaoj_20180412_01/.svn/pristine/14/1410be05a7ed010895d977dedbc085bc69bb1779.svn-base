package com.nork.design.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.nork.common.model.Mapper;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseProduct;

/**
 * Created by Administrator on 2015/12/23.
 */
public class ProductsCost  extends Mapper implements Serializable {

    /** 结算类型CODE **/
    private String costTypeCode;
    /** 结算类型值 **/
    private String costTypeValue;
    /** 结算类型名称 **/
    private String costTypeName;
    /** 类型下产品总价 **/
    private BigDecimal totalPrice;
    /** 价格单位 **/
    private String salePriceValueName;
    /** 类型下的结算产品列表 **/
    private List<ProductCostDetail> detailList;
    /** 包含产品的ID **/
    private String productIds;
    /** 设计方案ID **/
    private Integer planId;
    /** 登录用户ＩＤ **/
    private Integer userId;
    /**用户产品配置三种情况（1品牌，2品牌大类，3品牌大类小类） **/
    private String brands;
    private String bigType;
    private String smallType;
    /** 产品总数量 **/
    private Integer productCount;
	/*过滤条件:序列号里的品牌,大类,小类,产品ids(序列号条件设置到BaseProduct类中)*/
	private List<BaseProduct> baseProduct;

    public String getSalePriceValueName() {
        return salePriceValueName;
    }

    public void setSalePriceValueName(String salePriceValueName) {
        this.salePriceValueName = salePriceValueName;
    }

    public List<BaseProduct> getBaseProduct() {
		return baseProduct;
	}

	public void setBaseProduct(List<BaseProduct> baseProduct) {
		this.baseProduct = baseProduct;
	}
	
    public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
    
    private List<AuthorizedConfig> authorizedConfigList = new ArrayList<AuthorizedConfig>();

	public List<AuthorizedConfig> getAuthorizedConfigList() {
		return authorizedConfigList;
	}
	public void setAuthorizedConfigList(List<AuthorizedConfig> authorizedConfigList) {
		this.authorizedConfigList = authorizedConfigList;
	}
    
	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public String getBigType() {
		return bigType;
	}

	public void setBigType(String bigType) {
		this.bigType = bigType;
	}

	public String getSmallType() {
		return smallType;
	}

	public void setSmallType(String smallType) {
		this.smallType = smallType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCostTypeCode() {
        return costTypeCode;
    }

    public void setCostTypeCode(String costTypeCode) {
        this.costTypeCode = costTypeCode;
    }

    public String getCostTypeValue() {
        return costTypeValue;
    }

    public void setCostTypeValue(String costTypeValue) {
        this.costTypeValue = costTypeValue;
    }

    public String getCostTypeName() {
        return costTypeName;
    }

    public void setCostTypeName(String costTypeName) {
        this.costTypeName = costTypeName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductCostDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ProductCostDetail> detailList) {
        this.detailList = detailList;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
