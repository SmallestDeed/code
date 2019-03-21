package com.nork.cityunion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionStorefrontMapper;
import com.nork.cityunion.model.UnionStorefront;
import com.nork.cityunion.model.search.UnionStorefrontSearch;
import com.nork.cityunion.service.UnionStorefrontService;

/**   
 * @Title: UnionStorefrontServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟店面信息表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:22:46
 * @version V1.0   
 */
@Service("unionStorefrontService")
public class UnionStorefrontServiceImpl implements UnionStorefrontService {

	private UnionStorefrontMapper unionStorefrontMapper;

	@Autowired
	public void setUnionStorefrontMapper(
			UnionStorefrontMapper unionStorefrontMapper) {
		this.unionStorefrontMapper = unionStorefrontMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionStorefront
	 * @return  int 
	 */
	@Override
	public int add(UnionStorefront unionStorefront) {
		unionStorefrontMapper.insertSelective(unionStorefront);
		return unionStorefront.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionStorefront
	 * @return  int 
	 */
	@Override
	public int update(UnionStorefront unionStorefront) {
		return unionStorefrontMapper
				.updateByPrimaryKeySelective(unionStorefront);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionStorefrontMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionStorefront 
	 */
	@Override
	public UnionStorefront get(Integer id) {
		return unionStorefrontMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionStorefront
	 * @return   List<UnionStorefront>
	 */
	@Override
	public List<UnionStorefront> getList(UnionStorefront unionStorefront) {
	    return unionStorefrontMapper.selectList(unionStorefront);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionStorefront
	 * @return   int
	 */
	@Override
	public int getCount(UnionStorefrontSearch unionStorefrontSearch){
		return  unionStorefrontMapper.selectCount(unionStorefrontSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionStorefront
	 * @return   List<UnionStorefront>
	 */
	@Override
	public List<UnionStorefront> getPaginatedList(
			UnionStorefrontSearch unionStorefrontSearch) {
		return unionStorefrontMapper.selectPaginatedList(unionStorefrontSearch);
	}

	/**
	 * 其他
	 * 
	 */


}
