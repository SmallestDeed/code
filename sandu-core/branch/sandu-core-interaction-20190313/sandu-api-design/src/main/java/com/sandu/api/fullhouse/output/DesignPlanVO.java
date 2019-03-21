package com.sandu.api.fullhouse.output;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 添加单空间方案页面方案模型
 */
@Data
public class DesignPlanVO implements Serializable {
    /**
     * 组合方案的话ID为主方案的ID，否则为当前方案ID
     */
    private Integer designPlanId;
    /**
     * 组合方案的话为主方案的名称，否则为当前方案名称
     */
    private String designPlanName;
    /**
     * 组合方案的话为主方案的创建时间，否则为当前方案的创建时间
     */
    private Date gmtCreated;
    /**
     * 组合方案的话为主方案的缩略图，否则为当前方案的缩略图
     */
    private String picPath;
    /**
     * 是否是组合方案，组合方案有精选标签
     */
    private Integer isGroup;
    /**
     * 空间类型(1:客厅,2:餐厅,3:客餐厅,4:卧室,5:厨房,6:卫生间,7:书房,8:衣帽间)
     */
    private Integer spaceType;
}
