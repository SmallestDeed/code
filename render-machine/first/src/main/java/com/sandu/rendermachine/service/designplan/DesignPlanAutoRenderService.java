package com.sandu.rendermachine.service.designplan;

import com.sandu.rendermachine.model.render.AutoRenderTask;
import com.sandu.rendermachine.model.render.AutoRenderTaskState;
import com.sandu.rendermachine.model.user.LoginUser;
import com.sandu.rendermachine.model.vo.RenderTaskVO;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 5:43 2018/4/17 0017
 * @Modified By:
 */
public interface DesignPlanAutoRenderService {

    List<AutoRenderTask> getRenderTaskList(RenderTaskVO renderTaskVO,String token) throws UnknownHostException;

    //取消发布时时，通过方案ID更新已渲染完成的任务状态。
    Object updateTaskState(AutoRenderTaskState autoRenderTaskState, String token);

    AutoRenderTask getRedisStickTaskList(Integer maxSize,LoginUser loginUser,
                   String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException;

    //把任务状态插入DB
    void addTaskStateToDB(AutoRenderTaskState taskState);

    //取消发布时，删除还没有开始渲染的任务
    void deleteRenderTaskByTaskId(Integer taskId);

    AutoRenderTask getRedisReplaceTaskList(Integer maxSize,LoginUser loginUser
            ,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException;

    AutoRenderTaskState getStateByTaskId(Integer taskId);

    AutoRenderTask getRedisTaskList(Integer maxSize,LoginUser loginUser
            ,String renderMachineIp,Integer renderLevel,String renderProgramVersion) throws UnknownHostException;

}
