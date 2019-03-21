package com.nork.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.sandu.model.dto.TDesignSketch;
import com.nork.sync.model.ResEntity;
import com.nork.system.model.ResPic;
import com.nork.system.model.search.ResPicSearch;

/**   
 * @Title: ResPicMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-图片资源库Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:06:59
 * @version V1.0   
 */
@Repository
@Transactional
public interface ResPicMapper {
    int insertSelective(ResPic record);

    int updateByPrimaryKeySelective(ResPic record);
  
    int deleteByPrimaryKey(Integer id);
        
    ResPic selectByPrimaryKey(Integer id);
    
    int selectCount(ResPicSearch resPicSearch);
    
    TDesignSketch findById(Integer id);
    
    int findCount(ResPicSearch resPicSearch);
    
    List<TDesignSketch> findList(ResPicSearch resPicSearch);
    
	List<ResPic> selectPaginatedList(
			ResPicSearch resPicSearch);
			
    List<ResPic> selectList(ResPic resPic);
    
    int picPathCount(String picPath);
    
    ResEntity selectResEntity(Integer id);
    /**
     * 保存ResEntity对象
     * @param resEntity
     * @return
     */
    public int insertEntity(ResEntity resEntity);
    
    int selectCountGuide(ResPicSearch resPicSearch);
    
	List<ResPic> selectPaginatedListGuide(
			ResPicSearch resPicSearch);
	
	List<ResPic> getBatchGet(List<Integer> list);
	
	public List<ResPic> getPicList(List<String> list);

	List<ResPic> getPicPathByIdList(@Param("smallPicIdList") List<Integer> smallPicIdList);
}
