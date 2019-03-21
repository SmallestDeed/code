/**

 * @Title: DesigPlanWithResult.java
 * @Package com.nork.onekeydesign.model
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * 
 * @author Comsys-yangqin
 * @date 2016年11月18日 下午5:05:10
 * @version V1.0
 */

package com.nork.onekeydesign.model;

import java.io.Serializable;

/**
 * 
 * @ClassName: DesigPlanWithResult
 * @Description: TODO
 * @author Comsys-yangqin
 * @date 2016年11月18日 下午5:05:10
 * 
 */

public class DesigPlanWithResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
