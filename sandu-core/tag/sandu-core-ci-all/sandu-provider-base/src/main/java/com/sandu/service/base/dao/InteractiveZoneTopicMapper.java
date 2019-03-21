package com.sandu.service.base.dao;

import com.sandu.api.base.input.InteractiveZoneTopicQuery;
import com.sandu.api.base.model.InteractiveZoneTopic;
import com.sandu.system.model.ResFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractiveZoneTopicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InteractiveZoneTopic record);

    int insertSelective(InteractiveZoneTopic record);

    InteractiveZoneTopic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InteractiveZoneTopic record);

    int updateByPrimaryKeyWithBLOBs(InteractiveZoneTopic record);

    int updateByPrimaryKey(InteractiveZoneTopic record);

	List<InteractiveZoneTopic> getList(InteractiveZoneTopicQuery interactiveZoneTopicQuery);

	Integer totalCount(InteractiveZoneTopicQuery interactiveZoneTopicQuery);


    ResFile FindOneResFileById(Integer fileId);

    void batchInsert(@Param("addList") List<InteractiveZoneTopic> addList);
}