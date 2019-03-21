package com.sandu.user.model.input;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:30 2018/6/6 0006
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: 用户邀请回调接收参数
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/6/6 0006AM 10:30
 */
@Data
public class UserInviteAdd implements Serializable{


    //分页对象-页面大小
    public final static int DEFAULT_PAGE_PAGESIZE = 10;
    private static final long serialVersionUID = -8163466769844093266L;

    private Integer shareType;

    private String shareSign;

    private Integer inviteId;

    private Date inviteTime;

    private Date registerTime;

    /**
     * 被邀请人Id
     */
    private Integer fid;



    /**
     * 起始页
     */
    private int start;

    /**
     * 每页多少条
     */
    private int limit;


    /**
     * 当前页码
     */
    private int curPage;

    private String messageContent;

    /**
     * 每页显示多少条记录
     */
    private int pageSize;

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
