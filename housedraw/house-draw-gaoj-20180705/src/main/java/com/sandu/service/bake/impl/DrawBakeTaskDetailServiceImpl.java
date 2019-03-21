package com.sandu.service.bake.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.model.DrawBakeTaskDetail;
import com.sandu.api.house.service.DrawBakeTaskDetailService;
import com.sandu.service.bake.dao.DrawBakeTaskDetailMapper;

@Service("drawBakeTaskDetailService")
public class DrawBakeTaskDetailServiceImpl implements DrawBakeTaskDetailService {

	@Autowired
	private DrawBakeTaskDetailMapper drawBakeTaskDetailMapper;

	@Override
	public DrawBakeTaskDetail get(Long taskDetailId) {
		// 参数验证 ->start
		if (taskDetailId == null) {
			return null;
		}
		// 参数验证 ->end
		return drawBakeTaskDetailMapper.selectByPrimaryKey(taskDetailId);
	}

	@Override
	public DrawBakeTaskDetail getSubTask(Long taskDetailId) {
		if (taskDetailId == null) {
			return null;
		}

		return drawBakeTaskDetailMapper.getSubTask(taskDetailId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateTaskDetailStatus(int status, Long taskDetailId) {
		return drawBakeTaskDetailMapper.updateTaskDetailStatus(taskDetailId, status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int updateByPrimaryKeySelective(DrawBakeTaskDetail record) {
		if (record == null) {
			return -1;
		}
		return drawBakeTaskDetailMapper.updateByPrimaryKeySelective(record);
	}
}
