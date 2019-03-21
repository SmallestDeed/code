package com.sandu.product.model.output;

import com.sandu.common.model.Mapper;
import com.sandu.product.model.BaseProduct;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductsCostType extends Mapper implements Serializable {

	/** 类型CODE **/
    private String costTypeCode;
    /** 类型值 **/
    private String costTypeValue;
    /** 类型名称 **/
    private String costTypeName;
    /** 类型总价 **/
    private BigDecimal totalPrice;
	/** 价格单位 **/
	private String salePriceValueName;
    /** 类型下的结算小类型列表 **/
    private List<ProductsCost> detailList = new ArrayList<>();
    /** 包含结算小类型的valueKeys **/
    private String costCodes;
    /** 设计方案ID **/
    private Integer planId;
    /** 登录用户ＩＤ **/
    private Integer userId;
    /** 产品总数量 **/
    private Integer productCount;
	/*过滤条件:序列号里的品牌,大类,小类,产品ids(序列号条件设置到BaseProduct类中)*/
	private List<BaseProduct> baseProduct;

	private List<AuthorizedConfig> authorizedConfigList = new ArrayList<AuthorizedConfig>();

}
