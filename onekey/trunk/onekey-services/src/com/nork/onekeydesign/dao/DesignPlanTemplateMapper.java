package com.nork.onekeydesign.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nork.onekeydesign.model.DesignPlanTemplate;
import com.nork.onekeydesign.model.search.DesignPlanTemplateSearch;

@Repository
public interface DesignPlanTemplateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DesignPlanTemplate record);

    int insertSelective(DesignPlanTemplate record);

    DesignPlanTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignPlanTemplate record);

    int updateByPrimaryKeyWithBLOBs(DesignPlanTemplate record);

    int updateByPrimaryKey(DesignPlanTemplate record);

	int selectCount(DesignPlanTemplateSearch designPlanTemplateSearch);

	List<DesignPlanTemplate> selectList(DesignPlanTemplateSearch designPlanTemplateSearch);
}