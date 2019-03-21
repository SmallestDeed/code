package com.nork.task.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.task.model.SysTask;
import com.nork.task.model.search.SysTaskSearch;

/**   
 * @Title: SysTaskMapper.java 
 * @Package com.nork.task.dao
 * @Description:任务-系统任务表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-11-18 10:51:21
 * @version V1.0   
 */
@Repository
@Transactional
public interface SysTaskMapper {
    int insertSelective(SysTask record);

    int updateByPrimaryKeySelective(SysTask record);
  
    int deleteByPrimaryKey(Integer id);
        
    SysTask selectByPrimaryKey(Integer id);
    
    int selectCount(SysTaskSearch sysTaskSearch);
    
	List<SysTask> selectPaginatedList(
            SysTaskSearch sysTaskSearch);
			
    List<SysTask> selectList(SysTask sysTask);
    
    List<SysTask> findRenderPlanId(SysTask sysTask);
    
    Integer getRenderBatchSum();
	/**
	 * 其他
	 * 
	 */

	SysTask getMaxPriorityTask();

	Integer getCountOfRender(Map<String, Integer> map);

	Integer calculateWatingTaskCount(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: getSysTaskState   
	 * @Description: 查询设计方案是否有正在进行的渲染任务    
	 * @param: @param businessId
	 * @param: @return      
	 * @return: int      
	 */
	int getSysTaskState(int businessId);
	 /**
     * 通过idList删除sysTask
     * @author huangsongbo
     * @param taskIdList
     */
	void deleteByIdList(Map<String, Object> map);

	void deleteByIdList(@Param("idList") List<Integer> taskIdList, @Param("remark") String remark);

    int getCountFreeRender(Integer designPlanId);
}
