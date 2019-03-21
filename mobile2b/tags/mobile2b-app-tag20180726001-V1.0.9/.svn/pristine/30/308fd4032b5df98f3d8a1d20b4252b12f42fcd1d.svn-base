package com.nork.task.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nork.base.definedvalue.ConstValue.DEFAULT_IS_DELETED;
import com.nork.common.model.LoginUser;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.ImageUtils;
import com.nork.common.util.Utils;
import com.nork.design.controller.web.WebDesignPlanController;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.RenderConfig;
import com.nork.design.service.DesignPlanService;
import com.nork.pay.metadata.PayState;
import com.nork.pay.metadata.PayType;
import com.nork.pay.service.PayOrderService;
import com.nork.render.model.RenderTask;
import com.nork.render.model.RenderTypeCode;
import com.nork.render.service.RenderTaskService;
import com.nork.system.model.ResRenderPic;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysDictionaryService;
import com.nork.task.dao.SysTaskMapper;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskResult;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.model.search.SysTaskSearch;
import com.nork.task.service.SysTaskService;

/**   
 * @Title: SysTaskServiceImpl.java 
 * @Package com.nork.task.service.impl
 * @Description:任务-系统任务表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-11-18 10:51:21
 * @version V1.0   
 */
@Service("sysTaskService")
public class SysTaskServiceImpl implements SysTaskService {
	private static Logger logger = Logger.getLogger(SysTaskServiceImpl.class);
	
	@Autowired
	private SysTaskMapper sysTaskMapper;
	
	@Autowired
	private RenderTaskService renderTaskService;
	
	@Autowired
	private ResPicService resPicService;

