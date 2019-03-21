package com.sandu.common.model;

import java.io.Serializable;

public class PageModel implements Serializable {

    //分页对象-页面大小
    public final static int DEFAULT_PAGE_PAGESIZE = 10;
    //分页对象-当前页面
    public final static int DEFAULT_PAGE_CURRENTPAGE = 0;

    /**
     *
     */
    private static final long serialVersionUID = 2732675231781623431L;

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
     * 当前页码
     */
    private int curPage;

    /**
     * 总页数
     */
    private int totalPage;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        //初始化数据查询分页起始数
        if (0 != this.curPage) {
            //设置起始数
            this.start = (this.curPage - 1) * this.pageSize;         
        }
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


}
