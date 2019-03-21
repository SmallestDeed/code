package com.nork.common.model;

import java.io.Serializable;

public class PageModel extends BaseVO implements Serializable {
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     * 
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 4412841876300023560L;

    /** 每页显示多少条记录 */
    private int pageSize;

    /** 记录总数 */
    private int count;

    /** 开始记录数 */
    private int start;

    /** 当前页码 */
    private int curPage;

    /** 总页数 */
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
        setTotalPage(this.count == 0 ? 0 : (this.count + this.pageSize - 1) / (this.pageSize == 0 ? 20 : this.pageSize));
    }

    public int getCurPage() {
        return this.curPage;
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

    public int getStart() {
        return this.curPage;
    }

    public void setStart(int start) {
        this.start = start * pageSize;
    }
}
