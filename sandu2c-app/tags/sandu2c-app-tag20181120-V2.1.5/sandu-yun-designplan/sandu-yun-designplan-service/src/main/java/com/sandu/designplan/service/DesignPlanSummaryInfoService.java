package com.sandu.designplan.service;


import com.sandu.designplan.model.DesignPlanSummaryInfo;

import java.util.List;

/**
 * @version V1.0
 * @Title: DesignPlanSummaryInfoService.java
 * @Package com.nork.mobile2c.service
 * @Description:移动端2C-方案点赞收藏数量表Service
 * @createAuthor pandajun
 * @CreateDate 2018-01-09 10:16:03
 */
public interface DesignPlanSummaryInfoService {
    /**
     * 新增数据
     *
     * @param designPlanSummaryInfo
     * @return int
     */
    public int add(DesignPlanSummaryInfo designPlanSummaryInfo);

    /**
     * 更新数据
     *
     * @param designPlanSummaryInfo
     * @return int
     */
    public int update(DesignPlanSummaryInfo designPlanSummaryInfo);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    public int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param designId
     * @return DesignPlanSummaryInfo
     */
    public DesignPlanSummaryInfo get(Integer designId);

    /**
     * 所有数据
     *
     * @param designPlanSummaryInfo
     * @return List<DesignPlanSummaryInfo>
     */
    public List<DesignPlanSummaryInfo> getList(DesignPlanSummaryInfo designPlanSummaryInfo);

    /**
     * 新增或修改（缓存同步数据库用）
     *
     * @param summaryInfo
     * @return
     */
    int saveOrUpdate(DesignPlanSummaryInfo summaryInfo);

    /**
     * 查询方案点赞总数
     *
     * @param designId
     * @return
     */
    int getLikeNum(Integer designId);

    /**
     * 查询方案收藏总数
     *
     * @param designId
     * @return
     */
    int getCollectNum(Integer designId);

    /**
     * 修改查看次数+1
     *
     * @param id
     * @return
     */
    int updateViewNumberPlusOne(Integer id);

    /**
     * 获取全屋方案数据详情
     *
     * @param fullHouseId 全屋方案ID
     * @return
     */
    DesignPlanSummaryInfo getFullHouseInfo(int fullHouseId);
}
