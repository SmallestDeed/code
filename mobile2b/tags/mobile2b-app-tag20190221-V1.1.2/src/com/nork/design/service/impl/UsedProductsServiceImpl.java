package com.nork.design.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.dao.UsedProductsMapper;
import com.nork.design.model.UsedProducts;
import com.nork.design.model.UserProductPlan;
import com.nork.design.model.search.UsedProductsSearch;
import com.nork.design.service.UsedProductsService;

/**   
 * @Title: UsedProductsServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:设计方案-已使用产品表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-07-10 16:23:04
 * @version V1.0   
 */
@Service("usedProductsService")
@Transactional
public class UsedProductsServiceImpl implements UsedProductsService {

	private UsedProductsMapper usedProductsMapper;

	@Autowired
	public void setUsedProductsMapper(
			UsedProductsMapper usedProductsMapper) {
		this.usedProductsMapper = usedProductsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param usedProducts
	 * @return  int 
	 */
	@Override
	public int add(UsedProducts usedProducts) {
		usedProductsMapper.insertSelective(usedProducts);
		return usedProducts.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param usedProducts
	 * @return  int 
	 */
	@Override
	public int update(UsedProducts usedProducts) {
		return usedProductsMapper
				.updateByPrimaryKeySelective(usedProducts);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return usedProductsMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UsedProducts 
	 */
	@Override
	public UsedProducts get(Integer id) {
		return usedProductsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  usedProducts
	 * @return   List<UsedProducts>
	 */
	@Override
	public List<UsedProducts> getList(UsedProducts usedProducts) {
	    return usedProductsMapper.selectList(usedProducts);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  usedProducts
	 * @return   int
	 */
	@Override
	public int getCount(UsedProductsSearch usedProductsSearch){
		return  usedProductsMapper.selectCount(usedProductsSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  usedProducts
	 * @return   List<UsedProducts>
	 */
	@Override
	public List<UsedProducts> getPaginatedList(
			UsedProductsSearch usedProductsSearch) {
		return usedProductsMapper.selectPaginatedList(usedProductsSearch);
	}

	@Override
	public List<UserProductPlan> getUserProductPlan(UserProductPlan userProductPlan) {
		return usedProductsMapper.getUserProductPlan(userProductPlan);
	}

	@Override
	public List<UserProductPlan> getUsedProductPlanList(
			UsedProductsSearch usedProductsSearch) {
		return usedProductsMapper.getUsedProductPlanList(usedProductsSearch);
	}

	@Override
	public Integer getUsedProductPlanCount(
			UsedProductsSearch usedProductsSearch) {
		return usedProductsMapper.getUsedProductPlanCount(usedProductsSearch);
	}


}
