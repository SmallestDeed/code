package com.nork.home.dao;


import com.nork.home.model.UserHouseRecord;
import com.nork.render.model.search.OldRecordSearch;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHouseRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserHouseRecord record);

    UserHouseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserHouseRecord record);

    UserHouseRecord getOldRecord(OldRecordSearch oldRecordSearch);
}