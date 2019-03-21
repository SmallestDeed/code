package com.sandu.api.house.service;

import java.util.List;
import java.util.Map;

import com.sandu.api.house.bo.DrawBakeTaskBO;
import com.sandu.api.house.model.DrawBakeTask;
import com.sandu.api.house.model.DrawBakeTaskDetail;

/**
 * Description:
 * 烘焙任务接口
 *
 * @author 何文
 * @version 1.0
 * Company:Sandu
 * Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
public interface DrawBakeTaskService {

    /**
     * 保存烘焙任务
     *
     * @param task 烘焙任务对象
     */
    void saveDrawBakeTask(DrawBakeTask task);

    /**
     * 批量保存烘焙任务详细
     *
     * @param drawBakeTaskDetails 烘焙任务详情
     */
    void batchSaveDrawBakeTaskDetail(List<DrawBakeTaskDetail> drawBakeTaskDetails);

    /**
     * 查询未烘焙任务记录
     *
     * @param queueName 条数限制 limit为空时 默认50条
     * @return
     */
    List<DrawBakeTaskBO> listBakeTask(String queueName,Long taskId);

    DrawBakeTaskBO selectTaskMessage(Long id);
    
    Map<String, Object> getTaskDetailById(Long taskDetailId);

	int getTaskDetailCount(Long taskDetailId);

	int updateTaskStatusByTaskDetailId(int status, Long taskDetailId);
	
	/**
	 * 获取烘焙成功的任务
	 * @param taskId
	 * @return
	 */
	int getBakeSuccessTask(Long taskId);
	
	List<Map<String, Long>> getBakeFailTask();

	List<Map<String, Long>> getBakeFailTaskDetail();

	/**
	 * 定时重置烘焙子任务
	 * @return
	 */
	Map<String, Object> resetTaskDetail();
	
	/**
	 * 定时置烘焙主任务
	 * 
	 * @return
	 */
	Map<String, Object> resetTask();

	/**
	 * 获取烘焙户型id
	 * @param queueName
	 * @return
	 */
	Long getBakeTaskId(String queueName);

    void fix2_3Task();

    void pushBakeFailMail();
}
