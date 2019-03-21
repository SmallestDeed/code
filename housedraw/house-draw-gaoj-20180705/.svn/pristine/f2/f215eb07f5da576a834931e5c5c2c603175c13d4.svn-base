package com.sandu.service.templet.dao;

import com.sandu.api.house.model.DesignTempletJumpPositionRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignTempletJumpPositionRelMapper {
    int insertSelective(DesignTempletJumpPositionRel record);

    int batchInsertSelective(@Param("records") List<DesignTempletJumpPositionRel> records);

    DesignTempletJumpPositionRel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DesignTempletJumpPositionRel record);
}