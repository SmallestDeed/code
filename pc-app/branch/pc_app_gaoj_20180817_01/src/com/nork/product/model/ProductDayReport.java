package com.nork.product.model;

public class ProductDayReport {
	private Integer index;
private String productCode;
private String productName;
private String productType;
private String productStyle;
private Integer qualityNum;
private Integer usedNum;
private String designer;
private String modelDivision;
private String gmtCreate;
public ProductDayReport() {
	super();
}
public ProductDayReport(Integer index,String gmtCreate,String productCode, String productName,
		String productType, String productStyle, Integer qualityNum,
		Integer usedNum, String designer, String modelDivision,
		String renderDivision) {
	super();
	this.index = index;
	this.gmtCreate = gmtCreate;
	this.productCode = productCode;
	this.productName = productName;
	this.productType = productType;
	this.productStyle = productStyle;
	this.qualityNum = qualityNum;
	this.usedNum = usedNum;
	this.designer = designer;
	this.modelDivision = modelDivision;
	this.renderDivision = renderDivision;
}

public ProductDayReport(Integer index,String productCode, String productName,
		String productType, String productStyle, Integer qualityNum,
		Integer usedNum, String designer, String modelDivision,
		String renderDivision) {
	super();
	this.index = index;
	this.gmtCreate = gmtCreate;
	this.productCode = productCode;
	this.productName = productName;
	this.productType = productType;
	this.productStyle = productStyle;
	this.qualityNum = qualityNum;
	this.usedNum = usedNum;
	this.designer = designer;
	this.modelDivision = modelDivision;
	this.renderDivision = renderDivision;
}
private String renderDivision;

public String getGmtCreate() {
	return gmtCreate;
}
public void setGmtCreate(String gmtCreate) {
	this.gmtCreate = gmtCreate;
}
public Integer getIndex() {
	return index;
}
public void setIndex(Integer index) {
	this.index = index;
}
public String getProductCode() {
	return productCode;
}
public void setProductCode(String productCode) {
	this.productCode = productCode;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getProductType() {
	return productType;
}
public void setProductType(String productType) {
	this.productType = productType;
}
public String getProductStyle() {
	return productStyle;
}
public void setProductStyle(String productStyle) {
	this.productStyle = productStyle;
}
public Integer getQualityNum() {
	return qualityNum;
}
public void setQualityNum(Integer qualityNum) {
	this.qualityNum = qualityNum;
}
public Integer getUsedNum() {
	return usedNum;
}
public void setUsedNum(Integer usedNum) {
	this.usedNum = usedNum;
}
public String getDesigner() {
	return designer;
}
public void setDesigner(String designer) {
	this.designer = designer;
}
public String getModelDivision() {
	return modelDivision;
}
public void setModelDivision(String modelDivision) {
	this.modelDivision = modelDivision;
}
public String getRenderDivision() {
	return renderDivision;
}
public void setRenderDivision(String renderDivision) {
	this.renderDivision = renderDivision;
}
}