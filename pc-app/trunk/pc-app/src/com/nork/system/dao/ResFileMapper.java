package com.nork.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.sync.model.ResEntity;
import com.nork.system.model.ResFile;
import com.nork.system.model.search.ResFileSearch;
import org.apache.ibatis.annotations.Param;

/**   
 * @Title: ResFileMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统模块-文件资源库Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-07-02 17:36:00
 * @version V1.0   
 */
@Repository
@Transactional
public interface ResFileMapper {
    int insertSelective(ResFile record);

    int updateByPrimaryKeySelective(ResFile record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResFile selectByPrimaryKey(Integer id);
    
    int selectCount(ResFileSearch resFileSearch);
    
	List<ResFile> selectPaginatedList(
			ResFileSearch resFileSearch);
			
    List<ResFile> selectList(ResFile resFile);
    
	/**
	 * 其他
	 * 
	 */
   int filePathCount(String filePath);
   
   ResEntity selectResEntity(Integer id);

   /**
    * 保存ResEntity对象
    * @param resEntity
    * @return
    */
   int insertEntity(ResEntity resEntity);
   
   /**
    * 批量删除
    * @param resFileIds
    * @return
    * add by yangzhun
    */
   int batchDelete(@Param("resFileIds")List<Integer> resFileIds);
   
   /**
    * 根据文件Id的集合批量查询文件地址
    * add by chenm on 201180719
    * @param idList
    * @return
    */
   public List<ResFile> getFilePathByIdList(@Param("idList")List<Integer> idList);
}
