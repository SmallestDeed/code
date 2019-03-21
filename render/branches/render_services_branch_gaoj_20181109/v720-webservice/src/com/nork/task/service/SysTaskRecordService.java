package com.nork.task.service;

import java.util.List;

import com.nork.task.model.SysTaskRecord;
import com.nork.task.model.search.SysTaskSearch;

/**
 * Created by Administrator on 2016/10/16.
 */
public interface SysTaskRecordService {

    /**
     * 新增数据
     *
     * @param sysTaskRecord
     * @return  int
     */
    public int add(SysTaskRecord sysTaskRecord);

    /**
     *    更新数据
     *
     * @param sysTaskRecord
     * @return  int
     */
    public int update(SysTaskRecord sysTaskRecord);

    /**
     *    删除数据
     *
     * @param id
     * @return  int
     */
    public int delete(Integer id);

    /**
     *    获取数据详情
     *
     * @param id
     * @return  SysTask
     */
    public SysTaskRecord get(Integer id);

    /**
     * 所有数据
     *
     * @param  sysTaskRecord
     * @return   List<SysTask>
     */
    public List<SysTaskRecord> getList(SysTaskRecord sysTaskRecord);

    /**
     *    获取数据数量
     *
     * @param  sysTask
     * @return   int
     */
    public int getCount(SysTaskSearch sysTaskSearch);

    /**
     *    分页获取数据
     *
     * @param  sysTask
     * @return   List<SysTask>
     */
    public List<SysTaskRecord> getPaginatedList(
            SysTaskSearch sysTasktSearch);

    /**
     * 其他
     *
     */

}
