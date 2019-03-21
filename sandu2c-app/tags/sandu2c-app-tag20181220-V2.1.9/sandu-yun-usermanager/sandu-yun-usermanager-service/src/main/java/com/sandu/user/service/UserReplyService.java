package com.sandu.user.service;

import com.sandu.user.model.input.UserReviewsAdd; /**
 * @ Author     ：weisheng.
 * @ Date       ：Created in AM 10:06 2018/5/15 0015
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
public interface UserReplyService {
    Integer addUserReplyToReviews(UserReviewsAdd userReviewsAdd);

    Integer addUserReplyToReply(UserReviewsAdd userReviewsAdd);
}
