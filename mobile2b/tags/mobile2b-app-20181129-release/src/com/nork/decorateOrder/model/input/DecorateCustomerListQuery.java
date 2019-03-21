package com.nork.decorateOrder.model.input;

import com.nork.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;

/**
 * 装企客户信息列表查询入参
 * @author WangHaiLin
 * @date 2018/10/18  10:16
 */
@Data
public class DecorateCustomerListQuery extends Mapper implements Serializable {

    /**装企用户Id**/
    private Integer userId;

    /**订单类型**/
    private Integer orderType;

    /**订单状态**/
    private Integer orderStatus;

    /**合同状态**/
    private Integer contractStatus;

    /**开始**/
    private Integer start;

    /**数量**/
    private Integer limit;

}
