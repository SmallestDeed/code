package com.sandu.im.service;

import java.util.*;
import java.util.stream.Collectors;

import com.sandu.im.service.handlermsg.HistoryMessageHandler;
import com.sandu.im.service.handlermsg.impl.HnadlerMsgBeanFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sandu.im.cache.RedisService;
import com.sandu.im.common.bo.HistoryMessageBo;
import com.sandu.im.common.util.DateUtil;
import com.sandu.im.dao.HistoryMessageDao;
import com.sandu.im.event.entity.ChatMessage;
import com.sandu.im.model.HistoryMessage;

@Service
public class HistoryMessageService {

	private static final Logger logger = LoggerFactory.getLogger(HistoryMessageService.class);
	
	@Autowired
	HistoryMessageDao historyMessageDao;
	
	@Autowired
	RedisService redisService;

	@Transactional
	public void saveHistoryMessage(HistoryMessage historyMessage) {
		logger.info("保存历史消息");
		historyMessageDao.insert(historyMessage);
	}
	
	public void removeHistoryMessage(String userSessionId,String contactSessionId,Integer relatedObjType,Long relatedObjId) {
		logger.info("删除历史消息:"+userSessionId+","+contactSessionId+","+relatedObjType+","+relatedObjId);
		historyMessageDao.delete(userSessionId,contactSessionId,relatedObjType,relatedObjId);
		historyMessageDao.delete(contactSessionId,userSessionId,relatedObjType,relatedObjId);
	}
	
	public PageInfo<HistoryMessageBo> pageListHistoryMessage(String userSessionId, String contactSessionId, Integer relatedObjType, Long relatedObjId, Integer pageNum, Integer pageSize, String platformCode) {
		PageHelper.startPage(pageNum, pageSize);
		List<HistoryMessage> list = historyMessageDao.selectList(userSessionId,contactSessionId,relatedObjType,relatedObjId);
		List<HistoryMessageBo> resultList = new ArrayList<HistoryMessageBo>();
		if(list!=null && list.size()>0) {
			for(int i=0;i<list.size();i++) {
				HistoryMessage m = list.get(i);
				if(m.getFromUserSessionId().equals(userSessionId)) {
					resultList.add(new HistoryMessageBo(m.getFromUserName(),m.getToUserName(),m.getMsgBody(),DateUtil.format(m.getSendTime()),HistoryMessageBo.DIRECTION_SEND,m.getId(),m.getMsgType()));
				}else {
					resultList.add(new HistoryMessageBo(m.getFromUserName(),m.getToUserName(),m.getMsgBody(),DateUtil.format(m.getSendTime()),HistoryMessageBo.DIRECTION_RECEIVE,m.getId(),m.getMsgType()));
				}
			}
		}
		handlerMsg(resultList,platformCode);
		return new PageInfo<HistoryMessageBo>(resultList);
	}

	/**
	 * 根据平台编码处理消息
	 * @param resultList
	 * @param platformCode
	 */
	private void handlerMsg(List<HistoryMessageBo> resultList,String platformCode) {

		//处理发送方消息
		handlerFromUserMsg(resultList,platformCode);

		//处理接受方消息
	    handlerToUserMsg(resultList,platformCode);

	}

    private void handlerToUserMsg(List<HistoryMessageBo> resultList, String platformCode) {
		//处理发送方消息
		HistoryMessageHandler produce = HnadlerMsgBeanFactory.produce(platformCode);

		//处理接收单空间消息
		produce.handlerSingleSpaceDesingPlanToUser(resultList);

		//处理接收户型
		produce.handlerBaseHouseDesingPlanToUser(resultList);

		//处理接收全屋方案
		produce.handlerFullHouseDesingPlanToUser(resultList);
    }


    private void handlerFromUserMsg(List<HistoryMessageBo> resultList, String platformCode) {

		//处理发送方消息
		HistoryMessageHandler produce = HnadlerMsgBeanFactory.produce(platformCode);

		//处理发送单空间消息
		produce.handlerSingleSpaceDesingPlanFromUser(resultList);

		//处理发送户型
		produce.handlerBaseHouseDesingPlanFromUser(resultList);

		//处理发送全屋方案
		produce.handlerFullHouseDesingPlanFromUser(resultList);
	}

	/**
	 * 一次性从缓存获取用户与所有联系人的最后一条消息数据
	 * @param keys (key = userSessionId+":"+contactSessionId)
	 * @return 
	 */
	public Map<String,ChatMessage> getLastMsgFromRedisCache(String userSession,List<String> keys) {
		
		List<ChatMessage> listMsg = redisService.getObjects(keys, ChatMessage.class);
		Map<String,ChatMessage> map = new HashMap<String,ChatMessage>();
		listMsg.forEach(chatMsg->{
			map.put(chatMsg.getRelatedObjType()+":"
					+chatMsg.getRelatedObjId()+":"
					+userSession+":"
					+(userSession.equals(chatMsg.getFromUserSessionId())?chatMsg.getToUserSessionId():chatMsg.getFromUserSessionId()),
					chatMsg);
		});		
		return map;
		 
	}
	
}
