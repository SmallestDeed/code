package com.nork.design.dao;

import java.util.List;
import java.util.Map;

import com.nork.design.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.model.search.DesignPlanSearch;

import javax.ws.rs.GET;

/**   
 * @Title: DesignPlanMapper.java 
 * @Package com.nork.design.dao
 * @Description:设计方案-设计方案表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-06-26 11:01:53
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignPlanMapper {
    int insertSelective(DesignPlan record);

    int updateByPrimaryKeySelective(DesignPlan record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignPlan selectByPrimaryKey(Integer id);
    
    int selectCount(DesignPlanSearch designPlanSearch);
    
	List<DesignPlan> selectPaginatedList(
			DesignPlanSearch designPlanSearch);
			
    List<DesignPlan> selectList(DesignPlan designPlan);
    
	/**
	 * 其他
	 * 
	 */
    List<DesignPlanResult> designList(DesignPlan designPlan);
    
    List<DesignPlan> getPlanList(DesignPlan designPlan);
    List<DesignPlan> getDesignList(DesignPlan designPlan);
    
    List<DesignPlan> getPlanListByAreas(DesignPlan designPlan);
    
    int getPlanCount(DesignPlan designPlan);
    
    int getTemplateProductId(Map map);
    
    List<Map> getUserMaxPlan(Map map);
	/**
	 * 通过plan_id获得设计方案状态
	 * @param designPlanId
	 * @return
	 */
	int getDesignPlanState(Integer designPlanId);
	
	List<DesigPlanWithResult> selectDesignList(DesignPlan designPlan);

    DesignPlanModel selectDesignPlanInfo(Integer id);
    DesignPlanModel selectIosDesignPlanInfo(Integer id);
    DesignPlanModel selectAndroidDesignPlanInfo(Integer id);
    
    List<ProductDTO> getProductDTOList(Integer designPlanId);

    public List<DesignPlan> getPlansBySpaceInfo(@Param("spaceAreas") String spaceAreas, @Param("spaceFunctionId") Integer spaceFunctionId);

    DesignPlan getSceneModifiedById(int designPlanId);

    List<DesignPlan> getTempDesignPaln4RenderBakSceneTask();

    void batchDelTempDesign(@Param("delPlanIdList") List<Integer> delPlanIdList);
    /**
     * 
       
     * getTempDesignPalnBySceneId(返回只包含id，其余数据没返回，请注意使用)        
       
     * @param designPlan
     * @return 
    
     * @return DesignPlan    返回类型   
       
     * @Exception 异常对象    
       
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
    public DesignPlan getTempDesignPalnBySceneId(DesignPlan designPlan);

    /*<!-- COMMON-525 -->*/
    /**
     * 查询品牌所在企业所经营的产品大分类
     * @param brandId 品牌id
     * @return 产品小分类valuekey值
     */
    String getCompanyValueKey(@Param("brandId") Integer brandId);

    /**
     * 查询该品牌所经营的大小分类所过滤出的设计方案中产品中不是该品牌的产品编码
     * @param planId 设计方案Id
     * @param valuekeyList 产品小分类valuekey值
     * @param brandId 品牌id
     * @return 产品编码 集合
     */
    List<String> getProductCode(@Param("planId")Integer planId,@Param("valuekeyList")String[] valuekeyList,@Param("brandId")Integer brandId);

	Integer countGroundStructure(Integer designPlanId);
	
	Integer countGroundStructureProduct(Integer designPlanId);

	Integer getDesignPlanSource(Integer designPlanId);


    List<ProductDefaultUV> selectProductDefaultUV(@Param("productModelId") Integer productModelId);
}
