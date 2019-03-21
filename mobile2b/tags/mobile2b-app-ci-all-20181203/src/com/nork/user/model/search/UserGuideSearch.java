package com.nork.user.model.search;

import java.io.Serializable;

import com.nork.user.model.UserGuide;

/**
 * @Title: UserGuideSearch.java
 * @Package com.nork.user.model
 * @Description:用户指南-用户指南查询对象
 * @createAuthor pandajun
 * @CreateDate 2016-11-21 20:22:33
 * @version V1.0
 */
public class UserGuideSearch extends UserGuide implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 字符串-模糊查询 **/
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
