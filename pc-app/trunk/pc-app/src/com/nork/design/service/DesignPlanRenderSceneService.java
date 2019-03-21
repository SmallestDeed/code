package com.nork.design.service;

import java.util.List;
import java.util.Set;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignPlanResRenderScene;
import com.nork.design.model.DesignRenderGroup;
import com.nork.product.model.BaseBrand;

/**   
 * copy from DesignPlanService
 * @Title: DesignPlanEctypeService.java 
 * @Package com.nork.design.service
 * @Description:设计模块-设计方案Service
 * @createAuthor huangsongbo
 * @CreateDate 2017.7.14
 * @version V1.0   
 */
public interface DesignPlanRenderSceneService {
	
	/**
	 * 添加设计方案副本数据
	 * @author huangsongbo
	 * @param designPlanEctype
	 * @return
	 */
	public int add(DesignPlanRenderScene designPlanEctype);

	/**
	 * 更新/修改DesignPlanRenderScene
	 * @author huangsongbo
	 * @param designPlanEctype
	 */
	public void update(DesignPlanRenderScene designPlanEctype);

	/**
	 * 删除DesignPlanRenderScene(by id)
	 * @author huangsongbo
	 * @param id
	 */
	public void delete(long id);

	/**
	 * 通过设计方案副本id得到副本相关所有资源(DesignPlanResRenderScene)
	 * @author huangsongbo
	 * @param designPlanRenderSceneId
	 * @return
	 */
	public DesignPlanResRenderScene getDesignPlanResRenderSceneById(Integer designPlanRenderSceneId);

	/**
	 * 根据id查找设计方案副本(DesignPlanRenderScene)
	 * @author huangsongbo
	 * @param designPlanRenderSceneId
	 * @return
	 */
	public DesignPlanRenderScene get(Integer designPlanRenderSceneId);
	
	/**
	 *根据设计方案查询最后创建的副本
	 * @author yanghz
	 * @param designPlanId
	 * @return
	 */
	DesignPlanRenderScene getLatelyPlanScenByPlanId(int designPlanId);

	/**
	 * 根据缩略图id查找副本设计方案id
	 * @author huangsongbo
	 * @param thumbId
	 * @return
	 */
	public Integer getIdByThumbId(long thumbId);

	public DesignPlanRenderScene getDesignPlanSceneByThumbId(long thumbId);

	/**
	 * 删除设计方案副本以及其关联的所有数据(配置文件数据/配置文件物理文件,设计方案副本,设计方案副本产品表)
	 * 物理删除
	 * @author huangsongbo
	 * @param id
	 */
	public void deleteAllDataById(Integer id);

    int getDesignPlanSceneState(Integer designPlanId);

	public ResponseEnvelope<DesignPlanRenderScene> vendorPlanList(String msgId, String brandId, String planName,
			LoginUser loginUser, String limit, String start);

	
	/**
	 * 得到该厂商的所有品牌
	 * @param msgId
	 * @param loginUser
	 * @return
	 */
	public ResponseEnvelope<BaseBrand> manufacturerBrandList(String msgId, LoginUser loginUser);

	/**
	 * 
	 * 
	 * @author huangsongbo
	 * @param dprCommonId
	 * @param dprCapacityId
	 * @param roleSet
	 * @return
	 */
	public String getSubmitButtonStatus(Integer dprCommonId, Integer dprCapacityId, Set<String> roleSet);

	/**
	 * 根据效果图方案id 查询存在几个功能空间
	 * @author chenqiang
	 * @param idList id集合
	 * @return
	 * @date 2018/8/13 0013 16:09
	 */
	List<String> selectSpaceListByIdList(List<Integer> idList);

	/**
	 * 效果图方案打组
	 * @author chenqiang
	 * @param groupList 选中组合列表
	 * @return 
	 * @date 2018/8/13 0013 15:18
	 */
	boolean addGroupList(List<DesignRenderGroup> groupList,LoginUser loginUser);


	/**
	 * 效果图方案解组
	 * @author chenqiang
	 * @param designRenderId 方案id
	 * @return
	 * @date 2018/8/13 0013 16:27
	 */
	int delGroupList(Integer designRenderId,LoginUser loginUser);


	/**
	 * 根据组合id 查询组合方案列表
	 * @author chenqiang
	 * @param groupId 组合id
	 * @param isDeleted 是否删除
	 * @return
	 * @date 2018/8/13 0013 18:32
	 */
	List<DesignPlanRenderScene> getGroupList(Integer groupId,Integer isDeleted);
}
