package com.sandu.comment.service.impl;


import com.sandu.comment.dao.WxCommentRecordMapper;
import com.sandu.comment.input.WxCommentRecordQuery;
import com.sandu.comment.model.WxCommentRecord;
import com.sandu.comment.service.WxCommentRecordService;
import com.sandu.goods.model.BaseGoodsSKU;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 16:09 2018/12/6
 */

@Slf4j
@Service("wxCommentRecordService")
public class WxCommentRecordServiceImpl implements WxCommentRecordService {

    @Autowired
    private WxCommentRecordMapper wxCommentRecordMapper;


    @Override
    public int create(WxCommentRecord wxCommentRecord) {
        if (wxCommentRecordMapper.insertSelective(wxCommentRecord) > 0) {
            return wxCommentRecord.getId().intValue();
        }
        return 0;
    }

    @Override
    public int delete(Long id) {
        return wxCommentRecordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<WxCommentRecord> queryAll(WxCommentRecordQuery query) {
        return wxCommentRecordMapper.queryAll(query);
    }

    @Override
    public Integer updatePraiseCount(Long commentId, Integer count) {
        return wxCommentRecordMapper.updatePraiseCount(commentId, count);
    }

    @Override
    public Integer getCommentListCount(WxCommentRecordQuery query) {
        return wxCommentRecordMapper.getCommentListCount(query);
    }

    @Override
    public Integer updateOrderCommentStatus(Long orderId, int commentStatus) {
        return wxCommentRecordMapper.updateOrderCommentStatus(orderId, commentStatus);
    }

    @Override
    public List<BaseGoodsSKU> goodsSKUMap(List<Integer> listProductId) {
        return wxCommentRecordMapper.goodsSKUMap(listProductId);
    }

    @Override
    public WxCommentRecord checkCommented(Long orderId, Long planId) {
        return wxCommentRecordMapper.checkCommented(orderId, planId);
    }
}
