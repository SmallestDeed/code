package com.nork.cityunion.service;

import java.util.List;

import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.search.UnionGroupSearch;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.product.model.BaseBrand;
import com.nork.product.model.search.BaseBrandSearch;

/**   
 * @Title: UnionGroupService.java 
 * @Package com.nork.cityunion.service
 * @Description:同城联盟-联盟分组表Service
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:23:22
 * @version V1.0   
 */
public interface UnionGroupService {
	/**
	 * 新增数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	public int add(UnionGroup unionGroup);

	/**
	 *    更新数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	public int update(UnionGroup unionGroup);

	public int deleteById(Integer id);
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
	 * @return  UnionGroup 
	 */
	public UnionGroup get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  unionGroup
	 * @return   List<UnionGroup>
	 */
	public List<UnionGroup> getList(UnionGroup unionGroup);
	/**
	 * 所有数据
	 * 
	 * @param  unionGroup
	 * @return   List<UnionGroup>
	 */
	public List<UnionGroup> getListByUserId(Integer userId);

	/**
	 *    获取数据数量
	 *
	 * @param  unionGroup
	 * @return   int
	 */
	public int getCount(UnionGroupSearch unionGroupSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  unionGroup
	 * @return   List<UnionGroup>
	 */
	public List<UnionGroup> getPaginatedList(
				UnionGroupSearch unionGrouptSearch);

	/**
	 * 品牌列表 接口
	 * @param msgId
	 * @return
	 */
	ResponseEnvelope<BaseBrand> myBrandList(String msgId,BaseBrandSearch baseBrandSearch);

	/**
	 * 自动存储系统字段
	 */
	void sysSave(UnionGroup model,LoginUser loginUser);

}
