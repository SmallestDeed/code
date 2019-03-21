package com.nork.design.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.Constants;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.dao.DesignPlanMapper;
import com.nork.design.dao.DesignPlanRecommendedMapperV2;
import com.nork.design.dao.DesignPlanRenderSceneMapper;
import com.nork.design.dao.DesignTempletMapper;
import com.nork.design.model.AutoRenderTask;
import com.nork.design.model.AutoRenderTaskState;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.DesignPlanRecommended;
import com.nork.design.model.DesignPlanRenderScene;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.ResRenderPicQO;
import com.nork.design.model.ThumbData;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.home.dao.SpaceCommonMapper;
import com.nork.home.model.SpaceCommon;
import com.nork.mobile.service.MobilePayRenderService;
import com.nork.pay.metadata.BizType;
import com.nork.pay.metadata.FinanceType;
import com.nork.pay.metadata.PayState;
import com.nork.pay.metadata.ProductType;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUser;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysUserService;

@Service("designPlanAutoRenderService")
public class DesignPlanAutoRenderServiceImpl implements DesignPlanAutoRenderService {
	private static Logger logger = Logger.getLogger(DesignPlanAutoRenderServiceImpl.class);
	
	public static final String REDIS_TASK_LIST = "taskList"; 
	public static final String REDIS_TASK_REPLACE_LIST = "taskReplaceList"; 
	public static final String REDIS_RENDER_TASK_STICK = "renderTaskStick";
	public static final int AUTO_RENDER = 0; 
	public static final int USER_RENDER = 1; 
	@Autowired
	private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;
	@Autowired
	private SpaceCommonMapper spaceCommonMapper;

	@Autowired
	private DesignTempletMapper designTempletMapper;
	
	@Autowired
	private DesignPlanMapper designPlanMapper;
	
	@Autowired
	private DesignPlanRenderSceneMapper designPlanRenderSceneMapper;
	
	@Autowired
	private ResRenderPicMapper resRenderPicMapper;
	
	@Autowired
	private ResRenderPicService resRenderPicService;
	
	@Autowired
	private DesignPlanRecommendedMapperV2 designPlanRecommendedMapperV2;
	
	@Autowired
	private DesignPlanRecommendedServiceV2 designPlanRecommendedServiceV2;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PayOrderService payOrderService;
	@Autowired
	private MobilePayRenderService mobilePayRenderService;
	
	@Override
	public ResponseEnvelope<AutoRenderTask> getAutoRenderTaskList(Integer maxSize, String msgId,LoginUser loginUser) {
		List<AutoRenderTask> taskList = getRenderTasks(maxSize,loginUser);
		ResponseEnvelope<AutoRenderTask> result = new ResponseEnvelope<AutoRenderTask>(true, msgId, maxSize, taskList);
		return result;
	}
	@Override
	public ResponseEnvelope<AutoRenderTask> getTaskList(Integer maxSize, String msgId,LoginUser loginUser) {
		List<AutoRenderTask> taskList = findReplaceProductTaskList(maxSize,loginUser);
		ResponseEnvelope<AutoRenderTask> result = new ResponseEnvelope<AutoRenderTask>(true, msgId, maxSize, taskList);
		return result;
	}
	
	@Override
	public List<AutoRenderTask> getTaskList(AutoRenderTask autoRenderTask){
		return designPlanAutoRenderMapper.getTaskList(autoRenderTask);
	}
	
