package com.sandu.product.service.impl;

import com.sandu.product.dao.GroupProductDetailsMapper;
import com.sandu.product.model.GroupProductDetails;
import com.sandu.product.service.GroupProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**   
 * @Title: GroupProductDetailsServiceImpl.java 
 * @Package com.sandu.product.service.impl
 * @Description:产品模块-产品组合关联表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 20:37:16
 * @version V1.0   
 */
@Service("groupProductDetailsService")
public class GroupProductDetailsServiceImpl implements GroupProductDetailsService {

	private GroupProductDetailsMapper groupProductDetailsMapper;

	@Autowired
	public void setGroupProductDetailsMapper(
			GroupProductDetailsMapper groupProductDetailsMapper) {
		this.groupProductDetailsMapper = groupProductDetailsMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param groupProductDetails
	 * @return  int 
	 */
	@Override
	public int add(GroupProductDetails groupProductDetails) {
		groupProductDetailsMapper.insertSelective(groupProductDetails);
		return groupProductDetails.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param groupProductDetails
	 * @return  int 
	 */
	@Override
	public int update(GroupProductDetails groupProductDetails) {
		return groupProductDetailsMapper
				.updateByPrimaryKeySelective(groupProductDetails);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return groupProductDetailsMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  GroupProductDetails 
	 */
	@Override
	public GroupProductDetails get(Integer id) {
		return groupProductDetailsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  groupProductDetails
	 * @return   List<GroupProductDetails>
	 */
	@Override
	public List<GroupProductDetails> getList(GroupProductDetails groupProductDetails) {
	    return groupProductDetailsMapper.selectList(groupProductDetails);
	}
	
}
