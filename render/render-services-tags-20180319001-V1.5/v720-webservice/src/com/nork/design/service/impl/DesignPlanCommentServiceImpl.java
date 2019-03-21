package com.nork.design.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.design.dao.DesignPlanCommentMapper;
import com.nork.design.model.DesignPlanComment;
import com.nork.design.model.UserDesignPlanComment;
import com.nork.design.model.search.DesignPlanCommentSearch;
import com.nork.design.service.DesignPlanCommentService;

/**   
 * @Title: DesignPlanCommentServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:设计方案-评论表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-07-23 16:35:30
 * @version V1.0   
 */
@Service("designPlanCommentService")
@Transactional
public class DesignPlanCommentServiceImpl implements DesignPlanCommentService {

	private DesignPlanCommentMapper designPlanCommentMapper;

	@Autowired
	public void setDesignPlanCommentMapper(
			DesignPlanCommentMapper designPlanCommentMapper) {
		this.designPlanCommentMapper = designPlanCommentMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param designPlanComment
	 * @return  int 
	 */
	@Override
	public int add(DesignPlanComment designPlanComment) {
		designPlanCommentMapper.insertSelective(designPlanComment);
		return designPlanComment.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param designPlanComment
	 * @return  int 
	 */
	@Override
	public int update(DesignPlanComment designPlanComment) {
		return designPlanCommentMapper
				.updateByPrimaryKeySelective(designPlanComment);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return designPlanCommentMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanComment 
	 */
	@Override
	public DesignPlanComment get(Integer id) {
		return designPlanCommentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  designPlanComment
	 * @return   List<DesignPlanComment>
	 */
	@Override
	public List<DesignPlanComment> getList(DesignPlanComment designPlanComment) {
	    return designPlanCommentMapper.selectList(designPlanComment);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  designPlanComment
	 * @return   int
	 */
	@Override
	public int getCount(DesignPlanCommentSearch designPlanCommentSearch){
		return  designPlanCommentMapper.selectCount(designPlanCommentSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanComment
	 * @return   List<DesignPlanComment>
	 */
	@Override
	public List<DesignPlanComment> getPaginatedList(
			DesignPlanCommentSearch designPlanCommentSearch) {
		return designPlanCommentMapper.selectPaginatedList(designPlanCommentSearch);
	}

	@Override
	public List<DesignPlanComment> getUDPCList(
			DesignPlanCommentSearch designPlanCommentSearch) {
		return designPlanCommentMapper.getUDPCList(designPlanCommentSearch);
	}

	@Override
	public UserDesignPlanComment getById(Integer id) {
		return designPlanCommentMapper.selectByPrimaryId(id);
	}

	/**
	 * 其他
	 * 
	 */


}
