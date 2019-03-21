package com.sandu.service.solution.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sandu.api.solution.model.DesignRenderRoam;
import com.sandu.api.solution.service.DesignRenderRoamService;
import com.sandu.service.solution.dao.DesignRenderRoamMapper;

@Service("designRenderRoamService")
public class DesignRenderRoamServiceImpl implements DesignRenderRoamService {

	@Resource
	private DesignRenderRoamMapper designRenderRoamMapper;
	
	@Override
	public int add(DesignRenderRoam designRenderRoam) {
		designRenderRoamMapper.insertSelective(designRenderRoam);
		return designRenderRoam.getId();
	}
	
	@Override
	public DesignRenderRoam selectByScreenShotId(Integer id) {
		return designRenderRoamMapper.selectByScreenId(id);
	}
	
	

}
