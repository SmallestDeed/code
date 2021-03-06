package com.nork.design.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.DesignPlanBrand;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRecommendedProduct;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.model.input.FranchiserPlanListDTO;
import com.nork.design.model.input.PlanRecommendedListForFranchiserDTO;
import com.nork.design.model.output.FranchiserPlanListVO;
 

@Repository
@Transactional
public interface DesignPlanRecommendedMapperV2 {

	 int insertSelective(DesignPlanRecommended designPlanRecommended);
		
	 int updateByPrimaryKeySelective(DesignPlanRecommended designPlanRecommended);
	
	 int deleteByPrimaryKey(Integer id);
	
	 DesignPlanRecommended selectByPrimaryKey(Integer id);
	
	 List<DesignPlanRecommended> selectList(DesignPlanRecommended designPlanRecommended);

	 int selectCount(DesignPlanRecommended designPlanRecommended);

	Integer getPlanRecommendedCount(DesignPlanRecommended designPlanRecommended);
	Integer getPlanRecommendedCountMobile(DesignPlanRecommended designPlanRecommended);

	List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanRecommended designPlanRecommended);
	List<DesignPlanRecommendedResult> getPlanRecommendedListMobile(DesignPlanRecommended designPlanRecommended);
	/**
	 * 
	   
	 * getStatusByIds 批量查询id对应的发布状态      
	   
	 * @param ids
	 * @return 
	
	 * @return List<DesignPlanRecommended>    返回类型   
	   
	 * @Exception 异常对象    
	   
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public List<DesignPlanRecommended> getStatusByIds(List<Long> ids);
	/**
	 * 获取该方案推荐的白膜产品的数量，如果大于0那么是m+3 未装修完成的推荐（m+3 是不装修直接渲染的快捷键）
	 * @param id
	 * @return
	 */
	int recommendedDecorateState(Integer id);
	/**
	 *  获取方案推荐收藏夹数据量
	 * @param designPlanRecommended
	 * @return
	 */
	Integer getFavoritePlanRecommendedCount(DesignPlanRecommended designPlanRecommended);
	/**
	 *  获取方案推荐收藏夹列表
	 * @param designPlanRecommended
	 * @return
	 */
	List<DesignPlanRecommendedResult> getFavoritePlanRecommendedList(DesignPlanRecommended designPlanRecommended);

	/**
	 * 根据空间类型获取推荐方案ids
	 * @author xiaoxc
	 * @param spaceFunctionId
	 * @return
	 */
	List<Integer> findRecommendedPlanIdListBySpaceFunctionId(@Param("spaceFunctionId") Integer spaceFunctionId);

	/**
	 * 根据推荐方案ID获取推荐方案产品分类keys
	 * @author xiaoxc
	 * @param recommendedPlanId
	 * @return
	 */
	List<String> findListByRecommendedPlanId(@Param("recommendedPlanId") Integer recommendedPlanId);

	int getPlanRecommendedListSizeByDTO(PlanRecommendedListForFranchiserDTO dto);

	List<DesignPlanRecommendedResult> getPlanRecommendedListByDTO(PlanRecommendedListForFranchiserDTO dto);

	int getFranchiserPlanListSizeByDTO(FranchiserPlanListDTO dto);

	List<FranchiserPlanListVO> getFranchiserPlanListByDTO(FranchiserPlanListDTO dto);
}
