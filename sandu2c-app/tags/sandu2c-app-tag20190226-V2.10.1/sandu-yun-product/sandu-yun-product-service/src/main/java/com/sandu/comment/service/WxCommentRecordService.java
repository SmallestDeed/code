package com.sandu.comment.service;


import com.sandu.comment.input.WxCommentRecordQuery;
import com.sandu.comment.model.WxCommentRecord;
import com.sandu.goods.model.BaseGoodsSKU;

import java.util.List;

public interface WxCommentRecordService {
    /**
     * 新增评论
     *
     * @param wxCommentRecord
     * @return
     */
    int create(WxCommentRecord wxCommentRecord);

    /**
     * 删除活动
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 评论列表
     *
     * @param query
     * @return
     */
    List<WxCommentRecord> queryAll(WxCommentRecordQuery query);

    /**
     * 更新评论点赞数
     *
     * @param commentId
     * @param count
     * @return
     */
    Integer updatePraiseCount(Long commentId, Integer count);

    /**
     * 获取评论总数
     * @return
     */
    Integer getCommentListCount(WxCommentRecordQuery query);

    Integer updateOrderCommentStatus(Long orderId, int commentStatus);

    List<BaseGoodsSKU> goodsSKUMap(List<Integer> listProductId);

    WxCommentRecord checkCommented(Long orderId, Long planId);
}
