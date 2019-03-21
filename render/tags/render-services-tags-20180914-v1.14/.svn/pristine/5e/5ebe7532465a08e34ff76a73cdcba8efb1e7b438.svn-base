package com.nork.cityunion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionGroupMapper;
import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.search.UnionGroupSearch;
import com.nork.cityunion.service.UnionGroupService;

/**   
 * @Title: UnionGroupServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟分组表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:23:22
 * @version V1.0   
 */
@Service("unionGroupService")
public class UnionGroupServiceImpl implements UnionGroupService {

	private UnionGroupMapper unionGroupMapper;

	@Autowired
	public void setUnionGroupMapper(
			UnionGroupMapper unionGroupMapper) {
		this.unionGroupMapper = unionGroupMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	@Override
	public int add(UnionGroup unionGroup) {
		unionGroupMapper.insertSelective(unionGroup);
		return unionGroup.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionGroup
	 * @return  int 
	 */
	@Override
	public int update(UnionGroup unionGroup) {
		return unionGroupMapper
				.updateByPrimaryKeySelective(unionGroup);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionGroupMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionGroup 
	 */
	@Override
	public UnionGroup get(Integer id) {
		return unionGroupMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionGroup
	 * @return   List<UnionGroup>
	 */
	@Override
	public List<UnionGroup> getList(UnionGroup unionGroup) {
	    return unionGroupMapper.selectList(unionGroup);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionGroup
	 * @return   int
	 */
	@Override
	public int getCount(UnionGroupSearch unionGroupSearch){
		return  unionGroupMapper.selectCount(unionGroupSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionGroup
	 * @return   List<UnionGroup>
	 */
	@Override
	public List<UnionGroup> getPaginatedList(
			UnionGroupSearch unionGroupSearch) {
		return unionGroupMapper.selectPaginatedList(unionGroupSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
