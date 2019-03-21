package com.nork.cityunion.service.impl;

import java.util.Date;
import java.util.List;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionDesignPlanStoreReleaseDetailsMapper;
import com.nork.cityunion.model.UnionDesignPlanStoreReleaseDetails;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseDetailsSearch;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseDetailsService;

/**   
 * @Title: UnionDesignPlanStoreReleaseDetailsServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟素材发布明细表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:26:23
 * @version V1.0   
 */
@Service("unionDesignPlanStoreReleaseDetailsService")
public class UnionDesignPlanStoreReleaseDetailsServiceImpl implements UnionDesignPlanStoreReleaseDetailsService {

	private UnionDesignPlanStoreReleaseDetailsMapper unionDesignPlanStoreReleaseDetailsMapper;

	@Autowired
	public void setUnionDesignPlanStoreReleaseDetailsMapper(
			UnionDesignPlanStoreReleaseDetailsMapper unionDesignPlanStoreReleaseDetailsMapper) {
		this.unionDesignPlanStoreReleaseDetailsMapper = unionDesignPlanStoreReleaseDetailsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStoreReleaseDetails
	 * @return  int 
	 */
	@Override
	public int add(UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails) {
		unionDesignPlanStoreReleaseDetailsMapper.insertSelective(unionDesignPlanStoreReleaseDetails);
		return unionDesignPlanStoreReleaseDetails.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStoreReleaseDetails
	 * @return  int 
	 */
	@Override
	public int update(UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails) {
		return unionDesignPlanStoreReleaseDetailsMapper
				.updateByPrimaryKeySelective(unionDesignPlanStoreReleaseDetails);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionDesignPlanStoreReleaseDetailsMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionDesignPlanStoreReleaseDetails 
	 */
	@Override
	public UnionDesignPlanStoreReleaseDetails get(Integer id) {
		return unionDesignPlanStoreReleaseDetailsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStoreReleaseDetails
	 * @return   List<UnionDesignPlanStoreReleaseDetails>
	 */
	@Override
	public List<UnionDesignPlanStoreReleaseDetails> getList(UnionDesignPlanStoreReleaseDetails unionDesignPlanStoreReleaseDetails) {
	    return unionDesignPlanStoreReleaseDetailsMapper.selectList(unionDesignPlanStoreReleaseDetails);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreReleaseDetails
	 * @return   int
	 */
	@Override
	public int getCount(UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailsSearch){
		return  unionDesignPlanStoreReleaseDetailsMapper.selectCount(unionDesignPlanStoreReleaseDetailsSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreReleaseDetails
	 * @return   List<UnionDesignPlanStoreReleaseDetails>
	 */
	@Override
	public List<UnionDesignPlanStoreReleaseDetails> getPaginatedList(
			UnionDesignPlanStoreReleaseDetailsSearch unionDesignPlanStoreReleaseDetailsSearch) {
		return unionDesignPlanStoreReleaseDetailsMapper.selectPaginatedList(unionDesignPlanStoreReleaseDetailsSearch);
	}

	/**
	 * 其他
	 * 
	 */
	/**
	 * 自动存储系统字段
	 */
	@Override
	public void sysSave(UnionDesignPlanStoreReleaseDetails model, LoginUser loginUser){
		if(model != null){
			if (loginUser == null) {
				loginUser = new LoginUser();
				loginUser.setLoginName("nologin");
			}
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				}
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 根据发布ID更新发布详情数据
	 * @param releaseDetails
	 * @return
	 */
	@Override
	public int updateByReleaseId(UnionDesignPlanStoreReleaseDetails releaseDetails) {
		return unionDesignPlanStoreReleaseDetailsMapper.updateByReleaseId(releaseDetails);
	}


	/**
	 * 根据设计方案Id删除发布方案素材库数据
	 * @param designPlanId
	 * @return
	 */
	@Override
	public Integer deleteStoreReleaseDetailsByDesignPlanId(Integer designPlanId){
		return unionDesignPlanStoreReleaseDetailsMapper.deleteStoreReleaseDetailsByDesignPlanId(designPlanId);
	}

	/**
	 * 根据设计效果图方案Id删除发布方案素材库数据
	 * @param designSceneId
	 * @return
	 */
	@Override
	public Integer deleteStoreReleaseDetailsByDesignSceneId(Integer designSceneId){
		return unionDesignPlanStoreReleaseDetailsMapper.deleteStoreReleaseDetailsByDesignSceneId(designSceneId);
	}

}
