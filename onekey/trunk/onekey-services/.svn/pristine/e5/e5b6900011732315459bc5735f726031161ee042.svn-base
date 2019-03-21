package com.nork.onekeydesign.service;

import java.util.List;

import com.nork.onekeydesign.model.DesignPlanComment;
import com.nork.onekeydesign.model.UserDesignPlanComment;
import com.nork.onekeydesign.model.search.DesignPlanCommentSearch;

/**   
 * @Title: DesignPlanCommentService.java 
 * @Package com.nork.onekeydesign.service
 * @Description:设计方案-评论表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-07-23 16:35:30
 * @version V1.0   
 */
public interface DesignPlanCommentService {
	/**
	 * 新增数据
	 *
	 * @param designPlanComment
	 * @return  int 
	 */
	public int add(DesignPlanComment designPlanComment);

	/**
	 *    更新数据
	 *
	 * @param designPlanComment
	 * @return  int 
	 */
	public int update(DesignPlanComment designPlanComment);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesignPlanComment 
	 */
	public DesignPlanComment get(Integer id);
	
	public UserDesignPlanComment getById(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designPlanComment
	 * @return   List<DesignPlanComment>
	 */
	public List<DesignPlanComment> getList(DesignPlanComment designPlanComment);
	
	public List<DesignPlanComment> getUDPCList(DesignPlanCommentSearch designPlanCommentSearch);

	/**
	 *    获取数据数量
	 *
	 * @param  designPlanComment
	 * @return   int
	 */
	public int getCount(DesignPlanCommentSearch designPlanCommentSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designPlanComment
	 * @return   List<DesignPlanComment>
	 */
	public List<DesignPlanComment> getPaginatedList(
				DesignPlanCommentSearch designPlanCommenttSearch);

	/**
	 * 其他
	 * 
	 */

}
