package com.nork.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.system.dao.SysConsumingRecordsMapper;
import com.nork.system.model.SysConsumingRecords;
import com.nork.system.model.search.SysConsumingRecordsSearch;
import com.nork.system.service.SysConsumingRecordsService;

/**   
 * @Title: SysConsumingRecordsServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-消费记录ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-07-18 16:49:19
 * @version V1.0   
 */
@Service("sysConsumingRecordsService")
public class SysConsumingRecordsServiceImpl implements SysConsumingRecordsService {

	private SysConsumingRecordsMapper sysConsumingRecordsMapper;

	@Autowired
	public void setSysConsumingRecordsMapper(
			SysConsumingRecordsMapper sysConsumingRecordsMapper) {
		this.sysConsumingRecordsMapper = sysConsumingRecordsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysConsumingRecords
	 * @return  int 
	 */
	@Override
	public int add(SysConsumingRecords sysConsumingRecords) {
		sysConsumingRecordsMapper.insertSelective(sysConsumingRecords);
		return sysConsumingRecords.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysConsumingRecords
	 * @return  int 
	 */
	@Override
	public int update(SysConsumingRecords sysConsumingRecords) {
		return sysConsumingRecordsMapper
				.updateByPrimaryKeySelective(sysConsumingRecords);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysConsumingRecordsMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysConsumingRecords 
	 */
	@Override
	public SysConsumingRecords get(Integer id) {
		return sysConsumingRecordsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysConsumingRecords
	 * @return   List<SysConsumingRecords>
	 */
	@Override
	public List<SysConsumingRecords> getList(SysConsumingRecords sysConsumingRecords) {
	    return sysConsumingRecordsMapper.selectList(sysConsumingRecords);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysConsumingRecords
	 * @return   int
	 */
	@Override
	public int getCount(SysConsumingRecordsSearch sysConsumingRecordsSearch){
		return  sysConsumingRecordsMapper.selectCount(sysConsumingRecordsSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysConsumingRecords
	 * @return   List<SysConsumingRecords>
	 */
	@Override
	public List<SysConsumingRecords> getPaginatedList(
			SysConsumingRecordsSearch sysConsumingRecordsSearch) {
		return sysConsumingRecordsMapper.selectPaginatedList(sysConsumingRecordsSearch);
	}

	/**
	 * 自动存储系统字段
	 */
	public void sysSave(SysConsumingRecords model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				 }
				 
				if(model.getId() == null){
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
				model.setUserId(loginUser.getId());
		}
	}
	
}
