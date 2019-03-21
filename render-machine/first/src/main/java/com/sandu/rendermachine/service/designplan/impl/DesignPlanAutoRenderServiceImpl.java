package com.sandu.rendermachine.service.designplan.impl;

import com.google.gson.Gson;
import com.sandu.rendermachine.common.constant.DesignPlanConstants;
import com.sandu.rendermachine.common.constant.SysUserMessageConstants;
import com.sandu.rendermachine.common.constant.SystemCommonConstant;
import com.sandu.rendermachine.common.util.JedisUtils;
import com.sandu.rendermachine.common.util.Utils;
import com.sandu.rendermachine.dao.designplan.DesignPlanAutoRenderMapper;
import com.sandu.rendermachine.model.render.AutoRenderTask;
import com.sandu.rendermachine.model.render.AutoRenderTaskState;
import com.sandu.rendermachine.model.response.ResponseEnvelope;
import com.sandu.rendermachine.model.user.LoginUser;
import com.sandu.rendermachine.model.user.SysUser;
import com.sandu.rendermachine.model.user.SysUserMessage;
import com.sandu.rendermachine.model.vo.RenderTaskVO;
import com.sandu.rendermachine.service.designplan.DesignPlanAutoRenderService;
import com.sandu.rendermachine.service.user.SysUserMessageService;
import com.sandu.rendermachine.service.user.SysUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static com.sandu.rendermachine.common.constant.SystemCommonConstant.REDIS_TASK_LIST;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 5:44 2018/4/17 0017
 * @Modified By:
 */
@Slf4j
@Service("designPlanAutoRenderService")
public class DesignPlanAutoRenderServiceImpl implements DesignPlanAutoRenderService {

    private static final Gson Gson = new Gson();
    public static final String REDIS_RENDER_TASK_STICK = "renderTaskStick";
    public static final String REDIS_TASK_REPLACE_LIST = "taskReplaceList";

    @Autowired
    private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;
    @Autowired
    private SysUserMessageService sysUserMessageService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 渲染机获取任务
     * @param renderTaskVO
     * @param token
     * @return
     */
    @Override
    public List<AutoRenderTask> getRenderTaskList(RenderTaskVO renderTaskVO, String token) throws UnknownHostException {
        if (renderTaskVO == null) {
            return null;
        }
        Integer preTaskId = renderTaskVO.getPreTaskId();
        Integer maxSize = renderTaskVO.getMaxSize();
        String msgId = renderTaskVO.getMsgId();
        Integer renderLevel = renderTaskVO.getRenderLevel();
        String renderMachineIp = renderTaskVO.getRenderMachineIp();
        String renderProgramVersion = renderTaskVO.getRenderProgramVersion();

        if (preTaskId != null && preTaskId > 0) {
            AutoRenderTaskState autoRenderTaskState = designPlanAutoRenderMapper.getStateByTaskId(preTaskId);
            if (autoRenderTaskState != null) {
                if (autoRenderTaskState.getState().intValue() == 2 ) {
                    AutoRenderTaskState taskState = new AutoRenderTaskState();
                    taskState.setPlanId(autoRenderTaskState.getPlanId());
                    taskState.setTemplateId(autoRenderTaskState.getTemplateId());
                    taskState.setFailReason("服务器响应超时，请重新渲染!");
                    taskState.setFailType(88);
                    taskState.setTaskId(preTaskId);
                    this.updateTaskState(taskState,token);
                }
            }
        }

        // 置顶渲染为最高优先级 ，产品替换任务为第二优先级，如果不存在产品替换任务，才去取自动渲染任务
        LoginUser loginUser = new LoginUser();
        loginUser.setId(-1);
        loginUser.setLoginName("AUTO_USER");

        // 置顶任务 最高优先级
        AutoRenderTask stickRenderTask = this.getRedisStickTaskList(maxSize, loginUser,renderMachineIp,renderLevel,renderProgramVersion);
        List<AutoRenderTask> taskList = new ArrayList<AutoRenderTask>();

        if (stickRenderTask != null && stickRenderTask.getId() > 0) {
            taskList.add(stickRenderTask);
        } else {
            do {
                // 替换任务 第二优先级
                /**
                 * 三种case 1, 队列里有任务，但没执行 2, 队列里没任务 3, 对列里有任务，但已经执行过
                 **/
                AutoRenderTask replaceRenderTask = this.getRedisReplaceTaskList(maxSize, loginUser,renderMachineIp,renderLevel,renderProgramVersion);
                // 查看渲染状态是否有存在 如果存在取下一条
                if (replaceRenderTask != null) {
                    log.info("getRedisReplaceTaskList get replace taskId=>" + replaceRenderTask.getId());
                    taskList.add(replaceRenderTask);
                }
            } while ((JedisUtils.getSizeOfList(SystemCommonConstant.REDIS_TASK_REPLACE_LIST) > 0
                    && taskList.size() == 0));

            if (taskList.size() == 0) {
                do {
                    // 自动渲染任务 第三优先级
                    AutoRenderTask autoRenderTask = this.getRedisTaskList(maxSize, loginUser,renderMachineIp,renderLevel,renderProgramVersion);
                    // 查看渲染状态是否有存在 如果存在取下一条
                    if (autoRenderTask != null) {
                        log.info("getRedisTaskList get replace taskId=>" + autoRenderTask.getId());
                        taskList.add(autoRenderTask);
                    }
                } while ((JedisUtils.getSizeOfList(REDIS_TASK_LIST) > 0 && taskList.size() == 0));
            }
        }
        return taskList;
    }


