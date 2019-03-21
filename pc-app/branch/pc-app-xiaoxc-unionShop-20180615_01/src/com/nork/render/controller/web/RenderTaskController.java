package com.nork.render.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.base.controller.BaseController;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.AESUtil;
import com.nork.design.model.PlanMode;
import com.nork.render.model.RenderChannel;
import com.nork.render.model.RenderTask;
import com.nork.render.model.RenderTaskStates;
import com.nork.render.model.RenderTypeCode;
import com.nork.render.model.vo.RenderCheckVo;
import com.nork.render.model.vo.RenderPriceInfoVo;
import com.nork.render.service.RenderTaskService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.BaseMessageService;
import com.nork.system.service.SysDictionaryService;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.service.SysTaskService;

/**   
 * @Title: RenderTaskController.java 
 * @Package com.nork.render.controller
 * @Description:渲染-渲染任务Controller
 * @createAuthor yanghz 
 * @CreateDate 2017-01-15 17:46:41
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/web/render/renderTask")
public class RenderTaskController extends BaseController{
	private static Logger logger = Logger.getLogger(RenderTaskController.class);
	@Autowired
	private RenderTaskService renderTaskService;
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private SysTaskService sysTaskService;
	@Autowired
	private BaseMessageService baseMessageService;

	/**
	 *
	 * @Description: 渲染机渲染失败回调修改任务状态
	 * @return
	 * @author yanghz
	 */
	@RequestMapping(value = "/renderTaskFaildNotice")
	@ResponseBody
	public Object renderTaskFaildNotice(HttpServletRequest request){
		String taskAesID=request.getParameter("taskID");
		Map<String,String> resMap=new HashMap<String,String>();
		SysTask sysTask = null;
		if (taskAesID == null){
			resMap.put("state","2");
			resMap.put("data", "params [taskID] is null!");
			return resMap;
		}

		/*解密*/
		String taskID = "";
		try {
			taskID = AESUtil.decrypt(taskAesID, "");
		} catch (Exception e) {
			//解密失败
			logger.error("decode taskAesID : " + taskAesID + " error, ", e);
			resMap.put("state", "3");
			resMap.put("data", "decrypt params [taskID]: "+ taskID +"is error!");
			return resMap;
		}
		if(!StringUtils.isNumeric(taskID)){
			resMap.put("state", "3");
			resMap.put("data", "decrypt params [taskID]: "+ taskID +"is fail!");
			return resMap;

		}

		//检查改任务是否存在
		sysTask=sysTaskService.get(Integer.parseInt(taskID));
		if (sysTask == null){
			resMap.put("state", "4");
			resMap.put("data", "not found 'taskID'："+taskID);
			return resMap;
		}else if(SysTaskStatus.END_OF_EXECUTION == sysTask.getState() || SysTaskStatus.END_OF_EXCEPTION == sysTask.getState() || SysTaskStatus.END_OF_EXCEPTION_RENDERFAILD == sysTask.getState()) {
			resMap.put("state", "1");
			resMap.put("data", "The task [taskId]="+sysTask.getId()+" is already in an abnormal state or complete state="+sysTask.getState());
			return resMap;
		}else {
			RenderTask byTaskId = renderTaskService.getByTaskId(Integer.parseInt(taskID));
			if(byTaskId == null){
				resMap.put("state", "5");
				resMap.put("data", "Table [render_task] not found the taskId："+taskID);
				logger.error("Table [render_task] not found the taskId："+taskID);
				return resMap;
			}
			//修改任务状态
			SysTask sysTaskNew = new SysTask();
			sysTaskNew.setId(Integer.parseInt(taskID));
			sysTaskNew.setState(SysTaskStatus.END_OF_EXCEPTION_RENDERFAILD);
			sysTaskNew.setGmtModified(new Date());
			sysTaskNew.setRemark("渲染机渲染失败！");
			sysTaskService.update(sysTaskNew);

			RenderTask renderTaskNew = new RenderTask();
			renderTaskNew.setId(byTaskId.getId());
			renderTaskNew.setStatus(RenderTaskStates.END_OF_EXCEPTION_RENDERFAILD);//渲染失败
			renderTaskNew.setRemark("渲染机渲染失败！");
			renderTaskNew.setGmtModified(new Date());
			renderTaskService.update(renderTaskNew);
		}
		/*消息推送*/
		String msg = "渲染机渲染失败！";
		Integer messageid = baseMessageService.sendRenderMessage(sysTask,1,msg);
		//渲染失败退款和预警邮件
		renderTaskService.renderRefund(sysTask, msg);
		if(messageid.intValue() <= 0){
			logger.error("消息发送异常..."+",taskId =" + sysTask.getId());
			resMap.put("state", "2");
			resMap.put("data", "The render taskId="+taskID+" of state modified success but message send faild!");
			return resMap;
		}
		resMap.put("state", "1");
		resMap.put("data", "success:The render taskId="+taskID+" of state modified success. state=" + SysTaskStatus.END_OF_EXCEPTION_RENDERFAILD);
		return  resMap;
	}

	/**
	 * 
	* @Description: 获取支付通道信息（支付模式及对应价格）
	* @param msgId
	* @param type
	* @return     
	* @author yanghz
	 */
	@RequestMapping(value = "/getRenderChannelInfo_old")
	@ResponseBody
	public Object receiveTaskHostRenderStart(String msgId,String type){
		if( StringUtils.isEmpty(msgId) ){
			return new ResponseEnvelope<>(false, "缺少参数msgId", msgId);
		}
		if( StringUtils.isEmpty(type) ){
			return new ResponseEnvelope<>(false, "缺少参数type", msgId);
		}
		
		List<RenderChannel> renderChList = new ArrayList<RenderChannel>();
		List<PlanMode> priceListOfFast = new ArrayList<PlanMode>();
		List<PlanMode> priceListOfDelay = new ArrayList<PlanMode>();
		
		
		List<SysDictionary> sysDictionaryList=sysDictionaryService.findAllByType(type);
		if(sysDictionaryList == null || sysDictionaryList.size() < 1){
			logger.error("数据字典未查到type="+type+"记录！");
		}else{
			for(SysDictionary sysDictionary:sysDictionaryList){
//				renderChList.add(e)
				if(sysDictionary.getValuekey() != null){
					SysDictionary findOneByTypeAndValueKey = sysDictionaryService.findOneByTypeAndValueKey(type, sysDictionary.getValuekey());
					List<SysDictionary> findAllByType = sysDictionaryService.findAllByType(findOneByTypeAndValueKey.getValuekey());
					if(findAllByType != null && findAllByType.size() > 0){
						for(SysDictionary  temp: findAllByType){
							if(temp.getType() != null){
								if(RenderTypeCode.render_CHANNER_PIC_OF_FAST.equals(temp.getType())){
									//金额以分为单位，app显示以元为单位
									Double money = 0.0;
									if( StringUtils.isNoneBlank(temp.getAtt1()) ){
										money = Double.valueOf(temp.getAtt1());
									}
									priceListOfFast.add(new PlanMode(temp.getValue()+"", money, temp.getName(), temp.getType()));
								}else if(RenderTypeCode.RENDER_CHANNE_PIC_OF_DELAY.equals(temp.getType())){
									//金额以分为单位，app显示以元为单位
									Double money = 0.0;
									if( StringUtils.isNoneBlank(temp.getAtt1()) ){
										money = Double.valueOf(temp.getAtt1());
									}
									priceListOfDelay.add(new PlanMode(temp.getValue()+"", money, temp.getName(), temp.getType()));
								}else if(RenderTypeCode.RENDER_CHANNE_PANOR_OF_FAST.equals(temp.getType())){
									//金额以分为单位，app显示以元为单位
									Double money = 0.0;
									if( StringUtils.isNoneBlank(temp.getAtt1()) ){
										money = Double.valueOf(temp.getAtt1());
									}
									priceListOfFast.add(new PlanMode(temp.getValue()+"", money, temp.getName(), temp.getType()));
								}else if(RenderTypeCode.RENDER_CHANNE_PANOR_OF_DELAY.equals(temp.getType())){
									//金额以分为单位，app显示以元为单位
									Double money = 0.0;
									if( StringUtils.isNoneBlank(temp.getAtt1()) ){
										money = Double.valueOf(temp.getAtt1());
									}
									priceListOfDelay.add(new PlanMode(temp.getValue()+"", money, temp.getName(), temp.getType()));
								}
							}else{
								logger.error("数据字典中id="+temp.getId()+"记录缺少type 为null");
							}
						}
					}
				}
				if(RenderTypeCode.render_CHANNER_PIC_OF_FAST.equals(sysDictionary.getValuekey())){//快速照片
					renderChList.add(new RenderChannel(type, RenderTypeCode.RENDER_CHANNEL_OF_FAST, true, "渲染质量好，速度快，稍微等候即可出效果图", priceListOfFast));
				}else if(RenderTypeCode.RENDER_CHANNE_PIC_OF_DELAY.equals(sysDictionary.getValuekey())){//普通照片
					renderChList.add(new RenderChannel(type, RenderTypeCode.RENDER_CHANNEL_OF_DELAY, false, "保证在24小时内出效果图，渲染质量也是杠杠的", priceListOfDelay));
				}else if(RenderTypeCode.RENDER_CHANNE_PANOR_OF_FAST.equals(sysDictionary.getValuekey())){//快速全景
					renderChList.add(new RenderChannel(type, RenderTypeCode.RENDER_CHANNEL_OF_FAST, true, "渲染质量好，速度快，稍微等候即可出效果图", priceListOfFast));
				}else if(RenderTypeCode.RENDER_CHANNE_PANOR_OF_DELAY.equals(sysDictionary.getValuekey())){//普通全景
					renderChList.add(new RenderChannel(type, RenderTypeCode.RENDER_CHANNEL_OF_DELAY, false, "保证在24小时内出效果图，渲染质量也是杠杠的", priceListOfDelay));
				}
			}
		}
		//根据渲染类型查询对应通道的价格列表
		
		return new ResponseEnvelope<>(renderChList.size(), renderChList, msgId);
	}
	
	/**
	 * 
	* @Description: 5.6版本获取支付通道信息（支付模式及对应价格）
	* @param msgId
	* @param type
	* @return     
	* @author yanghz
	 */
	@RequestMapping(value = "/getRenderChannelInfo")
	@ResponseBody
	public Object getRenderChannelInfo(String msgId,Integer renderingType,String type){
		if( StringUtils.isEmpty(msgId) ){
			return new ResponseEnvelope<>(false, "缺少参数msgId", msgId);
		}
		if( renderingType ==null ){
			return new ResponseEnvelope<>(false, "缺少参数renderingType", msgId);
		}else{
			
		}
		//查询渲染免费信息
		RenderCheckVo renderFreeTimeInfo = renderTaskService.renderFreeTimeInfo();
		if(renderFreeTimeInfo == null){
			logger.error("获取渲染价格接口getRenderChannelInfo：未查出免费时间信息");
		}else{
		}
		//根据渲染类型查询价格列表
		List<RenderPriceInfoVo> priceInfoList = renderTaskService.findPriceInfo(renderingType);
		
		if(priceInfoList == null || priceInfoList.size() < 1){
			logger.error("获取渲染价格接口getRenderChannelInfo：未从数据字典中查询到渲染价格信息");
			return new ResponseEnvelope<>(false, "获取价格信息失败", msgId, null, 0, priceInfoList);
		}else{
			renderFreeTimeInfo.setPriceList(priceInfoList);
			return new ResponseEnvelope<>(true, "", msgId, renderFreeTimeInfo, priceInfoList.size(), null);
		}
	}
}
