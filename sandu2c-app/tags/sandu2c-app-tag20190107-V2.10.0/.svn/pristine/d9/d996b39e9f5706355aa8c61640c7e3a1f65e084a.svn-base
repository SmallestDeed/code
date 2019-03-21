package com.sandu.user.dao;

import com.sandu.user.model.UserReviews;
import com.sandu.user.model.view.UserReviewsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserReviewsMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(UserReviews record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(UserReviews record);

    /**
     *
     * @mbggenerated
     */
    UserReviews selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserReviews record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserReviews record);


    List<UserReviewsVo> selectAllUserReviews(@Param("supplyDemandId") Integer supplyDemandId, @Param("userId") Integer userId, @Param("start") int start, @Param("limit") int limit, @Param("order") String order,@Param("isRead") Integer isRead);

    int selectAllUserReviewsCount(@Param("supplyDemandId") Integer supplyDemandId, @Param("userId") Integer userId,@Param("isRead") Integer isRead);

    List<String> selectReviewsPicPath(List<Integer> coverPicIdList);

    int updateReviewsIsRead(Integer reviewsId);

    int delByIdAndUserId(UserReviews reviews);
}