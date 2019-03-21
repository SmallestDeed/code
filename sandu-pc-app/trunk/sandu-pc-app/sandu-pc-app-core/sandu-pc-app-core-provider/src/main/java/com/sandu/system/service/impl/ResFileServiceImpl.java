package com.sandu.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandu.system.dao.ResFileMapper;
import com.sandu.system.model.ResFile;
import com.sandu.system.service.ResFileService;

@Service("resFileService")
public class ResFileServiceImpl implements ResFileService {

	@Autowired
	private ResFileMapper resFileMapper;

	@Override
	public int add(ResFile resFile) {
		return resFileMapper.insertSelective(resFile);
	}

	@Override
	public int update(ResFile resFile) {
		return resFileMapper.updateByPrimaryKeySelective(resFile);
	}

	@Override
	public int delete(Integer id) {
		return resFileMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ResFile get(Integer id) {
		return resFileMapper.selectByPrimaryKey(id);
	}

}
