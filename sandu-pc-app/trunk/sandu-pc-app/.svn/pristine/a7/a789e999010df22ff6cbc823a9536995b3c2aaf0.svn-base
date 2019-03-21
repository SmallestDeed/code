package com.sandu.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sandu.system.dao.ResPicMapper;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;

@Service("resPicService")
public class ResPicServiceImpl implements ResPicService {

	@Autowired
	private ResPicMapper resPicMapper;

	@Override
	public int add(ResPic resPic) {
		return resPicMapper.insertSelective(resPic);
	}

	@Override
	public int update(ResPic resPic) {
		return resPicMapper.updateByPrimaryKeySelective(resPic);
	}

	@Override
	public int delete(Integer id) {
		return resPicMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ResPic get(Integer id) {
		return resPicMapper.selectByPrimaryKey(id);
	}

}
