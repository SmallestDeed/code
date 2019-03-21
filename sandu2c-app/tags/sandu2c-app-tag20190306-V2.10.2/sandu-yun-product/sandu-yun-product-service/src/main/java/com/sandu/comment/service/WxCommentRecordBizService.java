package com.sandu.comment.service;


import com.sandu.comment.input.WxCommentRecordAdd;
import com.sandu.comment.input.WxCommentRecordQuery;
import com.sandu.comment.model.WxCommentRecord;
import com.sandu.comment.output.WxCommentRecordVO;

import java.util.List;


public interface WxCommentRecordBizService {
    /**
     * 新增评论
     *
     * @param input
     * @return
     */
    int createComment(WxCommentRecordAdd input);
    int createComment(List<WxCommentRecordAdd> input);

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    int deleteComment(Long id);

    /**
     * 评论列表
     *
     * @param query
     * @return
     */
    List<WxCommentRecordVO> queryAll(WxCommentRecordQuery query);

    /**
     * 点赞or取消点赞
     *
     * @param userId
     * @param commentId
     * @param type
     * @return
     */
    Integer changePraiseState(Long userId, Long commentId, Long type);

    /**
     * 获取评论总数
     *
     * @return
     * @param query
     */
    Integer getCommentListCount(WxCommentRecordQuery query);

    /**
     * 检查是否评论
     *
     * @param orderId
     * @return
     */
    WxCommentRecord checkCommented(Long orderId, Long planId);
}