    /**
     * 更新失败任务
     */
    @Override
    public Object updateTaskState(AutoRenderTaskState autoRenderTaskState, String token) {
        log.info("更新状态表方案ID" + autoRenderTaskState.getPlanId() + "样板房ID" + autoRenderTaskState.getTemplateId());
        log.info("更新状态表任务ID" + autoRenderTaskState.getTaskId());
        Integer taskId = autoRenderTaskState.getTaskId();
        AutoRenderTask RenderTask = designPlanAutoRenderMapper.getRenderTaskById(taskId);
        autoRenderTaskState.setPlatformId(RenderTask.getPlatformId());
        autoRenderTaskState.setState(DesignPlanConstants.TASKSTATE.FAILUE.getValue());
        if ("1".equals(RenderTask.getRenderTypesStr())) {
            autoRenderTaskState.setRenderPic(DesignPlanConstants.RENDER_FAIL);
        } else if ("2".equals(RenderTask.getRenderTypesStr())) {
            autoRenderTaskState.setRender720(DesignPlanConstants.RENDER_FAIL);
        } else if ("3".equals(RenderTask.getRenderTypesStr())) {
            autoRenderTaskState.setRenderN720(DesignPlanConstants.RENDER_FAIL);
        } else if ("4".equals(RenderTask.getRenderTypesStr())) {
            autoRenderTaskState.setRenderVideo(DesignPlanConstants.RENDER_FAIL);
        }
        autoRenderTaskState.setState(0);
        AutoRenderTaskState taskState = designPlanAutoRenderMapper.getStateByTaskId(taskId);
        long startTime = taskState.getGmtCreate().getTime();
        long endTime = System.currentTimeMillis();
        long spendTimeLong = endTime - startTime;
        int secondTotal = (int) (spendTimeLong / 1000);
        int min = secondTotal / 60;
        int second = secondTotal % 60;
        autoRenderTaskState.setRenderTimeConsuming(min + "分" + second + "秒");
        Integer result = designPlanAutoRenderMapper.updateTaskStateByTaskId(autoRenderTaskState);
        log.info("更新数据库" + result + "条失败记录....");

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
        sysUserMessage.setFailingReason(autoRenderTaskState.getFailReason());
        sysUserMessage.setTaskId(taskId);
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
        int id = sysUserMessageService.add(sysUserMessage);
        log.info("insert a fail taskMessage into sys_user_message---->id：" + id);
        log.info("开始请求支付 退款===============================>" + "userId=" + RenderTask.getOperationUserId()
                + "orderNO=>" + RenderTask.getOrderNumber());
        boolean flag = updatePayOrder(RenderTask.getOperationUserId(), RenderTask.getOrderNumber(), token);
        log.info("进入更新失败任务 flag ======================>" + flag);
        if (!flag) {
            return new ResponseEnvelope<>(false, "退款失败！");
        }
        return new ResponseEnvelope<>(true, "退款成功！");

    }

    private static boolean updatePayOrder(Integer userId, String orderNo, String token) {
        log.info("updatePayOrder    -------> 进入了退款的方法、");
        String payPath = Utils.getPropertyName("app", "pay.server.url", "");
        String url = payPath + "/web/pay/payOrder/notifyRefund?userId=" + userId + "&orderNo=" + orderNo;
        log.info("updatePayOrder    -------> url = " + url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId.toString());
        params.put("orderNo", orderNo);
        try {
            String result = Utils.doPostMethod(url, params, token);
            log.info("result========" + result);
            JSONObject jsonObject = JSONObject.fromObject(result);
            log.info("jsonObject========" + jsonObject);
            boolean status = (boolean) jsonObject.get("status");
            log.info("updatePayOrder    -------> status === " + status);
            return status;
        } catch (Exception e) {
            log.error("updatePayOrder-------> try ->catch Exception:"+e);
            return false;
        }
    }

