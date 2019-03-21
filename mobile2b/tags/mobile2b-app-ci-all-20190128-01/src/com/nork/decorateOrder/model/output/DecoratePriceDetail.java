package com.nork.decorateOrder.model.output;

import com.nork.decorateOrder.model.ProprietorInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台派单详情---输出实体
 * @author WangHaiLin
 * @date 2018/10/20  14:11
 */
@Data
public class DecoratePriceDetail extends ProprietorInfo implements Serializable {

    //order表数据
    /**订单Id**/
    private Integer orderId;

    /**订单状态(0-待支付;1-订单超时;2-待沟通;3-有意向;4-无意向;5-已签约;6-已完成)**/
    private Integer orderStatus;

    /**订单类型(0-客户店铺预约;1-抢单;2-平台自动派单;3-内部推荐)**/
    private Integer orderType;

    /**客单价**/
    private BigDecimal price;

    /**三度客服备注**/
    private String remark1;


    //price 表数据
    /**报价Id**/
    private Integer priceId;

    /**材料费(元)**/
    private BigDecimal materialFee;

    /**质检费(元)**/
    private BigDecimal checkFee;

    /**人工费(元)**/
    private BigDecimal labourFee;

    /**设计费(元)**/
    private BigDecimal designFee;

    /**总费用**/
    private BigDecimal totalFee;

    /**报价状态**/
    private Integer priceStatus;

    /**截止时间时间**/
    private Date endTime;

    /**报价剩余时间毫秒数**/
    private Long offerRemainingTime;




    /**方案图片**/
    private String planPicPath;

    /**方案名称**/
    private String planName;

    /**方案Id**/
    private Long planRecommendedId;

    /**全屋方案UUID**/
    private String fullHousePlanUUID;

}