	/**
	 * 更新失败任务状态
	 */
	@Override
	public ResponseEnvelope<AutoRenderTaskState> updateAutoRenderTaskState(AutoRenderTaskState renderTask, String msgId) {
		String msg = "";
//		sysSaveState(renderTask, loginUser);
		 updateTaskState(renderTask);
		return new ResponseEnvelope<AutoRenderTaskState>(true, msg, msgId);
	}
	/**
	 * 通过方案mapping样板房
	 */
	@Override
	public List<AutoRenderTask> mappingForDesignTemplateByDesignPlan(DesignPlanRecommended designPlanRecommended,LoginUser loginUser) {
		List<DesignTemplet> designTemplets = null;
		//designPlan.setId(20532);
		Integer spaceCommonId = designPlanRecommended.getSpaceCommonId();
		//spaceCommonId = 1282;
		SpaceCommon spaceInfo = spaceCommonMapper.selectByPrimaryKey(spaceCommonId);
		String spaceCode = null;
		if (spaceInfo != null) {
			String spaceAreas = spaceInfo.getSpaceAreas();
			Integer spaceFunctionId = spaceInfo.getSpaceFunctionId();
			spaceCode = spaceInfo.getSpaceCode();
			//designTemplets = designTempletMapper.geTempletsBySpaceInfo("5", 5,5);
			Integer createdOfTemplateId = null;
//			if(designPlanRecommended.getDesignSourceType()!=null && 
//					designPlanRecommended.getDesignSourceType().intValue() == 7){
//				createdOfTemplateId = designPlanRecommended.getDesignId();
//			}

			//如果适用面积不为空，则查适用该面积的样板房，没有默认当前空间面积
			if (StringUtils.isNotEmpty(designPlanRecommended.getApplySpaceAreas())) {
				designTemplets = designTempletMapper.getTempletsBySpaceAreasInfo(designPlanRecommended.getApplySpaceAreas(), spaceFunctionId, createdOfTemplateId);
			}else{
				designTemplets = designTempletMapper.geTempletsBySpaceInfo(spaceAreas, spaceFunctionId, createdOfTemplateId);
			}
		}

		List<AutoRenderTask> tasks = convertTaskList(designPlanRecommended.getId(), designTemplets,spaceCode,loginUser);
		List<AutoRenderTaskState> taskStateList = designPlanAutoRenderMapper.getTaskStateListByDesignPlanId(designPlanRecommended.getId());
		List<AutoRenderTask> rendingTasks = designPlanAutoRenderMapper.getRenderTaskListByPlanId(designPlanRecommended.getId());
		List<AutoRenderTask> toDelList = new ArrayList<AutoRenderTask>();
		if (tasks != null && tasks.size() > 0) {
			for (AutoRenderTask task : tasks) {
				for (AutoRenderTaskState taskState : taskStateList) {
					if (task.getPlanId().intValue() == taskState.getPlanId().intValue()
							&& task.getTemplateId().intValue() == taskState.getTemplateId().intValue()) {
						toDelList.add(task);
					}
				}
				for (AutoRenderTask rendingTask : rendingTasks) {
					if (task.getPlanId().intValue() == rendingTask.getPlanId().intValue()
							&& task.getTemplateId().intValue() == rendingTask.getTemplateId().intValue()) {
						toDelList.add(task);
					}
				}
			}
			tasks.removeAll(toDelList);
		}
		return tasks;
	}
	private List<AutoRenderTask> convertTaskList(Integer planId, List<DesignTemplet> templets,String spaceCode,LoginUser loginUser) {
		List<AutoRenderTask> tasks = new ArrayList<AutoRenderTask>();
		for (DesignTemplet template : templets) {
			AutoRenderTask task = new AutoRenderTask();
			task.setPlanId(planId);
			task.setTemplateId(template.getId());
			task.setSpaceCode(spaceCode);
			task.setTaskType(0);
			tasks.add(task);
		}
		return tasks;
	}
	
	/***
	 * 通过样板房mapping方案
	 */
	@Override
	public List<AutoRenderTask> mappingForDesignPlanByDesignTemplate(DesignTemplet DesignTemplet) {
		//DesignTemplet.setId(1177);
		Integer spaceCommonId = DesignTemplet.getSpaceCommonId();
		//spaceCommonId = 1282;
		SpaceCommon spaceInfo = spaceCommonMapper.selectByPrimaryKey(spaceCommonId);
		List<DesignPlan> designPlans = null;
		String spaceCode = null;
		if (spaceInfo != null) {
			spaceCode = spaceInfo.getSpaceCode();
			String spaceAreas = spaceInfo.getSpaceAreas();
			Integer spaceFunctionId = spaceInfo.getSpaceFunctionId();
			//designPlans = designPlanMapper.getPlansBySpaceInfo("5", 5);
			designPlans = designPlanMapper.getPlansBySpaceInfo(spaceAreas, spaceFunctionId);
		}
		List<AutoRenderTask> tasks = convertTaskLists(DesignTemplet.getId(), designPlans,spaceCode);
		List<AutoRenderTaskState> taskStateList = designPlanAutoRenderMapper.getTaskStateListByTemplateId(DesignTemplet.getId());
		List<AutoRenderTask> rendingTasks = designPlanAutoRenderMapper.getRenderTaskListBytemplateId(DesignTemplet.getId());
		List<AutoRenderTask> toDelList = new ArrayList<AutoRenderTask>();
		if (tasks != null && tasks.size() > 0) {
			for (AutoRenderTask task : tasks) {
				for (AutoRenderTaskState taskState : taskStateList) {
					if (task.getPlanId().intValue() == taskState.getPlanId().intValue()
							&& task.getTemplateId().intValue() == taskState.getTemplateId().intValue()) {
						toDelList.add(task);
					}
				}
				for (AutoRenderTask rendingTask : rendingTasks) {
					if (task.getPlanId().intValue() == rendingTask.getPlanId().intValue()
							&& task.getTemplateId().intValue() == rendingTask.getTemplateId().intValue()) {
						toDelList.add(task);
					}
				}
			}
			tasks.removeAll(toDelList);
		}
		return tasks;
	}
	
	
	private List<AutoRenderTask> convertTaskLists(Integer templateId, List<DesignPlan> paln,String spaceCode) {
		List<AutoRenderTask> tasks = new ArrayList<AutoRenderTask>();
		for (DesignPlan designPlan : paln) {
			AutoRenderTask task = new AutoRenderTask();
			task.setPlanId(designPlan.getId());
			task.setTemplateId(templateId);
			task.setSpaceCode(spaceCode);
			tasks.add(task);
		}
		return tasks;
	}
	
	
	@Override
	public void addTaskStateToDB(AutoRenderTaskState taskState) {
		designPlanAutoRenderMapper.addTaskStateToDB(taskState);

	}

