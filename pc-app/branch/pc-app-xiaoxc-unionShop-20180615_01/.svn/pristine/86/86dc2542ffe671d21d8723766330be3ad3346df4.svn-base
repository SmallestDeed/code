package com.nork.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.product.dao.BaseSeriesMapper;
import com.nork.product.model.BaseSeries;
import com.nork.product.model.search.BaseSeriesSearch;
import com.nork.product.service.BaseSeriesService;

/**   
 * @Title: BaseSeriesServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:product-系列表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-11-04 11:06:16
 * @version V1.0   
 */
@Service("baseSeriesService")
public class BaseSeriesServiceImpl implements BaseSeriesService {

	private BaseSeriesMapper baseSeriesMapper;

	@Autowired
	public void setBaseSeriesMapper(
			BaseSeriesMapper baseSeriesMapper) {
		this.baseSeriesMapper = baseSeriesMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param baseSeries
	 * @return  int 
	 */
	@Override
	public int add(BaseSeries baseSeries) {
		baseSeriesMapper.insertSelective(baseSeries);
		return baseSeries.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseSeries
	 * @return  int 
	 */
	@Override
	public int update(BaseSeries baseSeries) {
		return baseSeriesMapper
				.updateByPrimaryKeySelective(baseSeries);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseSeriesMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseSeries 
	 */
	@Override
	public BaseSeries get(Integer id) {
		return baseSeriesMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseSeries
	 * @return   List<BaseSeries>
	 */
	@Override
	public List<BaseSeries> getList(BaseSeries baseSeries) {
	    return baseSeriesMapper.selectList(baseSeries);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseSeries
	 * @return   int
	 */
	@Override
	public int getCount(BaseSeriesSearch baseSeriesSearch){
		return  baseSeriesMapper.selectCount(baseSeriesSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseSeries
	 * @return   List<BaseSeries>
	 */
	@Override
	public List<BaseSeries> getPaginatedList(
			BaseSeriesSearch baseSeriesSearch) {
		return baseSeriesMapper.selectPaginatedList(baseSeriesSearch);
	}


	/**
	 * 获取用户授权码绑定的品牌系列
	 * @param userId
	 * @return
	 */
	@Override
	public List<BaseSeries> getSeriesByUserAuthorizedBrandCode(Integer userId){
		return baseSeriesMapper.findSeriesByUserAuthorizedBrandCode(userId);
	}

	/**
	 * 获取用户品牌系列列表
	 * @param  idsList
	 * @return   List<BaseSeries>
	 */
	@Override
	public List<BaseSeries> getSeriesList(List<Integer> idsList, Integer level) {
		return baseSeriesMapper.findSeriesList(idsList,level);
	}

	/**
	 * 获取用户品牌系列数量
	 * @param idsList
	 * @return
	 */
	@Override
	public int getSeriesCount(List<Integer> idsList, Integer level){
		return  baseSeriesMapper.findSeriesCount(idsList,level);
	}


}
