package com.sandu.designplan.dao;

import com.sandu.designplan.model.DesignPlanSummaryInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**   
 * @Title: Mobile2CDesignPlanLikeAndCollectCountMapper.java
 * @Package com.nork.mobile2c.dao
 * @Description:移动端2C-方案点赞收藏数量表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-09 10:16:03
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignPlanSummaryInfoMapper {
    int insertSelective(DesignPlanSummaryInfo record);

    int updateByPrimaryKeySelective(DesignPlanSummaryInfo record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignPlanSummaryInfo selectByPrimaryKey(Integer id);
			
    List<DesignPlanSummaryInfo> selectList(DesignPlanSummaryInfo designPlanSummaryInfo);

    DesignPlanSummaryInfo selectByDesignId(Integer designId);

    int saveOrUpdate(DesignPlanSummaryInfo summaryInfo);
}
