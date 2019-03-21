package com.sandu.comment.dao;


import com.sandu.comment.input.WxCommentRecordQuery;
import com.sandu.comment.model.WxCommentRecord;
import com.sandu.goods.model.BaseGoodsSKU;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxCommentRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxCommentRecord record);

    int insertSelective(WxCommentRecord record);

    WxCommentRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxCommentRecord record);

    int updateByPrimaryKey(WxCommentRecord record);

    /**
     * 评论列表
     *
     * @param query
     * @return
     */
    List<WxCommentRecord> queryAll(WxCommentRecordQuery query);

    /**
     * 更新评论点赞
     *
     * @param commentId
     * @param count
     * @return
     */
    Integer updatePraiseCount(@Param("commentId") Long commentId, @Param("count") Integer count);

    /**
     * 获取评论总数
     * @return
     */
    Integer getCommentListCount(WxCommentRecordQuery query);

    Integer updateOrderCommentStatus(@Param("orderId") Long orderId, @Param("commentStatus") int commentStatus);

    List<BaseGoodsSKU> goodsSKUMap(List<Integer> listProductId);

    WxCommentRecord checkCommented(@Param("orderId") Long orderId,
                                   @Param("planId") Long planId);
}