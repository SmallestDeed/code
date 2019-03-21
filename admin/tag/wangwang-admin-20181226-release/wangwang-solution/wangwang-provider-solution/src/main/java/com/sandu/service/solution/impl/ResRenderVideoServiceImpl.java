package com.sandu.service.solution.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sandu.api.solution.model.ResRenderVideo;
import com.sandu.api.solution.service.ResRenderVideoService;
import com.sandu.service.solution.dao.ResRenderVideoMapper;

@Service
public class ResRenderVideoServiceImpl implements ResRenderVideoService {

	@Resource
	private ResRenderVideoMapper resRenderVideoMapper;

	@Override
	public int add(ResRenderVideo resRenderVideo) {
		return resRenderVideoMapper.insertSelective(resRenderVideo);
	}

	@Override
	public int update(ResRenderVideo resRenderVideo) {
		return resRenderVideoMapper.updateByPrimaryKeySelective(resRenderVideo);
	}

	@Override
	public int delete(Integer id) {
		return resRenderVideoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public ResRenderVideo get(Integer id) {
		return resRenderVideoMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ResRenderVideo> getList(ResRenderVideo resRenderVideo) {
		return resRenderVideoMapper.selectList(resRenderVideo);
	}
}
