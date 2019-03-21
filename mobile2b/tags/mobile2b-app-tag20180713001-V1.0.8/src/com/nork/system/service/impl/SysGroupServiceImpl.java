package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.SysGroupMapper;
import com.nork.system.model.SysGroup;
import com.nork.system.model.search.SysGroupSearch;
import com.nork.system.service.SysGroupService;

/**   
 * @Title: SysGroupServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-组织表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-01 15:44:23
 * @version V1.0   
 */
@Service("sysGroupService")
@Transactional
public class SysGroupServiceImpl implements SysGroupService {

	private SysGroupMapper sysGroupMapper;

	@Autowired
	public void setSysGroupMapper(
			SysGroupMapper sysGroupMapper) {
		this.sysGroupMapper = sysGroupMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysGroup
	 * @return  int 
	 */
	@Override
	public int add(SysGroup sysGroup) {
		sysGroupMapper.insertSelective(sysGroup);
		return sysGroup.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysGroup
	 * @return  int 
	 */
	@Override
	public int update(SysGroup sysGroup) {
		return sysGroupMapper
				.updateByPrimaryKeySelective(sysGroup);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysGroupMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysGroup 
	 */
	@Override
	public SysGroup get(Integer id) {
		return sysGroupMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysGroup
	 * @return   List<SysGroup>
	 */
	@Override
	public List<SysGroup> getList(SysGroup sysGroup) {
	    return sysGroupMapper.selectList(sysGroup);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysGroup
	 * @return   int
	 */
	@Override
	public int getCount(SysGroupSearch sysGroupSearch){
		return  sysGroupMapper.selectCount(sysGroupSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysGroup
	 * @return   List<SysGroup>
	 */
	@Override
	public List<SysGroup> getPaginatedList(
			SysGroupSearch sysGroupSearch) {
		return sysGroupMapper.selectPaginatedList(sysGroupSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
