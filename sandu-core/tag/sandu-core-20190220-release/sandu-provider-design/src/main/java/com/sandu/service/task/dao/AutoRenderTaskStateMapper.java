package com.sandu.service.task.dao;

import com.sandu.api.task.model.AutoRenderTaskState;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRenderTaskStateMapper {

    AutoRenderTaskState getMainTaskStateByTaskId(Integer mainTaskId);

    int insertSelective(AutoRenderTaskState autoRenderTaskState);

    List<AutoRenderTaskState> getAllTaskStateByMainTaskId(Integer mainTaskId);

    int insertBatch(List<AutoRenderTaskState> list);
}
