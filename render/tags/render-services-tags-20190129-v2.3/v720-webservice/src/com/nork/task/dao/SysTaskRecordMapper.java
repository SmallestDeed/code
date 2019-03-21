package com.nork.task.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.task.model.SysTaskRecord;
import com.nork.task.model.search.SysTaskSearch;

/**
 * Created by Administrator on 2016/10/16.
 */
@Repository
@Transactional
public interface SysTaskRecordMapper {

    int insertSelective(SysTaskRecord sysTaskRecord);

    int updateByPrimaryKeySelective(SysTaskRecord sysTaskRecord);

    int deleteByPrimaryKey(Integer id);

    SysTaskRecord selectByPrimaryKey(Integer id);

    int selectCount(SysTaskSearch sysTaskSearch);

    List<SysTaskRecord> selectPaginatedList(
            SysTaskSearch sysTaskSearch);

    List<SysTaskRecord> selectList(SysTaskRecord sysTaskRecord);

}
