package com.sandu.service.activity.dao;

import com.sandu.api.activity.model.WxSpringActivity;
import com.sandu.api.activity.model.WxSpringActivityBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxSpringActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxSpringActivity record);

    int insertSelective(WxSpringActivity record);

    WxSpringActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxSpringActivity record);

    int updateByPrimaryKey(WxSpringActivity record);

    /**
     * 转盘列表
     * @return
     */
    List<WxSpringActivityBO> queryWheelList();

    /**
     * 根据转盘id获得信息
     * @param id
     * @return
     */
    WxSpringActivityBO getWheelInfo(@Param("id") String id);

    /**
     * 查询活动列表
     * @return
     */
    List<WxSpringActivityBO> queryAllActivity();

    /**
     * 签到总数据
     * @param activityId 
     * @return
     */
    List<WxSpringActivityBO> queryExportCheck(@Param("activityId")String activityId);

    /**
     * 日期
     * @return
     */
    List<WxSpringActivityBO> queryExportDate(@Param("activityId")String activityId);

    /**
     * 转盘
     * @return
     */
    List<WxSpringActivityBO> queryExportWheel(@Param("wheelId")String wheelId);

    /**
     * 拼图
     * @return
     */
    List<WxSpringActivityBO> queryExportPicture(@Param("activityId")String activityId);

    /**
     * 任务
     * @return
     */
    List<WxSpringActivityBO> queryExportTask(@Param("activityId")String activityId);
}