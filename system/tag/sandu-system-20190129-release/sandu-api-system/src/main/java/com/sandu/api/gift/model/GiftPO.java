package com.sandu.api.gift.model;

import lombok.Data;

import java.io.Serializable;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/28 18:10
 */
@Data
public class GiftPO implements Serializable {

    /**
     * id
     */
    private int id;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Integer isPutaway;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 每页数量
     */
    private Integer limit;

    /**
     * 排序字段
     */
    private String order;

    /**
     * 排序方式
     */
    private String sort;

    public String getOrderBy(){
        String orderBy="";
        if (order!=null&&!order.equals("")){
            orderBy=order+" "+sort;
        }
        return orderBy;
    }
}
