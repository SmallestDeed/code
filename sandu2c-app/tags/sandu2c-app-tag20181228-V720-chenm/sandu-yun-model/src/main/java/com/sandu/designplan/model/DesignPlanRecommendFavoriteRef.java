/**
 * 文件名：DesignPlanRecommendFavoriteRef.java
 * <p>
 * 版本信息：
 * 日期：2017年8月2日
 * Copyright 足下 Corporation 2017
 * 版权所有
 */
package com.sandu.designplan.model;

import com.sandu.common.model.Mapper;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目名称：timeSpace
 * 类名称：DesignPlanRecommendFavoriteRef
 * 类描述：    推荐方案收藏关联表
 * 创建人：Timy.Liu
 * 创建时间：2017年8月2日 上午11:34:01
 * 修改人：Timy.Liu
 * 修改时间：2017年8月2日 上午11:34:01
 * 修改备注：
 */
@Data
public class DesignPlanRecommendFavoriteRef extends Mapper implements Serializable {
    /**
     * serialVersionUID:TODO 方法描述：
     *
     * @since Ver 1.1
     */

    private static final long serialVersionUID = 1L;
    /**
     * 收藏夹业务UUID
     */
    private String bid;
    /**
     * 收藏夹业务id
     */
    private String fid;
    /**
     * 推荐方案id
     */
    private int recommendId;
    /**
     * 对应分享的次数
     */
    private int shareTimes;
    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 修改时间
     */
    private Date utime;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 是否收藏（0：未收藏，1：已收藏）
     */
    private Integer status;

    /**
     * 全屋方案ID
     */
    private Integer fullHouseDesignPlanId;

    /**
     * 方案类型(1:单空间方案,2:全屋方案)
     */
    private Integer designPlanType;

}
