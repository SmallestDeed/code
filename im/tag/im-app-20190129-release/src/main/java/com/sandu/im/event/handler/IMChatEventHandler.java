package com.sandu.im.event.handler;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sandu.im.model.*;
import com.sandu.im.service.DesignPlanService;
import com.sandu.im.service.handlermsg.HistoryMessageHandler;
import com.sandu.im.service.handlermsg.impl.BaseHandlerMsg;
import com.sandu.im.service.handlermsg.impl.HnadlerMsgBeanFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.sandu.im.cache.CacheInstances;
import com.sandu.im.cache.RedisService;
import com.sandu.im.common.bo.UserBo;
import com.sandu.im.common.constant.CacheConstant;
import com.sandu.im.common.util.GsonUtil;
import com.sandu.im.event.entity.ChatMessage;
import com.sandu.im.event.entity.LocMessage;
import com.sandu.im.service.HistoryMessageService;
import com.sandu.im.service.UserContactService;
import com.sandu.im.service.UserService;

import io.netty.handler.codec.http.HttpHeaders;

@Component
public class IMChatEventHandler     
{  
	private static final Logger logger = LoggerFactory.getLogger(IMChatEventHandler.class);
	private static final Integer[] APP_IDS = new Integer[] {LocMessage.APP_ID_MOBILE2B,LocMessage.APP_ID_PC2B,LocMessage.APP_ID_MINI_SD,LocMessage.APP_ID_PC_CITY_UNION};
	private static Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
              Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
	
	protected SocketIOServer server;
	@Autowired  
	HistoryMessageService historyMessageService;
	
	@Autowired
	UserContactService userContactService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RedisService redisService;

	@Autowired
	private DesignPlanService designPlanService;
	
    @Autowired  
    public IMChatEventHandler(SocketIOServer server)   
    {  
        this.server = server ;
    }
    
    @OnConnect  
    public void onConnect(SocketIOClient client)  
    {
    	String appId = client.getHandshakeData().getSingleUrlParam("appId") ; //登录端
    	String userSessionId = client.getHandshakeData().getSingleUrlParam("userSessionId") ; //当前用户
    	UUID clientSessionId = client.getSessionId();
    	if(StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(userSessionId)) {
    		CacheInstances.CHAT_SESSION_MAPPING_CACHE.put(appId+":"+userSessionId,clientSessionId);
    	}else {
    		logger.warn("appId-->("+appId+"),userSessionId-->("+userSessionId+")");
    	}
    	
    	logger.info("新连接:uid-->("+(appId+":"+userSessionId)+"),ip-->("+getClientIpAddress(client)+")");
    	logger.info("新连接-->当前人数:"+CacheInstances.CHAT_SESSION_MAPPING_CACHE.size()+",-->当前会话数:"+client.getNamespace().getAllClients().size());
    } 
    
    @OnDisconnect  
    public void onDisconnect(SocketIOClient client)  
    {
    	String appId = client.getHandshakeData().getSingleUrlParam("appId") ; //登录端
    	String userSessionId = client.getHandshakeData().getSingleUrlParam("userSessionId");
    	logger.info("断开连接:("+(appId+":"+userSessionId)+")");
    	if(userSessionId!=null) {
    		UUID offLineSessionId = CacheInstances.CHAT_SESSION_MAPPING_CACHE.get(appId+":"+userSessionId);
    		if(offLineSessionId!=null) {
	    		SocketIOClient offLineClient = client.getNamespace().getClient(offLineSessionId);
	    		//加此判断主要是为了防止网络抖动导致CHAT_SESSION_MAPPING_CACHE存储结果不正确
	    		if(offLineClient==null || !offLineClient.isChannelOpen()) {
		    		CacheInstances.CHAT_SESSION_MAPPING_CACHE.remove(appId+":"+userSessionId);
		    		CacheInstances.USER_LOC_MAPPING_CACHE.remove(appId+":"+userSessionId);
		    		logger.info("断开连接-->当前人数:"+CacheInstances.CHAT_SESSION_MAPPING_CACHE.size()+",-->当前会话数:"+client.getNamespace().getAllClients().size());
	    		}
    		}
    	}
    	
    }  
    
