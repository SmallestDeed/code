package com.nork.system.model;

import java.io.Serializable;
import java.util.Date;

/*
 *2017年9月1日下午4:02:21	
 *Author:ws
 *
 */
public class ResmodelPathAndProductId implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private Long id; //产品ID
	private Date gmt_create; //模型创建时间
	private String model_path; //模型文件路径
	private String product_code; //产品编码
	private String putaway_state;
	
	
	
	public String getPutaway_state() {
		return putaway_state;
	}
	public void setPutaway_state(String putaway_state) {
		this.putaway_state = putaway_state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getModel_path() {
		return model_path;
	}
	public void setModel_path(String model_path) {
		this.model_path = model_path;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	
	
}
