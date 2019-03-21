package com.sandu.pay.system.service.impl;

import com.sandu.pay.system.dao.SysTaskMapper;
import com.sandu.system.model.task.SysTask;
import com.sandu.system.service.SysTaskService;
import com.sandu.web.task.model.DesignPlan;
import com.sandu.web.task.model.search.SysTaskSearch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**   
 * @Title: SysTaskServiceImpl.java 
 * @Package com.nork.task.service.impl
 * @Description:任务-系统任务表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-11-18 10:51:21
 * @version V1.0   
 */
@Service("sysTaskService")
public class SysTaskServiceImpl implements SysTaskService {
	private static Logger logger = Logger.getLogger(SysTaskServiceImpl.class);
	
	@Autowired
	private SysTaskMapper sysTaskMapper;

	/**
	 * 新增数据
	 *
	 * @param sysTask
	 * @return  int 
	 */
	@Override
	public int add(SysTask sysTask) {
		sysTaskMapper.insertSelective(sysTask);
		return sysTask.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysTask
	 * @return  int 
	 */
	@Override
	public int update(SysTask sysTask) {
		return sysTaskMapper
				.updateByPrimaryKeySelective(sysTask);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysTaskMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysTask 
	 */
	@Override
	public SysTask get(Integer id) {
		return sysTaskMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysTask
	 * @return   List<SysTask>
	 */
	@Override
	public List<SysTask> getList(SysTask sysTask) {
	    return sysTaskMapper.selectList(sysTask);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysTaskSearch
	 * @return   int
	 */
	@Override
	public int getCount(SysTaskSearch sysTaskSearch){
		return  sysTaskMapper.selectCount(sysTaskSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysTaskSearch
	 * @return   List<SysTask>
	 */
	@Override
	public List<SysTask> getPaginatedList(
			SysTaskSearch sysTaskSearch) {
		return sysTaskMapper.selectPaginatedList(sysTaskSearch);
	}


	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlan 
	 */
	@Override
	public DesignPlan getDesignPlan(Integer id) {
		return sysTaskMapper.selectByPrimaryDesignPlan(id);
	}


}
