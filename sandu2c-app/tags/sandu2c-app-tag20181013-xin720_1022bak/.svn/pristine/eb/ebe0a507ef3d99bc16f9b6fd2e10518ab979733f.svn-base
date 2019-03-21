package com.sandu.designplan.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 产品相关推荐方案
 * @auth pengxuangang
 * @date 20171121
 */
@Data
public class RecommendedPlanProductRelatedVo implements Serializable {

    private static final long serialVersionUID = -4858042872181043331L;

    //推荐方案ID
    private Integer recommendPlanId;
    //推荐方案名称
    private String recommendPlanName;
    //户型类型名
    private String houseTypeName;
    //户型风格名
    private String houseStyleName;
    //户型面积
    private Integer houseArea;
    //推荐方案封面
    private String recommendPlanCover;
    //推荐方案封面图片ID
    private Integer recommendPlanCoverPicId;
    //渲染对象
    private Map<Integer, List<String>> renderMap;
    //是否点赞(0:否,1:已点赞)
    private Integer isLike;
    //是否收藏(0:否,1:已收藏)
    private Integer isFavorite;
    //方案点赞数
    private Integer likeNum;
    //方案收藏数
    private Integer collectNum;
    
    private Integer spaceType;
    
    private String designPlanCoverPath;
    private String designPlanName;
    private String spaceStyleName;
    private String spaceArea;
    private Integer designPlanRecommendId;
    /**主方案id 0没有打组，和designPlanRecommendId相同为主方案，不同则为主方案id**/
    private Integer groupPrimaryId;
    //推荐方案适用面积
    private String applySpaceAreas;
    // 浏览量
    private Integer viewNum;

    //方案是否收费
    private Integer chargeType;

    //方案被使用次数
    private Integer useCount;

    //方案价格
    private Double planPrice;

    //用户是否需要购买方案版权:0.不需要;1.需要
    private Integer copyRightPermission;

    //判断是否已购买方案.0.未购买 1.已购买
    private Integer havePurchased;

}
