package com.sandu.designplan.dao;

import com.sandu.render.model.vo.RenderStateVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRenderTaskMapper {
    @Select("select count(1) from auto_render_task_state where operation_user_id = #{userId} and  task_type = #{taskType} and state = 1")
    Integer getUserTaskCount(@Param("userId") Integer userId,
                             @Param("taskType") Integer taskType);

    @Select("select t1.state,t1.business_id,t2.vr_resource_uuid as vrUuid " +
            "from auto_render_task_state t1 left join full_house_design_plan t2 on t2.id = t1.new_full_house_plan_id" +
            " where t1.task_id = #{taskId} and t1.is_deleted = 0 and t1.is_valid = 0")
    RenderStateVo getTaskStatus(@Param("taskId") Integer taskId);
}
