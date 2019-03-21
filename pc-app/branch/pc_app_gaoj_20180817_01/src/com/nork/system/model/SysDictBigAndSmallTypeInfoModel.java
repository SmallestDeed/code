/**
 * 
 */
package com.nork.system.model;

/**
 * 
 * 根据 type、big 
 * @author louxinhua
 *
 */
public class SysDictBigAndSmallTypeInfoModel {
	
	/*
	 bsd.type as bigType, bsd.valuekey as bigValueKey, bsd.value as bigValue, bsd.name as bigName,
	ssd.name as smallName, ssd.valuekey as smallValueKey, ssd.value as smallValue, ssd.att3 as smallAtt3, ssd.att4 as smallAtt4
	 */
	private String bigType; //
	private String bigValueKey; //
	private Integer bigValue; //
	private String bigName; //
	private String smallName; //
	private String smallValueKey; //
	private Integer smallValue; //
	private String smallAtt3; //
	private String smallAtt4; //
	

	//============================================
	// Getter and Setter
	//============================================
	
	public String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType) {
		this.bigType = bigType;
	}
	public String getBigValueKey() {
		return bigValueKey;
	}
	public void setBigValueKey(String bigValueKey) {
		this.bigValueKey = bigValueKey;
	}
	public Integer getBigValue() {
		return bigValue;
	}
	public void setBigValue(Integer bigValue) {
		this.bigValue = bigValue;
	}
	public String getBigName() {
		return bigName;
	}
	public void setBigName(String bigName) {
		this.bigName = bigName;
	}
	public String getSmallName() {
		return smallName;
	}
	public void setSmallName(String smallName) {
		this.smallName = smallName;
	}
	public String getSmallValueKey() {
		return smallValueKey;
	}
	public void setSmallValueKey(String smallValueKey) {
		this.smallValueKey = smallValueKey;
	}
	public Integer getSmallValue() {
		return smallValue;
	}
	public void setSmallValue(Integer smallValue) {
		this.smallValue = smallValue;
	}
	public String getSmallAtt3() {
		return smallAtt3;
	}
	public void setSmallAtt3(String smallAtt3) {
		this.smallAtt3 = smallAtt3;
	}
	public String getSmallAtt4() {
		return smallAtt4;
	}
	public void setSmallAtt4(String smallAtt4) {
		this.smallAtt4 = smallAtt4;
	}

	
}
