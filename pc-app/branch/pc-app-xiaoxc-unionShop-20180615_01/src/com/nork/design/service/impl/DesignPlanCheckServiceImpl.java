package com.nork.design.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.dao.DesignPlanCheckMapper;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanCheck;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.service.DesignPlanCheckService;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserPlanRecommended;
import com.nork.system.service.SysRoleService;
import com.nork.system.service.SysUserService;

@Service("designPlanCheckService")
@Transactional
public class DesignPlanCheckServiceImpl implements DesignPlanCheckService{
	
	private static Logger logger = Logger.getLogger(DesignPlanCheckServiceImpl.class);
	
	DesignPlanCheckMapper designPlanCheckMapper;
	
	SysUserService sysUserService;
	
	SysRoleService sysRoleService;
 
	@Autowired
	public void setDesignPlanCheckMapper(DesignPlanCheckMapper designPlanCheckMapper) {
		this.designPlanCheckMapper = designPlanCheckMapper;
	}
  

	@Autowired
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	} 

	@Autowired
	public SysUserService getSysUserService() {
		return sysUserService;
	}


	public int add(DesignPlanCheck designPlanCheck) {
		return designPlanCheckMapper.insertSelective(designPlanCheck);
	}

 


	@Override
	public int update(DesignPlanCheck designPlanCheck) {
		return designPlanCheckMapper.updateByPrimaryKeySelective(designPlanCheck);
	}

	@Override
	public int delete(Integer id) {
		return designPlanCheckMapper.deleteByPrimaryKey(id);
	}

	@Override
	public DesignPlanCheck get(Integer id) {
		return designPlanCheckMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DesignPlanCheck> getList(DesignPlanCheck designPlanCheck) {
		return designPlanCheckMapper.selectList(designPlanCheck);
	}

	@Override
	public int getCount(DesignPlanCheck designPlanCheck) {
		return designPlanCheckMapper.selectCount(designPlanCheck);
	}

	/**
	 * 取得该设计方案的  审核人员列表
	 * @param designPlan
	 * @return
	 */
	@Override
	public List<SysUserPlanRecommended> planCheckUserList(DesignPlan designPlan) {
		
		List<SysUserPlanRecommended>resList = new ArrayList<SysUserPlanRecommended>();
		List<SysUser>list = null;
		List<DesignPlanCheck>checkList = null;

		if(designPlan==null){
			logger.error("planCheckUserList  methods the error:  designPlan is null ");
			return resList;
		}
		try{
			//通过角色编码 查询用户
			list = sysUserService.getUserByRoleCode("0232");
			if(list!=null && list.size()>0){
				DesignPlanCheck designPlanCheck = new DesignPlanCheck();
				designPlanCheck.setIsDeleted(0);
				designPlanCheck.setPlanId(designPlan.getId());
				checkList = designPlanCheckMapper.selectList(designPlanCheck);
				for (SysUser sysUser_ : list) {
					Integer userId = sysUser_.getId();
					SysUserPlanRecommended sysUserPlanRecommended = new SysUserPlanRecommended();
					sysUserPlanRecommended.setUserId(userId);
					sysUserPlanRecommended.setUserName(sysUser_.getUserName());
					if(checkList!=null && checkList.size()>0){
						for (DesignPlanCheck designPlanCheck_ : checkList) {
							Integer selectUserId = designPlanCheck_.getUserId();
							if(selectUserId.intValue() == userId.intValue()){
								sysUserPlanRecommended.setDesignPlanRecommendedBeSelected(1);
								sysUserPlanRecommended.setDesignPlanCheckId(designPlanCheck_.getId());
							}
						}
					}
					resList.add(sysUserPlanRecommended);
				}	
			}
		}catch (Exception e) {
			logger.error("planCheckUserList  methods the error:  "+ e );
			return resList;
		}
		return resList;
	}

	/**
	 * 方案推荐审核列表
	 * @param designPlanCheck
	 * @return
	 */
	@Override
	public List<DesignPlanRecommendedResult> getDesignPlanCheckList(DesignPlanCheck designPlanCheck) {
		return designPlanCheckMapper.getDesignPlanCheckList(designPlanCheck);
	}


	@Override
	public int getDesignPlanCheckCount(DesignPlanCheck designPlanCheck) {
		return designPlanCheckMapper.getDesignPlanCheckCount(designPlanCheck);
	}

}
