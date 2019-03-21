package com.nork.onekeydesign.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nork.onekeydesign.model.DesignPlanTemplateProduct;

@Repository
public interface DesignPlanTemplateProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DesignPlanTemplateProduct record);

    int insertSelective(DesignPlanTemplateProduct record);

    DesignPlanTemplateProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlanTemplateProduct record);

    int updateByPrimaryKey(DesignPlanTemplateProduct record);

	List<DesignPlanTemplateProduct> selectByPlanId(@Param("id") Integer id, @Param("isDeleted") Integer isDeleted);
}