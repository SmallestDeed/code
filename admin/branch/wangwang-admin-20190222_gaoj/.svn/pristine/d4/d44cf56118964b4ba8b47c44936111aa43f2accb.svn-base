package com.sandu.api.activity.service;

import com.github.pagehelper.PageInfo;
import com.sandu.api.activity.input.WxSpringActivityAdd;
import com.sandu.api.activity.input.WxSpringActivityQuery;
import com.sandu.api.activity.model.WxSpringActivityBO;

import java.util.List;

/**
 * @Author: Yuxc
 * @Description:
 * @Date: 2019/1/18
 */

public interface WxSpringActivityService {

    /**
     * 新增春节活动
     * @param add
     * @return
     */
    Integer addSpringActivity(WxSpringActivityAdd add);

    /**
     * 转盘列表
     * @return
     */
    List<WxSpringActivityBO> wheelList();

    /**
     * 根据id获得转盘信息
     * @param id
     * @return
     */
    WxSpringActivityBO getWheelInfo(String id);

    /**
     * 查询活动列表
     * @return
     */
    PageInfo<WxSpringActivityBO> queryAllActivity(WxSpringActivityQuery query);

    /**
     * 导出数据
     * @param export_check_data
     * @param activityId 
     * @return
     */
    List<WxSpringActivityBO> queryExport(Integer export_check_data, String activityId);
}
