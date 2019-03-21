package com.sandu.comment.dao;


import com.sandu.comment.model.WxCommentPraiseRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WxCommentPraiseRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxCommentPraiseRecord record);

    int insertSelective(WxCommentPraiseRecord record);

    WxCommentPraiseRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxCommentPraiseRecord record);

    int updateByPrimaryKey(WxCommentPraiseRecord record);

    /**
     * 根据用户id，评论id删除点赞记录
     *
     * @param userId
     * @param commentId
     * @return
     */
    Integer deleteByUserIdAndCommentId(@Param("userId") Long userId, @Param("commentId") Long commentId);
}