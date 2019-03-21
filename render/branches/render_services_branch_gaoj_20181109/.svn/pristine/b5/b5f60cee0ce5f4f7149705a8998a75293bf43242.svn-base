package com.nork.mobile.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nork.mobile.dao.MobileRenderRecordMapper;
import com.nork.mobile.model.ProductReplaceTaskDetail;
import com.nork.mobile.service.MobileRenderRecordService;

@Service("mobileRenderRecordService")
public class MobileRenderRecordServiceImpl implements MobileRenderRecordService {

	@Autowired
	private MobileRenderRecordMapper mobileRenderRecordMapper;
	@Override
	public List<ProductReplaceTaskDetail> selectListByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		return mobileRenderRecordMapper.selectListByTaskId(taskId);
	}

	@Override
	public List<ProductReplaceTaskDetail> selectTextureListByTaskId(Integer taskId) {
		// TODO Auto-generated method stub
		return mobileRenderRecordMapper.selectTextureListByTaskId(taskId);
	}
	
	
}
