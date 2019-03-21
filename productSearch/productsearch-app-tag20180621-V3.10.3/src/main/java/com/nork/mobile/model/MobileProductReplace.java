package com.nork.mobile.model;

import java.io.Serializable;
import java.util.List;
/**
 * 移动端一键替换产品  实体类
 * @author yangzhun
 */

public class MobileProductReplace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String loginUserName;
	private Integer operationSource;//操作来源，0，我的设计，1,为推荐方案
	/**
	 * 渲染类型
	 */
	private String renderingType;
	/**
	 * 推荐方案id
	 */
	private Integer recommendedPlanId;
	
	/**
	 * 设计方案id，对应的是design_plan_render_scene表id
	 */
	private Integer planId;
	
	/**
	 * 订单产品类型(具体可以见ProductType.java)
	 */
	private String productType;
	
	/**
	 * 订单总金额，单位为分
	 */
	private Double totalFee;
	
	/**
	 * 支付类型
	 */
	private String payType;
	
	/**
	 * 移动端一键替换的产品集合
	 */
	private List<ProductReplaceTaskDetail> productReplaceList;
	/**
	 * 移动端产品移除
	 */
	private List<ProductReplaceTaskDetail> productDeleteList;
	/**
	 * 移动端组合产品替换
	 */
	private List<ProductGroupReplaceTaskDetail> productGroupReplaceList;
	/**
	 * 移动端材质替换
	 */
	private List<ProductReplaceTaskDetail> textureReplaceList;
	
	private Integer templateId;
	
	private Integer designPlanSceneId;

	public List<ProductGroupReplaceTaskDetail> getProductGroupReplaceList() {
		return productGroupReplaceList;
	}

	public void setProductGroupReplaceList(List<ProductGroupReplaceTaskDetail> productGroupReplaceList) {
		this.productGroupReplaceList = productGroupReplaceList;
	}

	public List<ProductReplaceTaskDetail> getTextureReplaceList() {
		return textureReplaceList;
	}

	public void setTextureReplaceList(List<ProductReplaceTaskDetail> textureReplaceList) {
		this.textureReplaceList = textureReplaceList;
	}

	public List<ProductReplaceTaskDetail> getProductDeleteList() {
		return productDeleteList;
	}

	public void setProductDeleteList(List<ProductReplaceTaskDetail> productDeleteList) {
		this.productDeleteList = productDeleteList;
	}

	public Integer getDesignPlanSceneId() {
		return designPlanSceneId;
	}

	public void setDesignPlanSceneId(Integer designPlanSceneId) {
		this.designPlanSceneId = designPlanSceneId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getRenderingType() {
		return renderingType;
	}

	public void setRenderingType(String renderingType) {
		this.renderingType = renderingType;
	}

	public Integer getRecommendedPlanId() {
		return recommendedPlanId;
	}

	public void setRecommendedPlanId(Integer recommendedPlanId) {
		this.recommendedPlanId = recommendedPlanId;
	}

	public List<ProductReplaceTaskDetail> getProductReplaceList() {
		return productReplaceList;
	}

	public void setProductReplaceList(List<ProductReplaceTaskDetail> productReplaceList) {
		this.productReplaceList = productReplaceList;
	}

	public Integer getOperationSource() {
		return operationSource;
	}

	public void setOperationSource(Integer operationSource) {
		this.operationSource = operationSource;
	}
	
	
	
}
