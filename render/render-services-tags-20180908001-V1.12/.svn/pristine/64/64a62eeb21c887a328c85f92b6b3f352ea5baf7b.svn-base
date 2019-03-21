package com.nork.cityunion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionDesignPlanStoreCatoryMapper;
import com.nork.cityunion.model.UnionDesignPlanStoreCatory;
import com.nork.cityunion.model.search.UnionDesignPlanStoreCatorySearch;
import com.nork.cityunion.service.UnionDesignPlanStoreCatoryService;

/**   
 * @Title: UnionDesignPlanStoreCatoryServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟设计方案库类别ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:06
 * @version V1.0   
 */
@Service("unionDesignPlanStoreCatoryService")
public class UnionDesignPlanStoreCatoryServiceImpl implements UnionDesignPlanStoreCatoryService {

	private UnionDesignPlanStoreCatoryMapper unionDesignPlanStoreCatoryMapper;

	@Autowired
	public void setUnionDesignPlanStoreCatoryMapper(
			UnionDesignPlanStoreCatoryMapper unionDesignPlanStoreCatoryMapper) {
		this.unionDesignPlanStoreCatoryMapper = unionDesignPlanStoreCatoryMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStoreCatory
	 * @return  int 
	 */
	@Override
	public int add(UnionDesignPlanStoreCatory unionDesignPlanStoreCatory) {
		unionDesignPlanStoreCatoryMapper.insertSelective(unionDesignPlanStoreCatory);
		return unionDesignPlanStoreCatory.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStoreCatory
	 * @return  int 
	 */
	@Override
	public int update(UnionDesignPlanStoreCatory unionDesignPlanStoreCatory) {
		return unionDesignPlanStoreCatoryMapper
				.updateByPrimaryKeySelective(unionDesignPlanStoreCatory);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionDesignPlanStoreCatoryMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionDesignPlanStoreCatory 
	 */
	@Override
	public UnionDesignPlanStoreCatory get(Integer id) {
		return unionDesignPlanStoreCatoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStoreCatory
	 * @return   List<UnionDesignPlanStoreCatory>
	 */
	@Override
	public List<UnionDesignPlanStoreCatory> getList(UnionDesignPlanStoreCatory unionDesignPlanStoreCatory) {
	    return unionDesignPlanStoreCatoryMapper.selectList(unionDesignPlanStoreCatory);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreCatory
	 * @return   int
	 */
	@Override
	public int getCount(UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch){
		return  unionDesignPlanStoreCatoryMapper.selectCount(unionDesignPlanStoreCatorySearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreCatory
	 * @return   List<UnionDesignPlanStoreCatory>
	 */
	@Override
	public List<UnionDesignPlanStoreCatory> getPaginatedList(
			UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch) {
		return unionDesignPlanStoreCatoryMapper.selectPaginatedList(unionDesignPlanStoreCatorySearch);
	}

	/**
	 * 其他
	 * 
	 */


}
