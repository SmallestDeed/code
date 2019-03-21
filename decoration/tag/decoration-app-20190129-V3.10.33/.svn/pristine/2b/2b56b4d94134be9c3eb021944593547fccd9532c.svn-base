package com.nork.design.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.design.dao.DesignPlanCustomizedProductOrderMapper;
import com.nork.design.model.DesignPlanCustomizedProductOrder;
import com.nork.design.model.search.DesignPlanCustomizedProductOrderSearch;
import com.nork.design.service.DesignPlanCustomizedProductOrderService;

/**   
 * @Title: DesignPlanCustomizedProductOrderServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:设计方案-设计方案定制产品订单表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-11-26 17:46:44
 * @version V1.0   
 */
@Service("designPlanCustomizedProductOrderService")
public class DesignPlanCustomizedProductOrderServiceImpl implements DesignPlanCustomizedProductOrderService {

	private DesignPlanCustomizedProductOrderMapper designPlanCustomizedProductOrderMapper;

	@Autowired
	public void setDesignPlanCustomizedProductOrderMapper(
			DesignPlanCustomizedProductOrderMapper designPlanCustomizedProductOrderMapper) {
		this.designPlanCustomizedProductOrderMapper = designPlanCustomizedProductOrderMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param designPlanCustomizedProductOrder
	 * @return  int 
	 */
	@Override
	public int add(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder) {
		designPlanCustomizedProductOrderMapper.insertSelective(designPlanCustomizedProductOrder);
		return designPlanCustomizedProductOrder.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designPlanCustomizedProductOrder
	 * @return  int 
	 */
	@Override
	public int update(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder) {
		return designPlanCustomizedProductOrderMapper
				.updateByPrimaryKeySelective(designPlanCustomizedProductOrder);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designPlanCustomizedProductOrderMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanCustomizedProductOrder 
	 */
	@Override
	public DesignPlanCustomizedProductOrder get(Integer id) {
		return designPlanCustomizedProductOrderMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designPlanCustomizedProductOrder
	 * @return   List<DesignPlanCustomizedProductOrder>
	 */
	@Override
	public List<DesignPlanCustomizedProductOrder> getList(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder) {
	    return designPlanCustomizedProductOrderMapper.selectList(designPlanCustomizedProductOrder);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designPlanCustomizedProductOrder
	 * @return   int
	 */
	@Override
	public int getCount(DesignPlanCustomizedProductOrderSearch designPlanCustomizedProductOrderSearch){
		return  designPlanCustomizedProductOrderMapper.selectCount(designPlanCustomizedProductOrderSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanCustomizedProductOrder
	 * @return   List<DesignPlanCustomizedProductOrder>
	 */
	@Override
	public List<DesignPlanCustomizedProductOrder> getPaginatedList(
			DesignPlanCustomizedProductOrderSearch designPlanCustomizedProductOrderSearch) {
		return designPlanCustomizedProductOrderMapper.selectPaginatedList(designPlanCustomizedProductOrderSearch);
	}

	/**
	 * 其他
	 * 
	 */

	@Override
	public int updatePactNoSortTime(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder) {
		return designPlanCustomizedProductOrderMapper.updateByPactNo(designPlanCustomizedProductOrder);
	}

	@Override
	public int updateClientNameSortTime(DesignPlanCustomizedProductOrder designPlanCustomizedProductOrder) {
		return designPlanCustomizedProductOrderMapper.updateByClientName(designPlanCustomizedProductOrder);
	}

	@Override
	public int deleteByPlanId(Integer planId) {
		return designPlanCustomizedProductOrderMapper.deleteByPlanId(planId);
	}


}
