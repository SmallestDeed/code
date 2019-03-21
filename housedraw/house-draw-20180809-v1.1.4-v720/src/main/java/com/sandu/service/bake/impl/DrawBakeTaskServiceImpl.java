package com.sandu.service.bake.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.sandu.api.house.model.*;
import com.sandu.api.house.service.DrawBaseHouseService;
import com.sandu.api.house.service.DrawSpaceCommonService;
import com.sandu.common.constant.ResponseEnum;
import com.sandu.exception.BusinessException;
import com.sandu.mail.DefaultsFreeMailService;
import com.sandu.service.bake.dao.DrawBakeRecordMapper;
import com.sandu.util.http.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sandu.api.house.bo.DrawBakeTaskBO;
import com.sandu.api.house.service.DrawBakeTaskService;
import com.sandu.common.constant.house.DrawBaseHouseConstant;
import com.sandu.service.bake.dao.DrawBakeTaskDetailMapper;
import com.sandu.service.bake.dao.DrawBakeTaskMapper;
import com.sandu.service.house.dao.BaseHouseMapper;
import com.sandu.service.house.dao.DrawBaseHouseMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: 烘焙任务逻辑层
 * <p>
 * Company:Sandu Copyright:Copyright(c)2017
 *
 * @author 何文
 * @version 1.0
 * @date 2017/12/29
 */

@Slf4j
@Service
public class DrawBakeTaskServiceImpl implements DrawBakeTaskService {

    @Autowired
    private DrawBakeTaskMapper drawBakeTaskMapper;

    @Autowired
    private DrawBakeTaskDetailMapper drawBakeTaskDetailMapper;

    @Autowired
    private DrawBaseHouseMapper drawBaseHouseMapper;

    @Autowired
    private BaseHouseMapper baseHouseMapper;

    @Autowired
    private DrawBakeRecordMapper drawBakeRecordMapper;

    @Autowired
    private DefaultsFreeMailService defaultsFreeMailService;

    @Autowired
    DrawSpaceCommonService drawSpaceCommonService;

    @Autowired
    DrawBaseHouseService drawBaseHouseService;

    @Value("${defaults.bake.timeout:30}")
    private Integer timeout;

    @Value("${defaults.bake.fail.count:5}")
    private Integer failCount;

    @Value("${defaults.timer.reset.count:3}")
    private Integer resetCount;