	@Override
	public void addTaskStateToCache(AutoRenderTaskState taskState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRenderTaskByTaskId(Integer taskId) {
		designPlanAutoRenderMapper.deleteRenderTaskByTaskId(taskId);
		logger.info("删除渲染任务 taskId" + taskId);

	}
	/**
	 * 更新失败任务
	 */
	@Override
	public void updateTaskState(AutoRenderTaskState autoRenderTaskState) {
		logger.info("更新状态表方案ID"+ autoRenderTaskState.getPlanId()+"样板房ID"+autoRenderTaskState.getTemplateId());
		logger.info("更新状态表任务ID"+ autoRenderTaskState.getTaskId());
		Integer taskId = autoRenderTaskState.getTaskId();
		AutoRenderTask RenderTask = designPlanAutoRenderMapper.getRenderTaskById(taskId);
		autoRenderTaskState.setState(DesignPlanConstants.TASKSTATE.FAILUE.getValue());
		if (RenderTask.getRenderTypesStr().equals("1")) {
			autoRenderTaskState.setRenderPic(DesignPlanConstants.RENDER_FAIL);
		} else if (RenderTask.getRenderTypesStr().equals("2")) {
			autoRenderTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
		} else if (RenderTask.getRenderTypesStr().equals("3")) {
			autoRenderTaskState.setRenderN720(DesignPlanConstants.RENDER_FAIL);
		} else if (RenderTask.getRenderTypesStr().equals("4")) {
			autoRenderTaskState.setRenderVideo(DesignPlanConstants.RENDER_FAIL);
		}
		autoRenderTaskState.setState(0);
		AutoRenderTaskState taskState = designPlanAutoRenderMapper.getStateByTaskId(taskId);
		long startTime = taskState.getGmtCreate().getTime();
		long endTime = System.currentTimeMillis();
		long spendTimeLong = endTime -startTime;
		int secondTotal=(int) (spendTimeLong/1000);
		int min=secondTotal/60;
		int second=secondTotal%60;
		autoRenderTaskState.setRenderTimeConsuming(min+"分"+second+"秒");
		Integer result = designPlanAutoRenderMapper.updateTaskStateByTaskId(autoRenderTaskState);
		logger.info("更新"+result+"条失败记录....");
		if(taskState.getTaskSource().intValue() == 1) {
			Double totalFee = 0.00;
			String productType = "refunds";
			String productDesc = "";
			Integer userId = taskState.getOperationUserId();
			if("1".equals(taskState.getRenderTypesStr())) {
				totalFee = 0.00;
			}else if("2".equals(taskState.getRenderTypesStr())){
				totalFee = 500.00;
			}else if("3".equals(taskState.getRenderTypesStr())){
				totalFee = 700.00;
			}else if("4".equals(taskState.getRenderTypesStr())){
				totalFee = 900.00;
			}
			String flag = Utils.getValue("auto.task.test.flag", "false");
			if("true".equals(flag)) {
				//渲染测试模拟用户
				if(userId==null ||userId==0) {
				   userId = 36;
				}
			}
			//FIXME: 插入一条渲染失败款的记录到订单表pay_order，稍后修改到支付退款表pay_refund
			PayOrder payOrder = mobilePayRenderService.getOrder(totalFee.intValue(), "1", 202
					, productType, productDesc, "refunds", userId,taskId);
			logger.error("updateTaskState=>create the fail pay order = " + payOrder.getOrderNo() + "taskId=" + taskId);

			if(payOrder != null) {
				SysUser sysUser = sysUserService.get(userId);
				Double balanceAmount = sysUser.getBalanceAmount() + totalFee;
				Double consumAmount = sysUser.getConsumAmount() - totalFee;
				// 修改该用户的消费总金额和当前账户余额
				sysUser.setBalanceAmount(balanceAmount);
				sysUser.setConsumAmount(consumAmount);
				sysUserService.update(sysUser);
			}
			
		}
		
		

		
	}

	@Override
	public void updateTaskStateCach() {
		// TODO Auto-generated method stub

	}



	@Override
	public void addRenderTasksToDB(List<AutoRenderTask> tasks,LoginUser loginUser) {
		Integer result = designPlanAutoRenderMapper.batchInsertDataList(tasks);
	}

	// 给发布方案调用
	/**
	 * 
	 * @param designPlanRecommended 推荐方案对象
	 * @param designPlanId 方案ID design_plan
	 * @param loginUser
	 */
	@Override
	public void createTaskListByDesignPlan(DesignPlanRecommended designPlanRecommended, LoginUser loginUser,Integer designPlanId) {
		List<AutoRenderTask> taskList = mappingForDesignTemplateByDesignPlan(designPlanRecommended,loginUser);
		for (AutoRenderTask autoRenderTask : taskList) {
			sysSaveTask(autoRenderTask, loginUser,designPlanId);
		}
		if (taskList != null && taskList.size() > 0) {
			addRenderTasksToDB(taskList,loginUser);
		}
	}

	//给样板房上架调用
	@Override
	public void createTaskListByDesignTemplet(DesignTemplet template ,LoginUser loginUser, Integer designPlanId) {
		List<AutoRenderTask> taskList = mappingForDesignPlanByDesignTemplate(template);
		for (AutoRenderTask autoRenderTask : taskList) {
			sysSaveTask(autoRenderTask, loginUser, designPlanId);
		}
		if (taskList != null && taskList.size() > 0) {
			addRenderTasksToDB(taskList,loginUser);
		}
	}

	/**
	 * 从任务表获取任务插入状态表 并删除任务表任务
	 * @param maxSize
	 * @param loginUser
	 * @return
	 */
	private synchronized List<AutoRenderTask> getRenderTasks(Integer maxSize,LoginUser loginUser) {
		AutoRenderTask task = new AutoRenderTask();
		task.setMaxSize(maxSize);
		List<AutoRenderTask> taskList = designPlanAutoRenderMapper.getAutoRenderTaskList(task);
		if (taskList != null && taskList.size() > 0) {
			AutoRenderTask task2 = taskList.get(0);
			AutoRenderTaskState taskState = new AutoRenderTaskState();
			taskState.setPlanId(task2.getPlanId());
			taskState.setTemplateId(task2.getTemplateId());
			taskState.setDesignPlanId(task2.getDesignPlanId());
			taskState.setRenderPic(DesignPlanConstants.RENDERING);
			taskState.setRenderN720(DesignPlanConstants.RENDERING);
			taskState.setRender720(DesignPlanConstants.RENDERING);
			taskState.setRenderVideo(DesignPlanConstants.RENDERING);
			taskState.setTaskId(task2.getId());
			sysSaveState(taskState,loginUser);
			taskState.setGmtCreate(task2.getGmtCreate());
			taskState.setGmtModified(task2.getGmtModified());
			addTaskStateToDB(taskState);
			deleteRenderTaskByTaskId(task2.getId());
		}
		
		return taskList;
	}
	private synchronized List<AutoRenderTask> findReplaceProductTaskList(Integer maxSize,LoginUser loginUser) {
		AutoRenderTask task = new AutoRenderTask();
		task.setMaxSize(maxSize);
		List<AutoRenderTask> taskList = designPlanAutoRenderMapper.getReplaceTaskList(task);
		if (taskList != null && taskList.size() > 0) {
			AutoRenderTask task2 = taskList.get(0);
			Integer operationUserId = task2.getOperationUserId();
			Integer taskType = task2.getTaskType();
			String renderTypesStr = task2.getRenderTypesStr();
			AutoRenderTaskState taskState = new AutoRenderTaskState();
			taskState.setPlanId(task2.getPlanId());
			taskState.setTemplateId(task2.getTemplateId());
			taskState.setRenderPic(DesignPlanConstants.RENDERING);
			taskState.setRenderN720(DesignPlanConstants.RENDERING);
			taskState.setRender720(DesignPlanConstants.RENDERING);
			taskState.setRenderVideo(DesignPlanConstants.RENDERING);
			//添加三个字段 add by yangzhun
			taskState.setOperationUserId(operationUserId);
			taskState.setTaskType(taskType);
			taskState.setRenderTypesStr(renderTypesStr);
			sysSaveState(taskState,loginUser);
			taskState.setGmtCreate(task2.getGmtCreate());
			taskState.setGmtModified(task2.getGmtModified());
			addTaskStateToDB(taskState);//插入任务状态表
			deleteRenderTaskByTaskId(task2.getId());
		}
		
		return taskList;
	}

	/**
	 * 存储系统字段
	 */
	private void sysSaveTask(AutoRenderTask modelTask, LoginUser loginUser,
			Integer designPlanId) {
		if (modelTask != null) {
			if (modelTask.getId() == null) {
				modelTask.setGmtCreate(new Date());
				modelTask.setCreator(loginUser.getLoginName());
			}
			modelTask.setModifier(loginUser.getLoginName());
			modelTask.setDesignPlanId(designPlanId);
		}
		modelTask.setGmtModified(new Date());
		
	}
	/**
	 * 存储系统字段
	 */
	private void sysSaveState(AutoRenderTaskState modelState,LoginUser loginUser) {
		if (modelState != null) {
			if (modelState.getId() == null) {
//				modelState.setGmtCreate(new Date());
				modelState.setCreator(loginUser.getLoginName());
			}
			modelState.setModifier(loginUser.getLoginName());
		}
//		modelState.setGmtModified(new Date());
	}
	
    /* (non-Javadoc)    
     * @see com.nork.design.service.DesignPlanAutoRenderService#getrenderPicByPage(com.nork.design.model.ThumbData)    
     */
	@Override
	public ResponseEnvelope getrenderPicByPage(ThumbData thumbData) {
		ResponseEnvelope envelope = new ResponseEnvelope();
		int count = resRenderPicMapper.countRenderPicByPage(thumbData);
		if (count <= 0) {
			return envelope;
		}

		envelope.setTotalCount(count);

		if (thumbData.getStart() > count) {
			envelope.setDatalist(new ArrayList<>());
			return envelope;
		}
		List list = resRenderPicMapper.getRenderPicByPage(thumbData);
		if (list == null || list.size() <= 0) {
			envelope.setDatalist(list);
			return envelope;
		}

		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			ThumbData temp = (ThumbData) list.get(i);
			ids.add(temp.getCpId());
		}

		List<DesignPlanRecommended> recommendedList = designPlanRecommendedMapperV2
				.getStatusByIds(ids);
		if (recommendedList == null || recommendedList.size() <= 0) {
			envelope.setDatalist(list);
			return envelope;
		}

		for (int i = 0; i < list.size(); i++) {
			ThumbData temp = (ThumbData) list.get(i);
			for (int j = 0; j < recommendedList.size(); j++) {
				DesignPlanRecommended recommended = recommendedList.get(j);
				if (recommended.getPlanId().longValue() != temp.getCpId())
					continue;

				if (Constants.RECOMMENDED_TYPE_SHARE == recommended
						.getRecommendedType().intValue()) {
					temp.setPubSt(recommended.getIsRelease());
					continue;
				}
				if (Constants.RECOMMENDED_TYPE_ONE_KEY_PUB == recommended
						.getRecommendedType().intValue()) {
					temp.setOneKeySt(recommended.getIsRelease());
					continue;
				}
			}
		}
		envelope.setDatalist(list);
		return envelope;
	}
	
	
	
