package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.system.dao.SysUserLastLoginLogMapper;
import com.nork.system.model.SysUserLastLoginLog;
import com.nork.system.model.search.SysUserLastLoginLogSearch;
import com.nork.system.service.SysUserLastLoginLogService;

/**   
 * @Title: SysUserLastLoginLogServiceImpl.java 
 * @Package com.nork.系统模块.service.impl
 * @Description:system-用户最后登录时间ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-07-04 10:03:13
 * @version V1.0   
 */
@Service("sysUserLastLoginLogService")
public class SysUserLastLoginLogServiceImpl implements SysUserLastLoginLogService {

	private SysUserLastLoginLogMapper sysUserLastLoginLogMapper;

	@Autowired
	public void setSysUserLastLoginLogMapper(
			SysUserLastLoginLogMapper sysUserLastLoginLogMapper) {
		this.sysUserLastLoginLogMapper = sysUserLastLoginLogMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysUserLastLoginLog
	 * @return  int 
	 */
	@Override
	public int add(SysUserLastLoginLog sysUserLastLoginLog) {
		sysUserLastLoginLogMapper.insertSelective(sysUserLastLoginLog);
		return sysUserLastLoginLog.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysUserLastLoginLog
	 * @return  int 
	 */
	@Override
	public int update(SysUserLastLoginLog sysUserLastLoginLog) {
		return sysUserLastLoginLogMapper
				.updateByPrimaryKeySelective(sysUserLastLoginLog);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysUserLastLoginLogMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 * 这个方法被改成了 按用户Id查找用户最后登录记录，慎用
	 * @param id
	 * @return  SysUserLastLoginLog 
	 */
	@Override
	public SysUserLastLoginLog get(Integer id) {
		return sysUserLastLoginLogMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysUserLastLoginLog
	 * @return   List<SysUserLastLoginLog>
	 */
	@Override
	public List<SysUserLastLoginLog> getList(SysUserLastLoginLog sysUserLastLoginLog) {
	    return sysUserLastLoginLogMapper.selectList(sysUserLastLoginLog);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysUserLastLoginLog
	 * @return   int
	 */
	@Override
	public int getCount(SysUserLastLoginLogSearch sysUserLastLoginLogSearch){
		return  sysUserLastLoginLogMapper.selectCount(sysUserLastLoginLogSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserLastLoginLog
	 * @return   List<SysUserLastLoginLog>
	 */
	@Override
	public List<SysUserLastLoginLog> getPaginatedList(
			SysUserLastLoginLogSearch sysUserLastLoginLogSearch) {
		return sysUserLastLoginLogMapper.selectPaginatedList(sysUserLastLoginLogSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
