package com.sandu.user.service.impl;/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:07 2018/5/15 0015
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */

import com.sandu.user.dao.UserReplyMapper;
import com.sandu.user.model.UserReply;
import com.sandu.user.model.input.UserReviewsAdd;
import com.sandu.user.service.UserReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Title: 用户回复实现类
 * @Package
 * @Description:
 * @author weisheng
 * @date 2018/5/15 0015AM 10:07
 */
@Service("userReplyService")
public class UserReplyServiceImpl implements UserReplyService {

    @Autowired
    private UserReplyMapper userReplyMapper;

    //对评论进行回复
    @Override
    public Integer addUserReplyToReviews(UserReviewsAdd userReviewsAdd) {
        //封装用户回复信息
        Date date = new Date();
        UserReply userReply = new UserReply();
        userReply.setReviewsId(userReviewsAdd.getReviewsId());
        userReply.setReplyReviewsId(userReviewsAdd.getReviewsId());
        userReply.setReplyType(userReviewsAdd.getReplyType());
        userReply.setCreator(userReviewsAdd.getCreator());
        userReply.setGmtCreate(date);
        userReply.setIsDeleted(0);
        userReply.setReplyMsg(userReviewsAdd.getReplyMsg());
        userReply.setBusinessId(userReviewsAdd.getBusinessId());
        userReplyMapper.insertSelective(userReply);
        return userReply.getId();
    }

    //对回复进行回复
    @Override
    public Integer addUserReplyToReply(UserReviewsAdd userReviewsAdd) {
        //封装用户回复信息
        Date date = new Date();
        UserReply userReply = new UserReply();
        userReply.setReviewsId(userReviewsAdd.getReviewsId());
        userReply.setReplyReviewsId(userReviewsAdd.getReplyReviewsId());
        userReply.setReplyType(userReviewsAdd.getReplyType());
        userReply.setCreator(userReviewsAdd.getCreator());
        userReply.setGmtCreate(date);
        userReply.setIsDeleted(0);
        userReply.setReplyMsg(userReviewsAdd.getReplyMsg());
        userReply.setBusinessId(userReviewsAdd.getBusinessId());
        userReplyMapper.insertSelective(userReply);
        return userReply.getId();
    }
}
