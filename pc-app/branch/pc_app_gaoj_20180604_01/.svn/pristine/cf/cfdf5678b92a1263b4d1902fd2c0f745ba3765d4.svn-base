package com.nork.mobile.model.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.nork.common.model.Mapper;
import com.nork.product.model.small.SearchProCategorySmall;

/**
 * 移动端产品类目
 * @author yangzhun
 * @createTime 2017年11月2日19:58:46
 */
public class SearchProCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String type;
	// 分类ID
	private Integer cid;
	/**
	 * 分类code
	 */
	private String categoryCode;
	/**
	 * 父级ID
	 */
	private Integer pid;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 名字首字母
	 */
	private String initials;
	/**
	 * 子级分类集合
	 */
	private List<SearchProCategory> childrenNodeList = new ArrayList<SearchProCategory>();

	private String msgId;
	//排序
	private Integer ordering;
	
	
	
	public Integer getOrdering() {
		return ordering;
	}

	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public List<SearchProCategory> getChildrenNodeList() {
		return childrenNodeList;
	}

	public void setChildrenNodeList(List<SearchProCategory> childrenNodeList) {
		this.childrenNodeList = childrenNodeList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

}
