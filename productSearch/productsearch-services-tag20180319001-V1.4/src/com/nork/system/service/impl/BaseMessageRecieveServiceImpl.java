package com.nork.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.dao.BaseMessageRecieveMapper;
import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageRecieveSearch;
import com.nork.system.service.BaseMessageRecieveService;

/**   
 * @Title: BaseMessageRecieveServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-消息接收表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 17:08:13
 * @version V1.0   
 */
@Service("baseMessageRecieveService")
@Transactional
public class BaseMessageRecieveServiceImpl implements BaseMessageRecieveService {

	private BaseMessageRecieveMapper baseMessageRecieveMapper;

	@Autowired
	public void setBaseMessageRecieveMapper(
			BaseMessageRecieveMapper baseMessageRecieveMapper) {
		this.baseMessageRecieveMapper = baseMessageRecieveMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param baseMessageRecieve
	 * @return  int 
	 */
	@Override
	public int add(BaseMessageRecieve baseMessageRecieve) {
		baseMessageRecieveMapper.insertSelective(baseMessageRecieve);
		return baseMessageRecieve.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseMessageRecieve
	 * @return  int 
	 */
	@Override
	public int update(BaseMessageRecieve baseMessageRecieve) {
		return baseMessageRecieveMapper
				.updateByPrimaryKeySelective(baseMessageRecieve);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseMessageRecieveMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseMessageRecieve 
	 */
	@Override
	public BaseMessageRecieve get(Integer id) {
		return baseMessageRecieveMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseMessageRecieve
	 * @return   List<BaseMessageRecieve>
	 */
	@Override
	public List<BaseMessageRecieve> getList(BaseMessageRecieve baseMessageRecieve) {
	    return baseMessageRecieveMapper.selectList(baseMessageRecieve);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseMessageRecieve
	 * @return   int
	 */
	@Override
	public int getCount(BaseMessageRecieveSearch baseMessageRecieveSearch){
		return  baseMessageRecieveMapper.selectCount(baseMessageRecieveSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseMessageRecieve
	 * @return   List<BaseMessageRecieve>
	 */
	@Override
	public List<BaseMessageRecieve> getPaginatedList(
			BaseMessageRecieveSearch baseMessageRecieveSearch) {
		return baseMessageRecieveMapper.selectPaginatedList(baseMessageRecieveSearch);
	}

	@Override
	public List<UserMessageDesignPlan> getMessageList(
			UserMessageDesignPlan userMessageDesignPlan) {
		return baseMessageRecieveMapper.selectMessageList(userMessageDesignPlan);
	}

//	@Override
//	public List<UserMessageDesignPlan> getReceiverList(
//			UserMessageDesignPlan userMessageDesignPlan) {
//		return baseMessageRecieveMapper.selectReceiverList(userMessageDesignPlan);
//	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 通过消息ID和用户ID查询一行数据
	 * @param baseMessageRecieve
	 * @return
	 */
	public BaseMessageRecieve getOneByMessageIdAndUserId(BaseMessageRecieve baseMessageRecieve){
		baseMessageRecieve = baseMessageRecieveMapper.getOneByMessageIdAndUserId(baseMessageRecieve);
		return baseMessageRecieve;
	}
}
