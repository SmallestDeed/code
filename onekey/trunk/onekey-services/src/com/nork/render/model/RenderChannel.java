package com.nork.render.model;

import java.util.List;

import com.nork.onekeydesign.model.PlanMode;

public class RenderChannel {
	/**
	 * 渲染类型
	 */
	private String type;
	/**
	 * 渲染通道
	 */
	private Integer renderChannel;
	/**
	 * 默认选中
	 */
	private Boolean isSelected;
	/**
	 * 解释
	 */
	private String explain;
	/**
	 * 价格列表
	 */
	private List<PlanMode> priceList;
	
	public RenderChannel(String type, Integer renderChannel, Boolean isSelected, String explain,
			List<PlanMode> priceList) {
		super();
		this.type = type;
		this.renderChannel = renderChannel;
		this.isSelected = isSelected;
		this.explain = explain;
		this.priceList = priceList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getRenderChannel() {
		return renderChannel;
	}
	public void setRenderChannel(Integer renderChanne) {
		this.renderChannel = renderChanne;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public List<PlanMode> getPriceList() {
		return priceList;
	}
	public void setPriceList(List<PlanMode> priceList) {
		this.priceList = priceList;
	}
	
	
}
