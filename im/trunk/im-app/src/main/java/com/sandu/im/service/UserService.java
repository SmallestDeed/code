package com.sandu.im.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.im.common.bo.UserBo;
import com.sandu.im.dao.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public UserBo getUser(String userSessionId) {
		UserBo userBo = userDao.selectOne(userSessionId);
		if(StringUtils.isBlank(userBo.getUserName())) {
			userBo.setUserName(userBo.getMobile());
		}
		return userBo;
	}
	
	public List<UserBo> getUserList(List<String> sessionIdList) {
		return userDao.selectList(sessionIdList);
	}
	
/*	public List<Long> getUserIdList(List<UserBo> list){
		if(list==null || list.size()==0) {
			return new ArrayList<Long>();
		}
		return list.stream().map(UserBo::getUserId).collect(Collectors.toList());
	}
	*/
	/**
	 * 返回一个Map集合,key是userId,value是sessionId
	 * @param list
	 * @return
	 */
	/*public Map<Long,String> getUserIdSessionIdMapping(List<UserBo> list){
		if(list==null || list.size()==0) {
			return new HashMap<Long,String>();
		}
    	return list.stream().collect(Collectors.toMap(UserBo::getUserId, UserBo::getSessionId));
    }*/
	
	
	 /**
     * 返回一个Map集合,key是sessionId,value是userName&userType
     * @return
     */
	public Map<String,String> getUserNameTypeMapping(List<String> sessionIdList){
		if(sessionIdList==null || sessionIdList.size()==0) {
			return new HashMap<String,String>();
		}
		List<UserBo> list =userDao.selectList(sessionIdList);
    	return list.stream().collect(Collectors.toMap(UserBo::getSessionId, UserBo::getUserNameAndType));
    }
	
	 /**
     * 返回一个Map集合,key是sessionId,value是userName
     * @param sessionIds
     * @return
     */
	public Map<String,String> getUserNameMapping(List<String> sessionIds){
    	List<UserBo> list = userDao.selectList(sessionIds);
    	return list.stream().collect(Collectors.toMap(UserBo::getSessionId, UserBo::getUserName));
    }


	
}