	/**
	 * 效果图列表
	 * @param designPlanRenderScene
	 * @return ResponseEnvelope
	 */
	public ResponseEnvelope<ThumbData> getrenderPicByPageV2(DesignPlanRenderScene designPlanRenderScene) {
		
		ResponseEnvelope<ThumbData> envelope = new ResponseEnvelope<ThumbData>();
		
		List<ThumbData>resList = new ArrayList<ThumbData>(); 
		List<DesignPlanRenderScene>list = null;
		int count = 0;
		boolean isInternalUsers = designPlanRenderScene.isInternalUsers();
		count = designPlanRenderSceneMapper.getVendorCountV2(designPlanRenderScene);
		if(count <= 0){
			envelope.setDatalist(resList);
			envelope.setTotalCount(0);
			return envelope;
		}
		list = designPlanRenderSceneMapper.getVendorListV2(designPlanRenderScene);
		if(list == null || list.size() <= 0){
			envelope.setDatalist(resList);
			envelope.setTotalCount(0);
			return envelope;
		}
		
		List<Long> ids = new ArrayList<Long>();
		for (int i = 0; i < list.size(); i++) {
			ids.add((long)list.get(i).getId());
		}
		List<DesignPlanRecommended> recommendedList = designPlanRecommendedMapperV2.getStatusByIds(ids);
		for (DesignPlanRenderScene scene : list) {
			ThumbData thumbData = new ThumbData();
			thumbData.setCpId(scene.getId());
			thumbData.setFailCause(scene.getFailCause());
			thumbData.setCheckUserName(scene.getCheckUserName());
			this.coverPicHandling(scene,thumbData,isInternalUsers);
			
			if(isInternalUsers) {
				thumbData.setSpacecode(scene.getSpaceCode());
			}
			if(recommendedList !=null  && recommendedList.size() > 0 ){
				for (DesignPlanRecommended recommended : recommendedList) {
					if(recommended.getPlanId().longValue() != scene.getId()){
						continue;
					}
					if (Constants.RECOMMENDED_TYPE_SHARE == recommended.getRecommendedType().intValue()) {
						thumbData.setPubSt(recommended.getIsRelease());
						continue;
					}
					if (Constants.RECOMMENDED_TYPE_ONE_KEY_PUB == recommended.getRecommendedType().intValue()) {
						thumbData.setOneKeySt(recommended.getIsRelease());
						continue;
					}
				}
			}
			resList.add(thumbData);
		}
		envelope.setDatalist(resList);
		envelope.setTotalCount(count);
		return envelope;
	}
	
	
	/**
	 * 图片封面处理
	 * @param scene
	 * @param thumbData
	 */
	public void coverPicHandling(DesignPlanRenderScene scene,ThumbData thumbData,boolean isInternalUsers){
		if(scene == null || thumbData == null){
			return;
		}
		if(scene.getCoverPicId() != null && scene.getCoverPicId().intValue() > 0){
			ResRenderPic coverPic = resRenderPicService.get(scene.getCoverPicId());
			if(coverPic != null){
				thumbData.setName(scene.getPlanName());
				this.dataFilling(coverPic,thumbData);
				return;
			}
		}
		List<ResRenderPic> picList = new ArrayList<>(); //查询该设计方案的全部渲染缩略图列表
		ResRenderPicQO resRenderPicQO = new ResRenderPicQO();
		resRenderPicQO.setCreateUserId(scene.getUserId());
		resRenderPicQO.setDesignSceneId(scene.getId());
		resRenderPicQO.setIsDeleted(0);
		List<String>fileKeyLists = new ArrayList<String>();
		fileKeyLists.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
		fileKeyLists.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
		resRenderPicQO.setFileKeys(fileKeyLists);
		picList = resRenderPicService.selectListByFileKeys(resRenderPicQO);
		if(picList !=null && picList.size() > 0){
			int id = 0;
			for (ResRenderPic resRenderPic : picList) {
				if(id > resRenderPic.getId().intValue()){
					continue;
				}
				if(isInternalUsers==false){
				    thumbData.setName(scene.getPlanName().split("\\(")[0]);
				}else{
				thumbData.setName(scene.getPlanName());
				}
				this.dataFilling(resRenderPic,thumbData);
				id = resRenderPic.getId();
			}
		}
	}
	
	
	/**
	 * 对thumbData 进行数据填充
	 * @param resRenderPic
	 * @param thumbData
	 */
	public void dataFilling(ResRenderPic resRenderPic,ThumbData thumbData){
		if(resRenderPic == null || thumbData == null){
			return;
		}
		thumbData.setThumbId(resRenderPic.getId());
		//thumbData.setName(resRenderPic.getDesignPlanName());
		thumbData.setPic(resRenderPic.getPicPath());
		thumbData.setType(resRenderPic.getSpaceType());
		thumbData.setArea(resRenderPic.getArea());
		thumbData.setPlanId(resRenderPic.getBusinessId());
		if(resRenderPic.getGmtCreate()!=null){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			thumbData.setCtime(simpleDateFormat.format(resRenderPic.getGmtCreate()));
		}
		if(RenderTypeCode.COMMON_PICTURE_LEVEL == resRenderPic.getRenderingType().intValue()) {
			thumbData.setRenderPic(true);
		}
		if(RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType().intValue()) {
			thumbData.setRender720(true);
		}
		if(RenderTypeCode.ROAM_720_LEVEL == resRenderPic.getRenderingType().intValue()) {
			thumbData.setRenderRoam(true);
		}
		if(RenderTypeCode.COMMON_VIDEO == resRenderPic.getRenderingType().intValue()) {
			thumbData.setRenderVideo(true);
		}
	}
	
	
	
	
	
