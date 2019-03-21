package com.sandu.comment.service.impl;

import com.sandu.comment.dao.WxCommentPraiseRecordMapper;
import com.sandu.comment.model.WxCommentPraiseRecord;
import com.sandu.comment.service.WxCommentPraiseRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: YuXingchi
 * @Description:
 * @Date: Created in 16:23 2018/12/28
 */

@Slf4j
@Service("wxCommentPraiseRecordService")
public class WxCommentPraiseRecordServiceImpl implements WxCommentPraiseRecordService {

    @Autowired
    private WxCommentPraiseRecordMapper wxCommentPraiseRecordMapper;

    @Override
    public Integer deleteByUserIdAndCommentId(Long userId, Long commentId) {
        return wxCommentPraiseRecordMapper.deleteByUserIdAndCommentId(userId,commentId);
    }

    @Override
    public Integer addPraiseRecord(WxCommentPraiseRecord wxCommentPraiseRecord) {
        return wxCommentPraiseRecordMapper.insertSelective(wxCommentPraiseRecord);
    }
}
