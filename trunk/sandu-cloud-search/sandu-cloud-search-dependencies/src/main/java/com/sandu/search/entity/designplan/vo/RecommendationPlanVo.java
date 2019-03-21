package com.sandu.search.entity.designplan.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐方案VO对象
 *
 * @date 2018/6/1
 * @auth pengxuangang
 * @mail xuangangpeng@gmail.com
 */
@Data
public class RecommendationPlanVo implements Serializable {

    private static final long serialVersionUID = 4544210795687880753L;

    private Integer id;

    //设计方案ID
    private Integer designPlanId;
    //推荐方案类型(1:分享,2:一键装修)
    private Integer type;
    //名称
    private String name;
    //小区ID
    private Integer livingId;
    //备注
    private String remark;
    //设计样板房ID
    private Integer designTemplateId;
    //设计风格ID
    private Integer designStyleId;
    //空间功能类型
    private Integer spaceFunctionId;
    //空间面积
    private String spaceAreas;
    //空间形状
    private String spaceShape;
    //封面图片Url
    private String coverPicPath;
    //风格名
    private String styleName;
    //品牌名
    private String brandName;
    //收藏
    private int collect;
    //点赞
    private int like;
    //收藏数
    private int collectCount;
    //点赞数
    private int likeCount;
    //发布时间
    private Date publishTime;
    //适用空间面积
    private String applySpaceAreas;
}
