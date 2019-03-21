package com.nork.design.service;

import java.util.Set;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignPlanResRenderScene;
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
}
