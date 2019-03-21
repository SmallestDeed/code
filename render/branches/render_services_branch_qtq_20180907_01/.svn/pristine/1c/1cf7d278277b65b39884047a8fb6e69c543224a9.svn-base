package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.DesignerWorks;
import com.nork.system.model.DesignerWorksUser;
import com.nork.system.model.search.DesignerWorksSearch;

/**   
 * @Title: DesignerWorksMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-设计师作品Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-10-19 14:43:46
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignerWorksMapper {
    int insertSelective(DesignerWorks record);

    int updateByPrimaryKeySelective(DesignerWorks record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignerWorks selectByPrimaryKey(Integer id);
    
    int selectCount(DesignerWorksSearch designerWorksSearch);
    
	List<DesignerWorks> selectPaginatedList(
			DesignerWorksSearch designerWorksSearch);
			
    List<DesignerWorks> selectList(DesignerWorks designerWorks);
    
    //设计师
    List<DesignerWorksUser> getDesigners(DesignerWorksUser designerWorksUser);
    
    List<DesignerWorksUser> getWorks(DesignerWorksUser designerWorksUser);
    
    List<DesignerWorksUser> getdesignerWorkDetail(DesignerWorksUser designerWorksUser);
    List<DesignerWorksUser> getdesignerWorkList(DesignerWorksUser designerWorksUser);
    DesignerWorksUser getdesignerWorkRendered(DesignerWorksUser designerWorksUser);
    
    String getFilePath(DesignerWorksUser designerWorksUser);
    
	/**
	 * 其他
	 * 
	 */
}
