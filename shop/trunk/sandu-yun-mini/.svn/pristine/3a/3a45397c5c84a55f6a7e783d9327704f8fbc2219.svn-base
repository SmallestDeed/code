package com.sandu.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.sandu.base.model.DataEntity;
import com.sandu.common.persistence.CrudDao;

public abstract class CrudService<D extends CrudDao<T,Q,V>, T extends DataEntity<T>,Q,V>{
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	
}
