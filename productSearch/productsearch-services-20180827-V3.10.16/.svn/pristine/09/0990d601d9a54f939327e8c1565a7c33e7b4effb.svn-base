package com.nork.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.SysFuncMapper;
import com.nork.system.model.AppMenuModel;
import com.nork.system.model.SysFunc;
import com.nork.system.model.search.SysFuncSearch;
import com.nork.system.service.SysFuncService;

/**
 * @Title: SysFuncServiceImpl.java
 * @Package com.nork.system.service.impl
 * @Description:系统-功能菜单ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2015-05-28 10:10:35
 * @version V1.0
 */
@Service("sysFuncService")
@Transactional
public class SysFuncServiceImpl implements SysFuncService {

	private SysFuncMapper sysFuncMapper;

	@Autowired
	public void setSysFuncMapper(SysFuncMapper sysFuncMapper) {
		this.sysFuncMapper = sysFuncMapper;
	}

	/**
	 * 新增数据
	 * 
	 * @param sysFunc
	 * @return int
	 */
	@Override
	public int add(SysFunc sysFunc) {
		sysFuncMapper.insertSelective(sysFunc);
		return sysFunc.getId();
	}

	/**
	 * 更新数据
	 * 
	 * @param sysFunc
	 * @return int
	 */
	@Override
	public int update(SysFunc sysFunc) {
		return sysFuncMapper.updateByPrimaryKeySelective(sysFunc);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 * @return int
	 */
	@Override
	public int delete(Integer id) {
		return sysFuncMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 获取数据详情
	 * 
	 * @param id
	 * @return SysFunc
	 */
	@Override
	public SysFunc get(Integer id) {
		return sysFuncMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param sysFunc
	 * @return List<SysFunc>
	 */
	@Override
	public List<SysFunc> getList(SysFunc sysFunc) {
		return sysFuncMapper.selectList(sysFunc);
	}

	/**
	 * 获取数据数量
	 * 
	 * @param sysFunc
	 * @return int
	 */
	@Override
	public int getCount(SysFuncSearch sysFuncSearch) {
		return sysFuncMapper.selectCount(sysFuncSearch);
	}

	/**
	 * 分页获取数据
	 * 
	 * @param sysFunc
	 * @return List<SysFunc>
	 */
	@Override
	public List<SysFunc> getPaginatedList(SysFuncSearch sysFuncSearch) {
		return sysFuncMapper.selectPaginatedList(sysFuncSearch);
	}

	/**
	 * 获取用的菜单权限
	 * @param  userId
	 * @return   List<SysFunc>
	 */
	public List<SysFunc> getUserMenus(Integer userId){
		return sysFuncMapper.getUserMenus(userId);
	}

	@Override
	public List<AppMenuModel> getAppMenu(int userId) {
		List<AppMenuModel>list = new ArrayList<AppMenuModel>();
		AppMenuModel AppMenuModel = null;
		
		AppMenuModel = new AppMenuModel();
		AppMenuModel.setKey("");
		AppMenuModel.setKey("审核列表");
		list.add(AppMenuModel);
		
		AppMenuModel = new AppMenuModel();
		AppMenuModel.setKey("");
		AppMenuModel.setKey("方案设计");
		list.add(AppMenuModel);
		
		AppMenuModel = new AppMenuModel();
		AppMenuModel.setKey("");
		AppMenuModel.setKey("方案推荐");
		list.add(AppMenuModel);
		
		AppMenuModel = new AppMenuModel();
		AppMenuModel.setKey("");
		AppMenuModel.setKey("我的设计");
		list.add(AppMenuModel);
		
		AppMenuModel = new AppMenuModel();
		AppMenuModel.setKey("");
		AppMenuModel.setKey("建材家居");
		list.add(AppMenuModel);
		
		AppMenuModel = new AppMenuModel();
		AppMenuModel.setKey("");
		AppMenuModel.setKey("用户中心");
		list.add(AppMenuModel);
		
		AppMenuModel = new AppMenuModel();
		AppMenuModel.setKey("");
		AppMenuModel.setKey("帮助中心");
		list.add(AppMenuModel);
		return list;
	}

	/**
	 * 获取登录用户的U3D权限菜单
	 * add by yangzhun
	 * @param userId
	 * @return
	 */
	@Override
	public List<SysFunc> getUserU3DMenus(Integer userId) {
		return sysFuncMapper.getUserU3DMenus(userId);
	}
}
