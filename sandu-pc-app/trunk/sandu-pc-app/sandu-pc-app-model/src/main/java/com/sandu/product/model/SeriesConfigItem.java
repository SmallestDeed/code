package com.sandu.product.model;

import java.io.Serializable;
import java.util.List;

public class SeriesConfigItem implements Serializable {
	
	private static final long serialVersionUID = -8176503858363208922L;

	private String seriesProductType;
	
	private String seriesSign;

	private List<String> valuekeyList;
	
	public List<String> getValuekeyList() {
		return valuekeyList;
	}

	public void setValuekeyList(List<String> valuekeyList) {
		this.valuekeyList = valuekeyList;
	}

	public String getSeriesProductType() {
		return seriesProductType;
	}

	public void setSeriesProductType(String seriesProductType) {
		this.seriesProductType = seriesProductType;
	}

	public String getSeriesSign() {
		return seriesSign;
	}

	public void setSeriesSign(String seriesSign) {
		this.seriesSign = seriesSign;
	}
	
}
