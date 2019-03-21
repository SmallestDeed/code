package com.nork.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.product.dao.UserProductConfigMapper;
import com.nork.product.model.UserProductConfig;
import com.nork.product.model.search.UserProductConfigSearch;
import com.nork.product.service.UserProductConfigService;

/**   
 * @Title: UserProductConfigServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:用户产品配置-用户产品配置ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-03-28 14:40:39
 * @version V1.0   
 */
@Service("userProductConfigService")
public class UserProductConfigServiceImpl implements UserProductConfigService {

	private UserProductConfigMapper userProductConfigMapper;

	@Autowired
	public void setUserProductConfigMapper(
			UserProductConfigMapper userProductConfigMapper) {
		this.userProductConfigMapper = userProductConfigMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param userProductConfig
	 * @return  int 
	 */
	@Override
	public int add(UserProductConfig userProductConfig) {
		userProductConfigMapper.insertSelective(userProductConfig);
		return userProductConfig.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param userProductConfig
	 * @return  int 
	 */
	@Override
	public int update(UserProductConfig userProductConfig) {
		return userProductConfigMapper
				.updateByPrimaryKeySelective(userProductConfig);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return userProductConfigMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserProductConfig 
	 */
	@Override
	public UserProductConfig get(Integer id) {
		return userProductConfigMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  userProductConfig
	 * @return   List<UserProductConfig>
	 */
	@Override
	public List<UserProductConfig> getList(UserProductConfig userProductConfig) {
	    return userProductConfigMapper.selectList(userProductConfig);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  userProductConfig
	 * @return   int
	 */
	@Override
	public int getCount(UserProductConfigSearch userProductConfigSearch){
		return  userProductConfigMapper.selectCount(userProductConfigSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  userProductConfig
	 * @return   List<UserProductConfig>
	 */
	@Override
	public List<UserProductConfig> getPaginatedList(
			UserProductConfigSearch userProductConfigSearch) {
		return userProductConfigMapper.selectPaginatedList(userProductConfigSearch);
	}

	/**
	 * 其他
	 * 
	 */
	
	/**
	 * 根据用户id查询数据
	 * 
	 * @param  userProductConfig
	 * @return   List<UserProductConfig>
	 */
	@Override
	public List<UserProductConfig> getUserConfigList(UserProductConfig userProductConfig) {
	    return userProductConfigMapper.selectUserConfigList(userProductConfig);
	}


}
