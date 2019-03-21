package com.sandu.search.entity.designplan.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐方案持久化对象
 *
 * @date 2018/5/30
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Data
public class RecommendationPlanPo implements Serializable {

    private static final long serialVersionUID = -1755077915064748546L;

    //推荐方案类型-分享
    public static final int SHARE_RECOMMENDATION_PLAN_TYPE = 1;
    //推荐方案类型-一键装修
    public static final int ONEKEY_RECOMMENDATION_PLAN_TYPE = 2;

    //设计方案是否发布-否
    public static final int DESIGN_PLAN_NOT_IS_PUBLISH = 0;
    //设计方案是否发布-是
    public static final int DESIGN_PLAN_IS_PUBLISH = 1;

    //分享方案来源
    public static final String SHARE_PLAN_SOURCE = "share";
    //交付方案来源
    public static final String DELIVER_PLAN_SOURCE = "deliver";

    private Integer id;
    //推荐方案类型(1:分享,2:一键装修)
    private Integer type;
    //设计方案ID
    private Integer designPlanId;
    //名称
    private String name;
    //设计者ID
    private Integer createUserId;
    //小区ID
    private Integer livingId;
    //户型ID
    private Integer houseId;
    //备注
    private String remark;
    //是否支持一键装修
    private Integer isSupportOneKeyDecorate;
    //封面图片ID
    private Integer coverPicId;
    //设计样板房ID
    private Integer designTemplateId;
    //设计风格ID
    private Integer designStyleId;
    //创建时间
    private Date createTime;
    //修改时间
    private Date modifyTime;
    //发布时间
    private Date publishTime;
    //方案来源
    private String planSource;
    //适用空间面积
    private String applySpaceAreas;
    //是否发布(0:否,1:是)
    private Integer releaseStatus;
    //是否删除(0:否,1:是)
    private Integer dataIsDeleted;
}
