package com.nork.design.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.nork.common.model.Mapper;
import com.nork.common.util.Utils;
import com.nork.product.model.AuthorizedConfig;
import com.nork.product.model.BaseProduct;

public class ProductsCostType  extends Mapper implements Serializable {

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

	public List<ProductsCost> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ProductsCost> detailList) {
		this.detailList = detailList;
	}

	public String getCostCodes() {
		return costCodes;
	}

	public void setCostCodes(String costCodes) {
		this.costCodes = costCodes;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<AuthorizedConfig> getAuthorizedConfigList() {
		return authorizedConfigList;
	}

	public void setAuthorizedConfigList(List<AuthorizedConfig> authorizedConfigList) {
		this.authorizedConfigList = authorizedConfigList;
	}

	public String toString(){
		StringBuffer html = new StringBuffer("<li class=\"treeview \">\n" +
				"\t\t\t\t\t\t\t\t<a href=\"#\">\n" +
				"\t\t\t\t\t\t\t\t\t<span class=\"fa_title\">"+this.getCostTypeName()+"：</span>" +
				"\t\t\t\t\t\t\t\t\t<span class=\"fa_content\">￥"+(this.getTotalPrice()==null?"0.0":this.getTotalPrice().doubleValue())+"元</span>" +
				"\t\t\t\t\t\t\t\t\t&nbsp;<i class=\"fa_icon \"></i>\n" +
				"\t\t\t\t\t\t\t\t\t<i class=\"fa fa-angle-left pull-right\"></i>" +
				"\t\t\t\t\t\t\t\t</a>" +
				"\t\t\t\t\t\t\t\t<ul class=\"treeview-menu\">");
		if( this.getDetailList() != null && this.getDetailList().size() > 0 ){
			for( ProductsCost productCost : this.getDetailList() ) {
				html.append("\t\t\t\t\t\t\t\t\t<li>" +
					"\t\t\t\t\t\t\t\t\t\t<a href=\"#\">" +
					"\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa_title\">"+productCost.getCostTypeName()+"：</span>" +
					"\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa_content\">￥"+(productCost.getTotalPrice()==null?0.0:productCost.getTotalPrice().doubleValue())+"元</span>");
				// 硬装产品加特殊标记
				if( "1".equals(this.getCostTypeValue()) || "2".equals(this.getCostTypeValue()) || "3".equals(this.getCostTypeValue()) ) {
					html.append("\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa_icon \"></i>");
				}
				html.append("\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-angle-left pull-right\"></i>" +
					"\t\t\t\t\t\t\t\t\t\t</a>" +
					"\t\t\t\t\t\t\t\t\t\t<ul class=\"treeview-menu\">");
				if( productCost.getDetailList() != null && productCost.getDetailList().size() > 0 ) {
					int count = 0;
					for (ProductCostDetail productCostDetail : productCost.getDetailList()) {
						html.append("\t\t\t\t\t\t\t<dl class=\"list_menu\">" +
								"\t\t\t\t\t\t\t\t\t\t\t\t<dt><img src=\""+ Utils.getPropertyName("app","app.resources.url","")+productCostDetail.getProductPicPath()+"\" alt=\"\" class=\"\" ></dt>" +
								"\t\t\t\t\t\t\t\t\t\t\t\t<dd><p class=\"menu_left\">品名：</p><p class=\"menu_right\" name=\"productName\">"+productCostDetail.getProductName()+"</p></dd></br>" +
								"\t\t\t\t\t\t\t\t\t\t\t\t<dd><p class=\"menu_left\">品牌：</p><p class=\"menu_right\" name=\"brandName\">"+productCostDetail.getBrandName()+"</p></dd></br>" +
								"\t\t\t\t\t\t\t\t\t\t\t\t<dd><p class=\"menu_left\">单价：</p><p class=\"menu_right\" name=\"unitPrice\">"+(productCostDetail.getUnitPrice()==null?0.0:productCostDetail.getUnitPrice().doubleValue())+(productCostDetail.getProductUnit()==null?"元":productCostDetail.getProductUnit())+"</p></dd></br>" +
								"\t\t\t\t\t\t\t\t\t\t\t\t<dd><p class=\"menu_left\">数量：</p><p class=\"menu_right\" name=\"count\">"+productCostDetail.getCount()+"</p></dd></br>" +
								"\t\t\t\t\t\t\t\t\t\t\t\t<dd><p class=\"menu_left\">金额：</p><p class=\"menu_right\" name=\"totalPrice\">"+(productCostDetail.getTotalPrice()==null?0.0:productCostDetail.getTotalPrice().doubleValue())+"元</p></dd>" +
								"<input type=\"hidden\" name=\"productModelNumber\" value=\""+(productCostDetail.getProductModelNumber()==null?"":productCostDetail.getProductModelNumber())+"\"/>" +
								"<input type=\"hidden\" name=\"productSpec\" value=\""+(productCostDetail.getProductSpec()==null?"":productCostDetail.getProductSpec())+"\"/>" +
								"<input type=\"hidden\" name=\"productDesc\" value=\""+(productCostDetail.getProductDesc()==null?"":productCostDetail.getProductDesc())+"\"/>" +
								"<input type=\"hidden\" name=\"productOriginalPicPath\" value=\""+(productCostDetail.getProductOriginalPicPath()==null?"":Utils.getPropertyName("app","app.resources.url","")+productCostDetail.getProductOriginalPicPath())+"\"/>" +
								"\t\t\t\t\t\t\t\t\t\t\t</dl>");
						if( count < productCost.getDetailList().size() ) {
							html.append("<div class=\"clear\"></div>");
						}
						count++;
					}
				}
				html.append(
					"\t\t\t\t\t\t\t\t\t\t</ul>" +
					"\t\t\t\t\t\t\t\t\t</li>");
			}
		}
		html.append("\t\t\t\t\t\t\t\t\t</ul>");
		html.append("\t\t\t\t\t\t\t</li>");
		return html.toString();
	}
}
