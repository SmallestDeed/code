package com.sandu.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandu.system.dao.ResModelMapper;
import com.sandu.system.model.ResModel;
import com.sandu.system.service.ResModelService;

@Service("resModelService")
public class ResModelServiceImpl implements ResModelService {

	@Autowired
	private ResModelMapper resModelMapper;

	@Override
	public int add(ResModel resModel) {
		return resModelMapper.insertSelective(resModel);
	}

	@Override
	public int update(ResModel resModel) {
		return resModelMapper.updateByPrimaryKeySelective(resModel);
	}

	@Override
	public int delete(Integer id) {
		return resModelMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ResModel get(Integer id) {
		return resModelMapper.selectByPrimaryKey(id);
	}


}
