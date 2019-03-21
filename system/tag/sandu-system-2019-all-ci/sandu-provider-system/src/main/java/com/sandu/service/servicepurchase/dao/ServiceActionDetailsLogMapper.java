package com.sandu.service.servicepurchase.dao;

import com.sandu.api.servicepurchase.model.ServiceActionDetailsLog;

public interface ServiceActionDetailsLogMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(ServiceActionDetailsLog record);

	int insertSelective(ServiceActionDetailsLog record);

	ServiceActionDetailsLog selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ServiceActionDetailsLog record);

	int updateByPrimaryKey(ServiceActionDetailsLog record);
}