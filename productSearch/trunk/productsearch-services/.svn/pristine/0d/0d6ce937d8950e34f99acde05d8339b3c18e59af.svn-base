package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.BaseMessage;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageSearch;

/**   
 * @Title: BaseMessageMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-消息表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 14:30:45
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseMessageMapper {
    int insertSelective(BaseMessage record);

    int updateByPrimaryKeySelective(BaseMessage record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseMessage selectByPrimaryKey(Integer id);
    
    int selectCount(BaseMessageSearch baseMessageSearch);
    
	List<BaseMessage> selectPaginatedList(
			BaseMessageSearch baseMessageSearch);
			
    List<BaseMessage> selectList(BaseMessage baseMessage);
    List<UserMessageDesignPlan> selectMessageList(UserMessageDesignPlan userMessageDesignPlan);
    List<UserMessageDesignPlan> selectReceiverIdList(UserMessageDesignPlan userMessageDesignPlan);
    List<UserMessageDesignPlan> selectWebMessageList(UserMessageDesignPlan userMessageDesignPlan);
//    List<UserMessageDesignPlan> selectReceiverList(UserMessageDesignPlan userMessageDesignPlan);
    
	/**
	 * 其他
	 * 
	 */
    int unreadMessageCount(BaseMessage baseMessage);
}
