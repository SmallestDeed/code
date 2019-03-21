package com.nork.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.ProductListByTypeInfo.PlanProductInfo;
import com.nork.product.model.StructureProduct;
import com.nork.product.model.search.StructureProductSearch;

/**   
 * @Title: StructureProductMapper.java 
 * @Package com.nork.product.dao
 * @Description:产品模块-结构表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2016-12-02 15:32:05
 * @version V1.0   
 */
@Repository
@Transactional
public interface StructureProductMapper {
    int insertSelective(StructureProduct record);

    int updateByPrimaryKeySelective(StructureProduct record);
  
    int deleteByPrimaryKey(Integer id);
        
    StructureProduct selectByPrimaryKey(Integer id);
    
    int selectCount(StructureProductSearch structureProductSearch);
    
	List<StructureProduct> selectPaginatedList(
			StructureProductSearch structureProductSearch);
			
    List<StructureProduct> selectList(StructureProduct structureProduct);

    void updateDesignTempletStructureRelationByStructureId(@Param("structureId") Integer structureId);

    List<StructureProduct> getStructureObject(
            StructureProductSearch structureProductSearch);

	void updateStatus(@Param("oldStatus") Integer oldStatus, @Param("newStatus") Integer newStatus);

    int getCommonStructureCount(StructureProductSearch structureProductSearch);

    List<StructureProduct> getCommonStructureList(StructureProductSearch structureProductSearch);

    List<StructureProduct> getStructuresByDesignPlanId(@Param("designPlanId") Integer designPlanId);


    
	/**
	 * 批量删除
	 * */
	int deleteBatch(List<Integer> list);

	List<StructureProduct> getStructuresByRecommendedPlanId(@Param("recommendedPlanId") Integer recommendedPlanId);

	Integer easySearch(@Param("measureCode") String measureCode,@Param("styleId") Integer styleId, @Param("structureNumber") Integer structureNumber, @Param("identifyList") List<String> identifyList, @Param("orderGroundIdentify") String orderGroundIdentify, @Param("designTempletId") Integer designTempletId);

	List<PlanProductInfo> getPlanProductInfoListByStructureId(@Param("structureId") Integer structureId);

	Integer easySearchV2(StructureProductSearch structureProductSearch);
	
	List<StructureProduct> getListV2(StructureProductSearch search);

	int getCountV2(StructureProductSearch search);

	
	/**
	 * 通过ids 查询结构结合
	 * @param ids
	 * @return
	 */
	List<StructureProduct> getListByids(@Param("ids")List<Integer> ids);
	
}
