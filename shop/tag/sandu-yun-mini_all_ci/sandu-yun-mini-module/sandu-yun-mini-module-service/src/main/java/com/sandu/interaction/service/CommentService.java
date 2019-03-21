package com.sandu.interaction.service;

import com.sandu.interaction.model.Comment;
import com.sandu.interaction.model.query.CommentQuery;
import com.sandu.interaction.model.vo.CommentVo;
import com.sandu.matadata.Page;

/***
 * 评论服务接口
 * 
 * @author Administrator
 *
 */
public interface CommentService {
	/***
	 * 提交评论
	 * 
	 * @param comment
	 * @return
	 */
	boolean add(Comment comment);

	/***
	 * 删除评论
	 * 
	 * @param comment
	 * @return
	 */
	boolean delete(Comment comment);

	/***
	 * 根据评论类别和评论对象ID获取评论总数
	 * 
	 * @param type
	 * @param objId
	 * @return
	 */
	int getCount(int type, long objId);
	
	/***
	 * 根据评论类别、用户ID和评论对象ID获取评论总数
	 * 
	 * @param type
	 * @param objId
	 * @return
	 */
	int getCount(int type, long userId,long objId);

	/***
	 * 查询评论
	 * 
	 * @param query
	 * @return
	 */
	Page<CommentVo> getList(CommentQuery query);
}
