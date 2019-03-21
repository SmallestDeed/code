package com.nork.design.service.impl;

import com.google.gson.Gson;
import com.nork.common.cache.utils.JedisUtils;
import com.nork.common.constant.AutoRenderTaskConstant;
import com.nork.common.jwt.Jwt;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.Constants;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.design.common.DesignPlanConstants;
import com.nork.design.dao.*;
import com.nork.design.model.*;
import com.nork.design.service.DesignPlanAutoRenderService;
import com.nork.design.service.DesignPlanRecommendedServiceV2;
import com.nork.home.dao.SpaceCommonMapper;
import com.nork.home.model.SpaceCommon;
import com.nork.home.model.SpaceCommonConstant;
import com.nork.imPush.utils.SocketIOUtil;
import com.nork.render.model.RenderTypeCode;
import com.nork.system.common.SysUserMessageConstants;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.SysUser;
import com.nork.system.model.SysUserMessage;
import com.nork.system.service.ResRenderPicService;
import com.nork.system.service.SysUserMessageService;
import com.nork.system.service.SysUserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

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
    private SaveRenderPicServiceImpl saveRenderPicService;
    @Autowired
    private SysUserMessageService sysUserMessageService;
    @Autowired
    private FullHouseDesignPlanMapper fullHouseDesignPlanMapper;
    @Autowired
    private DesignPlanAutoRenderService designPlanAutoRenderService;
    @Autowired
    private SaveRenderPicServiceImpl saveRenderPicServiceImpl;

    @Override
    public ResponseEnvelope<AutoRenderTask> getAutoRenderTaskList(Integer maxSize, String msgId, LoginUser loginUser) {
        List<AutoRenderTask> taskList = getRenderTasks(maxSize, loginUser);
        ResponseEnvelope<AutoRenderTask> result = new ResponseEnvelope<AutoRenderTask>(true, msgId, maxSize, taskList);
        return result;
    }

    @Override
    public ResponseEnvelope<AutoRenderTask> getTaskList(Integer maxSize, String msgId, LoginUser loginUser) {
        List<AutoRenderTask> taskList = findReplaceProductTaskList(maxSize, loginUser);
        ResponseEnvelope<AutoRenderTask> result = new ResponseEnvelope<AutoRenderTask>(true, msgId, maxSize, taskList);
        return result;
    }

    @Override
    public List<AutoRenderTask> getTaskList(AutoRenderTask autoRenderTask) {
        return designPlanAutoRenderMapper.getTaskList(autoRenderTask);
    }

    /**
     * 更新失败任务状态
     */
    @Override
    public ResponseEnvelope<AutoRenderTaskState> updateAutoRenderTaskState(AutoRenderTaskState renderTask, String msgId,
                                                                            String token) {
	   /*修改全屋户型渲染状态信息表start*/
        int baseHousePicFullHousePlanRelId;
        try {
            baseHousePicFullHousePlanRelId = designPlanAutoRenderService.updateBaseHousePicFullHousePlanRelFalseByTaskId(renderTask.getTaskId());
            if(0 == baseHousePicFullHousePlanRelId){
                logger.warn("修改全屋户型渲染状态信息失败");
            }
            logger.info("修改全屋户型渲染状态信息成功"+baseHousePicFullHousePlanRelId);
        }catch (Exception e){
            logger.error("修改全屋户型渲染状态信息数据异常",e);

        }
        /*修改全屋户型渲染状态信息表end*/


        String msg = "";
        // sysSaveState(renderTask, loginUser);
        //处理超时任务的方法
        Integer failType = renderTask.getFailType();
        if (AutoRenderTaskConstant.TIME_OUT_FAIL_TYPE == failType) {
            return this.handleTimeOut(renderTask);
        } else {
            //处理其他失败任务的方法
            ResponseEnvelope envelope = (ResponseEnvelope) this.updateTaskState(renderTask, token);
            return envelope;
        }
    }

    /**
     * 通过方案mapping样板房
     */
    @Override
    public List<AutoRenderTask> mappingForDesignTemplateByDesignPlan(DesignPlanRecommended designPlanRecommended,
                                                                     LoginUser loginUser) {
        List<DesignTemplet> designTemplets = null;
        // designPlan.setId(20532);
        Integer spaceCommonId = designPlanRecommended.getSpaceCommonId();
        // spaceCommonId = 1282;
        SpaceCommon spaceInfo = spaceCommonMapper.selectByPrimaryKey(spaceCommonId);
        String spaceCode = null;
        if (spaceInfo != null) {
            String spaceAreas = spaceInfo.getSpaceAreas();
            Integer spaceFunctionId = spaceInfo.getSpaceFunctionId();
            spaceCode = spaceInfo.getSpaceCode();
            // designTemplets = designTempletMapper.geTempletsBySpaceInfo("5", 5,5);
            Integer createdOfTemplateId = null;
            // if(designPlanRecommended.getDesignSourceType()!=null &&
            // designPlanRecommended.getDesignSourceType().intValue() == 7){
            // createdOfTemplateId = designPlanRecommended.getDesignId();
            // }

            // 如果适用面积不为空，则查适用该面积的样板房，没有默认当前空间面积
            if (StringUtils.isNotEmpty(designPlanRecommended.getApplySpaceAreas())) {
                designTemplets = designTempletMapper.getTempletsBySpaceAreasInfo(
                        designPlanRecommended.getApplySpaceAreas(), spaceFunctionId, createdOfTemplateId);
            } else {
                designTemplets = designTempletMapper.geTempletsBySpaceInfo(spaceAreas, spaceFunctionId,
                        createdOfTemplateId);
            }
        }

        List<AutoRenderTask> tasks = convertTaskList(designPlanRecommended.getId(), designTemplets, spaceCode,
                loginUser);
        List<AutoRenderTaskState> taskStateList = designPlanAutoRenderMapper
                .getTaskStateListByDesignPlanId(designPlanRecommended.getId());
        List<AutoRenderTask> rendingTasks = designPlanAutoRenderMapper
                .getRenderTaskListByPlanId(designPlanRecommended.getId());
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

    private List<AutoRenderTask> convertTaskList(Integer planId, List<DesignTemplet> templets, String spaceCode,
                                                 LoginUser loginUser) {
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
        // DesignTemplet.setId(1177);
        Integer spaceCommonId = DesignTemplet.getSpaceCommonId();
        // spaceCommonId = 1282;
        SpaceCommon spaceInfo = spaceCommonMapper.selectByPrimaryKey(spaceCommonId);
        List<DesignPlan> designPlans = null;
        String spaceCode = null;
        if (spaceInfo != null) {
            spaceCode = spaceInfo.getSpaceCode();
            String spaceAreas = spaceInfo.getSpaceAreas();
            Integer spaceFunctionId = spaceInfo.getSpaceFunctionId();
            // designPlans = designPlanMapper.getPlansBySpaceInfo("5", 5);
            designPlans = designPlanMapper.getPlansBySpaceInfo(spaceAreas, spaceFunctionId);
        }
        List<AutoRenderTask> tasks = convertTaskLists(DesignTemplet.getId(), designPlans, spaceCode);
        List<AutoRenderTaskState> taskStateList = designPlanAutoRenderMapper
                .getTaskStateListByTemplateId(DesignTemplet.getId());
        List<AutoRenderTask> rendingTasks = designPlanAutoRenderMapper
                .getRenderTaskListBytemplateId(DesignTemplet.getId());
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

    private List<AutoRenderTask> convertTaskLists(Integer templateId, List<DesignPlan> paln, String spaceCode) {
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

    @Override
    public ResponseEnvelope<AutoRenderTaskState> handleTimeOut(AutoRenderTaskState taskState) {
        //返回失败类型为88时，删除本条task_state表的记录，然后把task任务放入redis队列队首中
        logger.error("处理超时任务taskId" + taskState.getTaskId());
        Integer taskId = taskState.getTaskId();
        //根据taskId查询state表记录，不存在表示此任务没有进行操作，数据有误
        AutoRenderTaskState stateByTaskId = designPlanAutoRenderMapper.getStateByTaskId(taskId);
        if (stateByTaskId == null) {
            return new ResponseEnvelope<>(false,"超时任务在state表没有记录，请查证taskId="+taskId);
        }
        //上面返回值已经验证了只有一条state数据存在，这里直接删除这条数据，返回删除的行数
        int count = designPlanAutoRenderMapper.deleteTaskStateByTaskId(taskId);
        logger.error("删除超时任务state表记录共count="+count);

        AutoRenderTask renderTask = designPlanAutoRenderMapper.getRenderTaskById(taskId);

        Gson gson = new Gson();
        String json = gson.toJson(renderTask);
        //把对应的任务放回redis队首
        Long result;
        if (AutoRenderTaskConstant.TASK_TYPE_AUTO_RENDER.equals(renderTask.getTaskType())) {
            result = JedisUtils.listLAdd(REDIS_TASK_LIST, json);
        } else {
            result = JedisUtils.listLAdd(REDIS_TASK_REPLACE_LIST, json);
        }
        logger.info("Redis list size ====>" + result);

        return new ResponseEnvelope(true,taskId,"处理超时任务完成");
    }

    @Override
    public int updateBaseHousePicFullHousePlanRelSuccessByTaskId(Integer taskId) {
        return designPlanAutoRenderMapper.updateBaseHousePicFullHousePlanRelSuccessByTaskId(taskId);
    }

    @Override
    public int updateBaseHousePicFullHousePlanRelFalseByTaskId(Integer taskId) {
        return designPlanAutoRenderMapper.updateBaseHousePicFullHousePlanRelFalseByTaskId(taskId);
    }


    /**
     * 更新失败任务
     */
    @Override
    public Object updateTaskState(AutoRenderTaskState autoRenderTaskState, String token) {
        logger.error("更新状态表方案ID" + autoRenderTaskState.getPlanId() + "样板房ID" + autoRenderTaskState.getTemplateId());
        logger.error("更新状态表任务ID" + autoRenderTaskState.getTaskId());
        Integer taskId = autoRenderTaskState.getTaskId();
        AutoRenderTask renderTask = designPlanAutoRenderMapper.getRenderTaskById(taskId);
        autoRenderTaskState.setPlatformId(renderTask.getPlatformId());
        autoRenderTaskState.setState(DesignPlanConstants.TASKSTATE.FAILUE.getValue());
        if ("1".equals(renderTask.getRenderTypesStr())) {
            autoRenderTaskState.setRenderPic(DesignPlanConstants.RENDER_FAIL);
        } else if ("2".equals(renderTask.getRenderTypesStr())) {
            autoRenderTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
        } else if ("3".equals(renderTask.getRenderTypesStr())) {
            autoRenderTaskState.setRenderN720(DesignPlanConstants.RENDER_FAIL);
        } else if ("4".equals(renderTask.getRenderTypesStr())) {
            autoRenderTaskState.setRenderVideo(DesignPlanConstants.RENDER_FAIL);
        }
        AutoRenderTaskState taskState = designPlanAutoRenderMapper.getStateByTaskId(taskId);
        long startTime = taskState.getGmtCreate().getTime();
        long endTime = System.currentTimeMillis();
        long spendTimeLong = endTime - startTime;
        int secondTotal = (int) (spendTimeLong / 1000);
        int min = secondTotal / 60;
        int second = secondTotal % 60;
        autoRenderTaskState.setRenderTimeConsuming(min + "分" + second + "秒");
        Integer result = designPlanAutoRenderMapper.updateTaskStateByTaskId(autoRenderTaskState);
        logger.error("更新数据库" + result + "条失败记录....");
        taskState = designPlanAutoRenderMapper.getStateByTaskId(taskId);
        if (AutoRenderTaskConstant.PLAN_SINGLE_HOUSE_TYPE.equals(taskState.getPlanHouseType())) {
            //如果是单空间方案，就生成渲染失败的消息

        } else if (AutoRenderTaskConstant.PLAN_FULL_HOUSE_TYPE_NEW.equals(taskState.getPlanHouseType())) {

            if (AutoRenderTaskConstant.TASK_TYPE_REPLACE.equals(taskState.getTaskType())) {
                //如果是替换渲染，则更新主任务为失败的状态
                this.updateFullHouseTaskState(taskState);
                //返回全屋方案主任务为处理之后逻辑的对象
                taskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());
            } else {
                // 装进我家的时候判断是不是最后一个方案失败，
                // 如果是最后一个方案失败则判断所有的子方案，有成功的子方案则把主方案修改为成功的状态
                // 如果不是最后一个方案失败，则更新主任务子任务数量的字段
                taskState = this.handleFullHouseAutoRenderTask(taskState);

            }

        } else {
            //如果是全屋方案，则改变主方案为失败的状态
            updateFullHouseTaskState(taskState);
            //返回全屋方案主任务为处理之后逻辑的对象
            taskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());
        }
        //创建渲染失败的消息
        int id = createFailMessage(taskState);
        logger.info("insert a fail taskMessage into sys_user_message---->id：" + id);
        try {
            Integer miniProgramId = saveRenderPicService.getPlatformIdByCode("miniProgram");
            if (miniProgramId != null && miniProgramId.equals(taskState.getPlatformId())) {
                String url = saveRenderPicService.SYSTEM_DOMAIN_NAME + "/v1/notify/wx/sendRenderTemplateMsg";
                logger.error("小程序发送模板消息 ===> url = "+url);
                Map<String, String> map = new HashMap<>();
                logger.error("小程序发送模板消息 ===> taskState = "+taskState);
                map.put("userId", taskState.getOperationUserId().toString());
                Integer planId = taskState.getPlanId();
                if (taskState.getGroupPrimaryId() != null && taskState.getGroupPrimaryId() != 0) {
                    //如果打组推荐方案主方案不为0则返回主方案id
                    planId = taskState.getGroupPrimaryId();
                }
                map.put("designPlanId", planId.toString());
                if (AutoRenderTaskConstant.PLAN_FULL_HOUSE_TYPE.equals(taskState.getPlanHouseType())) {
                    //如果是全屋方案
                    FullHouseDesignPlan fullHousePlan = fullHouseDesignPlanMapper.getFullHousePlanById(taskState.getPlanId());
                    map.put("designPlanName", fullHousePlan.getPlanName());
                } else {
                    //单空间方案找对应方案的名称
                    switch (taskState.getOperationSource()) {
                        case 0:
                            DesignPlanRenderScene designPlanRenderScene = designPlanRenderSceneMapper.get(taskState.getPlanId());
                            map.put("designPlanName", designPlanRenderScene.getPlanName());
                            break;
                        case 1:
                            DesignPlanRecommended designPlanRecommended = designPlanRecommendedMapperV2.selectByPrimaryKey(taskState.getPlanId());
                            map.put("designPlanName", designPlanRecommended.getPlanName());
                            break;
                    }
                }
                map.put("renderResult", "渲染失败");
                map.put("renderType", taskState.getTaskType().toString());
                switch (taskState.getRenderTypesStr()) {
                    case "1" :
                        map.put("renderLevel", "1");
                        break;
                    case "2" :
                        map.put("renderLevel", "4");
                        break;
                    case "3" :
                        map.put("renderLevel", "8");
                        break;
                    case "4" :
                        map.put("renderLevel", "6");
                        break;
                }
                logger.error("小程序发送模板消息 ===> url="+url+" ,map="+map);
                String resultStr = Utils.doPostMethodForm(url,map);
                logger.error("小程序发送模板消息 ===> resultStr : "+resultStr);
            }
        } catch (Exception e) {
            logger.error("小程序发送模板消息 ===> exception：",e);
        }


        logger.error("开始请求支付 退款===============================>" + "userId=" + taskState.getOperationUserId()
                + "orderNO=>" + taskState.getOrderNumber());
        boolean flag = updatePayOrder(taskState.getOperationUserId(), taskState.getOrderNumber(), token);
        logger.error("进入更新失败任务 flag ======================>" + flag);
        if (!flag) {
            return new ResponseEnvelope<>(false, "退款失败！");
        }
        return new ResponseEnvelope<>(true, "退款成功！");

    }

    /**
     *
     * @param taskState
     * @return
     */
    private AutoRenderTaskState handleFullHouseAutoRenderTask(AutoRenderTaskState taskState) {
        // 获取主任务状态
        AutoRenderTaskState mainTaskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());

        Integer subtaskCount = mainTaskState.getSubtaskCount();
        if (subtaskCount == null){
            List<AutoRenderTaskState> subtaskList = designPlanAutoRenderMapper.getSubTaskListByMainTaskId(taskState.getMainTaskId());
            int successed = DesignPlanConstants.TASKSTATE.FAILUE.getValue();
            for (AutoRenderTaskState autoRenderTaskState : subtaskList) {
                if (autoRenderTaskState.getState() == DesignPlanConstants.TASKSTATE.SUCCESS.getValue()){
                    successed = DesignPlanConstants.TASKSTATE.SUCCESS.getValue();
                }
            }
            mainTaskState.setState(successed);
            String renderTypesStr = taskState.getRenderTypesStr();

            if (successed == DesignPlanConstants.TASKSTATE.SUCCESS.getValue()) {
                if ("1".equals(renderTypesStr)) {
                    mainTaskState.setRenderPic(DesignPlanConstants.RENDER_SUCCESS);
                } else if ("2".equals(renderTypesStr)) {
                    mainTaskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
                } else if ("3".equals(renderTypesStr)) {
                    mainTaskState.setRenderN720(DesignPlanConstants.RENDER_SUCCESS);
                } else if ("4".equals(renderTypesStr)) {
                    mainTaskState.setRenderVideo(DesignPlanConstants.RENDER_SUCCESS);
                }
            } else {
                //更新全屋方案返回的结果为空的时候，设置失败的状态，远程调用没有问题的话不会走这里
                if ("1".equals(renderTypesStr)) {
                    mainTaskState.setRenderPic(DesignPlanConstants.RENDER_FAIL);
                } else if ("2".equals(renderTypesStr)) {
                    mainTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
                } else if ("3".equals(renderTypesStr)) {
                    mainTaskState.setRenderN720(DesignPlanConstants.RENDER_FAIL);
                } else if ("4".equals(renderTypesStr)){
                    mainTaskState.setRenderVideo(DesignPlanConstants.RENDER_FAIL);
                }
                mainTaskState.setRemark("所有子任务均渲染失败!");
            }

            //生成时间及更新主任务state表
            saveRenderPicServiceImpl.updateTaskStateTimeConsum(mainTaskState);
            return mainTaskState;
        } else if (subtaskCount <= 1) {
            List<AutoRenderTaskState> subtaskList = designPlanAutoRenderMapper.getSubTaskListByMainTaskId(taskState.getMainTaskId());
            int successed = DesignPlanConstants.TASKSTATE.FAILUE.getValue();
            for (AutoRenderTaskState autoRenderTaskState : subtaskList) {
                if (autoRenderTaskState.getState() == DesignPlanConstants.TASKSTATE.SUCCESS.getValue()){
                    successed = DesignPlanConstants.TASKSTATE.SUCCESS.getValue();
                }
            }
            mainTaskState.setState(successed);
            mainTaskState.setSubtaskCount(mainTaskState.getSubtaskCount() - 1);
            String renderTypesStr = taskState.getRenderTypesStr();
            if (successed == DesignPlanConstants.TASKSTATE.SUCCESS.getValue()) {
                //改变主任务的状态为渲染成功，
                if ("1".equals(renderTypesStr)) {
                    mainTaskState.setRenderPic(DesignPlanConstants.RENDER_SUCCESS);
                } else if ("2".equals(renderTypesStr)) {
                    mainTaskState.setRender720(DesignPlanConstants.RENDER_SUCCESS);
                } else if ("3".equals(renderTypesStr)) {
                    mainTaskState.setRenderN720(DesignPlanConstants.RENDER_SUCCESS);
                } else if ("4".equals(renderTypesStr)){
                    mainTaskState.setRenderVideo(DesignPlanConstants.RENDER_SUCCESS);
                }

            } else {
                //更新全屋方案返回的结果为空的时候，设置失败的状态，远程调用没有问题的话不会走这里
                if ("1".equals(renderTypesStr)) {
                    mainTaskState.setRenderPic(DesignPlanConstants.RENDER_FAIL);
                } else if ("2".equals(renderTypesStr)) {
                    mainTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
                } else if ("3".equals(renderTypesStr)) {
                    mainTaskState.setRenderN720(DesignPlanConstants.RENDER_FAIL);
                } else if ("4".equals(renderTypesStr)){
                    mainTaskState.setRenderVideo(DesignPlanConstants.RENDER_FAIL);
                }
                mainTaskState.setRemark("所有子任务均渲染失败!");
            }
            //生成时间及更新主任务state表
            saveRenderPicServiceImpl.updateTaskStateTimeConsum(mainTaskState);
            return mainTaskState;
        } else if (subtaskCount > 1) {
            synchronized (SaveRenderPicServiceImpl.class) {
                mainTaskState.setSubtaskCount(mainTaskState.getSubtaskCount() - 1);

                designPlanAutoRenderMapper.updateTaskStateByTaskId(mainTaskState);
            }
            return taskState;
        }
        return taskState;
    }

    /**
     * 更新全屋方案主任务为失败状态
     * @param taskState
     */
    private void updateFullHouseTaskState(AutoRenderTaskState taskState) {
        AutoRenderTaskState mainTaskState = designPlanAutoRenderMapper.getStateByTaskId(taskState.getMainTaskId());
        AutoRenderTaskState autoRenderTaskState = new AutoRenderTaskState();
        autoRenderTaskState.setTaskId(mainTaskState.getTaskId());
        autoRenderTaskState.setFailReason(taskState.getFailReason());
        autoRenderTaskState.setFailType(taskState.getFailType());
        autoRenderTaskState.setState(DesignPlanConstants.TASKSTATE.FAILUE.getValue());
        autoRenderTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
        //写先死固定为720单点和装进我家,不确定前面有没有维护这两个值，生成消息的时候需要
        autoRenderTaskState.setRenderTypesStr("2");
        autoRenderTaskState.setTaskType(0);
        long startTime = mainTaskState.getGmtCreate().getTime();
        long endTime = System.currentTimeMillis();
        long spendTimeLong = endTime - startTime;
        int secondTotal = (int) (spendTimeLong / 1000);
        int min = secondTotal / 60;
        int second = secondTotal % 60;
        autoRenderTaskState.setRenderTimeConsuming(min + "分" + second + "秒");
        Integer result = designPlanAutoRenderMapper.updateTaskStateByTaskId(autoRenderTaskState);
        logger.error("更新数据库" + result + "条失败记录....");
    }

    private int createFailMessage(AutoRenderTaskState taskState) {
        // 生成一条渲染失败的消息记录--start
        SysUserMessage sysUserMessage = new SysUserMessage();
        if ("1".equals(taskState.getRenderTypesStr())) {
            sysUserMessage.setTitle("照片级渲染失败");
        } else if ("2".equals(taskState.getRenderTypesStr())) {
            sysUserMessage.setTitle("720°渲染失败");
        } else if ("3".equals(taskState.getRenderTypesStr())) {
            sysUserMessage.setTitle("720°多点渲染失败");
        } else if ("4".equals(taskState.getRenderTypesStr())) {
            sysUserMessage.setTitle("漫游视频渲染失败");
        }
        sysUserMessage.setFailingReason(taskState.getFailReason());
        sysUserMessage.setTaskId(taskState.getTaskId());
        if (0 == taskState.getTaskType().intValue()) {
            sysUserMessage.setContent("装进我家 | " + taskState.getDesignName());
        } else if (1 == taskState.getTaskType().intValue()) {
            sysUserMessage.setContent("替换渲染 | " + taskState.getDesignName());
        }
        sysUserMessage.setCreator(taskState.getCreator());
        sysUserMessage.setModifier(taskState.getModifier());
        sysUserMessage.setMessageType(SysUserMessageConstants.RENDER_TASK_NEWS);
        sysUserMessage.setStatus(SysUserMessageConstants.FAIL);
        sysUserMessage.setUserId(taskState.getOperationUserId());
        sysUserMessage.setPlatformId(taskState.getPlatformId());
        //生成一条渲染失败的消息记录--end
        //插入消息记录到我的消息表
        logger.error("获取sysUserMessage中的userId =>{}"+sysUserMessage.getUserId());
        int add = sysUserMessageService.add(sysUserMessage);
        //发送渲染完成消息
        SocketIOUtil.adviceRenderResultToUser(sysUserMessage);
        return add;
    }
    
	/*public static void main(String[] args) {
		Integer usreId = 580;
		String orderNo = "20180112223634580480";
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1MTYwOTQ1MDY5ODcsInNpZ25mbGF0IjoidXNlcl9INVRva2VuOiIsInVpZCI6NTgwLCJtdHlwZSI6IjYiLCJ1bmFtZSI6IjE3NjAzMDgzOTUxIiwidXR5cGUiOjEsImFwcEtleSI6IjY5ZTYwYjZkZTAxNTQzYmRiMjcyYmQ5Y2E1OTYyNTgxIiwibWVkaWFUeXBlIjoiNiIsInVrZXkiOiI2OWU2MGI2ZGUwMTU0M2JkYjI3MmJkOWNhNTk2MjU4MSIsImlhdCI6MTUxNTgzNTMwNjk4N30.kWvobNgi5GRFeHUD11a1iyIHGxx-1oQYGNts5TWdQV4";
		boolean flag = updatePayOrder(usreId, orderNo, token);
	}*/
	
	public static void main(String[] args) {
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaWduZmxhdCI6InBjXzJiX3VzZXJfdG9rZW46IiwiZXh0IjoxNTE5NjU3MjU3NTgxLCJ1aWQiOjU4MCwibXR5cGUiOiIzIiwidW5hbWUiOiIxNzYwMzA4Mzk1MSIsInV0eXBlIjoxLCJhcHBLZXkiOiJmNjhjNWU0Mi1iNDJmLTQzNzEtOWI3OC05YmQzZTdmOGE1ZTMiLCJzZXNzaW9uVGltZW91dCI6MjU5MjAwLCJ1cGhvbmUiOiIxNzYwMzA4Mzk1MSIsInVrZXkiOiJmNjhjNWU0Mi1iNDJmLTQzNzEtOWI3OC05YmQzZTdmOGE1ZTMiLCJpYXQiOjE1MTk2MTQwNTc1ODF9.MYbhlx15vnNJdIpQ9G1aO7gXlaUWlGtUdCLokpf4Dfw";
		Map<String, Object> map = Jwt.validToken(token);
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}

	}

	/*public static void main(String[] args) {
        String params = "{\"taskSource\":1,\"taskType\":0,\"planRecommendedId\":89226,\"templateId\":10348,\"renderingType\":4,\"renderTaskType\":\"panorama_render\",\"userId\":\"580\",\"totalFee\":500,\"payType\":1,\"operationSource\":1,\"designPlanSceneId\":\"\"}";
        String url = "http://render.sanduspace.cn/render-app/app/render/server/addRenderTask.htm";
        JSONObject json = JSONObject.fromObject(params);
        System.out.println(json.toString());
        for (int i = 1; i <= 300; i++) {
            String result = Utils.HttpPostData(url, json);
            System.out.println("插入第" + i + "条任务:" + result);
        }
    }*/

    // TODO:need update
    private static boolean updatePayOrder(Integer userId, String orderNo, String token) {
        logger.error("updatePayOrder    -------> 进入了退款的方法、");
        String payPath = Utils.getPropertyName("app", "pay.server.url", "");
        String url = payPath + "/web/pay/payOrder/notifyRefund?userId=" + userId + "&orderNo=" + orderNo;
        logger.error("updatePayOrder    -------> url = " + url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId.toString());
        params.put("orderNo", orderNo);
        try {
            String result = Utils.doPostMethod(url, params, token);
            logger.error("result========" + result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            logger.error("jsonObject========" + jsonObject);
            boolean status = (boolean) jsonObject.get("status");
            logger.error("updatePayOrder    -------> status === " + status);
            return status;
        } catch (Exception e) {
            logger.error("updatePayOrder    -------> try ->catch ");
            return false;
        }
    }

    @Override
    public void updateTaskStateCach() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addRenderTasksToDB(List<AutoRenderTask> tasks, LoginUser loginUser) {
        Integer result = designPlanAutoRenderMapper.batchInsertDataList(tasks);
    }

    // 给发布方案调用

    /**
     * @param designPlanRecommended 推荐方案对象
     * @param designPlanId          方案ID design_plan
     * @param loginUser
     */
    @Override
    public void createTaskListByDesignPlan(DesignPlanRecommended designPlanRecommended, LoginUser loginUser,
                                           Integer designPlanId) {
        List<AutoRenderTask> taskList = mappingForDesignTemplateByDesignPlan(designPlanRecommended, loginUser);
        for (AutoRenderTask autoRenderTask : taskList) {
            sysSaveTask(autoRenderTask, loginUser, designPlanId);
        }
        if (taskList != null && taskList.size() > 0) {
            addRenderTasksToDB(taskList, loginUser);
        }
    }

    // 给样板房上架调用
    @Override
    public void createTaskListByDesignTemplet(DesignTemplet template, LoginUser loginUser, Integer designPlanId) {
        List<AutoRenderTask> taskList = mappingForDesignPlanByDesignTemplate(template);
        for (AutoRenderTask autoRenderTask : taskList) {
            sysSaveTask(autoRenderTask, loginUser, designPlanId);
        }
        if (taskList != null && taskList.size() > 0) {
            addRenderTasksToDB(taskList, loginUser);
        }
    }

    /**
     * 从任务表获取任务插入状态表 并删除任务表任务
     *
     * @param maxSize
     * @param loginUser
     * @return
     */
    private synchronized List<AutoRenderTask> getRenderTasks(Integer maxSize, LoginUser loginUser) {
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
            sysSaveState(taskState, loginUser);
            taskState.setGmtCreate(task2.getGmtCreate());
            taskState.setGmtModified(task2.getGmtModified());
            addTaskStateToDB(taskState);
            deleteRenderTaskByTaskId(task2.getId());
        }

        return taskList;
    }

    private synchronized List<AutoRenderTask> findReplaceProductTaskList(Integer maxSize, LoginUser loginUser) {
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
            // 添加三个字段 add by yangzhun
            taskState.setOperationUserId(operationUserId);
            taskState.setTaskType(taskType);
            taskState.setRenderTypesStr(renderTypesStr);
            sysSaveState(taskState, loginUser);
            taskState.setGmtCreate(task2.getGmtCreate());
            taskState.setGmtModified(task2.getGmtModified());
            addTaskStateToDB(taskState);// 插入任务状态表
            deleteRenderTaskByTaskId(task2.getId());
        }

        return taskList;
    }

    /**
     * 存储系统字段
     */
    private void sysSaveTask(AutoRenderTask modelTask, LoginUser loginUser, Integer designPlanId) {
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
    private void sysSaveState(AutoRenderTaskState modelState, LoginUser loginUser) {
        if (modelState != null) {
            if (modelState.getId() == null) {
                // modelState.setGmtCreate(new Date());
                modelState.setCreator(loginUser.getLoginName());
            }
            modelState.setModifier(loginUser.getLoginName());
        }
        // modelState.setGmtModified(new Date());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.nork.design.service.DesignPlanAutoRenderService#getrenderPicByPage(com.
     * nork.design.model.ThumbData)
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

        List<DesignPlanRecommended> recommendedList = designPlanRecommendedMapperV2.getStatusByIds(ids);
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

                if (Constants.RECOMMENDED_TYPE_SHARE == recommended.getRecommendedType().intValue()) {
                    temp.setPubSt(recommended.getIsRelease());
                    continue;
                }
                if (Constants.RECOMMENDED_TYPE_ONE_KEY_PUB == recommended.getRecommendedType().intValue()) {
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
     *
     * @param designPlanRenderScene
     * @return ResponseEnvelope
     */
    public ResponseEnvelope<ThumbData> getrenderPicByPageV2(DesignPlanRenderScene designPlanRenderScene,Integer type) {

        ResponseEnvelope<ThumbData> envelope = new ResponseEnvelope<ThumbData>();

        List<ThumbData> resList = new ArrayList<ThumbData>();
        List<DesignPlanRenderScene> list = null;
        int count = 0;
        boolean isInternalUsers = designPlanRenderScene.isInternalUsers();
        Integer spaceFunctionId = designPlanRenderScene.getSpaceFunctionId();
        if (spaceFunctionId != null && SpaceCommonConstant.FULL_HOUSE_SPACE_FUNCTION_ID.equals(spaceFunctionId.toString())) {
            //如果是查全屋设计方案
            count = fullHouseDesignPlanMapper.getFullHousePlanCountByUserId(designPlanRenderScene.getUserId());
        } else {
            //查单空间方案效果图
            if (type.intValue() == 1) {
                count = designPlanRenderSceneMapper.getAllVendorCount(designPlanRenderScene);
            } else {
                count = designPlanRenderSceneMapper.getVendorCountV2(designPlanRenderScene);
            }
        }
        if (count <= 0) {
            envelope.setDatalist(resList);
            envelope.setTotalCount(0);
            return envelope;
        }
        if (spaceFunctionId != null && SpaceCommonConstant.FULL_HOUSE_SPACE_FUNCTION_ID.equals(spaceFunctionId.toString())) {
            //如果是查全屋设计方案
            resList = fullHouseDesignPlanMapper.getFullHousePlanListByUserId(designPlanRenderScene);
            envelope.setDatalist(resList);
            envelope.setTotalCount(count);
            return envelope;
        } else {
            //查单空间方案效果图
            if (type.intValue() == 1) {
                list = designPlanRenderSceneMapper.getAllVendorList(designPlanRenderScene);
            } else {
                list = designPlanRenderSceneMapper.getVendorListV2(designPlanRenderScene);
            }
        }
        if (list == null || list.size() <= 0) {
            envelope.setDatalist(resList);
            envelope.setTotalCount(0);
            return envelope;
        }

        List<Long> ids = new ArrayList<Long>();
        for (int i = 0; i < list.size(); i++) {
            ids.add((long) list.get(i).getId());
        }
        List<DesignPlanRecommended> recommendedList = designPlanRecommendedMapperV2.getStatusByIds(ids);
        for (DesignPlanRenderScene scene : list) {
            ThumbData thumbData = new ThumbData();
            thumbData.setCpId(scene.getId());
            thumbData.setFailCause(scene.getFailCause());
            thumbData.setCheckUserName(scene.getCheckUserName());
            this.coverPicHandling(scene, thumbData);
            if (isInternalUsers) {
                thumbData.setSpacecode(scene.getSpaceCode());
            }
            if (recommendedList != null && recommendedList.size() > 0) {
                for (DesignPlanRecommended recommended : recommendedList) {
                    if (recommended.getPlanId().longValue() != scene.getId()) {
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
     *
     * @param scene
     * @param thumbData
     */
    public void coverPicHandling(DesignPlanRenderScene scene, ThumbData thumbData) {
        if (scene == null || thumbData == null) {
            return;
        }
        if (scene.getCoverPicId() != null && scene.getCoverPicId().intValue() > 0) {
            ResRenderPic coverPic = resRenderPicService.get(scene.getCoverPicId());
            if (coverPic != null) {
                thumbData.setName(scene.getPlanName());
                this.dataFilling(coverPic, thumbData);
                return;
            }
        }
        List<ResRenderPic> picList = new ArrayList<>(); // 查询该设计方案的全部渲染缩略图列表
        ResRenderPicQO resRenderPicQO = new ResRenderPicQO();
        resRenderPicQO.setCreateUserId(scene.getUserId());
        resRenderPicQO.setDesignSceneId(scene.getId());
        resRenderPicQO.setIsDeleted(0);
        List<String> fileKeyLists = new ArrayList<String>();
        fileKeyLists.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
        fileKeyLists.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
        resRenderPicQO.setFileKeys(fileKeyLists);
        picList = resRenderPicService.selectListByFileKeys(resRenderPicQO);
        if (picList != null && picList.size() > 0) {
            int id = 0;
            for (ResRenderPic resRenderPic : picList) {
                if (id > resRenderPic.getId().intValue()) {
                    continue;
                }
                thumbData.setName(scene.getPlanName());
                this.dataFilling(resRenderPic, thumbData);
                id = resRenderPic.getId();
            }
        }
    }

    /**
     * 对thumbData 进行数据填充
     *
     * @param resRenderPic
     * @param thumbData
     */
    public void dataFilling(ResRenderPic resRenderPic, ThumbData thumbData) {
        if (resRenderPic == null || thumbData == null) {
            return;
        }
        thumbData.setThumbId(resRenderPic.getId());
        // thumbData.setName(resRenderPic.getDesignPlanName());
        thumbData.setPic(resRenderPic.getPicPath());
        thumbData.setType(resRenderPic.getSpaceType());
        thumbData.setArea(resRenderPic.getArea());
        thumbData.setPlanId(resRenderPic.getBusinessId()==null?null:resRenderPic.getBusinessId().longValue());
        if (resRenderPic.getGmtCreate() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            thumbData.setCtime(simpleDateFormat.format(resRenderPic.getGmtCreate()));
        }
        if (RenderTypeCode.COMMON_PICTURE_LEVEL == resRenderPic.getRenderingType().intValue()) {
            thumbData.setRenderPic(true);
        }
        if (RenderTypeCode.COMMON_720_LEVEL == resRenderPic.getRenderingType().intValue()) {
            thumbData.setRender720(true);
        }
        if (RenderTypeCode.ROAM_720_LEVEL == resRenderPic.getRenderingType().intValue()) {
            thumbData.setRenderRoam(true);
        }
        if (RenderTypeCode.COMMON_VIDEO == resRenderPic.getRenderingType().intValue()) {
            thumbData.setRenderVideo(true);
        }
    }

    @Override
    public Object updateCoverPic(String picId, String planId, String msgId, String designPlanType) {
        if (StringUtils.isEmpty(picId) || StringUtils.isEmpty(planId) || StringUtils.isEmpty(msgId)) {
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
        if (scene == null) {
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
        List<AutoRenderTask> taskList = findReplaceProductTaskList(maxSize, loginUser);
        for (AutoRenderTask task : taskList) {
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
        logger.info("Redis list size ====>" + result);
        return result.intValue();
    }

    @Override
    public AutoRenderTask getRedisTaskList(Integer maxSize, LoginUser loginUser, String renderMachineIp,
                                           Integer renderLevel, String renderProgramVersion) throws UnknownHostException {
        Gson gson = new Gson();
        String jsonStr = null;
        List<String> result = JedisUtils.getBrpopList(REDIS_TASK_LIST);
        if (result != null && result.size() > 0) {
            jsonStr = result.get(1);
        }
        AutoRenderTask task = gson.fromJson(jsonStr, AutoRenderTask.class);
        Integer taskId = null;
        InetAddress addr = InetAddress.getLocalHost();
        String hostIp = addr.getHostAddress().toString();
        String hostName = addr.getHostName().toString();
        if (task != null) {
            // 如果该任务在任务状态表里已经存在，说明该任务已经执行过，则不返回改任务，直接删除该任务在Redis 队列
            AutoRenderTaskState autoRenderTaskState = this.getStateByTaskId(task.getId());
            if (autoRenderTaskState != null && autoRenderTaskState.getTaskId() > 0) {
                return null;
            }

            taskId = task.getId();
            AutoRenderTask task2 = designPlanAutoRenderMapper.getRenderTaskById(taskId);
            logger.error("getRedisTaskList ===>" + task2.getId());
            if (task2 != null) {
                if (AutoRenderTaskConstant.PLAN_FULL_HOUSE_TYPE.equals(task2.getPlanHouseType())
                        && !task2.getId().equals(task2.getMainTaskId())) {
                    //任务类型为全屋方案 并且任务id与主任务id不同，则为全屋子任务，
                    //如果是全屋方案的子任务，则判断主任务的状态，为失败的则不进行子任务的渲染
                    AutoRenderTaskState mainTaskState = designPlanAutoRenderMapper.getStateByTaskId(task2.getMainTaskId());
                    if (DesignPlanConstants.TASKSTATE.FAILUE.equals(mainTaskState.getState())) {
                        return null;
                    }
                }

                AutoRenderTaskState taskState = new AutoRenderTaskState();
                BeanUtils.copyProperties(task2,taskState);

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

                taskState.setGmtCreate(new Date());
                taskState.setGmtModified(new Date());
                taskState.setHostIp(hostIp);
                taskState.setHostName(hostName);
                taskState.setRenderMachineIp(renderMachineIp);
                taskState.setRenderLevel(renderLevel);
                taskState.setRenderProgramVersion(renderProgramVersion);

                addTaskStateToDB(taskState);
                deleteRenderTaskByTaskId(task2.getId());
            }
            //如果主任务id和任务id相同，则表示方案为主任务，不进入渲染机，删除缓存中的任务,但是得复制到state表
            if(task.getId().equals(task.getMainTaskId())) {
                return null;
            }
        }
        return task;
    }

    @Override
    public int addRedisReplaceList(AutoRenderTask autoRenderTask) {
        Gson gson = new Gson();
        String json = gson.toJson(autoRenderTask);
        Long result = JedisUtils.listAdd(REDIS_TASK_REPLACE_LIST, json);
        logger.info("Redis list size ====>" + result);
        return result.intValue();
    }

    @Override
    public AutoRenderTask getRedisReplaceTaskList(Integer maxSize, LoginUser loginUser, String renderMachineIp,
                                                  Integer renderLevel, String renderProgramVersion) throws UnknownHostException {
        Gson gson = new Gson();
        String jsonStr = null;
        InetAddress addr = InetAddress.getLocalHost();
        String hostIp = addr.getHostAddress().toString();
        String hostName = addr.getHostName().toString();
        List<String> result = JedisUtils.getBrpopList(REDIS_TASK_REPLACE_LIST);
        if (result != null && result.size() > 0) {
            jsonStr = result.get(1);
        }
        AutoRenderTask task = gson.fromJson(jsonStr, AutoRenderTask.class);
        Integer taskId = null;
        // 如果该任务在任务状态表里已经存在，说明该任务已经执行过，则不返回改任务，直接删除该任务在Redis 队列
        if (task != null) {
            AutoRenderTaskState autoRenderTaskState = this.getStateByTaskId(task.getId());
            if (autoRenderTaskState != null && autoRenderTaskState.getTaskId() > 0) {
                return null;
            }
        }
        if (task != null) {
            taskId = task.getId();
            AutoRenderTask task2 = designPlanAutoRenderMapper.getRenderTaskById(taskId);
            logger.error("getRedisTaskList ===>" + task2.getId());
            if (task2 != null) {

                AutoRenderTaskState taskState = new AutoRenderTaskState();
                BeanUtils.copyProperties(task2,taskState);

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
                taskState.setGmtCreate(new Date());
                taskState.setGmtModified(new Date());
                taskState.setHostIp(hostIp);
                taskState.setHostName(hostName);
                taskState.setRenderMachineIp(renderMachineIp);
                taskState.setRenderLevel(renderLevel);
                taskState.setRenderProgramVersion(renderProgramVersion);
                addTaskStateToDB(taskState);// 插入任务状态表
                deleteRenderTaskByTaskId(task2.getId());
            }
            //如果主任务id和任务id相同，则表示方案为主任务，不进入渲染机，删除缓存中的任务,但是得复制到state表
            if(task.getId().equals(task.getMainTaskId())) {
                return null;
            }
        }
        return task;
    }

    @Override
    public void getRedisStickList(Integer taskId) {
        AutoRenderTask autoRenderTask = designPlanAutoRenderMapper.getRenderTaskById(taskId);
        Gson gson = new Gson();
        String json = gson.toJson(autoRenderTask);
        Long result = JedisUtils.listLAdd(REDIS_RENDER_TASK_STICK, json);
        logger.error("Redis list size ====>" + result);

    }



    @Override
    public AutoRenderTask getRedisStickTaskList(Integer maxSize, LoginUser loginUser, String renderMachineIp,
                                                Integer renderLevel, String renderProgramVersion) throws UnknownHostException {
        Gson gson = new Gson();
        String jsonStr = null;
        InetAddress addr = InetAddress.getLocalHost();
        String hostIp = addr.getHostAddress().toString();
        String hostName = addr.getHostName().toString();
        List<String> result = JedisUtils.getBrpopList(REDIS_RENDER_TASK_STICK);
        if (result != null && result.size() > 0) {
            jsonStr = result.get(1);
        }
        AutoRenderTask task = gson.fromJson(jsonStr, AutoRenderTask.class);
        Integer taskId = null;
        // JSONObject jsonobject = JSONObject.fromObject(jsonStr);
        // AutoRenderTask autoRenderTask=
        // (AutoRenderTask)JSONObject.toBean(jsonobject,AutoRenderTask.class);
        if (task != null) {
            taskId = task.getId();
            AutoRenderTask task2 = designPlanAutoRenderMapper.getRenderTaskById(taskId);
            logger.error("getRedisTaskList ===>" + task.getId());
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
                taskState.setRenderMachineIp(renderMachineIp);
                taskState.setRenderLevel(renderLevel);
                taskState.setRenderProgramVersion(renderProgramVersion);
                taskState.setPlatformId(task2.getPlatformId());
                addTaskStateToDB(taskState);// 插入任务状态表
                deleteRenderTaskByTaskId(task2.getId());
            }
        }
        return task;
    }

    @Override
    public AutoRenderTaskState getStateByTaskId(Integer taskId) {
        logger.error("AAAAAAutoRenderTaskState--------getStateByTaskId----------taskId----------" + taskId);
        return designPlanAutoRenderMapper.getStateByTaskId(taskId);
    }
}
