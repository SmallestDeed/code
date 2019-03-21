package com.sandu.api.servicepurchase.output;

import java.io.Serializable;

public class ServicesUserVO implements Serializable {

	private static final long serialVersionUID = 6662646900031078099L;
	private Integer id;

	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
