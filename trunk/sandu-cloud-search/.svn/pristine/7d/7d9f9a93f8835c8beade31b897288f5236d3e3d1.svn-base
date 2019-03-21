package com.sandu.search.dao;

import com.sandu.search.entity.designplan.po.DesignPlanProductPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 元数据数据访问层
 *
 * @date 20171213
 * @auth pengxuangang
 */
@Repository
public interface MetaDataDao {

    /**
     * 获取草稿设计方案产品元数据
     *
     * @return
     */
    DesignPlanProductPo getTempDesignPlanProductMetaDataById(@Param("id") Integer id);

    /**
     * 获取推荐设计方案产品元数据
     *
     * @return
     */
    DesignPlanProductPo getRecommendDesignPlanProductMetaDataById(@Param("id") Integer id);

    /**
     * 获取自定义设计方案产品元数据
     *
     * @return
     */
    DesignPlanProductPo getDiyDesignPlanProductMetaDataById(@Param("id") Integer id);
}
