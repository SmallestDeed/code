package com.sandu.service.task.dao;

import com.sandu.api.task.model.AutoRenderTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRenderTaskMapper {

    AutoRenderTask selectById(Integer mainTaskId);

    int insertSelective(AutoRenderTask autoRenderTask);

    List<AutoRenderTask> getSubTaskByMainTaskId(Integer mainTaskId);

    int insertSubTaskBatch(List<AutoRenderTask> list);

    int updateMainTaskId(Integer newMainTaskId);

    Integer getUserHouseCount(@Param("userId") Integer userId,
                              @Param("houseId") Integer houseId,
                              @Param("houseName") String houseName);
}
