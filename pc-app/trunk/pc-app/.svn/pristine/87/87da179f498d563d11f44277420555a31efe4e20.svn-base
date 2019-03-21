package com.nork.design.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.design.dao.DesignPlanBrandMapper;
import com.nork.design.dao.DesignPlanCheckMapper;
import com.nork.design.dao.DesignPlanMapper;
import com.nork.design.model.DesignPlanBrand;
import com.nork.design.model.DesignPlanRecommendedResult;
import com.nork.design.service.DesignPlanBrandService;
import com.nork.product.model.BaseBrand;
import com.nork.system.dao.SysRoleMapper;
import com.nork.system.dao.SysUserMapper;
import com.nork.system.dao.SysUserRoleMapper;


@Service("DesignPlanBrandService")
@Transactional
public class DesignPlanBrandServiceImpl implements DesignPlanBrandService{
 
	
	DesignPlanCheckMapper designPlanCheckMapper;
	
	DesignPlanBrandMapper designPlanBrandMapper;
	
	DesignPlanMapper designPlanMapper;
	
	SysUserMapper sysUserMapper;
	
	SysUserRoleMapper sysUserRoleMapper;
 
	SysRoleMapper sysRoleMapper;
	
	Logger logger = Logger.getLogger(DesignPlanBrandServiceImpl.class);
	
	private String logPrefixClass = "function:DesignPlanBrandServiceImpl.";
	
	@Autowired
	public void setDesignPlanCheckMapper(DesignPlanCheckMapper designPlanCheckMapper) {
		this.designPlanCheckMapper = designPlanCheckMapper;
	}
	
	@Autowired
	public void setDesignPlanBrandMapper(DesignPlanBrandMapper designPlanBrandMapper) {
		this.designPlanBrandMapper = designPlanBrandMapper;
	}

	@Autowired
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
	}

	@Autowired
	public void setSysUserRoleMapper(SysUserRoleMapper sysUserRoleMapper) {
		this.sysUserRoleMapper = sysUserRoleMapper;
	}
	
	@Autowired
	public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
		this.sysRoleMapper = sysRoleMapper;
	}

	@Autowired
	public void setDesignPlanMapper(DesignPlanMapper designPlanMapper) {
		this.designPlanMapper = designPlanMapper;
	}

	@Autowired
	public void setdesignPlanBrandMapper(DesignPlanBrandMapper designPlanBrandMapper) {
		this.designPlanBrandMapper = designPlanBrandMapper;
	}

	@Override
	public int add(DesignPlanBrand designPlanBrand) {
		return designPlanBrandMapper.insertSelective(designPlanBrand);
	}

	@Override
	public int update(DesignPlanBrand designPlanBrand) {
		return designPlanBrandMapper.updateByPrimaryKeySelective(designPlanBrand);
	}

	@Override
	public int delete(Integer id) {
		return designPlanBrandMapper.deleteByPrimaryKey(id);
	}

	@Override
	public DesignPlanBrand get(Integer id) {
		return designPlanBrandMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<DesignPlanBrand> getList(DesignPlanBrand designPlanBrand) {
		return designPlanBrandMapper.selectList(designPlanBrand);
	}

	@Override
	public int getCount(DesignPlanBrand designPlanBrand) {
		return designPlanBrandMapper.selectCount(designPlanBrand);
	}

	@Override
	public Integer getPlanRecommendedCount(DesignPlanBrand designPlanBrand) {
		if(designPlanBrand.getBrandIds()==null||designPlanBrand.getBrandIds().size()<=0){
			return 0;
		}
		return designPlanBrandMapper.getPlanRecommendedCount(designPlanBrand);
	}

	@Override
	public List<DesignPlanRecommendedResult> getPlanRecommendedList(DesignPlanBrand designPlanBrand) {
		if(designPlanBrand.getBrandIds()==null||designPlanBrand.getBrandIds().size()<=0){
			return null;
		}
		return designPlanBrandMapper.getPlanRecommendedList(designPlanBrand);
	}
 
	/**
	 * 通过推荐方案id  查询 绑定的品牌
	 * @param parseInt
	 * @return
	 */
	@Override
	public List<BaseBrand> getListByPlanRecommendedId(int planRecommendedId) {
		return designPlanBrandMapper.getListByPlanRecommendedId(planRecommendedId);
	}

	@Override
	public void batchAdd(List<DesignPlanBrand> planBrandList) {
		designPlanBrandMapper.batchAdd(planBrandList);
	}

	@Override
	public void deleteByPlanId(Integer designPlanRecommendedId) {
		String logPrefixFunction = logPrefixClass + "deleteByPlanId -> ";
		
		// 参数验证 ->start
		if(designPlanRecommendedId == null) {
			logger.error(logPrefixFunction + "designPlanRecommendedId = null");
			return;
		}
		// 参数验证 ->end
		
		designPlanBrandMapper.deleteByPlanId(designPlanRecommendedId);
	}

	@Override
	public void save(Integer companyId, Integer planId, LoginUser loginUser) {
		// 参数验证 ->start
		if(companyId == null) {
			return;
		}
		if(planId == null) {
			return;
		}
		// 参数验证 ->end
		
		DesignPlanBrand designPlanBrand = new DesignPlanBrand();
		designPlanBrand.setIsDeleted(0);
		designPlanBrand.setPlanId(planId);
		designPlanBrand.setCompanyId(companyId);
		this.sysSave(designPlanBrand, loginUser);
		this.add(designPlanBrand);
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(DesignPlanBrand model, LoginUser loginUser){
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
	
}
