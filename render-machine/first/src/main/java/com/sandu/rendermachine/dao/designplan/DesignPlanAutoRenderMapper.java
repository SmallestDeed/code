package com.sandu.rendermachine.dao.designplan;

import com.sandu.rendermachine.model.render.AutoRenderTask;
import com.sandu.rendermachine.model.render.AutoRenderTaskState;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 10:26 2018/4/18 0018
 * @Modified By:
 */
@Repository
public interface DesignPlanAutoRenderMapper {

    AutoRenderTaskState getStateByTaskId(@Param("taskId")Integer taskId);

    AutoRenderTask getRenderTaskById(@Param("taskId")Integer taskId);

    int updateTaskStateByTaskId(AutoRenderTaskState autoRenderTaskState);

    int addTaskStateToDB(AutoRenderTaskState taskState);

    int deleteRenderTaskByTaskId(Integer taskId);
}
