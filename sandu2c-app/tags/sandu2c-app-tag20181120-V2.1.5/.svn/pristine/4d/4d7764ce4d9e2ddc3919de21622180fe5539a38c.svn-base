package com.sandu.user.service;

import com.nork.common.model.LoginUser;
import com.sandu.user.model.input.UserDemandReviewsAdd;
import com.sandu.user.model.input.UserReviewsAdd;
import com.sandu.user.model.view.UserReviewsVo;

import java.util.List;

/**
 * @ Author     ：weisheng.
 * @ Date       ：Created in PM 3:18 2018/5/4 0004
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
public interface UserReviewsService {

    int getAllUserReviewsCount(Integer supplyDemandId, Integer userId, Integer isRead);

    List<UserReviewsVo> getAllUserReviews(Integer supplyDemandId, Integer userId, int start, int limit, String s, Integer order);

    Integer addSupplyDemandUserReviews(UserReviewsAdd userReviewsAdd);

    int addUserReviews(UserDemandReviewsAdd userDemandReviewsAdd, LoginUser id);

    int updateReviewsIsRead(Integer reviewsId);
}
