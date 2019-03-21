package com.sandu.api.customer.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author sandu-lipeiyuan
 */
@Data
public class CustomerAlotZone implements Serializable {
    private static final long serialVersionUID = -7121556577803336324L;
    private Long id;

    private Integer companyId;

    private Integer channelCompanyId;

    private String provinceCode;

    private String cityCode;

    private String areaCode;

    private String longCode;

    private Integer sourceType;

    private Integer allotCount;

    private String creator;

    private LocalDateTime gmtCreate;

    private String modifier;

    private LocalDateTime gmtModified;

    private Integer isDeleted;


}