package com.sandu.api.springFestivalActivity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxRedPacketSummary {
    private Long id;

    private Long activityId;

    private Date signinDate;

    private BigDecimal redPacketDayNum;

    private BigDecimal redPacketUseNum;

    private BigDecimal redPacketRemainNum;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

    private BigDecimal preRedPackRemainNum;
}