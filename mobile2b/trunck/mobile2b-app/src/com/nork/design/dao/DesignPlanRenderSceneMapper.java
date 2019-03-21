package com.nork.design.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;

/**   
 * @Title: DesignPlanEctypeMapper.java 
 * @Package com.nork.design.dao
 * @Description:设计方案-设计方案表Mapper
 * @createAuthor huangsongbo
 * @CreateDate 2017.7.14
 * @version V1.0   
 */
@Repository
public interface DesignPlanRenderSceneMapper {

	/**
	 * 新增一条数据
	 * @author huangsongbo
	 * @param designPlanEctype
	 * @return
	 */
	int insert(DesignPlanRenderScene designPlanRenderScene);

	/**
	 * 删除数据
	 * @author huangsongbo
	 * @param id
	 */
	void delete(@Param("id") long id);

	/**
	 * 根据设计方案方案id获取最新的一条副本
	 * @author huangsongbo
	 * @param designPlanId
	 * @return
	 */
    DesignPlanRenderScene getLatelyPlanScenByPlanId(int designPlanId);

    /**
     * 更新数据
     * 
     * @param designPlanEctype
     */
	void update(DesignPlanRenderScene designPlanEctype);

	/**
	 * 通过id查找数据
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	DesignPlanRenderScene get(Integer id);

    int getDesignPlanSceneState(Integer designPlanId);

	int getVendorCount(DesignPlanRenderScene designPlanRenderScene);

	List<DesignPlanRenderScene> getVendorList(DesignPlanRenderScene designPlanRenderScene);

	int getVendorCountV2(DesignPlanRenderScene designPlanRenderScene);
	
	int getAllVendorCount(DesignPlanRenderScene designPlanRenderScene);

	List<DesignPlanRenderScene> getVendorListV2(DesignPlanRenderScene designPlanRenderScene);
	
	List<DesignPlanRenderScene> getAllVendorList(DesignPlanRenderScene designPlanRenderScene);

    Integer getPlatformIdByCode(String platformCode);

    /**
     * 根绝id集合查询效果图方案数据
     * @author: chenm
     * @date: 2019/1/23 20:00
     * @param idList
     * @return: java.util.List<com.nork.design.model.DesignPlanRenderScene>
     */
    List<DesignPlanRenderScene> getPlanListByIdList(@Param("idList") List<Integer> idList);
}
