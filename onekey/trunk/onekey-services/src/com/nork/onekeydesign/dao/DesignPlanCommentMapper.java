package com.nork.onekeydesign.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.DesignPlanComment;
import com.nork.onekeydesign.model.UserDesignPlanComment;
import com.nork.onekeydesign.model.search.DesignPlanCommentSearch;

/**   
 * @Title: DesignPlanCommentMapper.java 
 * @Package com.nork.onekeydesign.dao
 * @Description:设计方案-评论表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-07-23 16:35:30
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignPlanCommentMapper {
    int insertSelective(DesignPlanComment record);

    int updateByPrimaryKeySelective(DesignPlanComment record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignPlanComment selectByPrimaryKey(Integer id);
    UserDesignPlanComment selectByPrimaryId(Integer id);
    
    int selectCount(DesignPlanCommentSearch designPlanCommentSearch);
    
	List<DesignPlanComment> selectPaginatedList(
			DesignPlanCommentSearch designPlanCommentSearch);
			
    List<DesignPlanComment> selectList(DesignPlanComment designPlanComment);
    
    List<DesignPlanComment> getUDPCList(DesignPlanCommentSearch designPlanCommentSearch);
    
	/**
	 * 其他
	 * 
	 */
}
