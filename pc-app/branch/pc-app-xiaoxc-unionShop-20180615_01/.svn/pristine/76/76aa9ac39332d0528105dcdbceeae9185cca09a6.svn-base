package com.nork.cityunion.service.impl;

import java.util.List;

import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseSearch;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import com.nork.common.util.Constants;
import org.apache.log4j.Logger;
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

	private static Logger logger = Logger
			.getLogger(UnionDesignPlanStoreCatoryServiceImpl.class);

	private UnionDesignPlanStoreCatoryMapper unionDesignPlanStoreCatoryMapper;

	@Autowired
	private UnionDesignPlanStoreReleaseService unionDesignPlanStoreReleaseService;

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
	 * @param  unionDesignPlanStoreCatorySearch
	 * @return   int
	 */
	@Override
	public int getCount(UnionDesignPlanStoreCatorySearch unionDesignPlanStoreCatorySearch){
		return  unionDesignPlanStoreCatoryMapper.selectCount(unionDesignPlanStoreCatorySearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreCatorySearch
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

	/**
	 * 方案脚本素材分类数量
	 * @param userId
	 * @return
	 */
	@Override
	public int findStoreCatoryCount(Integer userId, String catoryName) {
		return unionDesignPlanStoreCatoryMapper.findStoreCatoryCount(userId, catoryName);
	}

	/**
	 * 方案脚本素材分类集合
	 * @param userId
	 * @return
	 */
	@Override
	public List<UnionDesignPlanStoreCatory> findStoreCatoryList(Integer userId) {
		return unionDesignPlanStoreCatoryMapper.findStoreCatoryList(userId);
	}

	/**
	 * 根据删除数据及关联关系数据
	 * @param id
	 * @return
	 */
	@Override
	public boolean del(Integer id) {
		int i = unionDesignPlanStoreCatoryMapper.deleteByPrimaryKey(id);
		logger.info("delete:id=" + id);
		if (i > 0) {
			//关联分类的发布数据更新到默认分类中（默认为0）
			UnionDesignPlanStoreReleaseSearch storeReleaseSearch = new UnionDesignPlanStoreReleaseSearch();
			storeReleaseSearch.setCatoryId(id);
			storeReleaseSearch.setIsDeleted(Constants.DATA_DELETED_STATUS_zero);
			int count = unionDesignPlanStoreReleaseService.getCount(storeReleaseSearch);
			if (count > 0) {
				UnionDesignPlanStoreRelease storeRelease = new UnionDesignPlanStoreRelease();
				storeRelease.setCatoryId(id);
				int n = unionDesignPlanStoreReleaseService.updateByStoreRelease(storeRelease);
				logger.info("delete:n=" + n);
			}
		} else {
			return false;
		}
		return true;
	}

}
