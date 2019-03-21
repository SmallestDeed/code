package com.sandu.commons;



/**
 * 分页帮助类
 * @author WangHaiLin
 * @date 2018/5/17  14:17
 */
public class PageHelper {

    /**
     * 查询开始点
     */
    private Integer start;

    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     *
     * @param totalCount 数据总数量
     * @param limit 每页数量
     * @param currentPage 当前页
     * @return
     */
    public static PageHelper getPage(Integer totalCount, Integer limit, Integer currentPage){
        PageHelper helper=new PageHelper();
        limit=limit==null?10:limit;
        currentPage=currentPage==null?1:currentPage;
        int totalPage=totalCount%limit==0?totalCount/limit:totalCount/limit+1;
        int start=(currentPage-1)*limit;
        helper.setTotalPage(totalPage);
        helper.setStart(start);
        helper.setPageSize(limit);
        return helper;
    }

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

    
}
