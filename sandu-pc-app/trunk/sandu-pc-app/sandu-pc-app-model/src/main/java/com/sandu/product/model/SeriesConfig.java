package com.sandu.product.model;

import java.io.Serializable;
import java.util.List;

public class SeriesConfig implements Serializable {

	private static final long serialVersionUID = -8969666426278404667L;

	private List<SeriesConfigItem> smallTypeValuekeyInfo;
	
	private String key;

	/**
	 * 整合所有小类valuekey
	 */
	private List<String> valuekeyList;
	
	public List<String> getValuekeyList() {
		return valuekeyList;
	}

	public void setValuekeyList(List<String> valuekeyList) {
		this.valuekeyList = valuekeyList;
	}

	public List<SeriesConfigItem> getSmallTypeValuekeyInfo() {
		return smallTypeValuekeyInfo;
	}

	public void setSmallTypeValuekeyInfo(List<SeriesConfigItem> smallTypeValuekeyInfo) {
		this.smallTypeValuekeyInfo = smallTypeValuekeyInfo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
