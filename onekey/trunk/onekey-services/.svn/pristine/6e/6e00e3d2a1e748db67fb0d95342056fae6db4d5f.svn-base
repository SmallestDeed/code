package com.nork.onekeydesign.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.dao.DesignPlanBrandMapper;
import com.nork.onekeydesign.dao.DesignPlanCheckMapper;
import com.nork.onekeydesign.dao.DesignPlanMapper;
import com.nork.onekeydesign.model.DesignPlanBrand;
import com.nork.onekeydesign.model.DesignPlanRecommendedResult;
import com.nork.onekeydesign.service.DesignPlanBrandService;
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
 
 
}
