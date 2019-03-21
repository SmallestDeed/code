package com.sandu.im.service;

import java.util.*;

import com.sandu.im.common.bo.ShopBo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.im.common.bo.UserBo;
import com.sandu.im.common.bo.UserContactBo;
import com.sandu.im.common.constant.CacheConstant;
import com.sandu.im.common.constant.UserConstant;
import com.sandu.im.common.util.DateUtil;
import com.sandu.im.dao.UserContactDao;
import com.sandu.im.event.entity.ChatMessage;
import com.sandu.im.model.UserContact;

@Service
public class UserContactService {

	private static final Logger logger = LoggerFactory.getLogger(UserContactService.class);
	
	@Autowired
	UserContactDao userContactDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	HistoryMessageService historyMessageService;
	
	@Autowired
	ShopService shopService;
	
	@Autowired
	SupplyService supplyService;
	
	
	public void addContact(UserContact userContact) {
		try {
			userContactDao.insert(userContact);
		}catch(Exception ex) {
			logger.warn("联系人已存在?",ex);
		}
	}
	
	public boolean isContactExist(String userSessionId,String contactSessionId,Integer relatedObjType,Long relatedObjId) {
		UserContact userContact = userContactDao.selectUserContact(userSessionId, contactSessionId,relatedObjType,relatedObjId);
		if(userContact==null) {
			return false;
		}
		return true;
	}

	public void resetUnreadMsg(String userSessionId,String contactSessionId,Integer relatedObjType,Long relatedObjId) {
		userContactDao.updateToResetUnreadMsg(userSessionId,contactSessionId,relatedObjType,relatedObjId);
	}
	
	/**
	 * 双向删除联系人,及聊天记录
	 * @param userSessionId
	 * @param contactSessionId
	 * @param relatedObjType
	 * @param relatedObjId
	 */
	public void removeContactAndHistoryMsg(String userSessionId,String contactSessionId,Integer relatedObjType,Long relatedObjId) {
		userContactDao.deleteContact(userSessionId,contactSessionId,relatedObjType,relatedObjId);
		userContactDao.deleteContact(contactSessionId,userSessionId,relatedObjType,relatedObjId);
		historyMessageService.removeHistoryMessage(userSessionId, contactSessionId, relatedObjType, relatedObjId);
	}
	
	public void increaseUnreadMsg(String userSessionId,String contactSessionId,Integer relatedObjType,Long relatedObjId) {
		userContactDao.updateToAddUnreadMsg(userSessionId,contactSessionId,relatedObjType,relatedObjId);
	}

