package com.sandu.service.bake.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sandu.api.house.model.DrawBakeTaskDetail;

/**
 * Description: 烘焙任务详情底层操作接口
 * 
 * @author 何文
 * @version 1.0 Company:Sandu Copyright:Copyright(c)2017
 * @date 2017/12/29
 */
@Repository
public interface DrawBakeTaskDetailMapper {

	void batchSave(@Param("list") List<DrawBakeTaskDetail> drawBakeTaskDetails);

	int updateTaskDetailStatus(@Param("id") Long id, @Param("status") Integer status);

	DrawBakeTaskDetail selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(DrawBakeTaskDetail record);

	int restFailTask(@Param("args") List<Map<String, Long>> args);

	Long getTaskDetailByTaskId(@Param("taskId") Long taskId, @Param("status") Integer status);

    DrawBakeTaskDetail getSubTask(Long taskDetailId);
}
