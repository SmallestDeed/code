package com.sandu.interaction.dao;

import org.springframework.stereotype.Repository;
import com.sandu.common.persistence.CrudDao;
import com.sandu.interaction.model.Like;
import com.sandu.interaction.model.query.LikeQuery;
import com.sandu.interaction.model.vo.LikeVo;

/***
 * 设计师点赞接口
 * @author Administrator
 *
 */
@Repository
public interface DesignerLikeDao extends CrudDao<Like,LikeQuery,LikeVo>{
	int cancel(Like like);
	int count(LikeQuery query);
}
