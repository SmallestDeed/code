package com.sandu.api.designplan.output;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐方案查询结果对象
 */
@Data
public class DesignPlanRecommendedResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer planRecommendedId;

    private Integer planId;
    private Integer renderCount;

    private Integer designPlanRecommendId;
    private String designPlanName;
    private String spaceStyleName;
    private String spaceArea;
    private String designPlanCoverPath;
    private Integer spaceType;

	private String planName;
    //适用于空间面积
    private String applySpaceAreas;
    
	/*封面图路径*/
    private String coverPath;

    //空间形状
    private Integer spaceShape;

    /*设计方案是否发布 0 否 1是*/
    private Integer isRelease;

    private Date gmtModified;

    private Date gmtCreate;

    /*面积*/
    private String spaceAreas;

    /*风格*/
    private String styleName;

    /*创建者*/
    private String creator;

    private String houseTypeName;

    private Integer spaceFunctionId;

    /*发布时间*/
    private Date releaseTime;

    private Integer livingId;

    private Integer houseId;

    private String remark;

    private String planSource;

    /*是否支持一件装修*/
    private Integer isDefaultDecorate;

    /*最新720渲染图地址链接*/
    private String resRenderPicPath;
    /*推荐收藏Bid*/
    private String bid;
    /*
     * 收藏夹name*/
    private String favoriteName;

    //是否点赞(0:否,1:已点赞)
    private Integer isLike;
	//是否收藏(0:否,1:已收藏)
    private Integer isFavorite;
    //方案点赞数
    private Integer likeNum;
    //方案收藏数
    private Integer collectNum;


    @Override
    public String toString() {
        return "DesignPlanRecommendedResult{" +
                "planRecommendedId=" + planRecommendedId +
                ", planId=" + planId +
                ", planName='" + planName + '\'' +
                ", coverPath='" + coverPath + '\'' +
                ", spaceShape=" + spaceShape +
                ", isRelease=" + isRelease +
                ", gmtModified=" + gmtModified +
                ", gmtCreate=" + gmtCreate +
                ", spaceAreas='" + spaceAreas + '\'' +
                ", styleName='" + styleName + '\'' +
                ", creator='" + creator + '\'' +
                ", houseTypeName='" + houseTypeName + '\'' +
                ", spaceFunctionId=" + spaceFunctionId +
                ", releaseTime=" + releaseTime +
                ", livingId=" + livingId +
                ", houseId=" + houseId +
                ", remark='" + remark + '\'' +
                ", planSource='" + planSource + '\'' +
                ", isDefaultDecorate=" + isDefaultDecorate +
                ", resRenderPicPath='" + resRenderPicPath + '\'' +
                ", bid='" + bid + '\'' +
                ", favoriteName='" + favoriteName + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
