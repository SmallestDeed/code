package com.nork.decorateOrder.common;

/**
 * @author WangHaiLin
 * @date 2018/10/27  16:23
 */
public class PageHelper {
    private Integer start;
    private Integer pageSize;
    private Integer totalPage;

    public PageHelper() {
    }

    public static PageHelper getPage(Integer totalCount, Integer limit, Integer currentPage) {
        PageHelper helper = new PageHelper();
        limit = limit == null ? 10 : limit.intValue();
        currentPage = currentPage == null ? 1 : currentPage.intValue();
        int totalPage = totalCount.intValue() % limit.intValue() == 0 ? totalCount.intValue() / limit.intValue() : totalCount.intValue() / limit.intValue() + 1;
        int start = (currentPage.intValue() - 1) * limit.intValue();
        helper.setTotalPage(totalPage);
        helper.setStart(start);
        helper.setPageSize(limit);
        return helper;
    }

    public Integer getStart() {
        return this.start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
