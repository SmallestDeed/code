package com.sandu.designplan.service.impl;

import com.google.gson.Gson;
import com.sandu.cache.service.RedisService;
import com.sandu.designplan.dao.DesignPlanSummaryInfoMapper;
import com.sandu.designplan.model.DesignPlanSummaryInfo;
import com.sandu.designplan.service.DesignPlanSummaryInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @version V1.0
 * @Title: DesignPlanSummaryInfoServiceImpl.java
 * @Package com.nork.mobile2c.service.impl
 * @Description:移动端2C-方案点赞收藏数量表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2018-01-09 10:16:03
 */
@Service("designPlanLikeAndCollectCountService")
public class DesignPlanSummaryInfoServiceImpl implements DesignPlanSummaryInfoService {

    private final static Gson GSON = new Gson();
    private final static String CLASS_LOG_PREFIX = "[方案模块-方案信息服务]:";
    private final static Logger logger = LoggerFactory.getLogger(DesignPlanLikeServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private DesignPlanSummaryInfoMapper designPlanSummaryInfoMapper;

    /**
     * 新增数据
     *
     * @param designPlanSummaryInfo
     * @return int
     */
    @Override
    public int add(DesignPlanSummaryInfo designPlanSummaryInfo) {
        designPlanSummaryInfoMapper.insertSelective(designPlanSummaryInfo);
        return designPlanSummaryInfo.getId();
    }

    /**
     * 更新数据
     *
     * @param designPlanSummaryInfo
     * @return int
     */
    @Override
    public int update(DesignPlanSummaryInfo designPlanSummaryInfo) {
        return designPlanSummaryInfoMapper
                .updateByPrimaryKeySelective(designPlanSummaryInfo);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return designPlanSummaryInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param designId
     * @return DesignPlanSummaryInfo
     */
    @Override
    public DesignPlanSummaryInfo get(Integer designId) {
        return designPlanSummaryInfoMapper.selectByDesignId(designId);
    }

    /**
     * 所有数据
     *
     * @param designPlanSummaryInfo
     * @return List<DesignPlanSummaryInfo>
     */
    @Override
    public List<DesignPlanSummaryInfo> getList(DesignPlanSummaryInfo designPlanSummaryInfo) {
        return designPlanSummaryInfoMapper.selectList(designPlanSummaryInfo);
    }

    /**
     * 新增或修改（缓存同步数据库用）
     * @param summaryInfo
     * @return
     */
    @Override
    public int saveOrUpdate(DesignPlanSummaryInfo summaryInfo) {
        if (summaryInfo == null || summaryInfo.getDesignId() == null) {
            return 0;
        }
        return designPlanSummaryInfoMapper.saveOrUpdate(summaryInfo);
    }

    @Override
    public int getLikeNum(Integer designId) {
        return 0;
    }

    @Override
    public int getCollectNum(Integer designId) {
        return 0;
    }

    @Override
    public int updateViewNumberPlusOne(Integer planRecommendId) {
        return designPlanSummaryInfoMapper.updateViewNumberPlusOne(planRecommendId);
    }


}
