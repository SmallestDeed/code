package com.sandu.api.base.model.vo;

import java.io.Serializable;

public abstract class BaseVo<T> implements Serializable {
	private static final long serialVersionUID = -482254375110609776L;
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
