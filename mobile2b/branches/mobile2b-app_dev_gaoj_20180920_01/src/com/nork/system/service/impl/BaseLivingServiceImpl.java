package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.BaseLivingMapper;
import com.nork.system.model.BaseLiving;
import com.nork.system.model.search.BaseLivingSearch;
import com.nork.system.service.BaseLivingService;

/**   
 * @Title: BaseLivingServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-小区ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 14:41:11
 * @version V1.0   
 */
@Service("baseLivingService")
@Transactional
public class BaseLivingServiceImpl implements BaseLivingService {

	private BaseLivingMapper baseLivingMapper;

	@Autowired
	public void setBaseLivingMapper(
			BaseLivingMapper baseLivingMapper) {
		this.baseLivingMapper = baseLivingMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param baseLiving
	 * @return  int 
	 */
	@Override
	public int add(BaseLiving baseLiving) {
		baseLivingMapper.insertSelective(baseLiving);
		return baseLiving.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseLiving
	 * @return  int 
	 */
	@Override
	public int update(BaseLiving baseLiving) {
		return baseLivingMapper
				.updateByPrimaryKeySelective(baseLiving);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseLivingMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseLiving 
	 */
	@Override
	public BaseLiving get(Integer id) {
		return baseLivingMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseLiving
	 * @return   List<BaseLiving>
	 */
	@Override
	public List<BaseLiving> getList(BaseLiving baseLiving) {
	    return baseLivingMapper.selectList(baseLiving);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseLiving
	 * @return   int
	 */
	@Override
	public int getCount(BaseLivingSearch baseLivingSearch){
		return  baseLivingMapper.selectCount(baseLivingSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseLiving
	 * @return   List<BaseLiving>
	 */
	@Override
	public List<BaseLiving> getPaginatedList(
			BaseLivingSearch baseLivingSearch) {
		return baseLivingMapper.selectPaginatedList(baseLivingSearch);
	}

	
	/**
	 * 获取同一区域下最大的小区编码数
	 * @param livingCode
	 * @return
	 */
	public int getMaxCodeNum(BaseLivingSearch baseLivingtSearch){
		return  baseLivingMapper.getMaxCodeNum(baseLivingtSearch);	
    }

	@Override
	public List<String> findAllName() {
		return baseLivingMapper.findAllName();
	}

	@Override
	public List<Integer> getIdsBySearch(BaseLivingSearch baseLivingSearch) {
		return baseLivingMapper.getIdsBySearch(baseLivingSearch);
	}

	@Override
	public int getCountBySearch(BaseLivingSearch baseLivingSearch) {
		return baseLivingMapper.getCountBySearch(baseLivingSearch);
	}

	@Override
	public Integer getCountByCreator(BaseLivingSearch baseLivingSearch) {
		return baseLivingMapper.getCountByCreator(baseLivingSearch);
	}

}