    @OnEvent(value = "im_chat_test")  
    public void onEvent2(SocketIOClient client, AckRequest request, Object data) throws UnsupportedEncodingException   
    {
    	logger.info("1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	for (Map.Entry<String,UUID> entry : CacheInstances.CHAT_SESSION_MAPPING_CACHE.entrySet()) {
    		logger.info("Key = " + entry.getKey() + ", enable = " + entry.getValue()); 
    	}
    	logger.info("2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	Collection<SocketIOClient> list =  client.getNamespace().getAllClients();
    	for(SocketIOClient c:list) {
    		if(c!=null) {
    			logger.info(">>>sessionId = " + c.getHandshakeData().getSingleUrlParam("userSessionId") + ", isChannelOpen = " + c.isChannelOpen()+ ", UUID = " + c.getSessionId());
    		}else {
    			logger.info(">>>client is null");
    		}
    	}
    	logger.info("3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	for (Map.Entry<String,LocMessage> entry : CacheInstances.USER_LOC_MAPPING_CACHE.entrySet()) {
    		logger.info("Key = " + entry.getKey() + ", loc = " + entry.getValue()); 
    	}
    	logger.info("4>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	/*try {
    		logger.info(data.toString());
			client.getNamespace().getBroadcastOperations().sendEvent("im_chat_test", "hello");
		}catch(Exception ex) {
			logger.error("发送消息(聊天)异常:",ex);
		}*/
    }
    
    @OnEvent(value = "im_chat_msg_event")
    public void onEvent(SocketIOClient client, AckRequest request, ChatMessage data) throws UnsupportedEncodingException   
    {
    	
    	//验证参数合法性
    	if(!this.validateParameter(client, data)) {
    		return;
    	}
    	
    	//补充消息中的发送者/接收者的名字
    	setUserName(data);
    	
    	//过滤信息内容里面的表情
    	filterEmoji(data);
    	
    	//添加双方联系人(双向):如果对方还不是联系人,则新增联系人
		this.addBidirectionalContact(data);
		
		//存入历史消息
		saveHistoryMessage(client,data);
				
		//缓存用户对话最后一条消息
		cacheLastMsgToRedis(data);

		//处理发送方案消息返回方案详情
		handlerDesignPlanMsgInfo(data);

		//将消息分发给发送方(有可能pc和移动端同时在线)和接收方
		this.dispatchToSenderAndReceiver(client, data);
		
    }

	/**
	 * 处理算选网接收消息返回方案详情
	 * @param data
	 */
	private void handlerDesignPlanMsgInfo(ChatMessage data) {

    	if (Objects.equals(data.getMsgType(),2)){
			//发送单空间方案
			ResRenderPic resRenderPic = designPlanService.selectResRenderCoverPicByDesignSceneId(Integer.parseInt(data.getMsgBody()));
			DesignPlanRenderScene renderScene = designPlanService.selectDesignPlanRenderSceneInfo(Integer.parseInt(data.getMsgBody()));
			renderScene.setCoverPath(resRenderPic.getPicPath());
			data.setObj(renderScene);
		}

		if (Objects.equals(data.getMsgType(),3)){
			//发送全屋方案
			FullHouseMainTaskInfo fullHouseMainTaskInfo = designPlanService.getMianTaskInfo(Integer.parseInt(data.getMsgBody()));
			if (Objects.nonNull(fullHouseMainTaskInfo)){
				//获取全屋方案的效果图
				String coverPic = designPlanService.getFullHouseCoverPicture(Integer.parseInt(data.getMsgBody()));
				fullHouseMainTaskInfo.setPicPath(coverPic);
			}
			data.setObj(fullHouseMainTaskInfo);
		}
	}

	private boolean validateParameter(SocketIOClient client,ChatMessage data) {
    	logger.info("输入验证参数:"+data.toString());
    	//获取接收方sessionId
    	String fromUserSessionId = data.getFromUserSessionId();
    	String toUserSessionId = data.getToUserSessionId();
    	
    	if(StringUtils.isBlank(fromUserSessionId) || data.getRelatedObjId()==null || data.getRelatedObjType()==null
    			|| StringUtils.isBlank(toUserSessionId)
    			) {
    		logger.error("非法参数:"+data.toString());
    		data.setMsgBody("非法参数");
    		client.sendEvent("im_chat_msg_event", data);
    		return false;
    	}

		if (Objects.isNull(data.getMsgType())){
			data.setMsgType(0);
		}


		if(CacheInstances.CHAT_SESSION_MAPPING_CACHE.get(data.getFromAppId()+":"+fromUserSessionId)==null){
    		logger.error("发送方不在线:"+data.toString());
			data.setMsgBody("发送方不在线");
    		client.sendEvent("im_chat_msg_event", data);
    		return false;
    	}
    	
    	
    	if(!StringUtils.isBlank(toUserSessionId)) {
    		UserBo userBo = userService.getUser(toUserSessionId);
    		if(userBo==null) {
    			logger.error("接收方不存在:"+data.toString());
    			data.setMsgBody("接收方不存在");
        		client.sendEvent("im_chat_msg_event", data);
        		return false;
    		}
    	}
    	
    	
    	
    	return true;
    }
	
	private void filterEmoji(ChatMessage data) {
		if(StringUtils.isNotBlank(data.getMsgBody())) {
			data.setMsgBody(doFilterEmoji(data.getMsgBody()));
		}
	}
	
	private String doFilterEmoji(String nickName){
        Matcher emojiMatcher = emoji.matcher(nickName);
        if (emojiMatcher.find()) {
            //将所获取的表情转换为*
            nickName = emojiMatcher.replaceAll("*");
            return nickName;
        }
        return nickName;
    }
    
    private void addBidirectionalContact(ChatMessage data){
    	logger.info("双向添加联系人:"+data.toString());
    	if(!userContactService.isContactExist(data.getFromUserSessionId(),data.getToUserSessionId(),data.getRelatedObjType(),data.getRelatedObjId())) {
			UserContact userContact = this.buildUserContact(data);
			userContactService.addContact(userContact);
		}
		
		if(!userContactService.isContactExist(data.getToUserSessionId(),data.getFromUserSessionId(),data.getRelatedObjType(),data.getRelatedObjId())) {
			UserContact reverseUserContact = this.buildReverseUserContact(data);
			userContactService.addContact(reverseUserContact);
		}
    }
    
    private UserContact buildUserContact(ChatMessage data) {
    	UserContact userContact = new UserContact();
		Date now = new Date();
		userContact.setId(UUID.randomUUID().toString().replace("-", ""));
		userContact.setUserSessionId(data.getFromUserSessionId());
		userContact.setRelatedObjType(data.getRelatedObjType());
		userContact.setRelatedObjId(data.getRelatedObjId());
		userContact.setRelatedObjOwnerSessionId(data.getRelatedObjOwnerSessionId());
		userContact.setContactSessionId(data.getToUserSessionId());
		userContact.setCreatedDate(now);
		userContact.setLastUpdatedDate(now);
		userContact.setUnreadMsg(0);
		return userContact;
    }
    
    private UserContact buildReverseUserContact(ChatMessage data) {
    	UserContact userContact = new UserContact();
		Date now = new Date();
		userContact.setId(UUID.randomUUID().toString().replace("-", ""));
		userContact.setRelatedObjType(data.getRelatedObjType());
		userContact.setRelatedObjId(data.getRelatedObjId());
		userContact.setRelatedObjOwnerSessionId(data.getRelatedObjOwnerSessionId());
		userContact.setUserSessionId(data.getToUserSessionId());
		userContact.setContactSessionId(data.getFromUserSessionId());
		userContact.setCreatedDate(now);
		userContact.setLastUpdatedDate(now);
		userContact.setUnreadMsg(0);
		return userContact;
    }
    
    private void dispatchToSenderAndReceiver(SocketIOClient client, ChatMessage data) {
    	sendToSender(client,data);
    	sendToReceiver(client,data);
    }
    
    private void sendToSender(SocketIOClient client, ChatMessage data) {
    	//遍历每一个应用端
    	for(int i=0;i<APP_IDS.length;i++) {
    		if(APP_IDS[i] != data.getFromAppId()) {
    			UUID senderId = CacheInstances.CHAT_SESSION_MAPPING_CACHE.get(APP_IDS[i]+":"+data.getFromUserSessionId());
    			//发送方在线
        		if(senderId!=null) {
        			logger.info("发送方名称:"+data.getFromUserName());
        			SocketIOClient senderClient = client.getNamespace().getClient(senderId);
        			//如果其他端的当前发送方也正好与此接收方聊天,则发送消息
        			if(senderClient!=null && isSenderChatingWithReceiver(APP_IDS[i],data)) {
						changeMobie2bUserSendPlanANDHouseMsg(APP_IDS[i],data);
        				sendMessage(senderClient,data);
        			}//如果不是正在聊天则,通知刷新联系人列表
        			else {
        				unreadMsgNotify(senderClient);
        			}
	        			//如果其他端的发送方在联系人列表中,则通知刷新联系人列表
	        			/*if(isInContactListView(APP_IDS[i],data.getFromUserSessionId())) {
	        				notifyToRefreshContactList(senderClient);
	        			}*/
        		}
    		}
    	}
    }

	private void changeMobie2bUserSendPlanANDHouseMsg(Integer appId, ChatMessage data) {
		if (Objects.equals(LocMessage.APP_ID_MOBILE2B,appId)){
			if (Objects.equals(2,data.getMsgType()) || Objects.equals(3,data.getMsgType())){
				data.setMsgBody("您发送了一个方案,请在pc端查看");
			}else if (Objects.equals(4,data.getMsgType())){
				data.setMsgBody("您发送了一个户型,请在pc端查看");
			}
		}
	}

	/**
     * 其他端的当前发送方也正好与此接收方聊天
     * @param toChatClient
     * @param fromUserSessionId
     * @return
     */
    private boolean isSenderChatingWithReceiver(Integer fromAppId,ChatMessage data) {
    	//联系人在toAppId所标识的app内的当前位置
    	LocMessage senderLocMessage = CacheInstances.USER_LOC_MAPPING_CACHE.get(fromAppId+":"+data.getFromUserSessionId());
    	if(senderLocMessage!=null) {
    		//发送方在聊天窗口(并且是同一个店铺,或者供求关系,有可能一个用户有多个店铺或者多个供求关系的情况)
    		if(senderLocMessage.getLoc()==LocMessage.LOC_CHAT_VIEW || senderLocMessage.getLoc()==LocMessage.LOC_SUPPLY_VIEW) {
    			if(senderLocMessage.getRelatedObjType()==data.getRelatedObjType()
    				&&senderLocMessage.getRelatedObjId()==data.getRelatedObjId()
    				&&senderLocMessage.getContactSessionId().equals(data.getToUserSessionId())) {
    				return true;
    			}
    		}   		
    		
    	}
    	return false;
    }
    
    
    private void sendToReceiver(SocketIOClient client, ChatMessage data) {
    	logger.info("准备开始发送消息接收方...");
    	boolean isChating =false; //是否正在聊天
    	//遍历每一个应用端
		int count = 0;
    	for(int i=0;i<APP_IDS.length;i++) {
        	UUID contactId = CacheInstances.CHAT_SESSION_MAPPING_CACHE.get(APP_IDS[i]+":"+data.getToUserSessionId());
        	logger.info("contactId:"+contactId);
        	//接收方在线
    		if(contactId!=null) {
    			//获取对方会话
    			SocketIOClient contactChatClient = client.getNamespace().getClient(contactId);
    			//接收方是否正在和我聊天
    			if(contactChatClient!=null && isReceiverChatingWithMe(APP_IDS[i],data)) {
    				logger.info("正在聊天,发送消息");
    				//如果是在移动收到方案或者户型消息,则提示用户到pc端查看
					changeMobie2bReceiverPlanANDHouseMsg(APP_IDS[i],data);
					count++;
    				sendMessage(contactChatClient,data);
    				isChating = true;
    			}
    		}
    	}
		logger.info("发送消息次数:"+count);
    	//如果两个人不是正在聊天,则更新其联系人存在未读消息,等下次用户上线进入联系人列表时,可查看未读消息.
		if(!isChating) {
			logger.info("不是正在聊天,增加未读消息数量");
			userContactService.increaseUnreadMsg(data.getFromUserSessionId(),data.getToUserSessionId(),data.getRelatedObjType(),data.getRelatedObjId());
			
			//遍历每一个应用端
			for(int i=0;i<APP_IDS.length;i++) {
				UUID contactClientChatSessionId = CacheInstances.CHAT_SESSION_MAPPING_CACHE.get(APP_IDS[i]+":"+data.getToUserSessionId());
				//接收方在线
				logger.info("即将发送未读消息 =>{}"+contactClientChatSessionId+"{APP_ID} =>{}"+APP_IDS[i]+"{data} =>{}"+data.getToUserSessionId());
	    		if(contactClientChatSessionId!=null) {
					//接收方如果在联系人列表窗口,则发送更新联系人消息
		    		/*if(isInContactListView(APP_IDS[i],data.getToUserSessionId())) {
		    			logger.info("对方在联系人窗口,发送刷新联系人列表事件");
		    			SocketIOClient toRefreshContactListClient = client.getNamespace().getClient(contactClientChatSessionId);
		    			notifyToRefreshContactList(toRefreshContactListClient);
		    		}*/
		    		logger.info("对方在线,但不是在于在聊天,发送刷新联系人列表事件 =>{}"+contactClientChatSessionId);
		    		SocketIOClient notifyClient = client.getNamespace().getClient(contactClientChatSessionId);
		    		unreadMsgNotify(notifyClient);
	    		}
			}
			
		}
		
		
    }

	private void changeMobie2bReceiverPlanANDHouseMsg(Integer appId, ChatMessage data) {
    	if (Objects.equals(LocMessage.APP_ID_MOBILE2B,appId)){
			if (Objects.equals(2,data.getMsgType()) || Objects.equals(3,data.getMsgType())){
				data.setMsgBody("您收到一个方案,请在pc端查看");
			}else if (Objects.equals(4,data.getMsgType())){
				data.setMsgBody("您收到一个户型,请在pc端查看");
			}
		}
	}


	/**
     * 两个人是否正在聊天(即同时打开对方聊天窗口)
     * @param toChatClient
     * @param fromUserSessionId
     * @return
     */
    private boolean isReceiverChatingWithMe(Integer toAppId,ChatMessage data) {
    	//联系人在toAppId所标识的app内的当前位置
    	LocMessage contactLocMessage = CacheInstances.USER_LOC_MAPPING_CACHE.get(toAppId+":"+data.getToUserSessionId());

    	if(contactLocMessage!=null) {
    		//在聊天窗口
    		if(contactLocMessage.getLoc()==LocMessage.LOC_CHAT_VIEW || contactLocMessage.getLoc()==LocMessage.LOC_SUPPLY_VIEW) {
    			if(contactLocMessage.getRelatedObjType().intValue()==data.getRelatedObjType().intValue()
					&&contactLocMessage.getRelatedObjId().longValue() == data.getRelatedObjId().longValue()
					&&contactLocMessage.getContactSessionId().equals(data.getFromUserSessionId())) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * 对方是否在联系人窗口
     * @param toUserSessionId
     * @return
     */
    private boolean isInContactListView(Integer appId,String userSessionId) {
    	LocMessage contactLocMessage = CacheInstances.USER_LOC_MAPPING_CACHE.get(appId+":"+userSessionId);
    	if(contactLocMessage!=null) {
    		if(contactLocMessage.getLoc()==LocMessage.LOC_OHTER_VIEW) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 通知有未读消息,需要更新联系人信息列表
     */
    private void unreadMsgNotify(SocketIOClient client) {
    	try {
    		client.sendEvent("im_unread_msg_event","{'hasUnreadMsg':1}" );
    	}catch(Exception ex) {
    		logger.error("发送消息(刷新联系人列表)异常:",ex);
    	}
    }
    
   /**
    * 补充消息中的发送者/接收者的用户名
    * @param data
    */
    private void setUserName(ChatMessage data) {
    	logger.info("补充消息中的发送者/接收者的用户名");
    	List<String> sessionIdList = new ArrayList<String>();
		sessionIdList.add(data.getFromUserSessionId());
		sessionIdList.add(data.getToUserSessionId());
		Map<String,String> userMap = userService.getUserNameMapping(sessionIdList);
		data.setFromUserName(userMap.get(data.getFromUserSessionId()));
		data.setToUserName(userMap.get(data.getToUserSessionId()));
    }
    

	/**
	 * 发送聊天消息
	 * @param client
	 * @param data
	 */
	private void sendMessage(SocketIOClient client, ChatMessage data) {
		try {
			client.sendEvent("im_chat_msg_event", data);
		}catch(Exception ex) {
			logger.error("发送消息(聊天)异常:"+data.toString(),ex);
		}
	}

	
    
	public String getClientIpAddress(SocketIOClient client) {  
		InetSocketAddress address = (InetSocketAddress) client.getRemoteAddress();
		HttpHeaders headers = client.getHandshakeData().getHttpHeaders();
	    String ip = headers.get("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = headers.get("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = headers.get("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = address.getHostString();  
	    }  
	    return ip;  
	}
	
	private void cacheLastMsgToRedis(ChatMessage data) {
		logger.info("缓存用户对话最后一条消息");
    	redisService.set(CacheConstant.LAST_MSG_PREFIX+data.getRelatedObjType()+":"+data.getRelatedObjId()+":"+data.getFromUserSessionId()+":"+data.getToUserSessionId(), GsonUtil.toJson(data));
    	redisService.set(CacheConstant.LAST_MSG_PREFIX+data.getRelatedObjType()+":"+data.getRelatedObjId()+":"+data.getToUserSessionId()+":"+data.getFromUserSessionId(), GsonUtil.toJson(data));
	}
	
	private void saveHistoryMessage(SocketIOClient client, ChatMessage data) {
    	HistoryMessage historyMessage = buildHistoryMessage(client,data);
		historyMessageService.saveHistoryMessage(historyMessage);
	}
	
	private HistoryMessage buildHistoryMessage(SocketIOClient client,ChatMessage data) {
		HistoryMessage historyMessage = new HistoryMessage();
		data.setSendTime(new Date());
		BeanUtils.copyProperties(data, historyMessage);
		historyMessage.setFromUserIp(getClientIpAddress(client));
		historyMessage.setId(UUID.randomUUID().toString().replace("-", ""));
		if (Objects.isNull(historyMessage.getMsgType())){
			historyMessage.setMsgType(0);
		}
		return historyMessage;
	} 

	

	
	
    
}  