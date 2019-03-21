package com.sandu.designplan.model;


import com.sandu.user.model.LoginUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlanRecommendedListModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msgId;

    private LoginUser loginUser;

    private String creator;

    private String brandName;

    private String houseType;

    private String livingName;

    private String areaValue;

    private String designRecommendedStyleId;

    private String displayType;
    
    //根据发布时间排序
    private Integer isSortByReleaseTime;
    //根据渲染次数排序
    private Integer isSortByRenderCount;
   
    private Integer platformId;
	//空间形状
    private Integer spaceShape;

    private int start;
    private int limit;

    private Integer templateId;
    //品牌
    private List<Integer> brandList;
    //公司id
    private Integer companyId;

    private String planName;
    //装修报价类型
    private Integer decoratePriceType;
    //装修报价区间
    private Integer decoratePriceRange;
    // 推荐方案ID
    private Integer id;
}
