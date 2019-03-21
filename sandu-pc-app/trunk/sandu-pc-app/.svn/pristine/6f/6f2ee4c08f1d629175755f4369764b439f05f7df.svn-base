package com.sandu.system.service.impl;

import java.util.List;

import com.sandu.system.dao.BaseAreaMapper;
import com.sandu.system.model.BaseArea;
import com.sandu.system.model.bo.HouseAreaBo;
import com.sandu.system.model.search.BaseAreaQueryBean;
import com.sandu.system.service.BaseAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**   
 * @Title: BaseAreaServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-行政区域ServiceImpl
 */
@Service("baseAreaService")
@Transactional
public class BaseAreaServiceImpl implements BaseAreaService {

	@Autowired
	private BaseAreaMapper baseAreaMapper;

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

	@Override
	public HouseAreaBo selectAreaInfoByAreaLongCode(BaseArea baseArea) {
		HouseAreaBo areaBo = new HouseAreaBo();
		BaseAreaQueryBean areaQueryBean = new BaseAreaQueryBean(baseArea.getLongCode());
		//查找到省市区信息
		areaBo = baseAreaMapper.selectAreaInfoByAreaLongCode(areaQueryBean);
		return areaBo;
	}
}
