package com.sandu.api.base.input;

import lombok.Data;

import java.io.Serializable;

/***
 * 查询实体基类
 * @author xiaoxc
 * @data 2018-05-31
 * @param <T>
 */
@Data
public class BaseQuery<T> implements Serializable{
	private static final long serialVersionUID = 3484679339093276713L;
	private int pageNo = 1; // 当前页码
	private int limit = 20; // 页面大小，设置为“-1”表示不进行分页（分页无效）
	private int start = 0;
	private String orderBy;
	private boolean isAscending = false; //是否升序

	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final int DEL_FLAG_NORMAL = 0;
	public static final int DEL_FLAG_DELETE = 1;
	public static final int DEL_FLAG_AUDIT = 2;
	
}
