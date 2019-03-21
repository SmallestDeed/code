package com.nork.render.service.impl;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.nork.base.definedvalue.ConstValue.DEFAULT_IS_DELETED;
import com.nork.common.util.AESUtil;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.HttpClient;
import com.nork.common.util.JsonObjectUtil;
import com.nork.common.util.RequestURIS;
import com.nork.common.util.SendEmail;
import com.nork.common.util.Utils;
import com.nork.onekeydesign.service.DesignPlanService;
import com.nork.pay.metadata.PayState;
import com.nork.pay.model.PayOrder;
import com.nork.pay.service.PayOrderService;
import com.nork.render.dao.RenderTaskMapper;
import com.nork.render.model.RenderHost;
import com.nork.render.model.RenderPriceTypeCode;
import com.nork.render.model.RenderTask;
import com.nork.render.model.RenderTaskStates;
import com.nork.render.model.RenderTypeCode;
import com.nork.render.model.RenderWayCode;
import com.nork.render.model.search.RenderTaskSearch;
import com.nork.render.model.vo.RenderCheckVo;
import com.nork.render.model.vo.RenderPriceInfoVo;
import com.nork.render.service.RenderHostService;
import com.nork.render.service.RenderTaskService;
import com.nork.system.model.SysDictionary;
import com.nork.system.model.SysUser;
import com.nork.system.service.BaseMessageRecieveService;
import com.nork.system.service.BaseMessageService;
import com.nork.system.service.SysDictionaryService;
import com.nork.system.service.SysUserService;
import com.nork.task.model.SysTask;
import com.nork.task.model.SysTaskStatus;
import com.nork.task.service.SysTaskService;

/**   
 * @Title: RenderTaskServiceImpl.java 
 * @Package com.nork.render.service.impl
 * @Description:渲染-渲染任务ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-01-17 20:31:06
 * @version V1.0   
 */
@Service("renderTaskService")
public class RenderTaskServiceImpl implements RenderTaskService{
	public final static String URL_ROOT = "http://";
	public final static Integer allowMaxCount = 1;
	private final static boolean JOB_LOG_FLAG = "true".equals(Utils.getValue("jobLog","false"))?true:false;


