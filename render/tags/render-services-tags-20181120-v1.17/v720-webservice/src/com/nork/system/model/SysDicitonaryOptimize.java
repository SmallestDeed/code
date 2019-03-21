package com.nork.system.model;

import java.io.Serializable;

//public class SysDicitonaryOptimize extends SysDictionary implements Serializable{
	public class SysDicitonaryOptimize  implements Serializable{
	private Integer id;
	/** 类型 */
	private String type;
	/** 唯一标示 */
	private String valuekey;
	/** 值 */
	private Integer value;
	/** 名称 */
	private String name;
	/** 排序 */
	private Integer ordering;
	/** 图片id */
	private Integer picId;
	/** 图片路径 */
	private String picPath;
	/** att1 */
	private String att1;
	/** att2 */
	private String att2;
	
	
	public String getAtt1() {
		return att1;
	}
	public void setAtt1(String att1) {
		this.att1 = att1;
	}
	public String getAtt2() {
		return att2;
	}
	public void setAtt2(String att2) {
		this.att2 = att2;
	}
	private String timeConsuming;
	
	
	public String getTimeConsuming() {
		return timeConsuming;
	}
	public void setTimeConsuming(String timeConsuming) {
		this.timeConsuming = timeConsuming;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValuekey() {
		return valuekey;
	}
	public void setValuekey(String valuekey) {
		this.valuekey = valuekey;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOrdering() {
		return ordering;
	}
	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}
	public Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId) {
		this.picId = picId;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	
}
