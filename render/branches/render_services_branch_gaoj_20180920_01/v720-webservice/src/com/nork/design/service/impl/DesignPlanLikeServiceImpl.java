package com.nork.design.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.design.dao.DesignPlanLikeMapper;
import com.nork.design.model.DesignPlanLike;
import com.nork.design.model.search.DesignPlanLikeSearch;
import com.nork.design.service.DesignPlanLikeService;

/**   
 * @Title: DesignPlanLikeServiceImpl.java 
 * @Package com.nork.设计方案.service.impl
 * @Description:设计方案点赞库-设计方案点赞ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-11-25 14:36:56
 * @version V1.0   
 */
@Service("designPlanLikeService")
public class DesignPlanLikeServiceImpl implements DesignPlanLikeService {

	private DesignPlanLikeMapper designPlanLikeMapper;

	@Autowired
	public void setDesignPlanLikeMapper(
			DesignPlanLikeMapper designPlanLikeMapper) {
		this.designPlanLikeMapper = designPlanLikeMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param designPlanLike
	 * @return  int 
	 */
	@Override
	public int add(DesignPlanLike designPlanLike) {
		designPlanLikeMapper.insertSelective(designPlanLike);
		return designPlanLike.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designPlanLike
	 * @return  int 
	 */
	@Override
	public int update(DesignPlanLike designPlanLike) {
		return designPlanLikeMapper
				.updateByPrimaryKeySelective(designPlanLike);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designPlanLikeMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanLike 
	 */
	@Override
	public DesignPlanLike get(Integer id) {
		return designPlanLikeMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designPlanLike
	 * @return   List<DesignPlanLike>
	 */
	@Override
	public List<DesignPlanLike> getList(DesignPlanLike designPlanLike) {
	    return designPlanLikeMapper.selectList(designPlanLike);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designPlanLike
	 * @return   int
	 */
	@Override
	public int getCount(DesignPlanLikeSearch designPlanLikeSearch){
		return  designPlanLikeMapper.selectCount(designPlanLikeSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanLike
	 * @return   List<DesignPlanLike>
	 */
	@Override
	public List<DesignPlanLike> getPaginatedList(
			DesignPlanLikeSearch designPlanLikeSearch) {
		return designPlanLikeMapper.selectPaginatedList(designPlanLikeSearch);
	}

	@Override
	public int deleteById(DesignPlanLike designPlanLike) {
		return designPlanLikeMapper.deleteById(designPlanLike);
	}

	/**
	 * 其他
	 * 
	 */


}
