package com.sandu.service.product.dao;

import com.sandu.api.product.model.BaseProductStyle;
import com.sandu.api.product.model.bo.DesignPlanStyleBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface BaseProductStyleDao {
    int save(BaseProductStyle baseProductStyle);

    int deleteById(@Param("id") long id);

    void update(BaseProductStyle baseProductStyle);

    BaseProductStyle getById(@Param("id") long id);

    List<BaseProductStyle> getByIds(@Param("ids") List<Long> ids);

    List<String> getNamesByIds(@Param("ids") List<Long> ids);

    List<BaseProductStyle> listBaseProductStyleIdAndName();

    List<BaseProductStyle> listBasePlanStyleIdAndName(@Param("numa") Integer numa, @Param("pid") Integer pid);

    List<DesignPlanStyleBO> listDesignPlanStyle();
}
