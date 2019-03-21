package com.nork.design.service;

import java.util.List;

import com.nork.design.model.DesignPlanCustomizedProductOrder;
import com.nork.design.model.search.DesignPlanCustomizedProductOrderSearch;

/**   
 * @Title: DesignPlanCustomizedProductOrderService.java 
 * @Package com.nork.design.service
 * @Description:设计方案-设计方案定制产品订单表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-11-26 17:46:44
 * @version V1.0   
 */
public interface DesignPlanCustomizedProductOrderService {
	/**
	 * 新增数据
	 *
	 * @param designPlanCustomizedProductOrder
	 * @return  int 
	 */
	public int add(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder);

	/**
	 *    更新数据
	 *
	 * @param designPlanCustomizedProductOrder
	 * @return  int 
	 */
	public int update(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder);

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
	 * @return  DesignPlanCustomizedProductOrder 
	 */
	public DesignPlanCustomizedProductOrder get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designPlanCustomizedProductOrder
	 * @return   List<DesignPlanCustomizedProductOrder>
	 */
	public List<DesignPlanCustomizedProductOrder> getList(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder);

	/**
	 *    获取数据数量
	 *
	 * @param  designPlanCustomizedProductOrder
	 * @return   int
	 */
	public int getCount(DesignPlanCustomizedProductOrderSearch designPlanCustomizedProductOrderSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanCustomizedProductOrder
	 * @return   List<DesignPlanCustomizedProductOrder>
	 */
	public List<DesignPlanCustomizedProductOrder> getPaginatedList(
				DesignPlanCustomizedProductOrderSearch designPlanCustomizedProductOrdertSearch);

	/**
	 * 其他
	 * 
	 */
	int updatePactNoSortTime(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder);

	int updateClientNameSortTime(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder);

	int deleteByPlanId(Integer planId);
}
