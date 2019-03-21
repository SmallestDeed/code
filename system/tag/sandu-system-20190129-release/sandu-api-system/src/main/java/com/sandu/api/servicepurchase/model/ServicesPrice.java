package com.sandu.api.servicepurchase.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @author sandu-lipeiyuan
 */
@Data
public class ServicesPrice implements Serializable {
    private static final long serialVersionUID = 8367056931259434942L;
    /**
     * 套餐价格id
     */
    private Long id;

    /**
     * 企业id
     */
    private Integer companyId;

    /**
     * 套餐id
     */
    private Long servicesId;

    /**
     * 价格年限
     */
    private Integer duration;

    /**
     * 赠送天数
     */
    private Integer giveDuration;

    /**
     * 免费渲染时间
     */
    private Integer freeRenderDuration;

    /**
     * 赠送度币
     */
    private Integer sanduCurrency;

    /**
     * 套餐价格
     */
    private BigDecimal price;

    /**
     * 价格时间单元
     */
    private String priceUnit;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改者
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除
     */
    private Integer isDeleted;


}