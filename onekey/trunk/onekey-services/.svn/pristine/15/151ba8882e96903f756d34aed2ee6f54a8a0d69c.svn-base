package com.nork.onekeydesign.dao;

import java.util.List;

import com.nork.onekeydesign.model.DesignRenderRoam;
import com.nork.onekeydesign.model.search.DesignRenderRoamSearch;
import org.springframework.stereotype.Repository;

/**   
 * @Title: DesignRenderRoamMapper.java 
 * @Package com.nork.render.dao
 * @Description:渲染漫游-720漫游Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-07-13 17:41:16
 * @version V1.0   
 */
@Repository
public interface DesignRenderRoamMapper {
    int insertSelective(DesignRenderRoam record);

    int updateByPrimaryKeySelective(DesignRenderRoam record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignRenderRoam selectByPrimaryKey(Integer id);
    
    int selectCount(DesignRenderRoamSearch designRenderRoamSearch);
    
	List<DesignRenderRoam> selectPaginatedList(
            DesignRenderRoamSearch designRenderRoamSearch);
			
    List<DesignRenderRoam> selectList(DesignRenderRoam designRenderRoam);
    
    public DesignRenderRoam countByScreenShotId(DesignRenderRoam designRenderRoam);
	/**
	 * 其他
	 * 
	 */

    /**
     * 通过UUID查询一个漫游
     * @param uuid
     * @return
     */
	DesignRenderRoam selectByUUID(String uuid);

    /**
     * 通过截图ID查询一个720漫游
     * @param screenId
     * @return
     */
    DesignRenderRoam selectByScreenId(Integer screenId);
}
