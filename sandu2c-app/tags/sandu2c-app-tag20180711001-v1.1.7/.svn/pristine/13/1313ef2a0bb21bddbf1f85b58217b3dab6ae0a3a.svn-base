package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:19 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.common.util.Utils;
import com.sandu.supplydemand.input.BaseSupplyDemandAdd;
import com.sandu.user.dao.UserReplyMapper;
import com.sandu.user.dao.UserReviewsMapper;
import com.sandu.user.model.UserReply;
import com.sandu.user.model.UserReviews;
import com.sandu.user.model.input.UserReviewsAdd;
import com.sandu.user.model.view.UserReviewsVo;
import com.sandu.user.service.UserReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Title: 用户评论
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/5/4 0004PM 3:19
 */
@Service("userReviewsService")
public class UserReviewsServiceImpl implements UserReviewsService{

    @Autowired
    private UserReviewsMapper userReviewsMapper;

    @Autowired
    private UserReplyMapper userReplyMapper;

    @Override
    public int getAllUserReviewsCount(BaseSupplyDemandAdd baseSupplyDemandAdd) {
        return userReviewsMapper.selectAllUserReviewsCount(baseSupplyDemandAdd);
    }

    @Override
    public List<UserReviewsVo> getAllUserReviews(BaseSupplyDemandAdd baseSupplyDemandAdd) {
        return userReviewsMapper.selectAllUserReviews(baseSupplyDemandAdd);
    }

    @Override
    public Integer addSupplyDemandUserReviews(UserReviewsAdd userReviewsAdd) {
        //封装用户评论信息
        UserReviews userReviews = new UserReviews();
        userReviews.setUserId(userReviewsAdd.getUserId());
        userReviews.setReviewsMsg(userReviewsAdd.getReviewsMsg());
        userReviews.setCreator(userReviewsAdd.getCreator());
        userReviews.setBusinessId(userReviewsAdd.getBusinessId());
        Date date =new Date();
        userReviews.setGmtCreate(date);
        userReviews.setIsDeleted(0);
        userReviewsMapper.insertSelective(userReviews);
        return userReviews.getId();
    }
}
