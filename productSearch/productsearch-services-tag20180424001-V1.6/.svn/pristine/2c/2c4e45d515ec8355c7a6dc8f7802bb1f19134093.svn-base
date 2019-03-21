package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.SysUserFansMapper;
import com.nork.system.model.SysUserFans;
import com.nork.system.model.search.SysUserFansSearch;
import com.nork.system.service.SysUserFansService;

/**   
 * @Title: SysUserFansServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-用户粉丝表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 17:34:55
 * @version V1.0   
 */
@Service("sysUserFansService")
@Transactional
public class SysUserFansServiceImpl implements SysUserFansService {

	private SysUserFansMapper sysUserFansMapper;

	@Autowired
	public void setSysUserFansMapper(
			SysUserFansMapper sysUserFansMapper) {
		this.sysUserFansMapper = sysUserFansMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysUserFans
	 * @return  int 
	 */
	@Override
	public int add(SysUserFans sysUserFans) {
		sysUserFansMapper.insertSelective(sysUserFans);
		return sysUserFans.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysUserFans
	 * @return  int 
	 */
	@Override
	public int update(SysUserFans sysUserFans) {
		return sysUserFansMapper
				.updateByPrimaryKeySelective(sysUserFans);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysUserFansMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysUserFans 
	 */
	@Override
	public SysUserFans get(Integer id) {
		return sysUserFansMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysUserFans
	 * @return   List<SysUserFans>
	 */
	@Override
	public List<SysUserFans> getList(SysUserFans sysUserFans) {
	    return sysUserFansMapper.selectList(sysUserFans);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysUserFans
	 * @return   int
	 */
	@Override
	public int getCount(SysUserFansSearch sysUserFansSearch){
		return  sysUserFansMapper.selectCount(sysUserFansSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserFans
	 * @return   List<SysUserFans>
	 */
	@Override
	public List<SysUserFans> getPaginatedList(
			SysUserFansSearch sysUserFansSearch) {
		return sysUserFansMapper.selectPaginatedList(sysUserFansSearch);
	}

	@Override
	public SysUserFans getObject(SysUserFans sysUserFans) {
		return sysUserFansMapper.selectByObject(sysUserFans);
	}

	@Override
	public int getFansCount(SysUserFans sysUserFans) {
		return sysUserFansMapper.selectFansCount(sysUserFans);
	}

	/**
	 * 其他
	 * 
	 */


}