	public List<UserContactBo> listUserContactsAndLastMsg(String userSessionId) {
		List<UserContactBo> list = userContactDao.selectUserContactList(userSessionId);
		//调用用户服务,获取联系人名称
		if(list!=null && list.size()>0) {
			List<String> sessionIds = new ArrayList<String>();
			List<String> userAndContactSessionIds = new ArrayList<String>();
			List<Long> shopIdList = new ArrayList<Long>();
			List<Long> supplyIdList = new ArrayList<Long>();
			list.forEach(userContactBo ->{
				sessionIds.add(userContactBo.getContactSessionId());
				userAndContactSessionIds.add(CacheConstant.LAST_MSG_PREFIX+userContactBo.getRelatedObjType()+":"+userContactBo.getRelatedObjId()+":"+userSessionId+":"+userContactBo.getContactSessionId());
				
				//关联实体为联系人所创建
				if(userContactBo.isObjOwnByContact()) {
					//如果是店铺
					if(userContactBo.getRelatedObjType()==1) {
						shopIdList.add(userContactBo.getRelatedObjId());
					}//否则为供求关系
					else {
						supplyIdList.add(userContactBo.getRelatedObjId());
					}
				}
			});
			
			//用于获取用户名称
			Map<String,String> userMapping = userService.getUserNameTypeMapping(sessionIds);
			
			//用于获取店铺名称
			Map<Long,String> shopMapping = shopService.getShopNameMapping(shopIdList);
			
			//用于供求标题
			Map<Long,String> supplyMapping = supplyService.getSupplyTitleMapping(supplyIdList);
			
			
			//获取最后一条消息
 			Map<String,ChatMessage> lastUserMsgMap = historyMessageService.getLastMsgFromRedisCache(userSessionId,userAndContactSessionIds);
			
			
			//补充用户联系人列表相关信息
			list.forEach(userContactBo ->{
				//填充联系名称和类型
				if(userMapping.get(userContactBo.getContactSessionId())!=null) {
					String[] userNameAndType = userMapping.get(userContactBo.getContactSessionId()).split("&");
					if(userNameAndType!=null ) {
						//关联实体为联系人所创建,则取相关的实体信息
						if(userContactBo.isObjOwnByContact()) {
							//如果是店铺,则取店铺名称
							if(userContactBo.getRelatedObjType()==1) {
								userContactBo.setContactName(shopMapping.get(userContactBo.getRelatedObjId()));
							}
							//供求关系
							else if(userContactBo.getRelatedObjType()==2) {
								userContactBo.setContactName(userNameAndType[0]+"("+supplyMapping.get(userContactBo.getRelatedObjId())+")");
							}
						}
						//否则取用户名称
						else {
							userContactBo.setContactName(userNameAndType[0]);
						}
					}
					if(userNameAndType.length>1 && StringUtils.isNotBlank(userNameAndType[1])) {
						//userContactBo.setUserType(UserConstant.userTypeMapping.get(Integer.parseInt(userNameAndType[1])));
						userContactBo.setUserType(Integer.parseInt(userNameAndType[1]));
					}
				}
				logger.info("userSessionId:"+userSessionId);
				//填充最后一条消息内容及时间
				ChatMessage userLastMsg = lastUserMsgMap.get(userContactBo.getRelatedObjType()+":"+userContactBo.getRelatedObjId()+":"+userSessionId+":"+userContactBo.getContactSessionId());
				if(userLastMsg!=null) {
					userContactBo.setLastMsg(userLastMsg.getMsgBody());
					userContactBo.setSendTime(DateUtil.format(userLastMsg.getSendTime()));
					userContactBo.setMsgType(userLastMsg.getMsgType());
				}
				
			});
		}
		return list;
	}

    public Map<String, Object> getContactUserShopInfo(String contactSessionId) {
		ShopBo shop = shopService.getShopInfoByContactUserSessionId(contactSessionId);
		Map<String,Object> result = new HashMap<>();
		result.put("isShopFlag", Objects.nonNull(shop) ? Boolean.TRUE : Boolean.FALSE);
		result.put("shop",shop);
		return result;
    }

	public int countChatRecordByTypeAndUserSessionId(String userSessionId, Integer userType,String userName,String shopName) {
		return userContactDao.countChatRecordByTypeAndUserSessionId(userSessionId,userType,userName,shopName);
	}

	public List<UserBo> getChatRecordByTypeAndUserSessionId(String userSessionId, Integer userType, String userName, String shopName, Integer page, Integer limit) {
		return userContactDao.getChatRecordByTypeAndUserSessionId(userSessionId,userType,userName,shopName,page,limit);
	}

	public List<UserBo> getHotDesignerUserInfo(List<Integer> hotDesignrIds, String userName, String shopName,Integer page) {
		return userContactDao.getHotDesignerUserInfo(hotDesignrIds,userName,shopName,page);
	}

	public int countSanduShopDesigners(String userName, String shopName,Integer userType) {
		return userContactDao.countSanduShopDesigners(userName,shopName,userType);
	}

	public List<UserBo> getSanduShopDesigners(String userName, String shopName, Integer userType, int sanduDesigners, int size) {
		return userContactDao.getSanduShopDesigners(userName, shopName, userType,sanduDesigners,size);
	}
}
