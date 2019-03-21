package com.sandu.service.feedback.impl;

import com.sandu.api.feedback.input.SysFeedbackQuery;
import com.sandu.api.feedback.model.SysUserFeedback;
import com.sandu.api.feedback.output.FeedbackCompany;
import com.sandu.api.feedback.output.SysFeedbackManageListVO;
import com.sandu.api.feedback.output.SysFeedbackWebListVO;
import com.sandu.api.feedback.service.SysFeedbackService;
import com.sandu.api.pic.model.po.ResPicPO;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.service.feedback.dao.SysFeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/12/13  19:24
 */
@Service("sysFeedbackService")
public class SysFeedbackServiceImpl implements SysFeedbackService {

    @Autowired
    private SysFeedbackMapper sysFeedbackMapper;

    @Autowired
    private ResPicService resPicService;

    @Override
    public int add(SysUserFeedback feedback) {
        return sysFeedbackMapper.insert(feedback);
    }

    @Override
    public int modifier(SysUserFeedback feedback) {
        return sysFeedbackMapper.update(feedback);
    }

    @Override
    public int delete(Long feedbackId) {
        return sysFeedbackMapper.delete(feedbackId);
    }

    @Override
    public SysUserFeedback getById(Long feedbackId) {
        return sysFeedbackMapper.selectDetailById(feedbackId);
    }

    @Override
    public List<SysFeedbackWebListVO> getListByUserId(Long userId,Integer platformId) {
        return sysFeedbackMapper.selectListByUserId(userId,platformId);
    }

    @Override
    public List<SysUserFeedback> getByUserId(Long userId,Integer platformId) {
        return sysFeedbackMapper.getByUserId(userId,platformId);
    }

    @Override
    public int getCount(SysFeedbackQuery query) {
        return sysFeedbackMapper.selectCount(query);
    }

    @Override
    public List<SysFeedbackManageListVO> getList(SysFeedbackQuery query) {
        return sysFeedbackMapper.selectList(query);
    }


    @Override
    public List<FeedbackCompany> getFeedbackCompany() {
        return sysFeedbackMapper.getFeedbackCompany();
    }


}
