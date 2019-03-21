package com.nork.design.controller.web;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.jwt.Jwt;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.service.DesignPlanAutoRenderService;

@Controller
@RequestMapping("/{style}/web/design/autoRender")
public class WebDesignPlanAutoRenderController {
	
	private static Logger logger = Logger
			.getLogger(WebDesignPlanAutoRenderController.class);
	
	@Autowired
	private DesignPlanAutoRenderService designPlanAutoRenderServiceImpl;
	
	/**
	 * 手动加入任务到缓存redis
	 * @param maxSize
	 * @return ResponseEnvelope
	 */
	@RequestMapping("/addRenderTaskList")
	@ResponseBody
	public ResponseEnvelope<AutoRenderTask> addRenderTaskList(Integer maxSize){
		AutoRenderTask autoRenderTask = new AutoRenderTask();
		autoRenderTask.setIsDeleted(0);
		autoRenderTask.setMaxSize(maxSize==null?10:maxSize);
		
		List<AutoRenderTask>  list = designPlanAutoRenderServiceImpl.getTaskList(autoRenderTask);
		if(list!=null && list.size()>0) {
			for(AutoRenderTask art:list) {
				designPlanAutoRenderServiceImpl.addRedisLists(art);
			}
		}
		ResponseEnvelope<AutoRenderTask> result = new ResponseEnvelope<AutoRenderTask>(true);
		return result;
	}
	
	
	/**
	 * 获取任务插入任务状态
	 * @param request
	 * @param response
	 * @param maxSize
	 * @param msgId
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping("/getRenderTaskList")
	@ResponseBody
	public ResponseEnvelope<AutoRenderTask> getRenderTaskList(HttpServletRequest request, HttpServletResponse response,Integer maxSize,String msgId
			,Integer preTaskId,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException{
		String token = request.getHeader("Authorization");
		if (preTaskId != null && preTaskId > 0) {
			AutoRenderTaskState autoRenderTaskState = designPlanAutoRenderServiceImpl.getStateByTaskId(preTaskId);
			if (autoRenderTaskState != null) {
				if (autoRenderTaskState.getState().intValue() == 2 ) {
					AutoRenderTaskState taskState = new AutoRenderTaskState();
					taskState.setPlanId(autoRenderTaskState.getPlanId());
					taskState.setTemplateId(autoRenderTaskState.getTemplateId());
					taskState.setFailReason("服务器响应超时，请重新渲染!");
					taskState.setFailType(88);
					taskState.setTaskId(preTaskId);
					designPlanAutoRenderServiceImpl.updateAutoRenderTaskState(taskState,msgId,token);
				}
			}
		}
		
		//LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		List<AutoRenderTask> taskList = new ArrayList<AutoRenderTask>();
		// 置顶渲染为最高优先级 ，产品替换任务为第二优先级，如果不存在产品替换任务，才去取自动渲染任务
		LoginUser loginUser = new LoginUser();
		loginUser.setId(-1);
		loginUser.setLoginName("AUTO_USER");

		// 置顶任务 最高优先级
		AutoRenderTask stickRenderTask = designPlanAutoRenderServiceImpl.getRedisStickTaskList(maxSize, loginUser,renderMachineIp,renderLevel,renderProgramVersion);
		AutoRenderTask replaceRenderTask = null;
		AutoRenderTask autoRenderTask = null;
//		AutoRenderTaskState autoRenderTaskState = null;
		if (stickRenderTask != null && stickRenderTask.getId() > 0) {
			taskList.add(stickRenderTask);
		} else {
			/*replaceRenderTask = designPlanAutoRenderServiceImpl.getRedisReplaceTaskList(maxSize, loginUser);
			if (replaceRenderTask != null && replaceRenderTask.getId() > 0) {
				taskList.add(replaceRenderTask);
			} else {
				autoRenderTask = designPlanAutoRenderServiceImpl.getRedisTaskList(maxSize, loginUser);
				if (autoRenderTask != null && autoRenderTask.getId() > 0) {
					taskList.add(autoRenderTask);
				}
			}*/
					
			do {
				// 替换任务 第二优先级
				/**
				 * 三种case 1, 队列里有任务，但没执行 2, 队列里没任务 3, 对列里有任务，但已经执行过
				 **/
				replaceRenderTask = designPlanAutoRenderServiceImpl.getRedisReplaceTaskList(maxSize, loginUser,renderMachineIp,renderLevel,renderProgramVersion);
				// 查看渲染状态是否有存在 如果存在取下一条
				if (replaceRenderTask != null) {
					logger.error("getRedisReplaceTaskList get replace taskId=>" + replaceRenderTask.getId());
					taskList.add(replaceRenderTask);
				}
			} while ((JedisUtils.getSizeOfList(SystemCommonConstant.REDIS_TASK_REPLACE_LIST) > 0
					&& taskList.size() == 0));

