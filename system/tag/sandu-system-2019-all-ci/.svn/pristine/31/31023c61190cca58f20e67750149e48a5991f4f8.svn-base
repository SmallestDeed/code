package com.sandu.api.feedback.service;

import com.sandu.api.feedback.input.SysFeedbackAdd;
import com.sandu.api.feedback.input.SysFeedbackQuery;
import com.sandu.api.feedback.model.SysUserFeedback;
import com.sandu.api.feedback.output.FeedbackCompany;
import com.sandu.api.feedback.output.SysFeedbackManageListVO;
import com.sandu.api.feedback.output.SysFeedbackWebListVO;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-feedback
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Dec-13 18:05
 */
@Component
public interface SysFeedbackService {

    /**
     * 插入
     *
     * @param feedback
     * @return
     */
    int add(SysUserFeedback feedback);

    /**
     * 更新
     *
     * @param feedback
     * @return
     */
    int modifier(SysUserFeedback feedback);

    /**
     * 删除
     * @param feedbackId
     * @return
     */
    int delete(Long feedbackId);

    /**
     * 通过ID获取详情
     * @param feedbackId
     * @return
     */
     SysUserFeedback getById(Long feedbackId);

    /**
     * 前端---查询反馈列表
     * @param userId 用户Id
     * @return
     */
    List<SysFeedbackWebListVO> getListByUserId(Long userId,Integer platformId);


    /**
     * 商家后台---查询反馈列表
     * @param userId 用户Id
     * @return
     */
    List<SysUserFeedback> getByUserId(Long userId,Integer platformId);

    /**
     * 后台管理--条件查询反馈数量
     * @return int
     */
    int getCount(SysFeedbackQuery query);

    /**
     * 后台管理--条件查询反馈列表
     * @return List
     */
    List<SysFeedbackManageListVO> getList(SysFeedbackQuery query);


    /**
     * 查询反馈企业
     * @return
     */
    List<FeedbackCompany>getFeedbackCompany();


}
