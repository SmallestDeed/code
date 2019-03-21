package com.sandu.interaction.dao;

import org.springframework.stereotype.Repository;
import com.sandu.common.persistence.CrudDao;
import com.sandu.interaction.model.Comment;
import com.sandu.interaction.model.query.CommentQuery;
import com.sandu.interaction.model.vo.CommentVo;

/***
 * 店铺评论接口
 * @author Administrator
 *
 */
@Repository
public interface ShopCommentDao extends CrudDao<Comment,CommentQuery,CommentVo>{
	int count(CommentQuery query);
}
