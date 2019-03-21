package com.sandu.supplydemand.output;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 11:05 2018/11/10 0010
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.vo.MydecorationPlanVo;
import com.sandu.home.model.BaseHouse;
import com.sandu.supplydemand.model.SupplyDemandPic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Title:
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/11/10 0010AM 11:05
 */
@Data
public class SupplyDemandDetailVo implements Serializable{
    private static final long serialVersionUID = 2898880843584822710L;

    //标题,发布人头像,发布人昵称,详情,图片,方案详情,户型详情

    private Integer id;

    private String title;

    private Integer userId;


    private Integer viewNum;
    //供求信息图片信息
    private List<SupplyDemandPic> supplyDemandPicList;

    //供求信息默认图片信息
    private SupplyDemandPic supplyDemandCoverPic;

    private String userPicPath;

    private String nickName;

    private String description;

    private Date gmtPublish;

    private DesignPlanRecommendedResult designPlanRecommendedResult;

    private BaseHouse baseHouse;

    private MydecorationPlanVo mydecorationPlanVo;

    private Integer favoriteNum;

    private Integer likeNum;

    private Integer commentNum;

    private Integer isFavorite;

    private Integer isLike;

    private Integer planType; //1:单空间推荐方案.2:全屋单推荐方案,3:单空间我的设计方案,4:全屋我的设计方案

    private String type; //需求类型

    private String address;//详细地址

    private boolean editAuthor = false; //false:无编辑权限 true: 有编辑权限

    private boolean delAuthor = false; //false:无删除权限 true: 有删除权限

    /**供求信息内容（可文字图片按格式存版）**/
    private String content;
}
