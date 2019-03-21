package com.sandu.service.bake.dao;

import java.util.List;
import java.util.Map;

import com.sandu.api.house.model.BakeFailTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.bo.DrawBakeTaskBO;
import com.sandu.api.house.model.DrawBakeTask;

/**
 * Description: 烘焙任务数据操作类
 *
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
@Repository
public interface DrawBakeTaskMapper {

    /**
     * 保存烘焙任务
     *
     * @param task
     * @return
     */
    Long save(DrawBakeTask task);

    /**
     * 查询烘焙任务集合
     *
     * @param queueName
     * @return
     */
    List<DrawBakeTaskBO> list(@Param("taskId") Long taskId, @Param("queueName") String queueName,
                              @Param("failCount") Integer failCount, @Param("resetCount") Integer resetCount);

    /**
     * 根据主键查询烘焙任务
     *
     * @param taskId
     * @return
     */
    DrawBakeTaskBO getDrawBakeTask(Long taskId);

    /**
     * 更新烘焙任务状态
     *
     * @param status
     * @param taskId
     * @param message
     */
    int updateDrawBakeTaskStatus(@Param("status") Integer status, @Param("message") String message, @Param("id") Long taskId);

    /**
     * 获取剩余taskId的剩余需要烘焙的任务
     *
     * @param taskDetailId
     * @return
     */
    int getTaskDetailCount(Long taskDetailId);

    /**
     * 将base_house的id存入draw_bake_task ==> rel_house_id字段
     *
     * @param taskId
     * @param refHouseId
     * @return
     */
    int updateForRelHouseId(Long taskId, Long refHouseId);

    DrawBakeTaskBO selectTaskMessage(@Param("houseId") Long houseId);

    Map<String, Object> getTaskDetailById(Long taskDetailId);

    int updateTaskStatusByTaskDetailId(@Param("status") int status, @Param("taskDetailId") Long taskDetailId);

    int getBakeSuccessTask(Long taskId);

    List<Map<String, Long>> getBakeFailTask(@Param("timeout") Integer timeout, @Param("resetCount") Integer resetCount);

    List<Map<String, Long>> getBakeFailTaskDetail(@Param("timeout") Integer timeout, @Param("failCount") Integer failCount,
                                                  @Param("resetCount") Integer resetCount);

    int restFailTask(@Param("args") List<Long> args);

    List<DrawBakeTask> getBakeTaskByQueueName(@Param("queueName") String queueName, @Param("resetCount") Integer resetCount);

    Integer fix2_3Task(@Param("failTasks") List<Map<String, Long>> failTasks);

    List<BakeFailTask> getPushBakeFailTask(@Param("resetCount") Integer resetCount);

    Integer deleteBakeFailTask(@Param("failTasks") List<BakeFailTask> failTasks);

    Integer deleteBakeFailTaskDetail(@Param("failTasks") List<BakeFailTask> failTasks);

    Integer batchResetTask(@Param("taskIds") List<Integer> taskIds);

    Integer resetDrawHouse(@Param("isDeleted") Integer isDeleted, @Param("houseIds") List<Integer> houseIds);

    DrawBakeTask getTaskById(Long taskId);

    int getTaskDetailCount2(Long taskId);

    Integer update(DrawBakeTask task);
}
