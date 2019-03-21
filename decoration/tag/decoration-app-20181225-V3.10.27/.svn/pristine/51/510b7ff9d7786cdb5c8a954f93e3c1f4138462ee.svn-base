package com.nork.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.user.dao.UserGuidePicMapper;
import com.nork.user.model.UserGuidePic;
import com.nork.user.model.search.UserGuidePicSearch;
import com.nork.user.service.UserGuidePicService;

/**   
 * @Title: UserGuidePicServiceImpl.java 
 * @Package com.nork.user.service.impl
 * @Description:用户指南-用户指南图片列表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-12-07 14:42:28
 * @version V1.0   
 */
@Service("userGuidePicService")
public class UserGuidePicServiceImpl implements UserGuidePicService {

	private UserGuidePicMapper userGuidePicMapper;

	@Autowired
	public void setUserGuidePicMapper(
			UserGuidePicMapper userGuidePicMapper) {
		this.userGuidePicMapper = userGuidePicMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param userGuidePic
	 * @return  int 
	 */
	@Override
	public int add(UserGuidePic userGuidePic) {
		userGuidePicMapper.insertSelective(userGuidePic);
		return userGuidePic.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param userGuidePic
	 * @return  int 
	 */
	@Override
	public int update(UserGuidePic userGuidePic) {
		return userGuidePicMapper
				.updateByPrimaryKeySelective(userGuidePic);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return userGuidePicMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UserGuidePic 
	 */
	@Override
	public UserGuidePic get(Integer id) {
		return userGuidePicMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  userGuidePic
	 * @return   List<UserGuidePic>
	 */
	@Override
	public List<UserGuidePic> getList(UserGuidePic userGuidePic) {
	    return userGuidePicMapper.selectList(userGuidePic);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  userGuidePic
	 * @return   int
	 */
	@Override
	public int getCount(UserGuidePicSearch userGuidePicSearch){
		return  userGuidePicMapper.selectCount(userGuidePicSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  userGuidePic
	 * @return   List<UserGuidePic>
	 */
	@Override
	public List<UserGuidePic> getPaginatedList(
			UserGuidePicSearch userGuidePicSearch) {
		return userGuidePicMapper.selectPaginatedList(userGuidePicSearch);
	}

	@Override
	public UserGuidePic getNextUserGuide(Integer sort) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * type ：上移一条， 下移一条，置顶 userGuidePic_current： 需要上移的 userGuidePic_replace： 被替换的
	 */
	@Override
	public int exchangeSort(Integer id, Integer sort, String type) {
		UserGuidePic userGuidePic_replace = null;
		UserGuidePic userGuidePic_current = new UserGuidePic();
		int state = 2;

		if ("up".equals(type)) {
			userGuidePic_replace = userGuidePicMapper.getUpUserGuidePic(sort);
			userGuidePic_current.setSort(userGuidePic_replace.getSort());
			userGuidePic_replace.setSort(sort);
		} else if ("down".equals(type)) {
			userGuidePic_replace = userGuidePicMapper.getNextUserGuidePic(sort);
			/* 最后一位,无法下移 */
			if (userGuidePic_replace == null)
				return 3;
			userGuidePic_current.setSort(userGuidePic_replace.getSort());
			userGuidePic_replace.setSort(sort);
		} else if ("one".equals(type)) {
			userGuidePicMapper.placedTop();
			userGuidePic_current.setSort(1);
		} else {
			return state;
		}

		try {
			userGuidePic_current.setId(id);
			userGuidePicMapper.updateByPrimaryKeySelective(userGuidePic_current);
			if (!"one".equals(type)) {
				userGuidePicMapper.updateByPrimaryKeySelective(userGuidePic_replace);
			}
			state = 1;
		} catch (Exception e) {

		}
		return state;
	}

	@Override
	public int getMaxSort() {
		return userGuidePicMapper.getMaxSort();
	}

	/**
	 * 其他
	 * 
	 */


}
