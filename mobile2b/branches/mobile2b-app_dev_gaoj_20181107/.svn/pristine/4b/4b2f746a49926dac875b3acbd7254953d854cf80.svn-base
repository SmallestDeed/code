package com.nork.system.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.collections.Lists;
import com.nork.system.dao.BaseAreaMapper;
import com.nork.system.model.BaseArea;
import com.nork.system.model.search.BaseAreaSearch;
import com.nork.system.model.web.WBaseArea;
import com.nork.system.service.BaseAreaService;

/**   
 * @Title: BaseAreaServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-行政区域ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 15:31:09
 * @version V1.0   
 */
@Service("baseAreaService")
@Transactional
public class BaseAreaServiceImpl implements BaseAreaService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BaseAreaServiceImpl.class);
	
	private final static String LOGPREFIX = "[省市区模块]:";
	
	private BaseAreaMapper baseAreaMapper;

	@Autowired
	public void setBaseAreaMapper(
			BaseAreaMapper baseAreaMapper) {
		this.baseAreaMapper = baseAreaMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param baseArea
	 * @return  int 
	 */
	@Override
	public int add(BaseArea baseArea) {
		baseAreaMapper.insertSelective(baseArea);
		return baseArea.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseArea
	 * @return  int 
	 */
	@Override
	public int update(BaseArea baseArea) {
		return baseAreaMapper
				.updateByPrimaryKeySelective(baseArea);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseAreaMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseArea 
	 */
	@Override
	public BaseArea get(Integer id) {
		return baseAreaMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseArea
	 * @return   List<BaseArea>
	 */
	@Override
	public List<BaseArea> getList(BaseArea baseArea) {
	    return baseAreaMapper.selectList(baseArea);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseArea
	 * @return   int
	 */
	@Override
	public int getCount(BaseAreaSearch baseAreaSearch){
		return  baseAreaMapper.selectCount(baseAreaSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseArea
	 * @return   List<BaseArea>
	 */
	@Override
	public List<BaseArea> getPaginatedList(
			BaseAreaSearch baseAreaSearch) {
		return baseAreaMapper.selectPaginatedList(baseAreaSearch);
	}

	/**
	 * 获取城市列表
	 */
	public List<BaseArea> getCityList() {
		
		return baseAreaMapper.selectCityList();
	}

	@Override
	public BaseArea selectCityName(BaseAreaSearch baseAreaSearch) {
		return baseAreaMapper.selectCityName(baseAreaSearch);
	}

	public WBaseArea getByCode(BaseAreaSearch baseAreaSearch){
		return baseAreaMapper.getByCode(baseAreaSearch);
	}

	@Override
	public BaseArea findOneByAreaCode(String areaCode) {
		// ------参数验证 ->start
		if(StringUtils.isEmpty(areaCode)) {
			LOGGER.error(LOGPREFIX + "StringUtils.isEmpty(areaCode) = true");
			return null;
		}
		// ------参数验证 ->end
		
		BaseAreaSearch baseAreaSearch = new BaseAreaSearch();
		baseAreaSearch.setStart(0);
		baseAreaSearch.setLimit(1);
		baseAreaSearch.setAreaCode(areaCode);
		List<BaseArea> baseAreaList = this.getPaginatedList(baseAreaSearch);
		if(Lists.isNotEmpty(baseAreaList)) {
			return baseAreaList.get(0);
		}
		return null;
	}

}
