package com.sandu.product.service;

import java.util.List;
import java.util.Map;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.product.model.BaseProduct;
import com.sandu.product.model.GroupProduct;
import com.sandu.product.model.GroupProductDetails;
import com.sandu.product.model.StructureProduct;
import com.sandu.product.model.result.GroupProductResult;
import com.sandu.product.model.result.SearchProductGroupResult;
import com.sandu.product.model.search.BaseBrandSearch;
import com.sandu.product.model.search.GroupProductSearch;
import com.sandu.user.model.LoginUser;



/**   
 * @Title: GroupProductService.java 
 * @Package com.nork.product.service
 * @Description:产品模块-产品组合主表Service
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:52:57
 * @version V1.0   
 */
public interface GroupProductService {
	/**
	 * 新增数据
	 *
	 * @param groupProduct
	 * @return  int 
	 */
	public int add(GroupProduct groupProduct);

	/**
	 *    更新数据
	 *
	 * @param groupProduct
	 * @return  int 
	 */
	public int update(GroupProduct groupProduct);

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
	 * @return  GroupProduct 
	 */
	public GroupProduct get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  groupProduct
	 * @return   List<GroupProduct>
	 */
	public List<GroupProduct> getList(GroupProduct groupProduct);

	public GroupProduct getMoreInfoById(Integer groupId);
	
	

}
