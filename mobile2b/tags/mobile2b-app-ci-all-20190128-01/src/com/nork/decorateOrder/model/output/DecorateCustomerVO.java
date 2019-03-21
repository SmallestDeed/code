package com.nork.decorateOrder.model.output;

import com.nork.decorateOrder.model.ProprietorInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的客户--输出实体
 * @author WangHaiLin
 * @date 2018/10/18  9:55
 */
@Data
public class DecorateCustomerVO extends ProprietorInfo implements Serializable{

    /**装修订单Id**/
    private Long orderId;

    /**装企userId**/
    private Long userId;

    /**订单状态(0-待支付;1-订单超时;2-待沟通;3-有意向;3-无意向;5-已签约;6-已完成)**/
    private Integer orderStatus;

    /**订单生成时间**/
    private Date orderTime;

    /**支付倒计时**/
    private long payRemainingTime;

    /**客单价**/
    private BigDecimal price;

    /**签约价格**/
    private BigDecimal contractFee;

    /**服务费**/
    private BigDecimal serviceFee;

    /**合同上传时间**/
    private Date contractUploadTime;

    /**订单类型(0-客户店铺预约;1-抢单;2-平台自动派单;3-内部推荐)**/
    private Integer orderType;

    /**合同状态(0-待上传;1-待审核;2-审核通过)**/
    private Integer contractStatus;

    /**服务费上缴截止时间**/
    private String endOfServicePayTimeStr;

    /**方案图片**/
    private String planPicPath;

    /**方案名称**/
    private String planName;

    /**方案Id**/
    private Long planRecommendedId;

    /**全屋方案UUID**/
    private String fullHousePlanUUID;




}
