package com.nork.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.product.dao.CollectCatalogMapper;
import com.nork.product.model.CollectCatalog;
import com.nork.product.model.search.CollectCatalogSearch;
import com.nork.product.service.CollectCatalogService;

/**   
 * @Title: CollectCatalogServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品管理-收藏目录表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-07-01 10:46:26
 * @version V1.0   
 */
@Service("collectCatalogService")
public class CollectCatalogServiceImpl implements CollectCatalogService {

	private CollectCatalogMapper collectCatalogMapper;

	@Autowired
	public void setCollectCatalogMapper(
			CollectCatalogMapper collectCatalogMapper) {
		this.collectCatalogMapper = collectCatalogMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param collectCatalog
	 * @return  int 
	 */
	@Override
	public int add(CollectCatalog collectCatalog) {
		collectCatalogMapper.insertSelective(collectCatalog);
		return collectCatalog.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param collectCatalog
	 * @return  int 
	 */
	@Override
	public int update(CollectCatalog collectCatalog) {
		return collectCatalogMapper
				.updateByPrimaryKeySelective(collectCatalog);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return collectCatalogMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  CollectCatalog 
	 */
	@Override
	public CollectCatalog get(Integer id) {
		return collectCatalogMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  collectCatalog
	 * @return   List<CollectCatalog>
	 */
	@Override
	public List<CollectCatalog> getList(CollectCatalog collectCatalog) {
	    return collectCatalogMapper.selectList(collectCatalog);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  collectCatalog
	 * @return   int
	 */
	@Override
	public int getCount(CollectCatalogSearch collectCatalogSearch){
		return  collectCatalogMapper.selectCount(collectCatalogSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  collectCatalog
	 * @return   List<CollectCatalog>
	 */
	@Override
	public List<CollectCatalog> getPaginatedList(
			CollectCatalogSearch collectCatalogSearch) {
		return collectCatalogMapper.selectPaginatedList(collectCatalogSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
