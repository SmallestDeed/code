package com.sandu.pay.system.dao;

import com.sandu.system.model.task.SysTask;
import com.sandu.web.task.model.DesignPlan;
import com.sandu.web.task.model.search.SysTaskSearch;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**   
 * @Title: SysTaskMapper.java 
 * @Package com.sandu.task.dao
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
    
    DesignPlan selectByPrimaryDesignPlan(Integer id);
    /**
	 *    根据设计方案id，获取designPlan表中的所有数据详情
	 *
	 * @param id
	 * @return  DesignPlan 
	 */
	public DesignPlan get(Integer id);
}