    @Override
    public AutoRenderTask getRedisStickTaskList(Integer maxSize, LoginUser loginUser, String renderMachineIp,
                                                Integer renderLevel, String renderProgramVersion) throws UnknownHostException {
        String jsonStr = null;
        InetAddress addr = InetAddress.getLocalHost();
        String hostIp = addr.getHostAddress().toString();
        String hostName = addr.getHostName().toString();
        List<String> result = JedisUtils.getBrpopList(REDIS_RENDER_TASK_STICK);
        if (result != null && result.size() > 0) {
            jsonStr = result.get(1);
        }
        AutoRenderTask task = Gson.fromJson(jsonStr, AutoRenderTask.class);
        Integer taskId;
        if (task != null) {
            taskId = task.getId();
            AutoRenderTask task2 = designPlanAutoRenderMapper.getRenderTaskById(taskId);
            log.info("getRedisTaskList ===>" + task.getId());
            if (task2 != null) {
                Integer operationUserId = task2.getOperationUserId();
                SysUser sysUser = sysUserService.get(operationUserId);
                LoginUser loginUser2 = sysUser.toLoginUser();
                Integer taskType = task2.getTaskType();
                String renderTypesStr = task2.getRenderTypesStr();
                AutoRenderTaskState taskState = new AutoRenderTaskState();
                taskState.setPlanId(task2.getPlanId());
                taskState.setTemplateId(task2.getTemplateId());

                setRenderType(task2, taskState);

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

    private void setRenderType(AutoRenderTask task2, AutoRenderTaskState taskState) {
        if (task2.getRenderTypesStr().equals("1")) {
            taskState.setRenderPic(DesignPlanConstants.RENDERING);
        } else if (task2.getRenderTypesStr().equals("2")) {
            taskState.setRender720(DesignPlanConstants.RENDERING);
        } else if (task2.getRenderTypesStr().equals("3")) {
            taskState.setRenderN720(DesignPlanConstants.RENDERING);
        } else if (task2.getRenderTypesStr().equals("4")) {
            taskState.setRenderVideo(DesignPlanConstants.RENDERING);
        }
    }

    @Override
    public AutoRenderTask getRedisReplaceTaskList(Integer maxSize, LoginUser loginUser, String renderMachineIp,
                          Integer renderLevel, String renderProgramVersion) throws UnknownHostException {
        String jsonStr = null;
        InetAddress addr = InetAddress.getLocalHost();
        String hostIp = addr.getHostAddress().toString();
        String hostName = addr.getHostName().toString();
        List<String> result = JedisUtils.getBrpopList(REDIS_TASK_REPLACE_LIST);
        if (result != null && result.size() > 0) {
            jsonStr = result.get(1);
        }
        AutoRenderTask task = Gson.fromJson(jsonStr, AutoRenderTask.class);
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
            log.info("getRedisTaskList ===>" + task2.getId());
            if (task2 != null) {
                Integer operationUserId = task2.getOperationUserId();
                SysUser sysUser = sysUserService.get(operationUserId);
                LoginUser loginUser2 = sysUser.toLoginUser();
                Integer taskType = task2.getTaskType();
                String renderTypesStr = task2.getRenderTypesStr();
                AutoRenderTaskState taskState = new AutoRenderTaskState();
                taskState.setPlanId(task2.getPlanId());
                taskState.setTemplateId(task2.getTemplateId());

                setRenderType(task2, taskState);

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
    public void addTaskStateToDB(AutoRenderTaskState taskState) {
        designPlanAutoRenderMapper.addTaskStateToDB(taskState);
    }

    @Override
    public void deleteRenderTaskByTaskId(Integer taskId) {
        designPlanAutoRenderMapper.deleteRenderTaskByTaskId(taskId);
        log.info("删除渲染任务 taskId" + taskId);
    }

    @Override
    public AutoRenderTaskState getStateByTaskId(Integer taskId) {
        log.info("AutoRenderTaskState--------getStateByTaskId----------taskId-" + taskId);
        return designPlanAutoRenderMapper.getStateByTaskId(taskId);
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
            log.info("getRedisTaskList ===>" + task2.getId());
            if (task2 != null) {
                AutoRenderTaskState taskState = new AutoRenderTaskState();
                Integer operationUserId = task2.getOperationUserId();
                SysUser sysUser = sysUserService.get(operationUserId);
                LoginUser loginUser2 = sysUser.toLoginUser();
                taskState.setPlanId(task2.getPlanId());
                taskState.setTemplateId(task2.getTemplateId());
                taskState.setDesignPlanId(task2.getDesignPlanId());

                setRenderType(task2, taskState);

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
                taskState.setRenderMachineIp(renderMachineIp);
                taskState.setRenderLevel(renderLevel);
                taskState.setRenderProgramVersion(renderProgramVersion);
                taskState.setPlatformId(task2.getPlatformId());
                addTaskStateToDB(taskState);
                deleteRenderTaskByTaskId(task2.getId());
            }
        }
        return task;
    }
}
