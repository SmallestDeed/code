package com.nork.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.system.model.BaseArea;
import com.nork.system.model.search.BaseAreaSearch;
import com.nork.system.model.web.WBaseArea;

/**   
 * @Title: BaseAreaMapper.java 
 * @Package com.nork.system.dao
 * @Description:系统-行政区域Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 15:31:09
 * @version V1.0   
 */
@Repository
@Transactional
public interface BaseAreaMapper {
    int insertSelective(BaseArea record);

    int updateByPrimaryKeySelective(BaseArea record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseArea selectByPrimaryKey(Integer id);
    
    BaseArea selectCityName(BaseAreaSearch baseAreaSearch);
    
    WBaseArea getByCode(BaseAreaSearch baseAreaSearch);
    
    int selectCount(BaseAreaSearch baseAreaSearch);
    
	List<BaseArea> selectPaginatedList(
			BaseAreaSearch baseAreaSearch);
			
    List<BaseArea> selectList(BaseArea baseArea);
    
    List<BaseArea> selectCityList();

    /**
     * 获取区域名称
     * @param provinceCode 省
     * @param cityCode  市
     * @param districtCode 区
     * @return
     */
    String selectAreaNamebyCode(@Param("provinceCode") String provinceCode,@Param("cityCode")String cityCode,@Param("districtCode")String districtCode);
}
