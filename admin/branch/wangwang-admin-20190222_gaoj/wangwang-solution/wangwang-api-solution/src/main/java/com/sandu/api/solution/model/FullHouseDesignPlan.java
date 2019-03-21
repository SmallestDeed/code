package com.sandu.api.solution.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 15:18 2018/8/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullHouseDesignPlan implements Serializable {
    private Integer id;

    private String uuid;

    private String planCode;

    private String planName;

    private Integer planStyleId;

    private String planStyleName;

    private Integer planPicId;

    private String planDescribe;

    private Integer companyId;

    private String brandId;

    private Integer userId;

    private Integer sourceType;

    private Integer sourcePlanId;

    private Integer openState;

    private String vrResourceUuid;

    private Integer version;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String remark;

    private Date openTime;

    private Integer descFileId;

    private Integer salePriceChargeType;

    private Double salePrice;

    private Integer chargeType;

    private Double planPrice;

    // 是否改变过：0 -> 否；1 -> 是
    private Integer isChanged;
}