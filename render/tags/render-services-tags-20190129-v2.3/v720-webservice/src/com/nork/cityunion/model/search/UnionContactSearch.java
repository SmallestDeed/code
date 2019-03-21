package com.nork.cityunion.model.search;


import java.io.Serializable;

public class UnionContactSearch implements Serializable {

	private static final long serialVersionUID = -526015776952446143L;
	
    /** 起始行 **/
    private Integer start;
    /** 每页数据数 **/
    private Integer limit;
    /** 创建人 **/
    private Integer userId;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
