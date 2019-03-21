package com.sandu.search.entity.designplan.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐方案搜索业务对象
 *
 * @date 2018/6/1
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Data
public class RecommendationPlanSearchVo implements Serializable {

    private static final long serialVersionUID = 4751804971686843788L;

    //发布时间正序
    public static final int RELEASE_TIME_SORT_ASC = 1;
    //发布时间逆序
    public static final int RELEASE_TIME_SORT_DESC = 2;

    //房屋类型
    private Integer houseType;
    //推荐方案类型
    private Integer recommendationPlanType;
    //发布状态
    private Integer releaseStatus;
    //风格ID
    private Integer designStyleId;
    //公司ID
    private Integer companyId;
    //空间面积
    private String spaceAreas;
    //排序类型(1:发布时间正序, 2:发布时间逆序)
    private int sortType;
    //方案来源
    private List<String> planSource;

}
