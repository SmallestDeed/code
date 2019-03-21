package com.sandu.service.proprietorinfo.impl;

import com.sandu.api.area.model.BaseArea;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.proprietorinfo.input.ProprietorInfoUpdate;
import com.sandu.api.proprietorinfo.model.ProprietorInfo;
import com.sandu.api.proprietorinfo.service.ProprietorInfoService;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.user.model.SysUser;
import com.sandu.service.proprietorinfo.dao.ProprietorInfoMapper;
import com.sandu.system.model.SysDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * ProprietorInfo
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Oct-18 14:00
 */
@Slf4j
@Service("ProprietorInfoService")
public class ProprietorInfoServiceImpl implements ProprietorInfoService {

	@Autowired
	private ProprietorInfoMapper proprietorInfoMapper;

	@Override
	public int insert(ProprietorInfo ProprietorInfo) {
		int result = proprietorInfoMapper.insert(ProprietorInfo);
		if (result > 0) {
			return ProprietorInfo.getId();
		}
		return 0;
	}

	@Override
	public int update(ProprietorInfo ProprietorInfo) {
		return proprietorInfoMapper.updateByPrimaryKey(ProprietorInfo);
	}

	@Override
	public ProprietorInfo getById(int ProprietorInfoId) {
		return proprietorInfoMapper.selectByPrimaryKey(ProprietorInfoId);
	}

	@Override
	public List<ProprietorInfo> getList(ProprietorInfo proprietorInfoQuery, Integer start, Integer limit) {
		return proprietorInfoMapper.getList(proprietorInfoQuery, start, limit);
	}

	@Override
	public List<BaseArea> getBaseAreaListByLevelId(Integer levelId) {
		return proprietorInfoMapper.getBaseAreaListByLevelId(levelId);
	}

	@Override
	public Integer updateProprietorInfo(ProprietorInfoUpdate proprietorInfoUpdate) {
		return proprietorInfoMapper.updateProprietorInfo(proprietorInfoUpdate);
	}

	@Override
	public List<ProprietorInfo> getById(List<Integer> ids) {
		return proprietorInfoMapper.selectByIds(ids);
	}

	@Override
	public String getRenderScenePlanPic(Integer designplanId) {
		return proprietorInfoMapper.getRenderScenePlanPic(designplanId);
	}

	@Override
	public String getFullHousePlanPic(Integer designplanId) {
		return proprietorInfoMapper.getFullHousePlanPic(designplanId);
	}

	@Override
	public CompanyShop getShopById(Integer id) {
		return proprietorInfoMapper.getShopById(id);
	}

	@Override
	public List<SysDictionary> findAllByType(String type) {
		return proprietorInfoMapper.findAllByType(type);
	}

	@Override
	public SysDictionary findOneByTypeAndValue(String type, Integer value) {
		return proprietorInfoMapper.findOneByTypeAndValue(type, value);
	}

	@Override
	public BaseAreaListVO queryAreaByCode(String provinceCode) {
		return proprietorInfoMapper.queryAreaByCode(provinceCode);
	}

	@Override
	public List<ProprietorInfo> listWithCustomerType(List<Integer> ids) {
		if (ids != null && ids.isEmpty()) {
			return Collections.emptyList();
		}
		return proprietorInfoMapper.listWithCustomerType(ids);
	}

	@Override
	public Integer getListCount(ProprietorInfo proprietorInfoQuery) {
		return proprietorInfoMapper.getListCount(proprietorInfoQuery);
	}

	@Override
	public SysUser getSysUserById(Integer id) {
		return proprietorInfoMapper.getSysUserById(id);
	}

	@Override
	public Integer deleteByIds(List<Integer> ids) {
		if (ids == null || ids.isEmpty()) {
			return -1;
		}
		return proprietorInfoMapper.deleteByIds(ids);
	}
}

