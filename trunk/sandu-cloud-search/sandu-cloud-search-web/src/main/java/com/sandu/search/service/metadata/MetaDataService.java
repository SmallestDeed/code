package com.sandu.search.service.metadata;

import com.sandu.search.entity.designplan.po.DesignPlanProductPo;
import com.sandu.search.exception.MetaDataException;

/**
 * 元数据服务
 *
 * @date 20171213
 * @auth pengxuangang
 */
public interface MetaDataService {

    /**
     * 获取草稿设计方案产品元数据
     *
     * @return
     */
    DesignPlanProductPo getTempDesignPlanProductMetaDataById(Integer id) throws MetaDataException;

    /**
     * 获取推荐设计方案产品元数据
     *
     * @return
     */
    DesignPlanProductPo getRecommendDesignPlanProductMetaDataById(Integer id) throws MetaDataException;

    /**
     * 获取自定义设计方案产品元数据
     *
     * @return
     */
    DesignPlanProductPo getDiyDesignPlanProductMetaDataById(Integer id) throws MetaDataException;
}
