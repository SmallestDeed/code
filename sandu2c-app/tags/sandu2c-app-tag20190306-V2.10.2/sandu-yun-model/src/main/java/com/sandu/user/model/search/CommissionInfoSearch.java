package com.sandu.user.model.search;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:25 2018/7/23 0023
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.annotation.Name;
import com.sandu.annotation.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @Title: 用户佣金详情
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/7/23 0023PM 3:25
 */
@Data
public class CommissionInfoSearch implements Serializable{

    private static final long serialVersionUID = -5602364964226020708L;

    @Name("年")
    private Integer year; //年


    @Name("月")
    private Integer month;//月


    //默认分页10条
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
