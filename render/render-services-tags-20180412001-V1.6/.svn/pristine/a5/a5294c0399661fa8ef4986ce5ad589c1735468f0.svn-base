package com.nork.cityunion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionDesignPlanStoreMapper;
import com.nork.cityunion.model.UnionDesignPlanStore;
import com.nork.cityunion.model.search.UnionDesignPlanStoreSearch;
import com.nork.cityunion.service.UnionDesignPlanStoreService;

/**   
 * @Title: UnionDesignPlanStoreServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟设计方案素材库表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:21:19
 * @version V1.0   
 */
@Service("unionDesignPlanStoreService")
public class UnionDesignPlanStoreServiceImpl implements UnionDesignPlanStoreService {

	private UnionDesignPlanStoreMapper unionDesignPlanStoreMapper;

	@Autowired
	public void setUnionDesignPlanStoreMapper(
			UnionDesignPlanStoreMapper unionDesignPlanStoreMapper) {
		this.unionDesignPlanStoreMapper = unionDesignPlanStoreMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStore
	 * @return  int 
	 */
	@Override
	public int add(UnionDesignPlanStore unionDesignPlanStore) {
		unionDesignPlanStoreMapper.insertSelective(unionDesignPlanStore);
		return unionDesignPlanStore.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStore
	 * @return  int 
	 */
	@Override
	public int update(UnionDesignPlanStore unionDesignPlanStore) {
		return unionDesignPlanStoreMapper
				.updateByPrimaryKeySelective(unionDesignPlanStore);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionDesignPlanStoreMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionDesignPlanStore 
	 */
	@Override
	public UnionDesignPlanStore get(Integer id) {
		return unionDesignPlanStoreMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStore
	 * @return   List<UnionDesignPlanStore>
	 */
	@Override
	public List<UnionDesignPlanStore> getList(UnionDesignPlanStore unionDesignPlanStore) {
	    return unionDesignPlanStoreMapper.selectList(unionDesignPlanStore);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStore
	 * @return   int
	 */
	@Override
	public int getCount(UnionDesignPlanStoreSearch unionDesignPlanStoreSearch){
		return  unionDesignPlanStoreMapper.selectCount(unionDesignPlanStoreSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStore
	 * @return   List<UnionDesignPlanStore>
	 */
	@Override
	public List<UnionDesignPlanStore> getPaginatedList(
			UnionDesignPlanStoreSearch unionDesignPlanStoreSearch) {
		return unionDesignPlanStoreMapper.selectPaginatedList(unionDesignPlanStoreSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