			if (taskList.size() == 0) {
				do {
					// 自动渲染任务 第三优先级
					autoRenderTask = designPlanAutoRenderServiceImpl.getRedisTaskList(maxSize, loginUser,renderMachineIp,renderLevel,renderProgramVersion);
					// 查看渲染状态是否有存在 如果存在取下一条
					if (autoRenderTask != null) {
						logger.error("getRedisTaskList get replace taskId=>" + autoRenderTask.getId());
						taskList.add(autoRenderTask);
					}
				} while ((JedisUtils.getSizeOfList(SystemCommonConstant.REDIS_TASK_LIST) > 0 && taskList.size() == 0));
			}

		}

		ResponseEnvelope<AutoRenderTask> result = new ResponseEnvelope<AutoRenderTask>(true, msgId, maxSize, taskList);
		return result;
	}
	
	/**
	 * 更新失败任务状态
	 * @param planId
	 * @param templateId
	 * @param businessId
	 * @param failType
	 * @param failReason
	 * @param request
	 * @param response
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/updateRenderTaskState")
	@ResponseBody
	public ResponseEnvelope<AutoRenderTaskState> updateRenderTaskState(Integer planId,Integer templateId,Integer businessId ,Integer failType,String failReason, HttpServletRequest request, HttpServletResponse response,String msgId,Integer taskId){
//		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
		String token = request.getHeader("Authorization");
		Map<String, Object> map = Jwt.validToken(token);
		 Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		         while (it.hasNext()) {
		              Entry<String, Object> entry = it.next();
		                logger.error("key= " + entry.getKey() + " and value= " + entry.getValue());
		         }
		if(taskId == null || taskId.intValue() < 1) {
			return new ResponseEnvelope<>(false,"任务ID为空！");
		}
		AutoRenderTaskState taskState = new AutoRenderTaskState();
		taskState.setPlanId(planId);
		taskState.setTemplateId(templateId);
		taskState.setFailReason(failReason);
		taskState.setFailType(failType);
		taskState.setBusinessId(businessId);
		taskState.setTaskId(taskId);
		return designPlanAutoRenderServiceImpl.updateAutoRenderTaskState(taskState, msgId,token);
	}

	/**
	 * 效果图封面设置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateCoverPic")
	@ResponseBody
	public Object updateCoverPic(HttpServletRequest request, HttpServletResponse response) {
		String picId = request.getParameter("picId");
		String planId = request.getParameter("planId");
		String msgId = request.getParameter("msgId");
		String designPlanType = request.getParameter("designPlanType");
		if (StringUtils.isEmpty(picId) || StringUtils.isEmpty(planId) || StringUtils.isEmpty(msgId) ) {
			return new ResponseEnvelope<DesignPlan>(false, "缺少参数", msgId);
		}
		return designPlanAutoRenderServiceImpl.updateCoverPic(picId,planId,msgId,designPlanType);
	}

	
	/**
     * 
       
     * getThumbList(这里用一句话描述这个方法的作用)        
       
     * @param request
     * @param response
     * @return 
    
     * @return PageModel    返回类型   
       
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
       http://localhost:8080/timeSpace/online/web/design/autoRender/listthumb.htm?st=0&pz=10&msgId=123&nm=客餐厅45平
       http://192.168.1.218:9080/app/online/web/design/autoRender/listthumb.htm?msgId=236MSG_LISTTHUMB&st=0&pz=10
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(/*method=RequestMethod.POST,*/ value="/listthumb")
    @ResponseBody
    public  ResponseEnvelope getThumbList(HttpServletRequest request, HttpServletResponse response){
        ResponseEnvelope  envelope = new ResponseEnvelope ();
        
        String sartIndex = request.getParameter("st");//第几条数据
        String pageSize = request.getParameter("pz");//每页显示多少条，最大不能超过20页
        String name = request.getParameter("nm");//查询名称
        String msgId = request.getParameter("msgId");
        envelope.setMsgId(msgId);
        
        if (StringUtils.isEmpty(msgId)) {
            envelope.setSuccess(false);
            envelope.setMessage("params error");
            return envelope;
        }
        
        LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser.getId()<0) {
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            return envelope;
        }
        int userId = loginUser.getId();
        int start = 0;
        int size = 20;
        if(StringUtils.isNotEmpty(pageSize) && StringUtils.isNumeric(pageSize)){
            size = Integer.valueOf(pageSize)>20?20:Integer.valueOf(pageSize);//最多二十条
        }
        if(size==0)
            size=20;
        
        if(StringUtils.isNotEmpty(sartIndex) && StringUtils.isNumeric(sartIndex)){
            start = Integer.valueOf(sartIndex);
        }
        /*ThumbData query =new ThumbData();
        query.setUserId(userId);
        query.setName(name);
        query.setStart(start);
        query.setPageSize(size);
        envelope = designPlanAutoRenderServiceImpl.getrenderPicByPage(query);*/
        DesignPlanRenderScene designPlanRenderScene = new DesignPlanRenderScene();
        if(loginUser.getUserType()!=null && loginUser.getUserType().intValue() == 1) {
        	designPlanRenderScene.setInternalUsers(true);
        }else {
        	designPlanRenderScene.setInternalUsers(false);
        }
        designPlanRenderScene.setUserId(userId);
        designPlanRenderScene.setPlanName(name);
        designPlanRenderScene.setStart(start);
        designPlanRenderScene.setLimit(size);
        envelope = designPlanAutoRenderServiceImpl.getrenderPicByPageV2(designPlanRenderScene,null);
        
        envelope.setMsgId(msgId);
        envelope.setMessage("success");
        return envelope;
        
    }

    @RequestMapping("/addRedisRenderStick")
	public void addRedisRenderStick(HttpServletRequest request,HttpServletResponse response,Integer taskId){
		
    	designPlanAutoRenderServiceImpl.getRedisStickList(taskId);
	}
}
