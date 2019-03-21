package com.nork.design.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.design.dao.TempletProductIndexInfoMapper;
import com.nork.design.model.TempletProductIndexInfo;
import com.nork.design.model.search.TempletProductIndexInfoSearch;
import com.nork.design.service.TempletProductIndexInfoService;

/**   
 * @Title: TempletProductIndexInfoServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:设计模块-同分类产品序号表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-06-13 16:19:25
 * @version V1.0   
 */
@Service("templetProductIndexInfoService")
public class TempletProductIndexInfoServiceImpl implements TempletProductIndexInfoService {

	private TempletProductIndexInfoMapper templetProductIndexInfoMapper;

	@Autowired
	public void setTempletProductIndexInfoMapper(
			TempletProductIndexInfoMapper templetProductIndexInfoMapper) {
		this.templetProductIndexInfoMapper = templetProductIndexInfoMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param templetProductIndexInfo
	 * @return  int 
	 */
	@Override
	public int add(TempletProductIndexInfo templetProductIndexInfo) {
		templetProductIndexInfoMapper.insertSelective(templetProductIndexInfo);
		return templetProductIndexInfo.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param templetProductIndexInfo
	 * @return  int 
	 */
	@Override
	public int update(TempletProductIndexInfo templetProductIndexInfo) {
		return templetProductIndexInfoMapper
				.updateByPrimaryKeySelective(templetProductIndexInfo);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return templetProductIndexInfoMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  TempletProductIndexInfo 
	 */
	@Override
	public TempletProductIndexInfo get(Integer id) {
		return templetProductIndexInfoMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  templetProductIndexInfo
	 * @return   List<TempletProductIndexInfo>
	 */
	@Override
	public List<TempletProductIndexInfo> getList(TempletProductIndexInfo templetProductIndexInfo) {
	    return templetProductIndexInfoMapper.selectList(templetProductIndexInfo);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  templetProductIndexInfo
	 * @return   int
	 */
	@Override
	public int getCount(TempletProductIndexInfoSearch templetProductIndexInfoSearch){
		return  templetProductIndexInfoMapper.selectCount(templetProductIndexInfoSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  templetProductIndexInfo
	 * @return   List<TempletProductIndexInfo>
	 */
	@Override
	public List<TempletProductIndexInfo> getPaginatedList(
			TempletProductIndexInfoSearch templetProductIndexInfoSearch) {
		return templetProductIndexInfoMapper.selectPaginatedList(templetProductIndexInfoSearch);
	}

	/**
	 * 通过样板房code获取productIndexInfo
	 * @param designCode
	 */
	@Override
	public List<TempletProductIndexInfo> findIndexInfoByCode(String designCode){
		return templetProductIndexInfoMapper.findIndexInfoByCode(designCode);
	}

	/**
	 * 自动存储系统字段
	 */
	public void sysSave(TempletProductIndexInfo model, LoginUser loginUser){
		if(model != null){
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
		}
	}

	/**
	 * 通过ids更新isDeleted
	 * @param templetProductIndexInfo
	 */
	@Override
	public int updateIndexInfoByIds(TempletProductIndexInfo templetProductIndexInfo){
		return templetProductIndexInfoMapper.updateIndexInfoByIds(templetProductIndexInfo);
	}

}
