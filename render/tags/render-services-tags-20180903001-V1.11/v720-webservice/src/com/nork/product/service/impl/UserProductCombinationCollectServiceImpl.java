package com.nork.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.product.dao.UserProductCombinationCollectMapper;
import com.nork.product.model.UserProductCombinationCollect;
import com.nork.product.model.search.UserProductCombinationCollectSearch;
import com.nork.product.service.UserProductCombinationCollectService;

/**   
 * @Title: UserProductCombinationCollectServiceImpl.java 
 * @Package com.nork.porduct.service.impl
 * @Description:产品组合收藏-产品组合收藏ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-03-23 11:13:32
 * @version V1.0   
 */
@Service("userProductCombinationCollectService")
public class UserProductCombinationCollectServiceImpl implements UserProductCombinationCollectService {

	private UserProductCombinationCollectMapper userProductCombinationCollectMapper;

	@Autowired
	public void setUserProductCombinationCollectMapper(
			UserProductCombinationCollectMapper userProductCombinationCollectMapper) {
		this.userProductCombinationCollectMapper = userProductCombinationCollectMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param userProductCombinationCollect
	 * @return  int 
	 */
	@Override
	public int add(UserProductCombinationCollect userProductCombinationCollect) {
		userProductCombinationCollectMapper.insertSelective(userProductCombinationCollect);
		return userProductCombinationCollect.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param userProductCombinationCollect
	 * @return  int 
	 */
	@Override
	public int update(UserProductCombinationCollect userProductCombinationCollect) {
		return userProductCombinationCollectMapper
				.updateByPrimaryKeySelective(userProductCombinationCollect);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return userProductCombinationCollectMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserProductCombinationCollect 
	 */
	@Override
	public UserProductCombinationCollect get(Integer id) {
		return userProductCombinationCollectMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  userProductCombinationCollect
	 * @return   List<UserProductCombinationCollect>
	 */
	@Override
	public List<UserProductCombinationCollect> getList(UserProductCombinationCollect userProductCombinationCollect) {
	    return userProductCombinationCollectMapper.selectList(userProductCombinationCollect);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  userProductCombinationCollect
	 * @return   int
	 */
	@Override
	public int getCount(UserProductCombinationCollectSearch userProductCombinationCollectSearch){
		return  userProductCombinationCollectMapper.selectCount(userProductCombinationCollectSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  userProductCombinationCollect
	 * @return   List<UserProductCombinationCollect>
	 */
	@Override
	public List<UserProductCombinationCollect> getPaginatedList(
			UserProductCombinationCollectSearch userProductCombinationCollectSearch) {
		return userProductCombinationCollectMapper.selectPaginatedList(userProductCombinationCollectSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
