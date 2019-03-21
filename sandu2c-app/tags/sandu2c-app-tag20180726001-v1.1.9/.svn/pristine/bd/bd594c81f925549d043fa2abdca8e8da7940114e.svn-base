package com.sandu.user.model.input;

import com.sandu.common.model.PageModel;
import lombok.Data;

import java.io.Serializable;


@Data
public class UserPrivateMessageAdd implements Serializable{

    //分页对象-页面大小
    public final static int DEFAULT_PAGE_PAGESIZE = 10;
    private static final long serialVersionUID = -835535396325102349L;
    /**
     * 接受者Id
     */
    private Integer friendId;


    /**
     * 发送者Id
     */
    private Integer userId;

    /**
     * 消息类型,1：普通消息 2：系统消息
     */
    private Integer messageType;


    /**
     * 消息状态 1：未读 2：已读 3：删除
     */
    private Byte status;

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

    private Integer type; // 供求信息1,同城服务2

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