	@Autowired
	private DesignPlanService designPlanService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private PayOrderService payOrderService;
	private final static ResourceBundle render = /*ResourceBundle.getBundle("render");*/
			WebDesignPlanController.render;
	//任务服务器
	private final static String TASK_SERVER_HOSTS = /*render.getString("taskServerHosts");*/
			WebDesignPlanController.TASK_SERVER_HOSTS;
	private static String[] taskServers = /*null;*/
			WebDesignPlanController.taskServers;
	private static Integer taskAllocationIndex = /*0;*/
			WebDesignPlanController.taskAllocationIndex;
	//渲染服务器
	private final static String RENDER_SERVER_HOSTS = /*render.getString("renderServerHosts");*/
			WebDesignPlanController.RENDER_SERVER_HOSTS;
	private static String[] renderServers = /*null;*/
			WebDesignPlanController.renderServers;
	private static String[] renderStorageType = /*null;*/
			WebDesignPlanController.renderStorageType;
	private static Integer renderAllocationIndex = /*0;*/
			WebDesignPlanController.renderAllocationIndex;
	private static Map<String,RenderConfig> renderServersMap = /*new HashMap<>();*/
			WebDesignPlanController.renderServersMap;
	private final static boolean JOB_LOG_FLAG = /*"true".equals(Utils.getValue("jobLog","false"))?true:false;*/
			WebDesignPlanController.JOB_LOG_FLAG;
	/**
	 * 新增数据
	 *
	 * @param sysTask
	 * @return  int 
	 */
	@Override
	public int add(SysTask sysTask) {
		sysTaskMapper.insertSelective(sysTask);
		return sysTask.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysTask
	 * @return  int 
	 */
	@Override
	public int update(SysTask sysTask) {
		return sysTaskMapper
				.updateByPrimaryKeySelective(sysTask);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysTaskMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysTask 
	 */
	@Override
	public SysTask get(Integer id) {
		return sysTaskMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysTask
	 * @return   List<SysTask>
	 */
	@Override
	public List<SysTask> getList(SysTask sysTask) {
	    return sysTaskMapper.selectList(sysTask);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysTaskSearch
	 * @return   int
	 */
	@Override
	public int getCount(SysTaskSearch sysTaskSearch){
		return  sysTaskMapper.selectCount(sysTaskSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysTaskSearch
	 * @return   List<SysTask>
	 */
	@Override
	public List<SysTask> getPaginatedList(
			SysTaskSearch sysTaskSearch) {
		return sysTaskMapper.selectPaginatedList(sysTaskSearch);
	}

	@Override
	public List<SysTask> getRenderPlanList(SysTask sysTask) {
		// TODO Auto-generated method stub
		return sysTaskMapper.findRenderPlanId(sysTask);
	}

	@Override
	public Integer getRenderBatchSum() {
		return sysTaskMapper.getRenderBatchSum();
	}

	/**
	 * 
	* @Title: getMaxPriority 
	* @Description: 获得优先级最高的一条任务
	* @param 
	* @return SysTask    返回类型 
	* @throws
	 */
	public SysTask getMaxPriorityTask() {
		
		
		// louxinhua 2017-05-02 modify
		SysTask task = sysTaskMapper.getMaxPriorityTask();
		if ( task == null || task.getId() < 1 ) {
			return null;
		}
		else {
			// 根据 id 去获取render_task 表里面有没有不是 deleted的数据
			Integer taskID = task.getId();
			List<RenderTask> list = renderTaskService.getByTaskId(taskID, DEFAULT_IS_DELETED.is_not_deleted);
			if ( list != null && list.size() > 0 ) { 
				// 有问题了，如果 systask 里面 state 为0的话， rendertask 里面不应该会有 DEFAULT_IS_DELETED.is_not_deleted 的记录
				logger.error("taskID:"+taskID + ", has " + list.size() + " rendertask record, and not deleted");
				// 需要更新 task 记录的状态， 这里去更新是为了解决一些异常，但是去更新会引起其它的一些问题。
				// 更新单条记录，不启用 spring 事务。前提是数据库没关闭单条事务。
				SysTask tempSt = new SysTask();
				tempSt.setId(taskID);
				tempSt.setState(SysTaskStatus.WAITING_EXECUTE);
				this.sysTaskMapper.updateByPrimaryKeySelective(tempSt);
				task = null;
			}
			else {
				// 说明正常，对，本座写必须有 else。。。
			}
		}
		
		
		return task;
	}

	/**
	 * 
	* @Description:获取某台渲染服务器上当前分配的任务数量
	* @param sysTaskId
	* @return     
	* @return Integer    返回类型 
	* @throws
	 */
	@Override
	public Integer getCountOfRender(Integer renderType, Integer sysTaskId) {
		Map<String,Integer> map =new HashMap<String,Integer>();
		RenderTask renderTask = renderTaskService.getByTaskId(sysTaskId);
		if(renderTask == null){
			logger.error("获取渲染机上分配的任务数量出错：sys_task表中任务id为"+sysTaskId+"的任务没有在render_task表中");
		}else{
			if(renderTask.getHostId() != null && renderType != null){
				map.put("renderType", renderType);
				map.put("hostId", renderTask.getHostId());
			}else{
				logger.error("renderType或者hostId参数错误:为空！");
			}
		}
		return sysTaskMapper.getCountOfRender(map);
	}

	/**
	 * 
	* @Description: 计算渲染任务等待时间
	* @param taskList
	* @return     
	* @return SysTaskResult    返回类型 
	* @throws
	 */
	@Override
	public List<SysTaskResult> calculateTaskRenderTime(List<SysTask> taskList) {
		List<SysTaskResult> list = new ArrayList<SysTaskResult>();
		//渲染完成估计剩余时间
		int singleWaitingTime = 0;
		//当前任务渲染估计时间 
		int singleExpectTaskTime = 0;
		
		//取到每种渲染类型的单条渲染估计时间
		int commonPicExpectedTime=Utils.getIntValue(Utils.getPropertyName("render", "app.render.common.pic.taskTime", "3"));//照片级普通 
		int hdPicExpectedTime=Utils.getIntValue(Utils.getPropertyName("render", "app.render.HDpic.taskTime", "4"));//照片级高清 
		int ultraHDPicExpectedTime=Utils.getIntValue(Utils.getPropertyName("render", "app.render.ultra.HDPic.taskTime", "5"));//照片级超高清
		int common720ExpectedTime=Utils.getIntValue(Utils.getPropertyName("render", "app.render.common720.taskTime", "15"));//720度普通
		int hd720ExpectedTime=Utils.getIntValue(Utils.getPropertyName("render", "app.render.HD720.taskTime", "20"));//720度高清
		
		Date now = new Date();
		for(SysTask task : taskList){//正在渲染中的任务
			SysTaskResult taskResult = new SysTaskResult();
			taskResult.setTaskId(task.getId());
			taskResult.setTaskState(task.getState());
			//取图
			if( task.getPicId() != null && task.getPicId().intValue() > 0){	
				ResRenderPic resPic = resRenderPicService.get(task.getPicId());
				taskResult.setPicPath(resPic==null?"":resPic.getPicPath());
			}else{
				logger.error("获取任务等待时间：Id为"+task.getId()+"的任务picId参数为空");
			}
			Integer renderingType=null;
			if(task.getRenderType() != null){
				renderingType = task.getRenderType();
				taskResult.setRenderingType(task.getRenderType());
			}
			
			//取到当前任务渲染估计时间
			if(renderingType == null){
				logger.error("Id为"+task.getId()+"的渲染任务renderingType参数为空！");
			}else{
				if(RenderTypeCode.COMMON_PICTURE_LEVEL == renderingType){ //照片级
					singleExpectTaskTime=commonPicExpectedTime;
				}else if(RenderTypeCode.HD_PICTURE_LEVEL == renderingType){//720
					singleExpectTaskTime=hdPicExpectedTime;
				}else if(RenderTypeCode.ULTRA_HD_PICTURE_LEVEL == renderingType){
					singleExpectTaskTime=ultraHDPicExpectedTime;
				}else if(RenderTypeCode.COMMON_720_LEVEL == renderingType){
					singleExpectTaskTime=common720ExpectedTime;
				}else if(RenderTypeCode.HD_720_LEVEL == renderingType){
					singleExpectTaskTime=hd720ExpectedTime;
				}else{
					logger.info("Id为"+task.getId()+"的任务renderingType参数"+renderingType+"没有匹配的类型");
				}
			}
			
			if(task.getState() == SysTaskStatus.EXECUTING){//正在渲染中的任务
				RenderTask byTaskId = renderTaskService.getByTaskId(task.getId());
				if(byTaskId != null && byTaskId.getGmtRenderStart() == null){
					logger.error("任务Id为"+task.getId()+"的任务没有在render_task表中记录渲染开始时间");
				}else{
					//当前正在渲染中任务的等待时间=预估时间-（当前时间-渲染开始时间）
					singleWaitingTime =singleExpectTaskTime - (int)(now.getTime() - byTaskId.getGmtRenderStart().getTime())/1000/60;
				}
				taskResult.setTaskTime(singleWaitingTime);
				list.add(taskResult);
			}
		}
		/** 已经在渲染中的任务耗时最小的一条所耗时 **/
		List<Integer> taskTimeList = new ArrayList<Integer>();
		Integer rederingMin = 0;
		if(list.size() > 0){
			for(SysTaskResult resultList : list){
				taskTimeList.add(resultList.getTaskTime());
			}
		}
		if(taskTimeList.size() > 0){
			rederingMin = Collections.min(taskTimeList);
		}
		
		for(SysTask task : taskList){//等待渲染
			SysTaskResult taskResult = new SysTaskResult();
			//取图
			if( task.getPicId() != null && task.getPicId().intValue() > 0){	
				ResRenderPic resPic = resRenderPicService.get(task.getPicId());
				taskResult.setPicPath(resPic==null?"":resPic.getPicPath());
			}else{
				logger.error("获取任务等待时间：Id为"+task.getId()+"的任务picId参数为空");
			}
			Integer renderingType=null;
			if(task.getRenderType() != null){
				renderingType = task.getRenderType();
				taskResult.setRenderingType(task.getRenderType());
			}
			taskResult.setTaskId(task.getId());
			taskResult.setTaskState(task.getState());
			
			if(task.getState() == SysTaskStatus.WAITING_EXECUTE){
				//当前任务前面   照片级普通  等待条数
				Integer watingTaskCount = this.calculateWatingTaskCount(RenderTypeCode.COMMON_PICTURE_LEVEL,task);
				//当前任务前面  照片级高清   等待条数
				Integer watingTaskCount1 = this.calculateWatingTaskCount(RenderTypeCode.HD_PICTURE_LEVEL,task);
				//当前任务前面  照片级超高清    等待条数
				Integer watingTaskCount2 = this.calculateWatingTaskCount(RenderTypeCode.ULTRA_HD_PICTURE_LEVEL,task);
				//当前任务前面	720度普通	等待条数
				Integer watingTaskCount3 = this.calculateWatingTaskCount(RenderTypeCode.COMMON_720_LEVEL,task);
				//当前任务前面   720度高清     等待条数
				Integer watingTaskCount4 = this.calculateWatingTaskCount(RenderTypeCode.HD_720_LEVEL,task);
				
				//取到当前任务渲染估计时间
				if(renderingType == null){
					logger.error("Id为"+task.getId()+"的渲染任务renderingType参数为空！");
				}else{
					if(RenderTypeCode.COMMON_PICTURE_LEVEL == renderingType){ //照片级
						singleExpectTaskTime=commonPicExpectedTime;
					}else if(RenderTypeCode.HD_PICTURE_LEVEL == renderingType){//720
						singleExpectTaskTime=hdPicExpectedTime;
					}else if(RenderTypeCode.ULTRA_HD_PICTURE_LEVEL == renderingType){
						singleExpectTaskTime=ultraHDPicExpectedTime;
					}else if(RenderTypeCode.COMMON_720_LEVEL == renderingType){
						singleExpectTaskTime=common720ExpectedTime;
					}else if(RenderTypeCode.HD_720_LEVEL == renderingType){
						singleExpectTaskTime=hd720ExpectedTime;
					}else{
						logger.info("Id为"+task.getId()+"的任务renderingType参数"+renderingType+"没有匹配的类型");
					}
				}
				int queueTime = 0;
				queueTime = watingTaskCount*commonPicExpectedTime + watingTaskCount1*hdPicExpectedTime + watingTaskCount2*ultraHDPicExpectedTime + watingTaskCount3*common720ExpectedTime + watingTaskCount4*hd720ExpectedTime;
				//当前等待渲染中的任务等待时间=排队时间  +自己渲染需要时间+ 正在渲染中剩余时间最小一条
				singleWaitingTime = queueTime + singleExpectTaskTime + rederingMin;
				taskResult.setTaskTime(singleWaitingTime);
				list.add(taskResult);
			}else if(task.getState() == SysTaskStatus.WAIT_EXECUTE){//未分配
				taskResult.setTaskTime(-1);
				list.add(taskResult);
			}else if(task.getState() == SysTaskStatus.CREATING_TASK){//准备参数状态的任务
				taskResult.setTaskTime(-1);
				list.add(taskResult);
			}else{
				
			}
		}
		return list;
	}

	/**
	 * 
	* @Description: 等待渲染中的任务计算排队个数
	* @param renderType
	* @param sysTask
	* @return     
	* @return Integer    返回类型 
	* @throws
	 */
	private Integer calculateWatingTaskCount(Integer renderType, SysTask sysTask) {
		RenderTask renderTask = renderTaskService.getByTaskId(sysTask.getId());
		Map<String, Object> map = new HashMap<String, Object>();
		if(renderTask == null){
			logger.error("获取任务等待时间接口：获取渲染机上分配的任务数量出错：sys_task表中任务id为"+sysTask.getId()+"的任务没有在render_task表中");
		}else{
			map.put("renderType", renderType);
			map.put("state", sysTask.getState());
			if(renderTask.getHostId() != null){
				map.put("hostId", renderTask.getHostId());
			}else{
				logger.error("Id为"+sysTask.getId()+"+的渲染任务没有分配渲染主机");
			}
			map.put("priority", sysTask.getPriority());
			map.put("gmtCreate", sysTask.getGmtCreate());
			return sysTaskMapper.calculateWatingTaskCount(map);
		}
		return 0;		
	}

	@Override
	public int getSysTaskState(int businessId) {
		int result = sysTaskMapper.getSysTaskState(businessId);
		return result;
	}

	/**
	 * 	创建一个未付款状态的渲染任务
	 * @author huangsongbo
	 */
	public SysTask createNonPaymentTask(Integer isTurnOn, Integer planId, String params, Integer priority,
			Integer viewPoint, Integer scene, Integer renderingType,Integer renderChannel,String priceInfo,
			HttpServletRequest request) {
		LoginUser loginUser_ = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
		String paramString = String.format(
				"保存渲染任务App端传来的参数：isTurnOn:%d, planId:%s, priority:%d, viewPoint:%d, scene:%d, renderingType:%d",
				isTurnOn, planId, priority, viewPoint, scene, renderingType);
		logger.info(paramString);
		SysTask sysTask = new SysTask();
		/** 验证样板房、空间和产品是否上架 **/
		DesignPlan designPlan = designPlanService.get(planId);
		if(designPlan == null){
			sysTask.setRemark("设计方案没找到");
			return sysTask;
		}
		String isShelvesValidate = Utils.getPropertyName("render", "app.render.isShelvesValidate", "0");// 验证所属空间和样板房是否上架,默认不验证（0）
		if ("1".equals(isShelvesValidate)) {
			JSONObject jsonObject = designPlanService.shelvesValidate(designPlan,loginUser_);
			if (jsonObject != null && !jsonObject.getBoolean("success")) {
				sysTask.setRemark(jsonObject.getString("message"));
				return sysTask;
			}
		}
		/** 保存高清截图信息 */
		String taskConfig = params;
		Integer picId = null;
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			String filePicName = null;
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				filePicName = mf.getOriginalFilename();
				logger.info("文件上传名称：" + filePicName);
				Map map = new HashMap();
				map.put(FileUploadUtils.UPLOADPATHTKEY,"design.designPlan.render.upload.path");
				boolean flag = false;
				map.put("code","");
				map.put(FileUploadUtils.FILE, mf);
				flag = FileUploadUtils.saveFile(map);
				// 720度渲染生成水印图 ->start
				if(new Integer(4).equals(renderingType)){
					String serverFilePath = map.get(FileUploadUtils.SERVER_FILE_PATH).toString();
					try {
						ImageUtils.watermarking2(serverFilePath, scene, 2, isTurnOn);
					} catch (IOException e1) {
						logger.error("水印图生成失败");
					}
				}
				// 720度渲染生成水印图 ->end
				if(flag){
//					 ResPic resPic = new ResPic();
					 ResRenderPic resPic = new ResRenderPic();
					 resPic.setPicSuffix(map.get("FileSuffix")+"");
					 resPic.setPicPath(map.get("dbFilePath")+"");
					 resPic.setPicFormat(map.get("format")+"");
					 resPic.setPicCode(map.get("code")+"");
					 resPic.setPicSize(Integer.parseInt(map.get("fileSize")+""));
					 resPic.setPicWeight(Integer.parseInt(map.get("picWeight") + ""));
					 resPic.setFileKey(map.get("fileKey")+"");
					 resPic.setPicName( map.get("fileName")+"");
					 resPic.setPicHigh(Integer.parseInt("" + map.get("picHeight")));
					 picId=resRenderPicService.add(resPic);
				}
			}
		}

		logger.info("点击渲染开始,进行任务创建planId =" + planId);
		try {
				JSONObject attribute = new JSONObject();
				attribute.accumulate("viewPoint", viewPoint);
				attribute.accumulate("scene", scene);
				attribute.accumulate("renderingType", renderingType);
				sysTask.setAttribute(attribute.toString());
				sysTask.setBusinessId(planId);
				sysTask.setRenderType(renderingType);
				String rootDirName = designPlan.getPlanCode() + "_" + Utils.getCurrentDateTime(Utils.DATETIME)
						+ Utils.generateRandomDigitString(6);
				logger.info("rootDirName=" + rootDirName);

				sysTask.setBusinessCode(rootDirName);
				/*SysDictionary sysDictionary = null;
				if (renderingType.intValue() == 4) {// 720度渲染
					sysDictionary = sysDictionaryService.findOneByTypeAndValue("panoramaRanderPay", 1);
				} else {
					sysDictionary = sysDictionaryService.findOneByTypeAndValue("renderType", renderingType);
				}*/
				if (priceInfo != null) {
					sysTask.setBusinessName(priceInfo);/* 业务名称 */
				}
				priority = 1000 - renderingType;// 优先级
				LoginUser loginUser = this.sysSave(sysTask, request);
				// 因为免登录通过session取不到用户
				sysTask.setCreator(designPlan.getCreator());
				sysTask.setModifier(designPlan.getModifier());

				// 识别为该用户是销售,则提高渲染优先级
				if (loginUser != null && loginUser.getUserType() != null
						&& new Integer(5).equals(loginUser.getUserType())) {
					priority -= 10;
				} else {
				}
				sysTask.setPriority(priority);// 任务优先级
				if (picId != null) {
					sysTask.setPicId(picId);
				}
				sysTask.setRenderChannel(renderChannel);
				sysTask.setState(SysTaskStatus.NON_PAYMENT);
				this.add(sysTask);
				/** 保存新渲染系统需要的任务参数 start */
				// 把最后一个字符“_3dmax”替换成任务id
				taskConfig = taskConfig.trim();
				if (taskConfig.endsWith("_3dmax")) {
					taskConfig = taskConfig.substring(0, taskConfig.lastIndexOf("_3dmax"));
					taskConfig = taskConfig + Integer.toString(sysTask.getId());
				} else {
					logger.error("Id为" + sysTask.getId() + "的任务params参数有误,没有以'_3dmax'字符结尾");
				}
				String renderParamUploadPath = designPlanService.saveRenderParams(taskConfig, designPlan);
				sysTask.setTaskConfig(renderParamUploadPath);
				/** 保存新渲染系统需要的任务参数 end */
				this.update(sysTask);
		} catch (Exception e) {
			logger.error(e);
		}
		return sysTask;
	}

	
	/**
	 * 自动存储系统字段
	 * @return 
	 */
	private LoginUser sysSave(SysTask model, HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		if (model != null) {
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null || com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
		return loginUser;
	}
	/**
	 * 自动存储系统字段
	 * @return 
	 */
	private void sysSave(SysTask model, LoginUser loginUser) {
		if (model != null) {
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	@Override
	public String updateNonPaymentTask(SysTask sysTask, String orderId, HttpServletRequest request) {
		Integer planId = sysTask.getBusinessId();
		/*sysTask.setOrderNo(orderId);*/
		DesignPlan designPlan = designPlanService.get(planId);
		if (designPlan == null) {
			logger.error("------设计方案未找到:id:" + planId);
			return "设计方案未找到";
		}
		try {
			SysTask task = new SysTask();
			task.setBusinessId(planId);
			task.setIsDeleted(0);
			List<SysTask> taskList = this.getList(task);
			if (taskList != null && taskList.size() == 1) {
				boolean flag = designPlanService.copyPlan_new(designPlan, sysTask, request);
				if (!flag) {
					return "渲染时复制设计方案异常！";
				}
			} else {
				/** 如果该方案变更过则渲染是需要复制一份设计方案并修改之前的变更状态。 */
				if (designPlan.getIsChange() == 1) {
					boolean flag = designPlanService.copyPlan_new(designPlan, sysTask,
							request);/*
										 * 点击渲染之后，新建一份设计方案，保存一份新的
										 * 用户当前的场景，防止在渲染中，用户修改了设计方案场景
										 */
					if (flag) {
						DesignPlan newDesignPlan = new DesignPlan();
						newDesignPlan.setId(designPlan.getId());
						newDesignPlan.setIsChange(0);
						designPlanService.update(newDesignPlan);
					} else {
						return "渲染时复制设计方案异常！";
					}
				} else {// 如果没有变更则找到上次渲染copy的设计方案ID
					task.setOrders(" id desc");
					List<SysTask> renderPlanList = this.getRenderPlanList(task);
					if (renderPlanList != null && renderPlanList.size() > 1) {
						SysTask st = renderPlanList.get(0);
						if(st != null){
							SysTask newSysTask = new SysTask();
							newSysTask.setId(st.getId());
							newSysTask.setPlanId(renderPlanList.get(1).getPlanId());
							this.update(newSysTask);
						}
					}
				}
			}

			SysTask task2 = new SysTask();
			task2.setId(sysTask.getId());
			task2.setState(SysTaskStatus.WAIT_EXECUTE);
			task2.setGmtModified(new Date());
			this.update(task2);
		} catch (Exception e) {
			e.printStackTrace();
			return "系统异常";
		}
		return "success";
	}
	/**
	 * 	5.6版本：创建一个未付款状态的渲染任务
	 * @author yanghz
	 */
	public SysTask createNonPaymentTaskNew(Integer isTurnOn, Integer planId, Integer priority,
			Integer viewPoint, Integer scene, Integer renderingType,String priceInfo,LoginUser loginUser) {
		String paramString = String.format(
				"保存渲染任务App端传来的参数：isTurnOn:%d, planId:%s, priority:%d, viewPoint:%d, scene:%d, renderingType:%d",
				isTurnOn, planId, priority, viewPoint, scene, renderingType);
		SysTask sysTask = new SysTask();
		DesignPlan designPlan = designPlanService.get(planId);
		if(designPlan == null){
			sysTask.setRemark("设计方案没找到");
			return sysTask;
		}
		try {
				JSONObject attribute = new JSONObject();
				attribute.accumulate("viewPoint", viewPoint);
				attribute.accumulate("scene", scene);
				attribute.accumulate("renderingType", renderingType);
				sysTask.setAttribute(attribute.toString());
				sysTask.setBusinessId(planId);
				sysTask.setRenderType(renderingType);
				sysTask.setRenderWay("local");
				String rootDirName = designPlan.getPlanCode() + "_" + Utils.getCurrentDateTime(Utils.DATETIME)
						+ Utils.generateRandomDigitString(6);

				sysTask.setBusinessCode(rootDirName);
				if (priceInfo != null) {
					sysTask.setBusinessName(priceInfo);/* 业务名称 */
				}
				priority = 1000 - renderingType;// 优先级
				this.sysSave(sysTask, loginUser);

				sysTask.setPriority(priority);// 任务优先级
				sysTask.setState(SysTaskStatus.WAITING_RENDER);
				sysTask.setRemark("未支付");
				this.add(sysTask);
		} catch (Exception e) {
			logger.error("------新建未支付渲染任务异常"+e);
		}
		return sysTask;
	}

	/**
	 * 5.6版本：任务状态变更
	 */
	@Override
	public String updateNonPaymentTaskNew(SysTask sysTask, LoginUser loginUser,String payType, String payState,Boolean msgSendIsSuccess) {
		Integer planId = sysTask.getBusinessId();
		DesignPlan designPlan = designPlanService.get(planId);
		if (designPlan == null) {
			logger.error("------设计方案未找到:id:" + planId);
			return "设计方案未找到";
		}

		SysTask task2 = new SysTask();
		task2.setId(sysTask.getId());
//		task2.setState(SysTaskStatus.WAITING_RENDER);
		String remark= "";
		if(msgSendIsSuccess == null){
			//余额支付
			if( PayState.SUCCESS.equals(payState)){//付款成功
				task2.setState(SysTaskStatus.RENDERING);
				if(PayType.ALIPAY.equals(payType)){
					remark = "付款类型：支付宝,支付状态:支付成功";
				}else if(PayType.WXPAY.equals(payType)){
					remark = "付款类型:微信,支付状态：支付成功";
				}else if(PayType.PREDEPOSIT.equals(payType)){
					remark = "付款类型:余额,支付状态：支付成功";
				}else{
					remark = "未知支付类型";
					logger.error("付款类型:未知"+payType+"+,支付状态：支付成功");
				}
			}else{//付款失败
				if(PayType.ALIPAY.equals(payType)){
					remark = "付款类型：支付宝,支付状态:支付失败";
				}else if(PayType.WXPAY.equals(payType)){
					remark = "付款类型:微信,支付状态：支付失败";
				}else if(PayType.PREDEPOSIT.equals(payType)){
					remark = "付款类型:余额,支付状态：支付失败";
				}else{
					remark = "未知支付类型";
					logger.error("付款类型:未知"+payType+"+,支付状态：支付失败");
				}
			}
		
		}else{
			if(msgSendIsSuccess){//扫码支付：消息发送成功
				if( PayState.SUCCESS.equals(payState)){//付款成功
					task2.setState(SysTaskStatus.RENDERING);
					if(PayType.ALIPAY.equals(payType)){
						remark = "付款类型：支付宝,支付状态:支付成功,通知app端websocket支付消息：发送成功";
					}else if(PayType.WXPAY.equals(payType)){
						remark = "付款类型:微信,支付状态：支付成功,通知app端websocket支付消息：发送成功";
					}else if(PayType.PREDEPOSIT.equals(payType)){
						remark = "付款类型:余额,支付状态：支付成功,通知app端websocket支付消息：发送成功";
					}else{
						remark = "未知支付类型";
						logger.error("付款类型:未知"+payType+"+,支付状态：支付成功,通知app端websocket支付消息：发送成功");
					}
					//记录消息发送成功时间
				   Date currentTime = new Date();
				   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   String dateString = formatter.format(currentTime);
				   task2.setAtt1(dateString);
				}else if(PayState.PAY_ERROR.equals(payState)){//付款失败
					if(PayType.ALIPAY.equals(payType)){
						remark = "付款类型：支付宝,支付状态:支付失败,通知app端websocket支付消息：发送成功";
					}else if(PayType.WXPAY.equals(payType)){
						remark = "付款类型:微信,支付状态：支付失败,通知app端websocket支付消息：发送成功";
					}else if(PayType.PREDEPOSIT.equals(payType)){
						remark = "付款类型:余额,支付状态：支付失败,通知app端websocket支付消息：发送成功";
					}else{
						remark = "未知支付类型";
						logger.error("付款类型:未知"+payType+"+,支付状态：支付失败,通知app端websocket支付消息：发送成功");
					}
				}else{
					
				}
			}else if(!msgSendIsSuccess){//扫码支付：消息发送失败
				task2.setState(SysTaskStatus.RENDER_FAILD);
				if( PayState.SUCCESS.equals(payState)){//付款成功
					if(PayType.ALIPAY.equals(payType)){
						remark = "付款类型：支付宝,支付状态:支付成功,通知app端websocket支付消息：发送失败，已退款到用户余额";
					}else if(PayType.WXPAY.equals(payType)){
						remark = "付款类型:微信,支付状态：支付成功,通知app端websocket支付消息：发送失败，已退款到用户余额";
					}else if(PayType.PREDEPOSIT.equals(payType)){
						remark = "付款类型:余额,支付状态：支付成功,通知app端websocket支付消息：发送失败，已退款到用户余额";
					}else{
						remark = "未知支付类型";
						logger.error("付款类型:未知"+payType+"+,支付状态：支付成功,通知app端websocket支付消息：发送失败，已退款到用户余额");
					}
				}else if(PayState.PAY_ERROR.equals(payState)){//付款失败
					if(PayType.ALIPAY.equals(payType)){
						remark = "付款类型：支付宝,支付状态:支付失败,通知app端websocket支付消息：发送失败";
					}else if(PayType.WXPAY.equals(payType)){
						remark = "付款类型:微信,支付状态：支付失败,通知app端websocket支付消息：发送失败";
					}else if(PayType.PREDEPOSIT.equals(payType)){
						remark = "付款类型:余额,支付状态：支付失败,通知app端websocket支付消息：发送失败";
					}else{
						remark = "未知支付类型";
						logger.error("付款类型:未知"+payType+"+,支付状态：支付失败,通知app端websocket支付消息：发送失败");
					}
				}else{
					
				}
				//付款成功但是消息发送失败的需要退款到用户余额
				payOrderService.renderRefund(sysTask, remark);
			}else{
				
			}
		}
		task2.setRemark(remark);
		task2.setGmtModified(new Date());
		this.update(task2);
		return "success";
	}

	@Override
	public void deleteByIdList(List<Integer> taskIdList, String remark) {
		sysTaskMapper.deleteByIdList(taskIdList,remark);
	}

	@Override
	public int getAllownFreeRenderTiems(Integer designPlanId) {
		return sysTaskMapper.getCountFreeRender(designPlanId);
	}
}
