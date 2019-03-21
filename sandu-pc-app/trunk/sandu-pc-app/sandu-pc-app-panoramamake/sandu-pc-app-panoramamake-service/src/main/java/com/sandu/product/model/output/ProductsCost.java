package com.sandu.product.model.output;


import com.sandu.common.model.Mapper;
import com.sandu.product.model.BaseProduct;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 */
@Data
public class ProductsCost extends Mapper implements Serializable {

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
    private List<ProductCostDetail> detailList = new ArrayList<>();
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
    private Integer productCount = 0;
	/*过滤条件:序列号里的品牌,大类,小类,产品ids(序列号条件设置到BaseProduct类中)*/
	private List<BaseProduct> baseProduct;
	//平台id
    private Integer platformId;
    //产品大类code
    private String productTypeValue;
    
    private List<AuthorizedConfig> authorizedConfigList = new ArrayList<AuthorizedConfig>();

}
