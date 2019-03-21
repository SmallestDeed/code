package com.sandu.search.service.metadata.impl;

import com.sandu.search.dao.MetaDataDao;
import com.sandu.search.entity.designplan.po.DesignPlanProductPo;
import com.sandu.search.exception.MetaDataException;
import com.sandu.search.service.metadata.MetaDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 元数据服务
 *
 * @date 20171213
 * @auth pengxuangang
 */
@Slf4j
@Service("metaDataService")
public class MetaDataServiceImpl implements MetaDataService {

    private final static String CLASS_LOG_PREFIX = "元数据服务:";

    private final MetaDataDao metaDataDao;

    @Autowired
    public MetaDataServiceImpl(MetaDataDao metaDataDao) {
        this.metaDataDao = metaDataDao;
    }

    @Override
    public DesignPlanProductPo getTempDesignPlanProductMetaDataById(Integer id) throws MetaDataException {

        //获取草稿设计方案产品元数据
        log.info(CLASS_LOG_PREFIX + "获取草稿设计方案产品元数据...");

        DesignPlanProductPo designPlanProductPo;
        try {
            designPlanProductPo = metaDataDao.getTempDesignPlanProductMetaDataById(id);
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "获取草稿设计方案产品元数据失败!Exception:{}", e);
            throw new MetaDataException(CLASS_LOG_PREFIX + "获取草稿设计方案产品元数据失败!Exception:{}" + e);
        }

        log.info(CLASS_LOG_PREFIX + "获取草稿设计方案产品元数据完成.DesignPlanProductPo:{}", null == designPlanProductPo ? null : designPlanProductPo.toString());

        return designPlanProductPo;
    }

    @Override
    public DesignPlanProductPo getRecommendDesignPlanProductMetaDataById(Integer id) throws MetaDataException {

        //查询推荐设计方案产品元数据
        log.info(CLASS_LOG_PREFIX + "获取推荐设计方案产品元数据...");

        DesignPlanProductPo designPlanProductPo;
        try {
            designPlanProductPo = metaDataDao.getRecommendDesignPlanProductMetaDataById(id);
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "获取推荐设计方案产品元数据失败!Exception:{}", e);
            throw new MetaDataException(CLASS_LOG_PREFIX + "获取推荐设计方案产品元数据失败!Exception:{}" + e);
        }

        log.info(CLASS_LOG_PREFIX + "获取推荐设计方案产品元数据完成.DesignPlanProductPo:{}", null == designPlanProductPo ? null : designPlanProductPo.toString());

        return designPlanProductPo;
    }

    @Override
    public DesignPlanProductPo getDiyDesignPlanProductMetaDataById(Integer id) throws MetaDataException {

        //查询自定义设计方案产品元数据
        log.info(CLASS_LOG_PREFIX + "获取自定义设计方案产品元数据...");

        DesignPlanProductPo designPlanProductPo;
        try {
            designPlanProductPo = metaDataDao.getDiyDesignPlanProductMetaDataById(id);
        } catch (Exception e) {
            log.error(CLASS_LOG_PREFIX + "获取自定义设计方案产品元数据失败!Exception:{}", e);
            throw new MetaDataException(CLASS_LOG_PREFIX + "获取自定义设计方案产品元数据失败!Exception:{}" + e);
        }

        log.info(CLASS_LOG_PREFIX + "获取自定义设计方案产品元数据完成.DesignPlanProductPo:{}", null == designPlanProductPo ? null : designPlanProductPo.toString());

        return designPlanProductPo;
    }
}
