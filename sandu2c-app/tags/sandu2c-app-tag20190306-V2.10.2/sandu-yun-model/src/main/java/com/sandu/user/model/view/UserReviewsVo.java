package com.sandu.user.model.view;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:46 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.designplan.model.DesignPlanRecommendedResult;
import com.sandu.designplan.vo.MydecorationPlanVo;
import com.sandu.home.model.BaseHouse;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Title: 用户评论返回对象
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/5/4 0004PM 3:46
 */
@Data
public class UserReviewsVo implements Serializable{
    private static final long serialVersionUID = 5661806585066920768L;

    /**
     * 评论ID
     */
    private Integer id;

    /**
     * 评论内容
     */
    private String reviewsMsg;

    /**
     * 评论时间
     */
    private Date gmtCreate;

    /**
     * 评论人昵称
     */
    private String nickName;

    /**
     * 评论人头像
     */
    private String userPicPath;


    /**
     * 需求信息ID
     */
    private Integer businessId;

    /**
     * 评论图片地址
     */
    private String  picIds;

    private String coverPicPath;

    private List<String> picPathList;


    /**
     * 评论方案ID
     */
    private Integer planId;

    /**
     * 评论方案ID
     */
    private Integer planType;

    /**
     * 评论户型ID
     */
    private Integer houseId;

    /**
     * 方案详情
     */
    private DesignPlanRecommendedResult designPlanRecommendedResult;

    /**
     * 绘制户型详情
     */
    private BaseHouse baseHouse;


    /**
      单空间设计方案详情
     */
    private MydecorationPlanVo mydecorationPlanVo;
    /**
     * 是否点赞
     */
    private Integer isLike;
    /**
     * 点赞数量
     */
    private Integer likeNum;


    private Integer isRead;
    /**
     * 供求信息封面图IDS
     */
    private String demandCoverPicId;

    private String userTypeName;//用户角色身份

    private Integer sex;

    private Integer userId;

    private Integer isTop; //置顶次数

    private boolean topSign=false; //false,true

    private Integer supplyDemandPublisherId;//信息发布者ID

    private boolean delAuthor = false; //false:无删除权限 true: 有删除权限

    private boolean topReviewsAuthor = false; //false:无置顶帖子评论权限false : 有置顶帖子评论权限true

    private Integer userPlatformType; //平台类型:1:C端用户,2:B端用户'

    private Integer shopId; //店铺ID

 private Integer virtualLikeNum;
    /**供求信息内容（可文字图片按格式存版）**/
    private String content;

    public Integer getLikeNum() {
        return likeNum==null?0:likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

}
