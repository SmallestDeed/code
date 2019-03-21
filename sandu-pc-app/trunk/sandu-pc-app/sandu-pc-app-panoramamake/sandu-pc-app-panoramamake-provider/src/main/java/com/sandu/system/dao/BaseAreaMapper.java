package com.sandu.system.dao;

import java.util.List;

import com.sandu.system.model.BaseArea;
import com.sandu.system.model.bo.HouseAreaBo;
import com.sandu.system.model.search.BaseAreaQueryBean;
import org.springframework.stereotype.Repository;

/**   
 * @Title: BaseAreaMapper.java 
 * @Package com.sandu.system.model.BaseArea
 * @Description:系统-行政区域Mapper
 */
@Repository
public interface BaseAreaMapper {
    int insertSelective(BaseArea record);

    int updateByPrimaryKeySelective(BaseArea record);
  
    int deleteByPrimaryKey(Integer id);
        
    BaseArea selectByPrimaryKey(Integer id);
    
    List<BaseArea> selectList(BaseArea baseArea);

    /**
     * 根据区域编码得到区域信息
     * @param queryBean
     * @return
     */
    HouseAreaBo selectAreaInfoByAreaLongCode(BaseAreaQueryBean queryBean);
}