    @Value("#{'${mail.bake.fail.subscribe}'.split(',')}")
    private List<String> targets;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDrawBakeTask(DrawBakeTask task) {
        drawBakeTaskMapper.save(task);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void batchSaveDrawBakeTaskDetail(List<DrawBakeTaskDetail> drawBakeTaskDetails) {
        drawBakeTaskDetailMapper.batchSave(drawBakeTaskDetails);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<DrawBakeTaskBO> listBakeTask(String queueName, Long taskId) {
        List<DrawBakeTaskBO> subTasks = drawBakeTaskMapper.list(taskId, queueName, failCount, resetCount);

        // 异常任务，重新获取新的子任务
        if (isErrorSubTask(subTasks, taskId)) {
            return listBakeTask(queueName, taskId);
        }

        if (subTasks == null || subTasks.isEmpty()) {
            return Lists.newArrayList();
        }

        // 主任务状态 ==> 烘焙中
        DrawBakeTaskBO subTask = subTasks.get(0);

        // 修改任务详情 ==> 烘焙中
        List<DrawBakeTaskDetail> taskDetails = subTask.getDrawBakeTaskDetail();
        if (taskDetails != null && !taskDetails.isEmpty()) {
            DrawBakeTaskDetail taskDetail = taskDetails.get(0);
            drawBakeTaskDetailMapper.updateTaskDetailStatus(taskDetail.getId(), DrawBaseHouseConstant.BAKE_TASK_STATUS_PROCESSING);
        }

        // 更改户型状态 ==> 烘焙中
        drawBaseHouseMapper.updateDrawHouseStatus(DrawBaseHouseConstant.HOUSE_BAKE_PROCESSING, null, subTask.getHouseId());

        // base_house烘焙中
        baseHouseMapper.updateHouseDealStatus(DrawBaseHouseConstant.DEAL_STATUS_BAKE, subTask.getHouseId());

        log.info("{}(ip), {}(queueName)获取烘焙子任务{}(taskDetailId)", HttpContextUtils.getIpAddress(), queueName, taskDetails != null ? taskDetails.get(0).getId() : null);

        return subTasks;
    }

    /**
     * 检查一个户型存在多个烘焙任务时，并且其中一个户型的空间是被删除的，
     * 那么这个户型默认不需要烘焙，只需要烘焙户型里没有删除的空间
     *
     * @param subTasks
     * @param taskId
     * @return
     */
    public boolean isErrorSubTask(List<DrawBakeTaskBO> subTasks, Long taskId) {
        if (subTasks != null && !subTasks.isEmpty()) {
            DrawBakeTaskBO subTask = subTasks.get(0);
            List<DrawBakeTaskDetail> taskDetails = subTask.getDrawBakeTaskDetail();
            if (taskDetails != null && !taskDetails.isEmpty()) {
                DrawBakeTaskDetail taskDetail = taskDetails.get(0);
                // 验证空间，删除的空间不用烘焙
                DrawSpaceCommon drawSpaceCommon = drawSpaceCommonService.getBakeSpaceCommon(taskDetail.getSpaceId());
                if (drawSpaceCommon == null) {
                    log.warn("{}(spaceId)空间未找到或被删除", subTask.getSpaceId());
                    drawBakeTaskDetailMapper.updateTaskDetailStatus(taskDetail.getId(), DrawBaseHouseConstant.BAKE_TASK_STATUS_SUCCESS);

                    // 继续获取户型任务
                    return true;
                }
            }
        } else {
            // 是否全部的空间处理完成
            DrawBakeTask task = drawBakeTaskMapper.getTaskById(taskId);
            if (task != null && task.getHouseId() != null) {
                // 剩余需要烘焙的空间
                int taskCount = drawBakeTaskMapper.getTaskDetailCount2(task.getId());
                if (taskCount <= 0) {
                    // 主任务
                    task.setStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_SUCCESS);
                    drawBakeTaskMapper.update(task);

                    // draw_base_house ==> house_status(HOUSE_BAKE_SUCCESS)
                    drawBaseHouseMapper.updateDrawHouseStatus(DrawBaseHouseConstant.HOUSE_BAKE_SUCCESS, null, task.getHouseId());

                    // base_house的所有的空间烘焙完成 deal_status(DEAL_STATUS_EDIT)
                    baseHouseMapper.updateHouseDealStatus(DrawBaseHouseConstant.DEAL_STATUS_EDIT, task.getHouseId());
                }
            }
        }

        return false;
    }

    @Override
    public DrawBakeTaskBO selectTaskMessage(Long houseId) {
        return drawBakeTaskMapper.selectTaskMessage(houseId);
    }

    @Override
    public Map<String, Object> getTaskDetailById(Long taskDetailId) {
        return drawBakeTaskMapper.getTaskDetailById(taskDetailId);
    }

    @Override
    public int getTaskDetailCount(Long taskDetailId) {
        return drawBakeTaskMapper.getTaskDetailCount(taskDetailId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateTaskStatusByTaskDetailId(int status, Long taskDetailId) {
        return drawBakeTaskMapper.updateTaskStatusByTaskDetailId(status, taskDetailId);
    }

    @Override
    public int getBakeSuccessTask(Long taskId) {
        if (taskId == null || taskId <= 0) {
            return -1;
        }
        return drawBakeTaskMapper.getBakeSuccessTask(taskId);
    }

    /**
     * 定时重置烘焙主任务
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> resetTask() {
        List<Map<String, Long>> tasks = getBakeFailTask();
        this.resetTask(tasks);
        return null;
    }

    /**
     * 定时重置烘焙子任务
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Object> resetTaskDetail() {
        List<Map<String, Long>> tasks = getBakeFailTaskDetail();
        this.resetTaskDetail(tasks);

        try {
            this.pushBakeFailMail();
        } catch (Exception e) {
            log.error("推送烘焙失败邮件异常", e);
        }

        return null;
    }

    private Integer resetTaskDetail(List<Map<String, Long>> args) {
        if (args == null || args.isEmpty()) {
            log.info("####################### 没有需要重置的烘焙子任务！ #######################");
            return -1;
        }

        log.info("需要重置的烘焙子任务 taskDetails ==> {}", args);

        // task detail
        Integer updateCount = drawBakeTaskDetailMapper.restFailTask(args);
        if (updateCount > 0) {
            this.resetTask(args);
        }

        return updateCount;
    }

    private Integer resetTask(List<Map<String, Long>> args) {
        if (args == null || args.isEmpty()) {
            log.info("####################### 没有需要重置的烘焙主任务！ #######################");
            return -1;
        }

        log.info("需要重置的烘焙主任务 tasks ==> {}", args);

        // task
        List<Long> tasks = args.stream().map((map) -> map.get("taskId")).distinct().collect(Collectors.toList());
        Integer updateCount = drawBakeTaskMapper.restFailTask(tasks);

        if (updateCount > 0) {
            // base house
            drawBaseHouseMapper.restFailTask(DrawBaseHouseConstant.HOUSE_BAKE_FAIL, args);
            // base_house烘焙中
            List<Long> list = drawBaseHouseMapper.listBaseHouseByDrawHouseId(args);
            if (list != null && !list.isEmpty()) {
                List<Long> houseList = list.stream().filter(p -> p == null || p <= 0).distinct().collect(Collectors.toList());
                if (!houseList.isEmpty()) {
                    baseHouseMapper.restFailTask(DrawBaseHouseConstant.DEAL_STATUS_FAIL, houseList);
                }
            }
        }

        return updateCount;
    }

    /**
     * 查询失败的子任务.
     * </p>
     * 1、正在处理的任务时间超过1小时 ；2、烘焙失败超过10次的任务
     */
    @Override
    public List<Map<String, Long>> getBakeFailTaskDetail() {
        return drawBakeTaskMapper.getBakeFailTaskDetail(timeout, failCount, resetCount);
    }

    /**
     * 查询失败的主任务.
     * 1、3个小时没有成功的主任务
     *
     * @return
     */
    @Override
    public List<Map<String, Long>> getBakeFailTask() {
        // 3个小时
        return drawBakeTaskMapper.getBakeFailTask(6 * timeout, resetCount);
    }

    /**
     * 获取烘焙任务id
     *
     * @param queueName
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long getBakeTaskId(String queueName) {
        List<DrawBakeTask> tasks = drawBakeTaskMapper.getBakeTaskByQueueName(queueName, resetCount);
        String ipAddress = HttpContextUtils.getIpAddress();
        if (tasks != null && !tasks.isEmpty()) {
            for (DrawBakeTask task : tasks) {
                // 主任务状态 ==> 烘焙中
                int result = drawBakeTaskMapper.updateDrawBakeTaskStatus(DrawBaseHouseConstant.BAKE_TASK_STATUS_PROCESSING, "", task.getId());
                if (result > 0) {
                    // 添加烘焙记录
                    DrawBakeRecord drawBakeRecord = new DrawBakeRecord();
                    drawBakeRecord.setTaskId(task.getId());
                    drawBakeRecord.setGmtCreate(new Date());
                    drawBakeRecord.setIpAddress(ipAddress);
                    drawBakeRecordMapper.insertSelective(drawBakeRecord);

                    log.info("{}(ip), {}(queueName)获取烘焙的主任务{}(taskId)", ipAddress, queueName, task.getId());
                    return task.getId();
                }
            }
        }

        log.info("{}(ip), {}(queueName)没有需要烘焙的主任务", ipAddress, queueName);

        return null;
    }

    /**
     * taskId = 177、191
     * 1、修复烘焙任务的相关的状态异常
     * 2、主任务烘焙中，子任务全部烘焙完成
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void fix2_3Task() {
        List<Map<String, Long>> failTasks = getBakeFailTask();
        if (failTasks == null || failTasks.isEmpty()) {
            log.info("###################### 没有需要重置的2-3异常数据任务 #####################");
            return;
        }

        for (Iterator<Map<String, Long>> itr = failTasks.iterator(); itr.hasNext(); ) {
            Map<String, Long> task = itr.next();
            // 子任务总数
            Long totalTask = drawBakeTaskDetailMapper.getTaskDetailByTaskId(task.get("taskId"), null);
            // 烘焙成功的子任务总数
            Long successTask = drawBakeTaskDetailMapper.getTaskDetailByTaskId(task.get("taskId"), DrawBaseHouseConstant.BAKE_TASK_STATUS_SUCCESS);

            totalTask = (totalTask == null ? -1L : totalTask);
            if ((totalTask == 0 && successTask == 0) || !totalTask.equals(successTask)) {
                itr.remove();
            }
        }

        if (failTasks.isEmpty()) {
            log.info("##################### 没有需要重置的2-3异常数据任务 ######################");
            return;
        }

        log.info("需要修正的2-3烘焙任务异常数据, fixTask => {}", failTasks);
        //  task
        Integer update = drawBakeTaskMapper.fix2_3Task(failTasks);
        if (update > 0) {
            // draw house
            drawBaseHouseMapper.fix2_3Task(failTasks);

            // base house
            baseHouseMapper.fix2_3Task(failTasks);
            log.info("修正2-3烘焙任务异常数据完成, fixTask => {}", failTasks);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void pushBakeFailMail() {
        List<BakeFailTask> failTasks = drawBakeTaskMapper.getPushBakeFailTask(resetCount);
        if (failTasks != null && !failTasks.isEmpty()) {
            // task
            drawBakeTaskMapper.deleteBakeFailTask(failTasks);
            // task detail
            drawBakeTaskMapper.deleteBakeFailTaskDetail(failTasks);

            StringBuilder buf = new StringBuilder();
            buf.append("主任务ID").append("\t\t").append("任务详情ID").append("\t\t\t\t\t").append("时间").append("\t\t\t\t\t").append("ERROR").append("\n\n");
            for (BakeFailTask failTask : failTasks) {
                buf.append(failTask);
            }

            // 邮件服务
            defaultsFreeMailService.push(targets, "线上烘焙失败的任务", buf.toString());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void batchResetTask(List<Integer> taskIds) {
        if (taskIds == null || taskIds.isEmpty()) {
            throw new BusinessException(false, ResponseEnum.PARAM_ERROR);
        }

        drawBakeTaskMapper.batchResetTask(taskIds);
        drawBakeTaskDetailMapper.batchResetTask(taskIds);
        log.info("手动重置烘焙任务 taskIds => {}", Arrays.toString(taskIds.toArray()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer resetDrawHouse(Integer isDeleted, List<Integer> houseIds) {
        if (houseIds == null || houseIds.isEmpty()) {
            return -1;
        }

        Integer updateCount = drawBakeTaskMapper.resetDrawHouse(isDeleted, houseIds);
        updateCount += drawBakeTaskDetailMapper.resetDrawHouse(isDeleted, houseIds);

        return updateCount;
    }
}
