package com.nork.design.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignPlanCheck;
import com.nork.design.model.DesignPlanRecommendedResult;
 
@Repository
@Transactional
public interface DesignPlanCheckMapper {
	
	 int insertSelective(DesignPlanCheck designPlanCheck);
		
	 int updateByPrimaryKeySelective(DesignPlanCheck designPlanCheck);
	
	 int deleteByPrimaryKey(Integer id);
	
	 DesignPlanCheck selectByPrimaryKey(Integer id);
	
	 List<DesignPlanCheck> selectList(DesignPlanCheck designPlanCheck);

	 int selectCount(DesignPlanCheck designPlanCheck);
	/**
	 * 通过设计方案id  删除 审核人员
	 * @param id
	 */
	 void deletedBydesignPlanId(Integer id);
	 /**
	 * 方案推荐审核列表
	 * @param designPlanCheck
	 * @return
	 */
	List<DesignPlanRecommendedResult> getDesignPlanCheckList(DesignPlanCheck designPlanCheck);

	int getDesignPlanCheckCount(DesignPlanCheck designPlanCheck);
}
