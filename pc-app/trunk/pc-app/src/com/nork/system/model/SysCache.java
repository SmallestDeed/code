package com.nork.system.model;

import java.io.Serializable;

/**
 * 缓存
 * @author huangsongbo
 */
public class SysCache implements Serializable{

	private static final long serialVersionUID = 1L;

	/*key*/
	private String key;
	/*value*/
	private String object;
	
	public SysCache() {
		super();
	}

	public SysCache(String key, String object) {
		super();
		this.key = key;
		this.object = object;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}
	
}
