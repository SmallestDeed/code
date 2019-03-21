package com.nork.home.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.home.dao.BaseHouseApplyMapper;
import com.nork.home.model.BaseHouseApply;
import com.nork.home.model.search.BaseHouseApplySearch;
import com.nork.home.service.BaseHouseApplyService;
import com.nork.system.model.SysUser;

/**   
 * @Title: BaseHouseApplyServiceImpl.java 
 * @Package com.nork.home.service.impl
 * @Description:户型房型-户型申请表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-10-13 11:45:31
 * @version V1.0   
 */
@Service("baseHouseApplyService")
public class BaseHouseApplyServiceImpl implements BaseHouseApplyService {

	private BaseHouseApplyMapper baseHouseApplyMapper;

	@Autowired
	public void setBaseHouseApplyMapper(
			BaseHouseApplyMapper baseHouseApplyMapper) {
		this.baseHouseApplyMapper = baseHouseApplyMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param baseHouseApply
	 * @return  int 
	 */
	@Override
	public int add(BaseHouseApply baseHouseApply) {
		baseHouseApplyMapper.insertSelective(baseHouseApply);
		return baseHouseApply.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseHouseApply
	 * @return  int 
	 */
	@Override
	public int update(BaseHouseApply baseHouseApply) {
		return baseHouseApplyMapper
				.updateByPrimaryKeySelective(baseHouseApply);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseHouseApplyMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseHouseApply 
	 */
	@Override
	public BaseHouseApply get(Integer id) {
		return baseHouseApplyMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseHouseApply
	 * @return   List<BaseHouseApply>
	 */
	@Override
	public List<BaseHouseApply> getList(BaseHouseApply baseHouseApply) {
	    return baseHouseApplyMapper.selectList(baseHouseApply);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseHouseApply
	 * @return   int
	 */
	@Override
	public int getCount(BaseHouseApplySearch baseHouseApplySearch){
		return  baseHouseApplyMapper.selectCount(baseHouseApplySearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseHouseApply
	 * @return   List<BaseHouseApply>
	 */
	@Override
	public List<BaseHouseApply> getPaginatedList(
			BaseHouseApplySearch baseHouseApplySearch) {
		return baseHouseApplyMapper.selectPaginatedList(baseHouseApplySearch);
	}

	public void sysSave(BaseHouseApply model, SysUser loginUser) {
		if(model != null){
				if(model.getId() == null){
					model.setUserId(loginUser.getId());
					model.setGmtCreate(new Date());
					model.setApplyTime(model.getGmtCreate());
					model.setCreator(loginUser.getNickName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getNickName());
		}
	}
	
}
