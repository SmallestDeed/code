package com.nork.decorateOrder.model.input;

import com.nork.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author WangHaiLin
 * @date 2018/10/23  15:21
 */
@Data
public class DecoratePriceListQuery extends Mapper implements Serializable {

    /**装企用户Id**/
    private Integer userId;

    /**报价状态**/
    private Integer priceStatus;

    /**开始**/
    private Integer start;

    /**数量**/
    private Integer limit;

    /**报价状态列表**/
    private List<Integer> statusList;

    /**所属企业Id**/
    private Integer companyId;

}
