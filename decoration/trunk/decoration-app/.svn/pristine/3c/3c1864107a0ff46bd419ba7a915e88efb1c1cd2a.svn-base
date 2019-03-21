package com.nork.design.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.design.dao.DesignPlanCustomizedProductMapper;
import com.nork.design.model.DesignPlanCustomizedProduct;
import com.nork.design.model.search.DesignPlanCustomizedProductSearch;
import com.nork.design.service.DesignPlanCustomizedProductService;

/**   
 * @Title: DesignPlanCustomizedProductServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:设计方案-设计方案定制产品表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-08-28 11:04:09
 * @version V1.0   
 */
@Service("designPlanCustomizedProductService")
public class DesignPlanCustomizedProductServiceImpl implements DesignPlanCustomizedProductService {

	private DesignPlanCustomizedProductMapper designPlanCustomizedProductMapper;

	@Autowired
	public void setDesignPlanCustomizedProductMapper(
			DesignPlanCustomizedProductMapper designPlanCustomizedProductMapper) {
		this.designPlanCustomizedProductMapper = designPlanCustomizedProductMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param designPlanCustomizedProduct
	 * @return  int 
	 */
	@Override
	public int add(DesignPlanCustomizedProduct designPlanCustomizedProduct) {
		designPlanCustomizedProductMapper.insertSelective(designPlanCustomizedProduct);
		return designPlanCustomizedProduct.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designPlanCustomizedProduct
	 * @return  int 
	 */
	@Override
	public int update(DesignPlanCustomizedProduct designPlanCustomizedProduct) {
		return designPlanCustomizedProductMapper
				.updateByPrimaryKeySelective(designPlanCustomizedProduct);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designPlanCustomizedProductMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanCustomizedProduct 
	 */
	@Override
	public DesignPlanCustomizedProduct get(Integer id) {
		return designPlanCustomizedProductMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designPlanCustomizedProduct
	 * @return   List<DesignPlanCustomizedProduct>
	 */
	@Override
	public List<DesignPlanCustomizedProduct> getList(DesignPlanCustomizedProduct designPlanCustomizedProduct) {
	    return designPlanCustomizedProductMapper.selectList(designPlanCustomizedProduct);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designPlanCustomizedProduct
	 * @return   int
	 */
	@Override
	public int getCount(DesignPlanCustomizedProductSearch designPlanCustomizedProductSearch){
		return  designPlanCustomizedProductMapper.selectCount(designPlanCustomizedProductSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanCustomizedProduct
	 * @return   List<DesignPlanCustomizedProduct>
	 */
	@Override
	public List<DesignPlanCustomizedProduct> getPaginatedList(
			DesignPlanCustomizedProductSearch designPlanCustomizedProductSearch) {
		return designPlanCustomizedProductMapper.selectPaginatedList(designPlanCustomizedProductSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
