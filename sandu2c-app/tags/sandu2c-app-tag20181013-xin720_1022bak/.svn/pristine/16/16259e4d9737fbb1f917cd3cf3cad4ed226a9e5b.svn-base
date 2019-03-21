package com.sandu.user.model.view;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:46 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.user.model.UserReply;
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
    private String picPath;


    /**
     * 业务ID
     */
    private Integer businessId;

    /**
     * 回复列表
     */
    private List<UserReply> userReplyList;


}
