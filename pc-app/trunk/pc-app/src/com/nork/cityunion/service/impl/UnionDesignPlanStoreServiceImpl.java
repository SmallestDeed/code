package com.nork.cityunion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.UnionDesignPlanStoreReleaseDetails;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseDetailsService;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Constants;
import com.nork.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionDesignPlanStoreMapper;
import com.nork.cityunion.model.UnionDesignPlanStore;
import com.nork.cityunion.model.search.UnionDesignPlanStoreSearch;
import com.nork.cityunion.service.UnionDesignPlanStoreService;

import javax.servlet.http.HttpServletRequest;

/**   
 * @Title: UnionDesignPlanStoreServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟设计方案素材库表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:21:19
 * @version V1.0   
 */
@Service("unionDesignPlanStoreService")
public class UnionDesignPlanStoreServiceImpl implements UnionDesignPlanStoreService {

	private UnionDesignPlanStoreMapper unionDesignPlanStoreMapper;

	@Autowired
	private UnionDesignPlanStoreReleaseDetailsService unionDesignPlanStoreReleaseDetailsService;
	@Autowired
	private UnionDesignPlanStoreReleaseService unionDesignPlanStoreReleaseService;
	@Autowired
	private UnionDesignPlanStoreService unionDesignPlanStoreService;

