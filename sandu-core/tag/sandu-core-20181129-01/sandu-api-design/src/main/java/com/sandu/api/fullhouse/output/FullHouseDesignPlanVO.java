package com.sandu.api.fullhouse.output;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 全屋方案列表页面全屋方案输出对象
 */
@Data
public class FullHouseDesignPlanVO implements Serializable {
    /**
     * 全屋方案ID
     */
    private Integer fullHouseId;
    /**
     * 全屋方案名称
     */
    private String designPlanName;
    /**
     * 全屋方案创建时间
     */
    private Date gmtCreated;
    /**
     * 全屋方案缩略图
     */
    private String picPath;
    /**
     * 全屋方案风格名称
     */
    private String designPlanStyleName;
}
