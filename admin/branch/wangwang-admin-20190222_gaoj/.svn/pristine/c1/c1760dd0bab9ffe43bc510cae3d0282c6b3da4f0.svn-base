package com.sandu.api.product.service;

import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.model.bo.DesignPlanStyleBO;

import java.util.List;

/**
 * @author Sandu
 */

public interface BaseProductStyleService {
    /**
     * 保存产品风格
     *
     * @param baseProductStyle
     * @return
     */
    int saveBaseProductStyle(BaseProductStyle baseProductStyle);

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    int deleteBaseProductStyleById(long id);

    /**
     * 更新
     *
     * @param baseProductStyle
     */
    void updateBaseProductStyle(BaseProductStyle baseProductStyle);

    /**
     * 根据主键获取
     *
     * @param id
     * @return
     */
    BaseProductStyle getBaseProductStyleById(long id);

    /**
     * 批量获取(根据主键)
     *
     * @param ids
     * @return
     */
    List<BaseProductStyle> getBaseProductStyleByIds(List<Long> ids);

    /**
     * 根据id获取名称
     *
     * @param ids
     * @return
     */
    List<String> getBaseProductStyleNameByIds(List<Long> ids);

    /**
     * 获取所有的ID和名称
     *
     * @return
     */
    List<BaseProductStyle> listBaseProductStyleIdAndName();

    /**
     * 获取方案风格
     *
     * @return
     */
    List<BaseProductStyle> listBasePlanStyleIdAndName(Integer numa);

    List<DesignPlanStyleBO> listDesignPlanStyle();

}
