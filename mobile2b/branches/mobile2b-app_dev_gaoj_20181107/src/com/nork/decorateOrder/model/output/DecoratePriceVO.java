package com.nork.decorateOrder.model.output;

import com.nork.decorateOrder.model.ProprietorInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台派单列表查询---输出实体
 * @author WangHaiLin
 * @date 2018/10/20  14:10
 */
@Data
public class DecoratePriceVO extends ProprietorInfo implements Serializable{

    /**报价Id**/
    private Long priceId;

    /**报价状态**/
    private Integer priceStatus;

    /**方案图片**/
    private String planPicPath;

    /**方案名称**/
    private String planName;

    /**方案Id**/
    private Long planRecommendedId;


    /**截止时间**/
    private Date endTime;

    /**报价剩余时间毫秒数**/
    private Long offerRemainingTime;

}
