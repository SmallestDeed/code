package com.nork.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.util.SendEmail;
import com.nork.common.util.WebSocketUtils;
import com.nork.design.model.DesignPlan;
import com.nork.design.service.DesignPlanService;
import com.nork.home.model.SpaceCommon;
import com.nork.home.service.SpaceCommonService;
import com.nork.pay.metadata.PayType;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.BaseMessageMapper;
import com.nork.system.model.BaseMessage;
import com.nork.system.model.BaseMessageRecieve;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.model.UserMessageDesignPlan;
import com.nork.system.model.search.BaseMessageSearch;
import com.nork.system.service.BaseMessageRecieveService;
import com.nork.system.service.BaseMessageService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import com.nork.system.websocket.obj.MessageResponse;
import com.nork.task.model.SysTask;

/**   
 * @Title: BaseMessageServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-消息表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-08-13 14:30:45
 * @version V1.0   
 */
@Service("baseMessageService")
@Transactional
public class BaseMessageServiceImpl implements BaseMessageService {
	private static Logger logger = Logger
			.getLogger(BaseMessageServiceImpl.class);
	private BaseMessageMapper baseMessageMapper;

	@Autowired
	public void setBaseMessageMapper(
			BaseMessageMapper baseMessageMapper) {
		this.baseMessageMapper = baseMessageMapper;
	}
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BaseMessageRecieveService baseMessageRecieveService;
	@Autowired
	private  SpaceCommonService spaceCommonService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private PayOrderService payOrderService;
	/**
	 * 新增数据
	 *
	 * @param baseMessage
	 * @return  int 
	 */
	@Override
	public int add(BaseMessage baseMessage) {
		baseMessageMapper.insertSelective(baseMessage);
		return baseMessage.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param baseMessage
	 * @return  int 
	 */
	@Override
	public int update(BaseMessage baseMessage) {
		return baseMessageMapper
				.updateByPrimaryKeySelective(baseMessage);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseMessageMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseMessage 
	 */
	@Override
	public BaseMessage get(Integer id) {
		return baseMessageMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseMessage
	 * @return   List<BaseMessage>
	 */
	@Override
	public List<BaseMessage> getList(BaseMessage baseMessage) {
	    return baseMessageMapper.selectList(baseMessage);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseMessage
	 * @return   int
	 */
	@Override
	public int getCount(BaseMessageSearch baseMessageSearch){
		return  baseMessageMapper.selectCount(baseMessageSearch);	
    }
	/**
	 *    获取用户所有消息数据数量
	 *
	 * @param  baseMessage
	 * @return   int
	 */
	@Override
	public int getCountAllMessage(BaseMessageSearch baseMessageSearch){
		return  baseMessageMapper.selectCountAllMessage(baseMessageSearch);	
	}
	/**
	 *    获取用户所有未读消息数据数量
	 *
	 * @param  baseMessage
	 * @return   int
	 */
	@Override
	public int getCountUnreaded(BaseMessageSearch baseMessageSearch){
		return  baseMessageMapper.selectCountUnreaded(baseMessageSearch);	
	}
	

	/**
	 *    分页获取数据
	 *
	 * @param  baseMessage
	 * @return   List<BaseMessage>
	 */
	@Override
	public List<BaseMessage> getPaginatedList(
			BaseMessageSearch baseMessageSearch) {
		return baseMessageMapper.selectPaginatedList(baseMessageSearch);
	}
	/**
	 *    分页获取用户所有消息数据
	 *
	 * @param  baseMessage
	 * @return   List<BaseMessage>
	 */
	@Override
	public List<BaseMessage> getAllMessage(
			BaseMessageSearch baseMessageSearch) {
		return baseMessageMapper.selectAllMessage(baseMessageSearch);
	}
	
	/**
	 * 查询站内消息列表
	 */
	@Override
	public List<UserMessageDesignPlan> getMessageList(
			UserMessageDesignPlan userMessageDesignPlan) {
		return baseMessageMapper.selectMessageList(userMessageDesignPlan);
	}

//	@Override
//	public List<UserMessageDesignPlan> getReceiverList(
//			UserMessageDesignPlan userMessageDesignPlan) {
//		return baseMessageMapper.selectReceiverList(userMessageDesignPlan);
//	}

	@Override
	public List<UserMessageDesignPlan> getReceiverIdList(
			UserMessageDesignPlan userMessageDesignPlan) {
		return baseMessageMapper.selectReceiverIdList(userMessageDesignPlan);
	}

	@Override
	public List<UserMessageDesignPlan> getWebLettersList(
			UserMessageDesignPlan userMessageDesignPlan) {
		return baseMessageMapper.selectWebMessageList(userMessageDesignPlan);
	}

	@Override
	public int getUnReadMessageCount(BaseMessage baseMessage) {
		// TODO Auto-generated method stub
		return baseMessageMapper.unreadMessageCount(baseMessage);
	}

	/**
	 * 发送渲染消息
	 * @param task
	 * @param state
	 * @param msg
	 * @return
	 */
	public int sendRenderMessage(SysTask task, Integer state,String msg){
			Integer messageId = 0;
			//失败之后发送信息
			Integer userId=null;
			DesignPlan designPlan_=null;
			designPlan_=designPlanService.get(task.getBusinessId());
			if(designPlan_ != null){
				userId = designPlan_.getUserId();
			}


			SysUser sysUser = sysUserService.get(userId);
			if(sysUser != null){
				logger.info(task.getExecCount()+"+++++++++++++++++++++++++++++--------------------------");
				BaseMessage baseMessage= new BaseMessage();
				baseMessage.setUserId(userId);
				baseMessage.setBusinessTypeId(5);
				baseMessage.setBusinessObjId(designPlan_.getId());
				baseMessage.setBusinessObjType("design_plan");
				baseMessage.setMessageType(0);
				baseMessage.setCreator(sysUser.getNickName());
				baseMessage.setGmtCreate(new Date());
				baseMessage.setModifier(sysUser.getNickName());
				baseMessage.setGmtModified(new Date());
				baseMessage.setIsDeleted(0);
				if(state==2){
					if(task.getRenderType().intValue()==4){
						baseMessage.setContent("<color='#01017A'>"+designPlan_.getPlanName()+designPlan_.getPlanCode()+"</color>"+ ProductType.PANORAMA_RENDER_NAME+"成功");
					}else{
						baseMessage.setContent("<color='#01017A'>"+designPlan_.getPlanName()+designPlan_.getPlanCode()+"</color>"+ProductType.PICTURE_RENDER_NAME+"成功");
					}
				}else{
					if(task.getRenderType().intValue()==4){
						baseMessage.setContent("<color='#01017A'>"+designPlan_.getPlanName()+designPlan_.getPlanCode()+"</color>"+ProductType.PANORAMA_RENDER_NAME+"失败,请联系客服");
					}else{
						baseMessage.setContent("<color='#01017A'>"+designPlan_.getPlanName()+designPlan_.getPlanCode()+"</color>"+ProductType.PICTURE_RENDER_NAME+"失败,请联系客服");
					}
				}
				messageId = this.add(baseMessage);

				BaseMessageRecieve baseMessageRecieve= new BaseMessageRecieve();
				baseMessageRecieve.setMessageId(messageId);
				baseMessageRecieve.setUserId(userId);
				baseMessageRecieve.setIsReaded(0);
				baseMessageRecieve.setCreator(sysUser.getNickName());
				baseMessageRecieve.setGmtCreate(new Date());
				baseMessageRecieve.setModifier(sysUser.getNickName());
				baseMessageRecieve.setGmtModified(new Date());
				baseMessageRecieve.setIsDeleted(0);
				baseMessageRecieveService.add(baseMessageRecieve);
			}
			MessageResponse messageResponse = new MessageResponse();
			logger.info("=======================任务状态："+state + "        " +(state.intValue() == 2));
			
			String houseName = "";
			SpaceCommon spaceCommon = null;
			spaceCommon = spaceCommonService.get(designPlan_.getSpaceCommonId());
			if(spaceCommon!=null){
				Integer spaceFunctionId = spaceCommon.getSpaceFunctionId();
				if(spaceFunctionId!=null){
					SysDictionary sysDictionary = new SysDictionary();
					sysDictionary.setValue(spaceFunctionId);
					sysDictionary.setType("houseType");
					sysDictionary.setIsDeleted(0);
					List<SysDictionary>houseNameList = sysDictionaryService.getList(sysDictionary);
					if(houseNameList!=null&&houseNameList.size()==1){
						houseName = houseNameList.get(0).getName();
					}
				}
			}
			String renderTypeName = "";
			if(task.getRenderType()!=null){
				if(task.getRenderType().intValue() == RenderTypeCode.SCREEN_OF_PIC){
					renderTypeName = ",高清图片";
				}else if(task.getRenderType().intValue() == RenderTypeCode.COMMON_PICTURE_LEVEL){
					renderTypeName = ",照片级普通 ";
				}else if(task.getRenderType().intValue() == RenderTypeCode.HD_PICTURE_LEVEL){
					renderTypeName = ",照片级高清";
				}else if(task.getRenderType().intValue() == RenderTypeCode.ULTRA_HD_PICTURE_LEVEL){
					renderTypeName = ",照片级超高清";
				}else if(task.getRenderType().intValue() == RenderTypeCode.COMMON_720_LEVEL){
					renderTypeName = ",720度普通";
				}else if(task.getRenderType().intValue() == RenderTypeCode.HD_720_LEVEL){
					renderTypeName = ",多点全景";
				}
			}
			
			
			if( state.intValue() == 2 ){
				messageResponse.setSuccess(true);
				//messageResponse.setMessage("渲染成功!");
				String message = houseName + designPlan_.getPlanCode() + renderTypeName + "渲染成功！";
				messageResponse.setMessage(message);
			}else{
				messageResponse.setSuccess(false);
				//messageResponse.setMessage(msg);
				String msg1 ="渲染失败,本次扣款将退回到您的账户里！";
				PayOrder findOneByTaskId = payOrderService.findOneByTaskId(task.getId());
				if(findOneByTaskId != null){
					if(findOneByTaskId.getTotalFee() != null &&  findOneByTaskId.getTotalFee() == 0 && PayType.PREDEPOSIT == findOneByTaskId.getPayType()){
						msg="渲染失败";
					}
				}
				String message = houseName + designPlan_.getPlanCode() + renderTypeName + msg +msg1;
				messageResponse.setMessage(message);
			}
			messageResponse.setType(1);
			Map<String,String> map=new HashMap<String,String>();
			map.put("designPlanId", designPlan_.getId()+"");
			messageResponse.setObj(map);
			JSONObject jsonObject = JSONObject.fromObject(messageResponse);
			String message=jsonObject.toString();
			//noticeWsServer(userId+"",message);
			try {
				WebSocketUtils.sendMessage("app.webSocket.message", userId.toString(), message);
			}catch(Exception e){
				logger.error("message websocket链接异常"+e);
				// 发送邮件
				List<SysUser> warningUserList = sysUserService.getUserByRoleCode("RENDER_WARNING");
				if( warningUserList != null && warningUserList.size() > 0 ){
					StringBuffer toEmailsStr = new StringBuffer();
					int count = 0;
					for( SysUser warningUser : warningUserList ){
						if( com.nork.common.util.StringUtils.isNotBlank(warningUser.getEmail()) ) {
							if( count < warningUserList.size() ) {
								toEmailsStr.append(warningUser.getEmail() + ",");
							}else{
								toEmailsStr.append(warningUser.getEmail());
							}
							count++;
						}
					}
					if( toEmailsStr.length() > 0 ) {
						String[] toEmails = toEmailsStr.toString().split(",");
						StringBuffer stringBuffer = new StringBuffer("Dear All ,<br>");
						stringBuffer.append("websocket服务器可能已中断,"+ WebSocketUtils.webSocket.getString("app.webSocket.message"));
						String subject = "【websocket链接异常】";
						SendEmail.massSend(toEmails, subject, stringBuffer.toString());
					}
				}else{
					logger.error("warningUserList is null");
				}
			}
			return messageId;
		}

}
