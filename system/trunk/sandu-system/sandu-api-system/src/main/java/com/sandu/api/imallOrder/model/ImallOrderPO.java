package com.sandu.api.imallOrder.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ImallOrderPO implements Serializable {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

    /**
     * 用户名
     */
    private String buyerNickName;

    /**
     * 收件人
     */
    private String consignee;

    /**
     * 收件人电话
     */
    private String mobile;


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
