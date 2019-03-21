package com.nork.design.service;

import java.util.List;

import com.nork.design.model.DesignPlanCustomizedProduct;
import com.nork.design.model.search.DesignPlanCustomizedProductSearch;

/**   
 * @Title: DesignPlanCustomizedProductService.java 
 * @Package com.nork.design.service
 * @Description:设计方案-设计方案定制产品表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-08-28 11:04:09
 * @version V1.0   
 */
public interface DesignPlanCustomizedProductService {
	/**
	 * 新增数据
	 *
	 * @param designPlanCustomizedProduct
	 * @return  int 
	 */
	public int add(DesignPlanCustomizedProduct designPlanCustomizedProduct);

	/**
	 *    更新数据
	 *
	 * @param designPlanCustomizedProduct
	 * @return  int 
	 */
	public int update(DesignPlanCustomizedProduct designPlanCustomizedProduct);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanCustomizedProduct 
	 */
	public DesignPlanCustomizedProduct get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designPlanCustomizedProduct
	 * @return   List<DesignPlanCustomizedProduct>
	 */
	public List<DesignPlanCustomizedProduct> getList(DesignPlanCustomizedProduct designPlanCustomizedProduct);

	/**
	 *    获取数据数量
	 *
	 * @param  designPlanCustomizedProduct
	 * @return   int
	 */
	public int getCount(DesignPlanCustomizedProductSearch designPlanCustomizedProductSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanCustomizedProduct
	 * @return   List<DesignPlanCustomizedProduct>
	 */
	public List<DesignPlanCustomizedProduct> getPaginatedList(
            DesignPlanCustomizedProductSearch designPlanCustomizedProducttSearch);

	/**
	 * 其他
	 * 
	 */

}
