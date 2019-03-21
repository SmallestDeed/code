package com.nork.design.dao;

import java.util.List;

import com.nork.design.model.DesignPlanResRenderScene;
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

	List<DesignPlanRenderScene> getVendorListV2(DesignPlanRenderScene designPlanRenderScene);

	/**
	 * 根据效果图方案id 查询存在几个功能空间
	 * @author chenqiang
	 * @param idList id集合
	 * @return
	 * @date 2018/8/13 0013 16:09
	 */
	List<String> selectSpaceListByIdList(@Param("idList")List<Integer> idList);

	/**
	 * 根据主方案id 解除 组合
	 * @author chenqiang
	 * @param id 组合方案主方案id
	 * @return 
	 * @date 2018/8/13 0013 16:32
	 */
	int delGroupList(@Param("id")Integer id,@Param("name") String name);

	/**
	 * 根据组合id 查询组合方案列表
	 * @author chenqiang
	 * @param groupId 组合id
	 * @param isDeleted 是否删除
	 * @return
	 * @date 2018/8/13 0013 18:32
	 */
	List<DesignPlanRenderScene> getGroupList(@Param("groupId") Integer groupId, Integer isDeleted);
}
