package com.nork.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.task.dao.SysTaskRecordMapper;
import com.nork.task.model.SysTaskRecord;
import com.nork.task.model.search.SysTaskSearch;
import com.nork.task.service.SysTaskRecordService;

/**
 * Created by Administrator on 2016/10/16.
 */
@Service("sysTaskRecordService")
public class SysTaskRecordServiceImpl implements SysTaskRecordService {

    private SysTaskRecordMapper sysTaskRecordMapper;

    @Autowired
    public void setSysTaskMapper(
            SysTaskRecordMapper sysTaskMapper) {
        this.sysTaskRecordMapper = sysTaskMapper;
    }

    /**
     * 新增数据
     *
     * @param sysTaskRecord
     * @return  int
     */
    @Override
    public int add(SysTaskRecord sysTaskRecord) {
        sysTaskRecordMapper.insertSelective(sysTaskRecord);
        return sysTaskRecord.getId();
    }

    /**
     *    更新数据
     *
     * @param sysTaskRecord
     * @return  int
     */
    @Override
    public int update(SysTaskRecord sysTaskRecord) {
        return sysTaskRecordMapper.updateByPrimaryKeySelective(sysTaskRecord);
    }

    /**
     *    删除数据
     *
     * @param id
     * @return  int
     */
    @Override
    public int delete(Integer id) {
        return sysTaskRecordMapper.deleteByPrimaryKey(id);
    }

    /**
     *    获取数据详情
     *
     * @param id
     * @return  SysTask
     */
    @Override
    public SysTaskRecord get(Integer id) {
        return sysTaskRecordMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param  sysTaskRecord
     * @return   List<SysTask>
     */
    @Override
    public List<SysTaskRecord> getList(SysTaskRecord sysTaskRecord) {
        return sysTaskRecordMapper.selectList(sysTaskRecord);
    }

    /**
     *    获取数据数量
     *
     * @param  sysTaskSearch
     * @return   int
     */
    @Override
    public int getCount(SysTaskSearch sysTaskSearch){
        return  sysTaskRecordMapper.selectCount(sysTaskSearch);
    }


    /**
     *    分页获取数据
     *
     * @param  sysTaskSearch
     * @return   List<SysTask>
     */
    @Override
    public List<SysTaskRecord> getPaginatedList(
            SysTaskSearch sysTaskSearch) {
        return sysTaskRecordMapper.selectPaginatedList(sysTaskSearch);
    }

    /**
     * 其他
     *
     */

}
