package com.nork.resource.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.resource.dao.ResUsedProductsMapper;
import com.nork.resource.model.ResUsedProducts;
import com.nork.resource.model.search.ResUsedProductsSearch;
import com.nork.resource.service.ResUsedProductsService;

/**   
 * @Title: ResUsedProductsServiceImpl.java 
 * @Package com.nork.resource.service.impl
 * @Description:文件资源-已使用产品资源库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-07-26 16:50:18
 * @version V1.0   
 */
@Service("resUsedProductsService")
public class ResUsedProductsServiceImpl implements ResUsedProductsService {

	private ResUsedProductsMapper resUsedProductsMapper;

	@Autowired
	public void setResUsedProductsMapper(
			ResUsedProductsMapper resUsedProductsMapper) {
		this.resUsedProductsMapper = resUsedProductsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param resUsedProducts
	 * @return  int 
	 */
	@Override
	public int add(ResUsedProducts resUsedProducts) {
		resUsedProductsMapper.insertSelective(resUsedProducts);
		return resUsedProducts.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resUsedProducts
	 * @return  int 
	 */
	@Override
	public int update(ResUsedProducts resUsedProducts) {
		return resUsedProductsMapper
				.updateByPrimaryKeySelective(resUsedProducts);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return resUsedProductsMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResUsedProducts 
	 */
	@Override
	public ResUsedProducts get(Integer id) {
		return resUsedProductsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  resUsedProducts
	 * @return   List<ResUsedProducts>
	 */
	@Override
	public List<ResUsedProducts> getList(ResUsedProducts resUsedProducts) {
	    return resUsedProductsMapper.selectList(resUsedProducts);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resUsedProducts
	 * @return   int
	 */
	@Override
	public int getCount(ResUsedProductsSearch resUsedProductsSearch){
		return  resUsedProductsMapper.selectCount(resUsedProductsSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resUsedProducts
	 * @return   List<ResUsedProducts>
	 */
	@Override
	public List<ResUsedProducts> getPaginatedList(
			ResUsedProductsSearch resUsedProductsSearch) {
		return resUsedProductsMapper.selectPaginatedList(resUsedProductsSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