	@Autowired
	public void setUnionDesignPlanStoreMapper(
			UnionDesignPlanStoreMapper unionDesignPlanStoreMapper) {
		this.unionDesignPlanStoreMapper = unionDesignPlanStoreMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStore
	 * @return  int 
	 */
	@Override
	public int add(UnionDesignPlanStore unionDesignPlanStore) {
		unionDesignPlanStoreMapper.insertSelective(unionDesignPlanStore);
		return unionDesignPlanStore.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStore
	 * @return  int 
	 */
	@Override
	public int update(UnionDesignPlanStore unionDesignPlanStore) {
		return unionDesignPlanStoreMapper
				.updateByPrimaryKeySelective(unionDesignPlanStore);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionDesignPlanStoreMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionDesignPlanStore 
	 */
	@Override
	public UnionDesignPlanStore get(Integer id) {
		return unionDesignPlanStoreMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStore
	 * @return   List<UnionDesignPlanStore>
	 */
	@Override
	public List<UnionDesignPlanStore> getList(UnionDesignPlanStore unionDesignPlanStore) {
	    return unionDesignPlanStoreMapper.selectList(unionDesignPlanStore);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreSearch
	 * @return   int
	 */
	@Override
	public int getCount(UnionDesignPlanStoreSearch unionDesignPlanStoreSearch){
		return  unionDesignPlanStoreMapper.selectCount(unionDesignPlanStoreSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreSearch
	 * @return   List<UnionDesignPlanStore>
	 */
	@Override
	public List<UnionDesignPlanStore> getPaginatedList(
			UnionDesignPlanStoreSearch unionDesignPlanStoreSearch) {
		return unionDesignPlanStoreMapper.selectPaginatedList(unionDesignPlanStoreSearch);
	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 获取用户素材库数量
	 * @param designPlanName
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public int findMyDesignPlanStoreCount(String designPlanName,Integer userId,Integer start,Integer limit) {
		return unionDesignPlanStoreMapper.findMyDesignPlanStoreCount(designPlanName,userId,start,limit);
	}

	/**
	 * 获取用户素材库list
	 * @param designPlanName
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<UnionDesignPlanStore> findMyDesignPlanStoreList(String designPlanName,Integer userId,Integer start,Integer limit) {
		return  unionDesignPlanStoreMapper.findMyDesignPlanStoreList(designPlanName,userId,start,limit);
	}

	/**
	 * 自动存储系统字段
	 */
	@Override
	public void sysSave(UnionDesignPlanStore model,LoginUser loginUser){
		if(loginUser == null || loginUser.getId() == null){
			loginUser = new LoginUser();
			loginUser.setLoginName("noLogin");
		}
		if(model != null){
			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if(model.getSysCode()==null || "".equals(model.getSysCode())){
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 获取打包发布优先进入方案素材ID
	 * @param storeIdList
	 * @return
	 */
	@Override
	public Integer getStoreIdByStoreIds(List<Integer> storeIdList) {
		return unionDesignPlanStoreMapper.getStoreIdByStoreIds(storeIdList);
	}

	/**
	 * 打包发布设计方案素材排序
	 * @param storeIdList
	 * @return
	 */
	@Override
	public List<Integer> findStoreIdByStoreIdsList(List<Integer> storeIdList) {
		return unionDesignPlanStoreMapper.findStoreIdByStoreIdsList(storeIdList);
	}

	/**
	 * 删除渲染图时查询是否存在方案素材库中
	 * @param designPlanId
	 * @param renderPicSmallId
	 * @return
	 */
	@Override
	public UnionDesignPlanStore getDesignPlanStore(Integer designPlanId, Integer renderPicSmallId) {
		return unionDesignPlanStoreMapper.getDesignPlanStore(designPlanId, renderPicSmallId);
	}


	/**
	 * 删除草图渲染图时删除同盟设计方案素材库相关数据及发布方案素材库相关数据
	 * @param designPlanId
	 * @param renderPicSmallId
	 */
	@Override
	public void delDesignPlanStoreRelatedData(Integer designPlanId, Integer renderPicSmallId) {
		UnionDesignPlanStore planStore = unionDesignPlanStoreMapper.getDesignPlanStore(designPlanId,renderPicSmallId);
		if (planStore != null) {
			//逻辑删除设计方案素材库
			UnionDesignPlanStore newPlanStore = new UnionDesignPlanStore();
			newPlanStore.setId(planStore.getId());
			newPlanStore.setIsDeleted(Constants.DATA_DELETED_STATUS_ONE);
			newPlanStore.setGmtModified(new Date());
			unionDesignPlanStoreMapper.updateByPrimaryKeySelective(newPlanStore);
			//TODO 设计方案发布素材库处理
			UnionDesignPlanStoreReleaseDetails storeReleaseDetails = new UnionDesignPlanStoreReleaseDetails();
			storeReleaseDetails.setUnionDesignPlanStoreId(planStore.getId());
			storeReleaseDetails.setIsDeleted(Constants.DATA_DELETED_STATUS_zero);
			//查询该素材已发布集合
			List<UnionDesignPlanStoreReleaseDetails> releaseDetailsList = unionDesignPlanStoreReleaseDetailsService.getList(storeReleaseDetails);
			//相关数据逻辑删除
			if (releaseDetailsList != null && releaseDetailsList.size() > 0) {
				storeReleaseDetails.setIsDeleted(Constants.DATA_DELETED_STATUS_ONE);
				storeReleaseDetails.setGmtModified(new Date());
				unionDesignPlanStoreReleaseDetailsService.updateByReleaseId(storeReleaseDetails);
			}
			//查询该素材被发布过的数据集合
			UnionDesignPlanStoreRelease storeRelease = new UnionDesignPlanStoreRelease();
			storeRelease.setDesignPlanStoreId(planStore.getId());
			storeRelease.setIsDeleted(Constants.DATA_DELETED_STATUS_zero);
			List<UnionDesignPlanStoreRelease> storeReleaseList = unionDesignPlanStoreReleaseService.getList(storeRelease);

			//TODO 处理是否更新还是直接删除发布方案
			processStoreReleaseList(storeReleaseList);
		}

	}

	//删除方案素材库，处理发布方案素材库逻辑
	private void processStoreReleaseList(List<UnionDesignPlanStoreRelease> storeReleaseList){
		if (storeReleaseList == null || storeReleaseList.size() == 0) {
			return ;
		}
		for (UnionDesignPlanStoreRelease release : storeReleaseList) {
			Integer releaseId = release.getId();
			UnionDesignPlanStoreReleaseDetails releaseDetails1 = new UnionDesignPlanStoreReleaseDetails();
			releaseDetails1.setUnionDesignPlanStoreReleaseId(releaseId);
			releaseDetails1.setIsDeleted(Constants.DATA_DELETED_STATUS_zero);
			List<UnionDesignPlanStoreReleaseDetails> releaseDetailsList1 = unionDesignPlanStoreReleaseDetailsService.getList(releaseDetails1);
			if (releaseDetailsList1 != null && releaseDetailsList1.size() > 0) {
				//如果发布方案下只有一个素材直接更新发布方案的designPlanStoreId
				if (releaseDetailsList1.size() == 1) {
					UnionDesignPlanStoreReleaseDetails releaseDetails2 = releaseDetailsList1.get(0);
					UnionDesignPlanStoreRelease newStoreRelease = new UnionDesignPlanStoreRelease();
					newStoreRelease.setId(releaseId);
					newStoreRelease.setDesignPlanStoreId(releaseDetails2.getUnionDesignPlanStoreId());
					newStoreRelease.setGmtModified(new Date());
					unionDesignPlanStoreReleaseService.update(newStoreRelease);
					continue;
				}
				//发布方案详情如有多个素材则需筛选优先进入房间ID更新到发布方案designPlanStoreId里
				List<Integer> storeIdList = new ArrayList<>();
				for (UnionDesignPlanStoreReleaseDetails releaseDetails2 : releaseDetailsList1) {
					Integer storeId = releaseDetails2.getUnionDesignPlanStoreId();
					storeIdList.add(storeId);
				}
				if (storeIdList != null && storeIdList.size() > 0) {
					Integer storeId = unionDesignPlanStoreService.getStoreIdByStoreIds(storeIdList);
					UnionDesignPlanStoreRelease newStoreRelease = new UnionDesignPlanStoreRelease();
					newStoreRelease.setId(releaseId);
					newStoreRelease.setDesignPlanStoreId(storeId == null ? 0 : storeId);
					newStoreRelease.setGmtModified(new Date());
					unionDesignPlanStoreReleaseService.update(newStoreRelease);
				}
			} else {
				unionDesignPlanStoreReleaseService.delete(releaseId);
			}
		}
		return ;
	}


	/**
	 * 根据素材Id获取渲染资源code
	 * @param storeId
	 * @return
	 */
	@Override
	public String getRenderPicCodeByStoreId(Integer storeId){
		return unionDesignPlanStoreMapper.getRenderPicCodeByStoreId(storeId);
	}

	/**
	 * 根据设计效果图方案Id删除同城联盟方案素材及发布的方案素材
	 * @param designSceneId
	 * @return
	 */
	@Override
	public void deleteCityUnionPlanStoreByDesignPlanId(Integer designSceneId){
		//首先删除发布方案详情里的方案素材库
		unionDesignPlanStoreReleaseDetailsService.deleteStoreReleaseDetailsByDesignSceneId(designSceneId);
		//根据方案Id查询发布方案素材库集合（处理逻辑是否删除还是更新）
		List<UnionDesignPlanStoreRelease> storeReleaseList = unionDesignPlanStoreReleaseService.findStoreReleaseListByDesignSceneId(designSceneId);
		if (storeReleaseList != null && storeReleaseList.size() > 0) {
			this.processStoreReleaseList(storeReleaseList);
		}
		//删除方案素材库数据
		unionDesignPlanStoreMapper.deleteDesignPlanStoreByDesignSceneId(designSceneId);
	}

	/**
	 * 根据storeids获取store对象
	 * @param storeIdList
	 * @return
	 */
	@Override
	public List<UnionDesignPlanStore> getStorePicByStoreIds(List<Integer> storeIdList) {
		return unionDesignPlanStoreMapper.getStorePicByStoreIds(storeIdList);
	}
}
