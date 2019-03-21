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
public class WxSigninSummary {
    private Long id;

    private Long activityId;

    private Long userId;

    private BigDecimal redPacketNum;

    private Integer signTimes;

    private Integer lotteryTimes;

    private Integer lotteryUseTimes;

    private Integer lotteryRemainTimes;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;
}