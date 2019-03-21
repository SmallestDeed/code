package com.sandu.api.base.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageModel implements Serializable {

    private static final long serialVersionUID = 2732675231781623431L;
    //分页对象-页面大小
    public final static int DEFAULT_PAGE_PAGESIZE = 10;

    //分页对象-当前页面
    public final static int DEFAULT_PAGE_CURRENTPAGE = 0;

    /**
     * 每页显示多少条记录
     */
    private int pageSize;

    /**
     * 记录总数
     */
    private int count;

    /**
     * 开始记录数
     */
    private int start;
    /**
     * 显示数据条数
     */
    private int limit;

    /**
     * 当前页码
     */
    private int curPage;

    /**
     * 总页数
     */
    private int totalPage;
    
}
