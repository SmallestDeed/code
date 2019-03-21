package com.sandu.api.base.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BaseBrand implements Serializable {
    private Long id;

    private String sysCode;

    private String brandName;

    private Integer companyId;

    private String brandLogo;

    private String brandDesc;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private String brandCode;

    private String att1;

    private String att2;

    private String att3;

    private String att4;

    private String att5;

    private String att6;

    private Date dateAtt1;

    private Date dateAtt2;

    private Integer numAtt1;

    private Integer numAtt2;

    private BigDecimal numAtt3;

    private BigDecimal numAtt4;

    private Integer brandStyleId;

    private String brandReferred;

    private Integer statusShowWu;

    private String remark;
}