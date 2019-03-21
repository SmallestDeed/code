package com.sandu.api.designplan.input;

import com.sandu.api.base.common.LoginUser;
import com.sandu.api.base.input.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 推荐方案列表查询条件
 *
 * @author xiaoxc
 * @data 2018-05-31
 */
@Data
public class PlanRecommendedQuery extends BaseQuery<PlanRecommendedQuery> {

    @ApiModelProperty(value = "u3d客户端标识")
    private String msgId;

    @ApiModelProperty(value = "用户对象")
    private LoginUser loginUser;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value ="空间类型")
    private String spaceType;

    @ApiModelProperty(value ="空间面积")
    private String spaceArea;

    @ApiModelProperty(value ="方案风格")
    private String designPlanStyleId;

    @ApiModelProperty(value = "小区名称")
    private String livingName;

    @ApiModelProperty(value ="方案类型(一键装修：decorate,公开方案：public)")
    private String displayType;

    @ApiModelProperty(value = "根据发布时间排序")
    private Integer isSortByReleaseTime;

    @ApiModelProperty(value = "根据渲染次数排序")
    private Integer isSortByRenderCount;

    @ApiModelProperty(value = "平台Id")
    private Integer platformId;

    @ApiModelProperty(value = "空间形状(长方形、正方形、凸形等)")
    private Integer spaceShape;

    @ApiModelProperty(value = "样板房ID")
    private Integer templateId;

    @ApiModelProperty(value = "品牌id集合")
    private List<Integer> brandList;

    @ApiModelProperty(value = "企业ID")
    private Integer companyId;

    @ApiModelProperty(value = "设计师ID")
    private Integer designerId;

    @ApiModelProperty(value = "方案名称")
    private String planName;


    @Override
    public String toString() {
        return "PlanRecommendedQuery{" +
                "msgId='" + msgId + '\'' +
                ", creator='" + creator + '\'' +
                ", brandName='" + brandName + '\'' +
                ", spaceType='" + spaceType + '\'' +
                ", spaceArea='" + spaceArea + '\'' +
                ", designPlanStyleId='" + designPlanStyleId + '\'' +
                ", livingName='" + livingName + '\'' +
                ", displayType='" + displayType + '\'' +
                ", isSortByReleaseTime=" + isSortByReleaseTime +
                ", isSortByRenderCount=" + isSortByRenderCount +
                ", platformId=" + platformId +
                ", spaceShape=" + spaceShape +
                ", templateId=" + templateId +
                ", brandList=" + brandList +
                ", companyId=" + companyId +
                ", planName='" + planName + '\'' +
                '}';
    }
}
