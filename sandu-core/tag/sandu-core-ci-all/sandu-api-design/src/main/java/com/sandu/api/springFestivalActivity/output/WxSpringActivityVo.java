package com.sandu.api.springFestivalActivity.output;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WxSpringActivityVo implements Serializable {
    /**
     * 活动ID
     */
    private Long id;
    /**
     * 转盘ID
     */
    private String luckyWheelId;
}
