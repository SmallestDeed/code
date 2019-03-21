package com.nork.product.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.product.dao.UserProductCollectMapper;
import com.nork.product.model.UserProductCollect;
import com.nork.product.model.UserProductsConllect;
import com.nork.product.model.search.UserProductCollectSearch;
import com.nork.product.service.UserProductCollectService;

/**   
 * @Title: UserProductCollectServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-我的产品收藏ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-06-15 18:27:21
 * @version V1.0   
 */
@Service("userProductCollectService")
@Transactional
public class UserProductCollectServiceImpl implements UserProductCollectService {

	private UserProductCollectMapper userProductCollectMapper;

	@Autowired
	public void setUserProductCollectMapper(
			UserProductCollectMapper userProductCollectMapper) {
		this.userProductCollectMapper = userProductCollectMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param userProductCollect
	 * @return  int 
	 */
	@Override
	public int add(UserProductCollect userProductCollect) {
		userProductCollectMapper.insertSelective(userProductCollect);
		return userProductCollect.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param userProductCollect
	 * @return  int 
	 */
	@Override
	public int update(UserProductCollect userProductCollect) {
		return userProductCollectMapper
				.updateByPrimaryKeySelective(userProductCollect);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return userProductCollectMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserProductCollect 
	 */
	@Override
	public UserProductCollect get(Integer id) {
		return userProductCollectMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  userProductCollect
	 * @return   List<UserProductCollect>
	 */
	@Override
	public List<UserProductCollect> getList(UserProductCollect userProductCollect) {
	    return userProductCollectMapper.selectList(userProductCollect);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  userProductCollect
	 * @return   int
	 */
	@Override
	public int getCount(UserProductCollectSearch userProductCollectSearch){
		return  userProductCollectMapper.selectCount(userProductCollectSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  userProductCollect
	 * @return   List<UserProductCollect>
	 */
	@Override
	public List<UserProductCollect> getPaginatedList(
			UserProductCollectSearch userProductCollectSearch) {
		return userProductCollectMapper.selectPaginatedList(userProductCollectSearch);
	}

	@Override
	public List<UserProductsConllect> getUserProductsConllectList(
			UserProductsConllect userProductsConllect) {
		return userProductCollectMapper.getUserProductsConllectList(userProductsConllect);
	}

	@Override
	public UserProductCollect getUserProductConllect(UserProductCollect userProductCollect) {
		return userProductCollectMapper.getUserProductConllect(userProductCollect);
	}

	@Override
	public List<UserProductsConllect> selectUserNamelist(
			UserProductsConllect userProductsConllect) {
		return userProductCollectMapper.selectUserNamelist(userProductsConllect);
	}

	@Override
	public int getUserProductsConllectCount(
			UserProductsConllect userProductsConllect) {
		return userProductCollectMapper.getUserProductsConllectCount(userProductsConllect);
	}

	@Override
	public Integer cancelConllect(UserProductCollect userProductCollect) {
		return userProductCollectMapper.cancelConllect(userProductCollect);
	}

	@Override
	public List<UserProductsConllect> getAllList(
			UserProductsConllect userProductsConllect) {
		return userProductCollectMapper.getAllList(userProductsConllect);
	}

	@Override
	public void transferCollection(
			UserProductCollectSearch userProductCollectSearch) {
		userProductCollectMapper.transferCollection(userProductCollectSearch);
		
	}

	@Override
	public List getUserProductsCollectByValue(
			List<Map<Object, Object>> valueList) {
		return userProductCollectMapper.getUserProductsCollectByValue(valueList);
		
	}

	@Override
	public int getUserProductsConllectCount_All(
			UserProductsConllect userProductsCollect) {
		return userProductCollectMapper.getUserProductsConllectCount_All(userProductsCollect);
	}

	@Override
	public List<UserProductsConllect> getAllList_All(
			UserProductsConllect userProductsCollect) {
		return userProductCollectMapper.getAllList_All(userProductsCollect);
	}

	/**
	 * 其他
	 * 
	 */


}
