package com.sandu.designplan.model;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 5:28 2018/9/21 0021
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/9/21 0021PM 5:28
 */
@Data
public class MydecorationPlanAdd implements Serializable{
    private static final long serialVersionUID = 2493794785219834626L;

    private Integer userId;

    private Integer state;

    private Integer houseId;

    private Integer taskType;

    private Integer probationHouseId;

    public final static int DEFAULT_PAGE_PAGESIZE = 10;
    /**
     * 当前页码
     */
    private int curPage;

    /**
     * 每页显示多少条记录(默认10)
     */
    private int pageSize;
    /**
     * 起始页
     */
    private int start;

    /**
     * 每页多少条
     */
    private int limit;

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        //初始化数据查询分页起始数
        if (0 != this.curPage) {
            //设置起始数
            this.start = (this.curPage - 1) * this.pageSize;
        }
        return this.start;
    }


    public int getLimit() {
        if(0!=this.pageSize){
            return this.pageSize;
        }
        return DEFAULT_PAGE_PAGESIZE;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
