package com.nork.cityunion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionDesignPlanStoreReleaseMapper;
import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseSearch;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;

/**   
 * @Title: UnionDesignPlanStoreReleaseServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟素材发布表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:43
 * @version V1.0   
 */
@Service("unionDesignPlanStoreReleaseService")
public class UnionDesignPlanStoreReleaseServiceImpl implements UnionDesignPlanStoreReleaseService {

	private UnionDesignPlanStoreReleaseMapper unionDesignPlanStoreReleaseMapper;

	@Autowired
	public void setUnionDesignPlanStoreReleaseMapper(
			UnionDesignPlanStoreReleaseMapper unionDesignPlanStoreReleaseMapper) {
		this.unionDesignPlanStoreReleaseMapper = unionDesignPlanStoreReleaseMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStoreRelease
	 * @return  int 
	 */
	@Override
	public int add(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
		unionDesignPlanStoreReleaseMapper.insertSelective(unionDesignPlanStoreRelease);
		return unionDesignPlanStoreRelease.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStoreRelease
	 * @return  int 
	 */
	@Override
	public int update(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
		return unionDesignPlanStoreReleaseMapper
				.updateByPrimaryKeySelective(unionDesignPlanStoreRelease);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionDesignPlanStoreReleaseMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionDesignPlanStoreRelease 
	 */
	@Override
	public UnionDesignPlanStoreRelease get(Integer id) {
		return unionDesignPlanStoreReleaseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStoreRelease
	 * @return   List<UnionDesignPlanStoreRelease>
	 */
	@Override
	public List<UnionDesignPlanStoreRelease> getList(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
	    return unionDesignPlanStoreReleaseMapper.selectList(unionDesignPlanStoreRelease);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreRelease
	 * @return   int
	 */
	@Override
	public int getCount(UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch){
		return  unionDesignPlanStoreReleaseMapper.selectCount(unionDesignPlanStoreReleaseSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreRelease
	 * @return   List<UnionDesignPlanStoreRelease>
	 */
	@Override
	public List<UnionDesignPlanStoreRelease> getPaginatedList(
			UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch) {
		return unionDesignPlanStoreReleaseMapper.selectPaginatedList(unionDesignPlanStoreReleaseSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
