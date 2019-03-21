package com.sandu.api.servicepurchase.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ServicesAccountRel implements Serializable {
    private static final long serialVersionUID = 4710905170817582034L;
    private Long id;

    private String companyId;

    private Long servicesId;

    private Long purchaseRecordId;

    private String account;

    private String password;

    private String status;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Date effectiveBegin;

    private Date effectiveEnd;

    private Integer isDeleted;

    private String businessType;

    private Long userId;


}