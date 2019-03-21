package com.sandu.service.feedback.dao;

import com.sandu.api.feedback.input.SysFeedbackQuery;
import com.sandu.api.feedback.model.SysUserFeedback;
import com.sandu.api.feedback.output.FeedbackCompany;
import com.sandu.api.feedback.output.SysFeedbackManageListVO;
import com.sandu.api.feedback.output.SysFeedbackWebListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/12/13  19:25
 */
@Repository
public interface SysFeedbackMapper {
    /**
     * 插入
     *
     * @param feedback
     * @return
     */
    int insert(SysUserFeedback feedback);

    /**
     * 更新
     *
     * @param feedback
     * @return
     */
    int update(SysUserFeedback feedback);

    /**
     * 删除
     * @param feedbackId
     * @return
     */
    int delete(@Param("feedbackId") Long feedbackId);

    /**
     * 通过ID获取详情
     * @param feedbackId
     * @return
     */
    SysUserFeedback selectDetailById(@Param("feedbackId")Long feedbackId);

    /**
     * 前端---查询列表
     * @param userId
     * @return
     */
    List<SysFeedbackWebListVO> selectListByUserId(@Param("userId")Long userId,
                                                  @Param("platformId")Integer platformId);

    /**
     * 商家后台---查询列表
     * @param userId
     * @return
     */
    List<SysUserFeedback> getByUserId(@Param("userId") Long userId,
                                      @Param("platformId")Integer platformId);

    /**
     * 后台管理--条件查询反馈数量
     * @param query
     * @return
     */
    int selectCount(SysFeedbackQuery query);

    /**
     * 后台管理--条件查询反馈列表
     * @return List
     */
    List<SysFeedbackManageListVO> selectList(SysFeedbackQuery query);


    /**
     * 查询反馈企业
     * @return
     */
    List<FeedbackCompany>getFeedbackCompany();

}