	private static Logger logger = Logger
			.getLogger(RenderTaskServiceImpl.class);
	@Autowired
	private RenderTaskMapper renderTaskMapper;
	@Autowired
	private SysTaskService sysTaskService;
	@Autowired
	private RenderHostService renderHostService;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private BaseMessageService baseMessageService;
	@Autowired
	private BaseMessageRecieveService baseMessageRecieveService;
	@Autowired
	private PayOrderService payOrderService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
	/**
	 * 新增数据
	 *
	 * @param renderTask
	 * @return  int 
	 */
	@Override
	public int add(RenderTask renderTask) {
		renderTaskMapper.insertSelective(renderTask);
		return renderTask.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param renderTask
	 * @return  int 
	 */
	@Override
	public int update(RenderTask renderTask) {
		return renderTaskMapper
				.updateByPrimaryKeySelective(renderTask);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return renderTaskMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  RenderTask 
	 */
	@Override
	public RenderTask get(Integer id) {
		return renderTaskMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  renderTask
	 * @return   List<RenderTask>
	 */
	@Override
	public List<RenderTask> getList(RenderTask renderTask) {
	    return renderTaskMapper.selectList(renderTask);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  renderTaskSearch
	 * @return   int
	 */
	@Override
	public int getCount(RenderTaskSearch renderTaskSearch){
		return  renderTaskMapper.selectCount(renderTaskSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  renderTaskSearch
	 * @return   List<RenderTask>
	 */
	@Override
	public List<RenderTask> getPaginatedList(
			RenderTaskSearch renderTaskSearch) {
		return renderTaskMapper.selectPaginatedList(renderTaskSearch);
	}
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(RenderTask model){
		if(model != null){
				 /*LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				 }*/
				 
				if(model.getId() == null){
					model.setGmtCreate(new Date());
					//model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				//model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 固定时间段停止发送渲染任务
	 * add by yanghz
	 * @return
	 */
	public Boolean renderStopTime(){
		SysDictionary renderStopTime = sysDictionaryService.findOneByTypeAndValueKey("render", RenderTypeCode.RENDER_STOP_TIME_KEY);
		if (renderStopTime == null){
			logger.error("固定时间段停止发送渲染任务数据字典valueKey="+RenderTypeCode.RENDER_STOP_TIME_KEY+"找不到该记录");
		}else{
			if (renderStopTime.getAtt1() == null || renderStopTime.getAtt1() == null){
				logger.error("固定时间段停止发送渲染任务数据字典valueKey="+RenderTypeCode.RENDER_STOP_TIME_KEY+"att1或att2字段值为空！");
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String timeStart = renderStopTime.getAtt1();
				String timeEnd = renderStopTime.getAtt2();
				try {
					long startTimeLong = sdf.parse(timeStart).getTime();
					long endTimeLong = sdf.parse(timeEnd).getTime();
					long nowTimeLong = sdf.parse(sdf.format(new Date())).getTime();

					//判断当前时间是否在渲时间段内:返回false则停止渲染
					if (startTimeLong < nowTimeLong && nowTimeLong < endTimeLong){
						return false;
					}else{
						return true;
					}
				} catch (ParseException e) {
					logger.error(e);
				}
			}
		}
		return true;
	}

	/**
	 * 
	* @Description: 渲染定时任务   
	* @return void    返回类型 
	* @throws
	 */

	public void renderTask() {
//		if(JOB_LOG_FLAG){
			logger.error("start renderTask");
//		}
		Boolean flag = Boolean.TRUE;
		//判断当前时间是否在渲染免费时间段内
		flag = this.renderStopTime();
		while(flag){
			//logger.error("任务开始");
			//渲染主机分配，返回一台渲染主机信息
			RenderHost host = renderHostService.allocationRenderHost();
			if(host == null){
//				if(JOB_LOG_FLAG){
					logger.error("hire host is null!");
//				}
				flag = Boolean.FALSE;
			}else{
				//获取一条需要渲染的任务
				SysTask maxPriorityTask = sysTaskService.getMaxPriorityTask();
				if(maxPriorityTask == null){
//					if(JOB_LOG_FLAG){
//						logger.error("没有需要渲染的任务!");
//					}
					flag = Boolean.FALSE;
				}else{
					flag = this.startSendRender(maxPriorityTask, host);
				}
			}
		}
		
	}


	/**
	 * 真正发送请求，开始渲染
	 * @author louxinhua
	 * @return boolean 是否继续循环
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	private boolean startSendRender(SysTask maxPriorityTask, RenderHost host) {

		boolean flag = true;

		//调租用主机分发渲染任务
		String isSuccess = this.sendHireHostRenderTaskPost(host,maxPriorityTask);

		if(isSuccess == null || isSuccess.length() < 1){
			logger.error("任务Id为"+maxPriorityTask.getId()+"的任务发送http请求渲染任务,dispatch hire host fail!");
			//发送渲染失败websocket消息
			String msg = "渲染失败!";
			int i = baseMessageService.sendRenderMessage(maxPriorityTask,3,msg);
			if (i < 1){
			    logger.error("渲染任务id"+maxPriorityTask+"发送消息失败");
            }
			flag = Boolean.FALSE;
		} else {
			JsonObject object = JsonObjectUtil.stringToJsonObject(isSuccess);
			boolean success = object.get("success").getAsBoolean();
			if(success){//发送渲染任务成功
				//成功(render_task表添加任务)
				RenderTask renderTask = new RenderTask();
				renderTask.setTaskId(maxPriorityTask.getId());
				renderTask.setHostId(host.getId());
				renderTask.setStatus(RenderTaskStates.WAITING_RENDERING);//任务状态：等待渲染
				renderTask.setGmtTaskStart(new Date());//任务开始时间
				renderTask.setGmtModified(new Date());
				renderTask.setCreator(maxPriorityTask.getCreator());
				sysSave(renderTask);
				this.add(renderTask);

				//更新sys_task表任务状态
				SysTask sysTask = new SysTask();
				sysTask.setId(maxPriorityTask.getId());
				sysTask.setState(SysTaskStatus.WAITING_EXECUTE);
				sysTask.setRenderWay(RenderWayCode.RENDER_WAY_CLOUD);
				sysTask.setTaskServer(this.getRenderHostIp());
				sysTaskService.update(sysTask);
			}else{//发送渲染任务失败
				//失败原因
				logger.error("任务发送失败：任务Id为"+maxPriorityTask.getId()+"的任务给"+host.getIp()+":"+host.getPort()+"发送http请求渲染任务返回信息："+isSuccess.toString());
				String message = object.get("message").getAsString();
				String result = object.get("result").toString();

				SysTask sysTask = new SysTask();
                sysTask.setTaskServer(this.getRenderHostIp());//获取当前服务器ip
				sysTask.setId(maxPriorityTask.getId());
				sysTask.setExecCount(maxPriorityTask.getExecCount()+1);//执行次数加1
				if(maxPriorityTask.getExecCount() >= allowMaxCount){
					sysTask.setRemark((StringUtils.isBlank(maxPriorityTask.getRemark())?"":maxPriorityTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + maxPriorityTask.getExecCount() + "次渲染，执行失败！原因："+message+result+"]");
					sysTask.setState(SysTaskStatus.END_OF_EXCEPTION);
				}else{
					sysTask.setState(SysTaskStatus.WAIT_EXECUTE);
				}
				sysTaskService.update(sysTask);
                //发送渲染失败websocket消息
                String msg = "渲染失败!";
                int i = baseMessageService.sendRenderMessage(maxPriorityTask,3,msg);
                if (i < 1){
                    logger.error("渲染任务id"+maxPriorityTask+"发送消息失败");
                }
			}
		}

		return flag;
	}


	/**
	 *
	* @Description: 通知租用主机添加渲染任务 
	* @param renderHost
	* @param priorityTask     
	* @return void    返回类型 
	* @throws
	 */
	public String sendHireHostRenderTaskPost(RenderHost renderHost,SysTask priorityTask){
		if(priorityTask.getTaskConfig() == null || priorityTask.getTaskConfig().length() < 1){
			return null;
		}
		if(priorityTask.getPriority() == null){
			return null;
		}
		if(renderHost.getIp() == null){
			return null;
		}
		if(renderHost.getPort() == null){
			return null;
		}
		if(priorityTask.getTaskConfig() == null){
			return null;
		}
		//允许执行的次数
		priorityTask.setExecCount(priorityTask.getExecCount()+1);//执行次数加1
		
		/**获取txt配置文件*/
		File file = null;
		boolean flag = Boolean.FALSE;
		String taskConfigPath = priorityTask.getTaskConfig();
		if( StringUtils.isNotBlank(taskConfigPath) ){
			Integer uploadMethod = Integer.valueOf(FileUploadUtils.FTP_UPLOAD_METHOD);
			if( uploadMethod == 1 ){
				/*file = new File(Constants.UPLOAD_ROOT  + taskConfigPath);*/
				file = new File(Utils.getAbsolutePath(taskConfigPath, null));
			}else{
				String fileName = taskConfigPath.substring(taskConfigPath.lastIndexOf("/") + 1);
				flag = FtpUploadUtils.downFile(taskConfigPath.substring(0, taskConfigPath.lastIndexOf("/")), fileName, "\\" + fileName);
				if(flag){
					/*file = new File(Constants.UPLOAD_ROOT + "\\" + taskConfigPath.substring(taskConfigPath.lastIndexOf("/") + 1));*/
					file = new File(Utils.getAbsolutePath(taskConfigPath.substring(taskConfigPath.lastIndexOf("/") + 1), null));
				}
			}
		}
		if( file == null || !file.exists() ){
			SysTask sysTask = new SysTask();
			sysTask.setId(priorityTask.getId());
			sysTask.setRemark((StringUtils.isBlank(priorityTask.getRemark())?"":priorityTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + priorityTask.getExecCount() + "次渲染，执行失败！渲染txt配置文件未找到.]");
			if(priorityTask.getExecCount() >= allowMaxCount){
				sysTask.setState(SysTaskStatus.END_OF_EXCEPTION);
				// 如果渲染异常结束，则把渲染金额退到用户账户余额中
                String renderErroMsg = priorityTask.getRemark();
				renderRefund(priorityTask,renderErroMsg);
			}else{
				sysTask.setState(SysTaskStatus.WAIT_EXECUTE);
			}
			sysTaskService.update(sysTask);
			return null;
		}
		
		/**组装渲染需要参数路径（检查对应的资源文件是否在预解压路径中）：所有资源路径放入list集合中*/
		Integer planId=priorityTask.getBusinessId();
		if(priorityTask.getPlanId()!=null&&priorityTask.getPlanId()>0){
			planId=priorityTask.getPlanId();
		}
		//组装资源路径
		List<String> renderParamsFilePath = designPlanService.getRenderParamsFilePath(planId, priorityTask);
		if(renderParamsFilePath == null){//记录组装资源路径异常信息并更新任务状态
			SysTask sysTask = new SysTask();
			priorityTask.setRemark((StringUtils.isBlank(priorityTask.getRemark())?"":priorityTask.getRemark()+"\r\n") + "[" + Utils.getCurrentDateTime(Utils.DATE_TIME) + ":第" + priorityTask.getExecCount() + "次渲染，执行失败！需要的paramsPath参数路径错误.]");
			if(priorityTask.getExecCount() >= allowMaxCount){
				sysTask.setId(priorityTask.getId());
				sysTask.setState(SysTaskStatus.END_OF_EXCEPTION);
				// 如果渲染异常结束，则把渲染金额退到用户账户余额中
//				renderRefund(priorityTask);//此方法已挪位置   add by yanghz
			}else{
				priorityTask.setState(SysTaskStatus.WAIT_EXECUTE);
			}
			sysTaskService.update(sysTask);
			return null;
		}else{
			logger.info("Id为"+priorityTask.getId()+"的渲染任务planId为"+planId+"的设计方案渲染需要的参数路径汇总：" + renderParamsFilePath.toString());
			/**调用发送任务接口*/
			Map<String,String> map = new HashMap<String, String>();
			map.put("taskId", AESUtil.encrypt(priorityTask.getId().toString(), ""));
			//map.put("priority", priorityTask.getPriority().toString());
			if(priorityTask.getRenderChannel() != null){
				map.put("priority", 1000-priorityTask.getRenderChannel().intValue()+"");//TODO   先传固定值
			}else{
				map.put("priority", "999");//TODO   先传固定值
			}
			map.put("taskWeight", priorityTask.getTaskWeight().toString());
			//拼装url
			StringBuffer url = new StringBuffer();
			url.append(URL_ROOT).append(renderHost.getIp()).append(":"+renderHost.getPort()).append(RequestURIS.POST_SEND_RENDER_TASK);
			//添加txt文件
			List<File> fileList = new ArrayList<File>();
			fileList.add(file);
			
			//发http请求（发送渲染任务）
			String urlString = url.toString();
			String sendRender = HttpClient.getInstance().sendRenderHttpPost(urlString, map, fileList, renderParamsFilePath);
			if(sendRender != null && sendRender.length() > 0){
				logger.info("Id为"+priorityTask.getId()+"的任务渲染需要的预解压参数list：" + renderParamsFilePath.toString());
				return sendRender;
			}else{
				logger.error("Id为"+priorityTask.getId()+"的渲染任务发送渲染任务地址："+url.toString()+"调用失败"+"可能原因为地址有误或者拒绝访问");
			}
		}
		return null;
}



	/**
	 * 
	* @Description: 获取一台主机正在渲染的任务数量
	* @param renderTaskSearch
	* @return int    任务数量
	* @throws
	 */
	@Override
	public int getUsingTaskCount(RenderTaskSearch renderTaskSearch) {
		// TODO Auto-generated method stub
		return renderTaskMapper.selectUsingTaskCount(renderTaskSearch);
	}

	/**
	 *    根据sys_task表的id获取数据详情
	 *
	 * @param taskId
	 * @return  RenderTask 
	 */
	@Override
	public RenderTask getByTaskId(Integer taskId) {
		return renderTaskMapper.selectByTaskId(taskId);
	}

	
	/**
	 * 渲染失败退款到账户余额
	 * @param sysTask
	 */
	public void renderRefund(SysTask sysTask,String renderErroMsg){
		if( sysTask != null ){
			// 找到渲染任务的订单
			//*****暂时从渲染任务表中取订单orderNo，等新的支付、渲染流程好了后使用注释中的方法查询*****//
			//PayOrder orderSearch = new PayOrder();
			//orderSearch.setTaskId(sysTask.getId());
			//List<PayOrder> payOrderList = payOrderService.getList(orderSearch);
			//******************************************************************************//

			//***************临时方案，暂时使用****************//
			List<PayOrder> payOrderList = new ArrayList<>();
			PayOrder order = null;
			/*if(sysTask != null && StringUtils.isNotBlank(sysTask.getOrderNo())){
				order	=	payOrderService.get(sysTask.getOrderNo());
			    payOrderList.add(order);
			}*/
			// 修改为用payOrder的taskId字段关联
			if(sysTask != null && sysTask.getId() != null){
				order = payOrderService.findOneByTaskId(sysTask.getId());
			    payOrderList.add(order);
			}
			//*******************************//
			if( payOrderList != null && payOrderList.size() > 0){
				PayOrder payOrder = payOrderList.get(0);
				// 付款成功的才会退款
				if( PayState.SUCCESS.equals(payOrder.getPayState()) ) {
					Double totalFee = new Double(payOrder.getTotalFee());// 渲染扣除金额（分）
					// 更新账户余额
					SysUser sysUser = new SysUser();
					sysUser.setId(payOrder.getUserId());
					sysUser.setBalanceAmount(totalFee);
					sysUser.setConsumAmount(-totalFee);
					sysUserService.updateFinance(sysUser);
				}else{
					logger.error("order has not been paid successfully... orderNo:"+sysTask.getOrderNo());
				}
				// 发送渲染失败告警邮件
				List<SysUser> warningUserList = sysUserService.getUserByRoleCode("RENDER_WARNING");
				if( warningUserList != null && warningUserList.size() > 0 ){
					StringBuffer toEmailsStr = new StringBuffer();
					int count = 0;
					for( SysUser warningUser : warningUserList ){
						if( StringUtils.isNotBlank(warningUser.getEmail()) ) {
							if( (count+1) < warningUserList.size() ) {
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
						stringBuffer.append("  有渲染任务异常结束。渲染任务编码：<br>");
						stringBuffer.append("<font color=\"red\"><strong>"+sysTask.getBusinessCode()+"</strong></font><br>");

                        SysDictionary sysDictionary = null;
						if (sysTask.getCreator() != null){
							stringBuffer.append("  用户名:"+sysTask.getCreator()+"<br>");
							List<SysUser> oneByLoginName = sysUserService.findOneByLoginName(sysTask.getCreator());
							if (oneByLoginName != null && oneByLoginName.size() >0){
                                sysDictionary =sysDictionaryService.getSysDictionaryByValue("userType", oneByLoginName.get(0).getUserType());
                            }else{
							    logger.error("未查到该用户："+sysTask.getCreator());
                            }
						}
						if (sysDictionary != null){
                            stringBuffer.append("  用户类型："+sysDictionary.getName()+"<br>");
                        }
						stringBuffer.append("异常信息："+renderErroMsg);
						String subject = "【渲染异常告警】";
						SendEmail.massSend(toEmails, subject, stringBuffer.toString());
						logger.error("有渲染任务异常结束。渲染任务编码："+sysTask.getBusinessCode());
					}
				}else{
					logger.error("warningUserList is null");
				}
			}else{
				logger.error("not found payOrder... orderNo:"+sysTask.getOrderNo());
			}
		}
	}

    /**
     * 获取当前服务器ip
     * @return
     */
	public  String getRenderHostIp(	){
		String taskHostIp = "";
        try {
            InetAddress address=InetAddress.getLocalHost();
            taskHostIp = address.getHostAddress();
        } catch (UnknownHostException e) {
            logger.error(e);
        }
        return taskHostIp;
	}

	/**
	 * 判断渲染免费时间段
	 * @author yanghz
	 * @since 2017-05-03
	 * @return
	 */
	public Boolean renderFreeTime(){
		SysDictionary renderFreeTime = sysDictionaryService.findOneByTypeAndValueKey("render", RenderTypeCode.RENDER_FREE_TIME_KEY);
		if (renderFreeTime == null){
			logger.error("渲染免费时间   数据字典valueKey="+RenderTypeCode.RENDER_FREE_TIME_KEY+"找不到该记录");
		}else{
			if (renderFreeTime.getAtt1() == null || renderFreeTime.getAtt2() == null){
				logger.error("渲染免费时间   数据字典valueKey="+RenderTypeCode.RENDER_FREE_TIME_KEY+"att1或att2字段值为空！");
			}else{
				String attr1=renderFreeTime.getAtt1();
				String attr2=renderFreeTime.getAtt2();
				
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String timeStartStr ="";
				String timeEndStr ="";
				
				String dayStartStr = "";
				String dayEndStr = "";
				
				if(attr1 == null || attr1.length() < 1){
					
				}else{
					String[] attr1Split = attr1.split(",");
					timeStartStr = attr1Split[0];
					dayEndStr = attr1Split[1];
				}
				if(attr2 == null || attr2.length() < 1){
					
				}else{
					String[] attr2Split = attr2.split(",");
					dayStartStr = attr2Split[0];
					timeEndStr = attr2Split[1];
				}
				
				try {
					Date dateNow = sdf.parse(sdf.format(new Date()));
					
					Date dataStart = sdf.parse(timeStartStr);
					Date dataEnd = sdf.parse(timeEndStr);
					
					long startTimeLong = dataStart.getTime();
					long endTimeLong = dataEnd.getTime();
					
					long nowTimeLong = dateNow.getTime();
					
					long dayStartLong = sdf.parse(dayStartStr).getTime();
					long dayEndLong = sdf.parse(dayEndStr).getTime();
					//判断当前时间是否在渲染免费时间段内
					if ((startTimeLong < nowTimeLong && nowTimeLong < dayEndLong) || (dayStartLong < nowTimeLong && nowTimeLong < endTimeLong)){
						return true;
					}else{
						return false;
					}
				} catch (ParseException e) {
					logger.error(e);
				}
		}
		}
		return false;
	}
	/**
	 * 5.6版本：判断渲染免费时间段和返回免费时间段
	 * @author yanghz
	 * @since 2017-05-26
	 * @return
	 */
	public RenderCheckVo renderFreeTimeInfo(){
		RenderCheckVo rederCheck = new RenderCheckVo();
		SysDictionary renderFreeTime = sysDictionaryService.findOneByTypeAndValueKey("render", RenderTypeCode.RENDER_FREE_TIME_KEY);
		if (renderFreeTime == null){
			logger.error("渲染免费时间   数据字典valueKey="+RenderTypeCode.RENDER_FREE_TIME_KEY+"找不到该记录");
		}else{
			if (renderFreeTime.getAtt1() == null || renderFreeTime.getAtt2() == null){
				logger.error("渲染免费时间   数据字典valueKey="+RenderTypeCode.RENDER_FREE_TIME_KEY+"att1或att2字段值为空！");
			}else{
				String attr1=renderFreeTime.getAtt1();
				String attr2=renderFreeTime.getAtt2();
				
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String timeStartStr ="";
				String timeEndStr ="";
				
				String dayStartStr = "";
				String dayEndStr = "";
				
				if(attr1 == null || attr1.length() < 1){
					
				}else{
					String[] attr1Split = attr1.split(",");
					timeStartStr = attr1Split[0];
					dayEndStr = attr1Split[1];
				}
				if(attr2 == null || attr2.length() < 1){
					
				}else{
					String[] attr2Split = attr2.split(",");
					dayStartStr = attr2Split[0];
					timeEndStr = attr2Split[1];
				}
				
				try {
					Date dateNow = sdf.parse(sdf.format(new Date()));
					
					Date dataStart = sdf.parse(timeStartStr);
					Date dataEnd = sdf.parse(timeEndStr);
					
					long startTimeLong = dataStart.getTime();
					long endTimeLong = dataEnd.getTime();
					
					long nowTimeLong = dateNow.getTime();
					
					long dayStartLong = sdf.parse(dayStartStr).getTime();
					long dayEndLong = sdf.parse(dayEndStr).getTime();
					//判断当前时间是否在渲染免费时间段内
					if ((startTimeLong < nowTimeLong && nowTimeLong < dayEndLong) || (dayStartLong < nowTimeLong && nowTimeLong < endTimeLong)){
						rederCheck.setIsFree(RenderTypeCode.RENDER_FREE_TIME_TYPE_KEY);//免费
						rederCheck.setFreeTimeStart(timeStartStr.substring(0,timeStartStr.lastIndexOf(":")));
						rederCheck.setFreeTimeEnd(timeEndStr.substring(0,timeEndStr.lastIndexOf(":")));
						rederCheck.setFreeTimeName("(免费时间段:晚"+rederCheck.getFreeTimeStart()+"-早"+rederCheck.getFreeTimeEnd()+")");
						return rederCheck;
					}else{
						rederCheck.setIsFree(RenderTypeCode.RENDER_NOT_FREE_TIME_TYPE_KEY);//收费
						rederCheck.setFreeTimeStart(timeStartStr.substring(0,timeStartStr.lastIndexOf(":")));
						rederCheck.setFreeTimeEnd(timeEndStr.substring(0,timeEndStr.lastIndexOf(":")));
						rederCheck.setFreeTimeName("(免费时间段:晚"+rederCheck.getFreeTimeStart()+"-早"+rederCheck.getFreeTimeEnd()+")");
						return rederCheck;
					}
				} catch (ParseException e) {
					logger.error(e);
				}
			}
		}
		rederCheck.setIsFree(RenderTypeCode.RENDER_FREE_TIME_TYPE_KEY);//收费
		rederCheck.setFreeTimeStart("10:00");//默认值
		rederCheck.setFreeTimeEnd("08:00");//默认值
		rederCheck.setFreeTimeName("晚"+rederCheck.getFreeTimeStart()+"-早"+rederCheck.getFreeTimeEnd());
		return rederCheck;
	}
	
	
	
	/**
	 * 根据渲染 id，是否已删除，来获取列表
	 * @author louxinhua
	 * @since 2017-05-03
	 * @param taskId 渲染任务 id
	 * @param isDeleted 是否已删除
	 * @return
	 */
	public List<RenderTask> getByTaskId(Integer taskId, DEFAULT_IS_DELETED isDeleted) {
		
		RenderTaskSearch rts = new RenderTaskSearch();
		rts.setTaskId(taskId);
		if ( isDeleted == DEFAULT_IS_DELETED.is_deleted ) {
			rts.setIsDeleted(DEFAULT_IS_DELETED.is_deleted.ordinal());
		}
		else {
			rts.setIsDeleted(DEFAULT_IS_DELETED.is_not_deleted.ordinal());
		}
		List<RenderTask> list = this.renderTaskMapper.selectList(rts);
		
		return list;
	}

	/**
	 * 更具渲染类型查询价格列表
	 * @author yanghz
	 * @since 2017-05-26
	 * @return List
	 */
	@Override
	public List<RenderPriceInfoVo> findPriceInfo(Integer renderingType) {
		List<RenderPriceInfoVo> priceList = new ArrayList<>();
		String priceType = "";
		if(RenderTypeCode.COMMON_PICTURE_LEVEL == renderingType){
			priceType = RenderPriceTypeCode.RENDER_PRICE_OF_PHOTO;
		}else if(RenderTypeCode.COMMON_720_LEVEL == renderingType) {
			priceType = RenderPriceTypeCode.RENDER_PRICE_OF_720;
		}else if( RenderTypeCode.COMMON_VIDEO == renderingType ){
			priceType = RenderPriceTypeCode.RENDER_PRICE_OF_VIDEO;
		}else if( RenderTypeCode.ROAM_720_LEVEL == renderingType ){
			priceType = RenderPriceTypeCode.RENDER_PRICE_OF_ROAM720;
		}else{
		}
		List<SysDictionary> renderPrice = sysDictionaryService.findAllByType(priceType);
		if(renderPrice == null){
			logger.error("查询渲染价格数据字典：type="+priceType+"未查询到信息！");
		}else{
			for(SysDictionary sysTemp : renderPrice){
				RenderPriceInfoVo priceInfoVo = new RenderPriceInfoVo();
				priceInfoVo.setType(priceType);
				if(sysTemp.getValue() == null){
					logger.error("数据字典id="+sysTemp.getId()+"未设置value值");
				}else{
					priceInfoVo.setMode(sysTemp.getValue()+"");
				}
				if(sysTemp.getAtt1() == null || "".equals(sysTemp.getAtt1()) ){
					logger.error("数据字典id="+sysTemp.getId()+"未设置价格信息:att1字段");
				}else{
					priceInfoVo.setPrice(sysTemp.getAtt1());
				}
				if(sysTemp.getName() == null || "".equals(sysTemp.getName())){
					logger.error("数据字典id="+sysTemp.getId()+"未设置单位信息:name字段");
				}else{
					priceInfoVo.setName(sysTemp.getName());
				}
				priceList.add(priceInfoVo);
			}
		}
		
		return priceList;
	}
	
	
	/**
	 * 定时检测客户端是否有传图过来：30min对应的任务没有图片记录则判定客户端生成图片失败
	 * add by yanghz
	 */
	public void checkRenderPic(){
		Boolean flag = Boolean.TRUE;
		while(flag){
			SysTask sysTask = new SysTask();
			sysTask.setState(SysTaskStatus.RENDERING);//渲染中状态
			List<SysTask> list = sysTaskService.getList(sysTask );
			if(list != null && list.size() > 0){
				for(SysTask tmpTask : list){
					PayOrder payOrder = payOrderService.get(tmpTask.getId());
					if(payOrder == null){
						logger.error("通过任务id="+tmpTask.getId()+"未查询到对应的支付订单!");
					}else{
						if(!PayState.SUCCESS.equals(payOrder.getPayState())){
							logger.info("通过任务id="+tmpTask.getId()+"查询到对应的支付订单id="+payOrder.getId()+"对应的状态为"+payOrder.getPayState()+"！");
						}else{
							String att1 = tmpTask.getAtt1();
							if(StringUtils.isEmpty(att1)){
								continue;
							}
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date parse = null;
							try {
								parse = formatter.parse(att1);
							} catch (ParseException e) {
								logger.error("时间转换错误："+e);
							}
							if(parse != null){
								long time = parse.getTime();
								long now = new Date().getTime();
								long diff = now - time;
								if(diff/(1000*60) > 30){//超过30分钟
									//退款、发送websocket消息
									String renderErroMsg = "已支付任务保持渲染中状态超过三十分钟，客户端渲染可能失败，系统自动退款至余额";
									payOrderService.renderRefund(tmpTask, renderErroMsg);
									//更新任务状态为：渲染失败
									SysTask sysTask2 = new SysTask();
									sysTask2.setState(SysTaskStatus.RENDER_FAILD);
									sysTask2.setRemark(renderErroMsg);
									sysTaskService.update(sysTask2);
									//发送渲染失败消息  TODO:暂时不处理，后期看考虑是否要加上
									
								}else{
									
								}
							}else{
								
							}
						}
					}
				}
			}else{
				flag = false;
				logger.info("本地渲染没有渲染中的任务");
			}
		}
	}
	
}