	@Override
	public Object updateCoverPic(String picId, String planId, String msgId, String designPlanType) {
		if (StringUtils.isEmpty(picId) || StringUtils.isEmpty(planId) || StringUtils.isEmpty(msgId) ) {
			return new ResponseEnvelope<DesignPlan>(false, "缺少参数", msgId);
		}
		ResRenderPic resRenderPic = resRenderPicService.get(Integer.parseInt(picId));
		if (resRenderPic == null) {
			return new ResponseEnvelope<DesignPlan>(false, "图片不存在或被删除，请刷新页面", msgId);
		}
		if (resRenderPic.getRenderingType() == null) {
			return new ResponseEnvelope<DesignPlan>(false, "图片类型错误，只允许上传照片级渲染图", msgId);
		}
		if (resRenderPic.getRenderingType().intValue() == RenderTypeCode.COMMON_720_LEVEL
				|| resRenderPic.getRenderingType().intValue() == RenderTypeCode.HD_720_LEVEL) {
			return new ResponseEnvelope<DesignPlan>(false, "720渲染图不允许设为封面", msgId);
		}
		if (resRenderPic.getRenderingType().intValue() == RenderTypeCode.SCREEN_OF_PIC) {
			return new ResponseEnvelope<DesignPlan>(false, "高清渲染不允许设为封面", msgId);
		}
		if (resRenderPic.getRenderingType().intValue() != RenderTypeCode.COMMON_PICTURE_LEVEL
				&& resRenderPic.getRenderingType().intValue() != RenderTypeCode.HD_PICTURE_LEVEL
				&& resRenderPic.getRenderingType().intValue() != RenderTypeCode.ULTRA_HD_PICTURE_LEVEL) {
			return new ResponseEnvelope<DesignPlan>(false, "图片类型错误，只允许上传照片级渲染图", msgId);
		}
		DesignPlanRenderScene scene = designPlanRenderSceneMapper.get(Integer.parseInt(planId));
		if(scene ==null ){
			return new ResponseEnvelope<DesignPlan>(false, "该效果图被删除,请刷新页面", msgId);
		}
		scene = new DesignPlanRenderScene();
		scene.setCoverPicId(Integer.parseInt(picId));
		scene.setId(Integer.parseInt(planId));
		designPlanRenderSceneMapper.update(scene);
		return new ResponseEnvelope<DesignPlan>(true, "封面设置成功", msgId);
	}
	@Override
	public int add(AutoRenderTask autoRenderTask) {
		// TODO Auto-generated method stub
		designPlanAutoRenderMapper.createTask(autoRenderTask);
		return autoRenderTask.getId();
	}
	@Override
	public int addTask(AutoRenderTask autoRenderTask) {
		// TODO Auto-generated method stub
		designPlanAutoRenderMapper.insertSelective(autoRenderTask);
		return autoRenderTask.getId();
	}
	@Override
	public List<AutoRenderTask> getReplaceProductTask(Integer maxSize, LoginUser loginUser) {
		List<AutoRenderTask> taskList = findReplaceProductTaskList(maxSize,loginUser);
		for(AutoRenderTask task : taskList) {
			Integer planRecommendedId = task.getPlanId();
			DesignPlanRecommended designPlanRecommended = designPlanRecommendedServiceV2.get(planRecommendedId);
			task.setLivingId(designPlanRecommended.getLivingId());
			task.setHouseId(designPlanRecommended.getHouseId());
			task.setDesignPlanId(designPlanRecommended.getPlanId());
		}
		
		return taskList;
	}
	@Override
	public int addRedisLists(AutoRenderTask autoRenderTask) {
		Gson gson = new Gson();
		String json = gson.toJson(autoRenderTask);
		Long result = JedisUtils.listAdd(REDIS_TASK_LIST, json);
		logger.info("Redis list size ====>"+result);
		return result.intValue();
	}
	@Override
	public AutoRenderTask getRedisTaskList(Integer maxSize,LoginUser loginUser) throws UnknownHostException {
		Gson gson = new Gson();
		String jsonStr = null;
		List<String>  result = JedisUtils.getBrpopList(REDIS_TASK_LIST);
		if(result != null && result.size() > 0) {
			jsonStr = result.get(1);
		}
		AutoRenderTask task = gson.fromJson(jsonStr, AutoRenderTask.class);
		Integer taskId = null;
		InetAddress addr = InetAddress.getLocalHost();  
        String hostIp=addr.getHostAddress().toString(); 
        String hostName=addr.getHostName().toString(); 
      //如果该任务在任务状态表里已经存在，说明该任务已经执行过，则不返回改任务，直接删除该任务在Redis 队列
        if (task != null) {
			AutoRenderTaskState autoRenderTaskState = this.getStateByTaskId(task.getId());
			if (autoRenderTaskState !=null && autoRenderTaskState.getTaskId() >0) {
				return null;
			}
		}
		if (task != null) {
			taskId = task.getId();
			AutoRenderTask task2 = designPlanAutoRenderMapper.getRenderTaskById(taskId);
			logger.error("getRedisTaskList ===>"+task2.getId());
			if (task2 != null) {
				AutoRenderTaskState taskState = new AutoRenderTaskState();
				Integer operationUserId = task2.getOperationUserId();
				SysUser sysUser = sysUserService.get(operationUserId);
				LoginUser loginUser2 = sysUser.toLoginUser();
				taskState.setPlanId(task2.getPlanId());
				taskState.setTemplateId(task2.getTemplateId());
				taskState.setDesignPlanId(task2.getDesignPlanId());
				if (task2.getRenderTypesStr().equals("1")) {
					taskState.setRenderPic(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("2")) {
					taskState.setRender720(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("3")) {
					taskState.setRenderN720(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("4")) {
					taskState.setRenderVideo(DesignPlanConstants.RENDERING);
				}
				taskState.setTaskId(task2.getId());
				taskState.setOperationUserId(operationUserId);
				taskState.setTaskType(task2.getTaskType());
				taskState.setRenderTypesStr(task2.getRenderTypesStr());
				taskState.setGmtCreate(new Date());
				taskState.setGmtModified(new Date());
				taskState.setCreator(loginUser2.getName());
				taskState.setModifier(loginUser2.getName());
				taskState.setOrderNumber(task2.getOrderNumber());
				taskState.setDesignCode(task2.getDesignCode());
				taskState.setDesignName(task2.getDesignName());
				taskState.setTemplateCode(task2.getTemplateCode());
				taskState.setHostIp(hostIp);
				taskState.setHostName(hostName);
				taskState.setTaskSource(task2.getTaskSource());
				addTaskStateToDB(taskState);
				deleteRenderTaskByTaskId(task2.getId());
			}
		}
		return task;
	}
	
	@Override
	public int addRedisReplaceList(AutoRenderTask autoRenderTask) {
		Gson gson = new Gson();
		String json = gson.toJson(autoRenderTask);
		Long result = JedisUtils.listAdd(REDIS_TASK_REPLACE_LIST, json);
		logger.info("Redis list size ====>"+result);
		return result.intValue();
	}
	@Override
	public AutoRenderTask getRedisReplaceTaskList(Integer maxSize,LoginUser loginUser) throws UnknownHostException {
		Gson gson = new Gson();
		String jsonStr = null;
		InetAddress addr = InetAddress.getLocalHost();  
        String hostIp=addr.getHostAddress().toString(); 
        String hostName=addr.getHostName().toString(); 
		List<String>  result = JedisUtils.getBrpopList(REDIS_TASK_REPLACE_LIST);
		if(result != null && result.size() > 0) {
			jsonStr = result.get(1);
		}
		AutoRenderTask task = gson.fromJson(jsonStr, AutoRenderTask.class);
		Integer taskId = null;
		//如果该任务在任务状态表里已经存在，说明该任务已经执行过，则不返回改任务，直接删除该任务在Redis 队列
		if (task != null) {
			AutoRenderTaskState autoRenderTaskState = this.getStateByTaskId(task.getId());
			if (autoRenderTaskState !=null && autoRenderTaskState.getTaskId() >0) {
				return null;
			}
		}
		if (task != null) {
			taskId = task.getId();
			AutoRenderTask task2 = designPlanAutoRenderMapper.getRenderTaskById(taskId);
			logger.error("getRedisTaskList ===>"+task2.getId());
			if (task2 != null) {
				Integer operationUserId = task2.getOperationUserId();
				SysUser sysUser = sysUserService.get(operationUserId);
				LoginUser loginUser2 = sysUser.toLoginUser();
				Integer taskType = task2.getTaskType();
				String renderTypesStr = task2.getRenderTypesStr();
				AutoRenderTaskState taskState = new AutoRenderTaskState();
				taskState.setPlanId(task2.getPlanId());
				taskState.setTemplateId(task2.getTemplateId());
				if (task2.getRenderTypesStr().equals("1")) {
					taskState.setRenderPic(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("2")) {
					taskState.setRender720(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("3")) {
					taskState.setRenderN720(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("4")) {
					taskState.setRenderVideo(DesignPlanConstants.RENDERING);
				}
				taskState.setOperationUserId(operationUserId);
				taskState.setTaskType(taskType);
				taskState.setRenderTypesStr(renderTypesStr);
				taskState.setTaskId(task2.getId());
				taskState.setGmtCreate(new Date());
				taskState.setGmtModified(new Date());
				taskState.setCreator(loginUser2.getName());
				taskState.setModifier(loginUser2.getName());
				taskState.setOrderNumber(task2.getOrderNumber());
				taskState.setDesignCode(task2.getDesignCode());
				taskState.setDesignName(task2.getDesignName());
				taskState.setHostIp(hostIp);
				taskState.setHostName(hostName);
				taskState.setTaskSource(task2.getTaskSource());
				addTaskStateToDB(taskState);// 插入任务状态表
				deleteRenderTaskByTaskId(task2.getId());
			}
		}
		return task;
	}
	@Override
	public void getRedisStickList(Integer taskId) {
		AutoRenderTask  autoRenderTask = designPlanAutoRenderMapper.getRenderTaskById(taskId);
		Gson gson = new Gson();
		String json = gson.toJson(autoRenderTask);
		Long result = JedisUtils.listLAdd(REDIS_RENDER_TASK_STICK, json);
		logger.error("Redis list size ====>"+result);
		
	}
	@Override
	public AutoRenderTask getRedisStickTaskList(Integer maxSize,LoginUser loginUser) throws UnknownHostException {
		Gson gson = new Gson();
		String jsonStr = null;
		InetAddress addr = InetAddress.getLocalHost();  
        String hostIp=addr.getHostAddress().toString(); 
        String hostName=addr.getHostName().toString(); 
		List<String>  result = JedisUtils.getBrpopList(REDIS_RENDER_TASK_STICK);
		if(result != null && result.size() > 0) {
			jsonStr = result.get(1);
		}
		AutoRenderTask task = gson.fromJson(jsonStr, AutoRenderTask.class);
		Integer taskId = null;
//		JSONObject jsonobject = JSONObject.fromObject(jsonStr);
//		AutoRenderTask autoRenderTask= (AutoRenderTask)JSONObject.toBean(jsonobject,AutoRenderTask.class);
		if (task != null) {
			taskId = task.getId();
			AutoRenderTask task2 = designPlanAutoRenderMapper.getRenderTaskById(taskId);
			logger.error("getRedisTaskList ===>"+task.getId());
			if (task2 != null) {
				Integer operationUserId = task2.getOperationUserId();
				SysUser sysUser = sysUserService.get(operationUserId);
				LoginUser loginUser2 = sysUser.toLoginUser();
				Integer taskType = task2.getTaskType();
				String renderTypesStr = task2.getRenderTypesStr();
				AutoRenderTaskState taskState = new AutoRenderTaskState();
				taskState.setPlanId(task2.getPlanId());
				taskState.setTemplateId(task2.getTemplateId());
				if (task2.getRenderTypesStr().equals("1")) {
					taskState.setRenderPic(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("2")) {
					taskState.setRender720(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("3")) {
					taskState.setRenderN720(DesignPlanConstants.RENDERING);
				} else if (task2.getRenderTypesStr().equals("4")) {
					taskState.setRenderVideo(DesignPlanConstants.RENDERING);
				}
				taskState.setOperationUserId(operationUserId);
				taskState.setTaskType(taskType);
				taskState.setRenderTypesStr(renderTypesStr);
				taskState.setTaskId(task2.getId());
				taskState.setGmtCreate(task2.getGmtCreate());
				taskState.setGmtModified(task2.getGmtModified());
				taskState.setCreator(loginUser2.getName());
				taskState.setModifier(loginUser2.getName());
				taskState.setOrderNumber(task2.getOrderNumber());
				taskState.setDesignCode(task2.getDesignCode());
				if (task2.getTemplateCode() != null) {
					taskState.setTemplateCode(task2.getTemplateCode());
				}
				taskState.setDesignName(task2.getDesignName());
				taskState.setHostIp(hostIp);
				taskState.setHostName(hostName);
				taskState.setTaskSource(task2.getTaskSource());
				addTaskStateToDB(taskState);// 插入任务状态表
				deleteRenderTaskByTaskId(task2.getId());
			}
		}
		return task;
	}
	@Override
	public AutoRenderTaskState getStateByTaskId(Integer taskId) {
		logger.error("AAAAAAutoRenderTaskState--------getStateByTaskId----------taskId----------" + taskId);
		AutoRenderTaskState autoRenderTaskState = null;
		try {
			autoRenderTaskState = designPlanAutoRenderMapper.getStateByTaskId(taskId);
		} catch (Exception e) {
			logger.error("AutoRenderTaskState--------getStateByTaskId----------taskId----------" + taskId + "-----Exception-----" + e);
		}
		return autoRenderTaskState;
	}
}
