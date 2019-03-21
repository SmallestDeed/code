package com.sandu.api.servicepurchase.input.query;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class ServicesPurchaseRecordQuery implements Serializable {
    private Long id;

    private String companyId;

    private String orderNo;

    private Long servicesId;

    private Integer servicesPriceId;

    private String userScope;

    private String purchaseSource;

    private BigDecimal unitPrice;

    private BigDecimal purchaseAmount;

    private Integer duration;

    private Integer giveDuration;

    private Integer freeRenderDuration;

    private Integer sanduCurrency;

    private BigDecimal purchaseMomey;

    private String payType;

    private Date purchaseTime;

    private String purchaseStatus;

    private String remark;

    private String userid;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private Integer BusinessType;

    /**
     * 价格时间单元
     */
    private String priceUnit;

    private String telephone;

    /**
     * 当前第几页
     */
    private Integer start;
    /**
     * 每页显示多少条
     */
    private Integer limit;

}
