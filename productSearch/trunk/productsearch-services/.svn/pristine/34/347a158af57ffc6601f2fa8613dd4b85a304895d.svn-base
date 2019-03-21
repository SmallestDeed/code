package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageRecieveSearch;

/**   
 * @Title: BaseMessageRecieveMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-消息接收表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 17:08:13
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseMessageRecieveMapper {
    int insertSelective(BaseMessageRecieve record);

    int updateByPrimaryKeySelective(BaseMessageRecieve record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseMessageRecieve selectByPrimaryKey(Integer id);
    
    int selectCount(BaseMessageRecieveSearch baseMessageRecieveSearch);
    
	List<BaseMessageRecieve> selectPaginatedList(
			BaseMessageRecieveSearch baseMessageRecieveSearch);
			
    List<BaseMessageRecieve> selectList(BaseMessageRecieve baseMessageRecieve);
    List<UserMessageDesignPlan> selectMessageList(UserMessageDesignPlan userMessageDesignPlan);
//    List<UserMessageDesignPlan> selectReceiverList(UserMessageDesignPlan userMessageDesignPlan);
    
	/**
	 * 其他
	 * 
	 */

    /**
     * 通过消息ID和用户ID查询一行数据
     * @param baseMessageRecieve
     * @return
     */
    BaseMessageRecieve getOneByMessageIdAndUserId(BaseMessageRecieve baseMessageRecieve);
}
