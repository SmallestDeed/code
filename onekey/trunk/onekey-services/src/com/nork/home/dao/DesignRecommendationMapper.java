package com.nork.home.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nork.onekeydesign.model.RecommendationSpaceResult;
import com.nork.home.model.DesignProgramDiy;
import com.nork.home.model.DesignRecommendation;
import com.nork.home.model.search.DesignRecommendationSearch;

/**   
 * @Title: DesignRecommendationMapper.java 
 * @Package com.nork.home.dao
 * @Description:户型房型-效果图推荐Mapper
 * @createAuthor pandajun 
 * @CreateDate 2015-07-06 20:00:19
 * @version V1.0   
 */
@Repository
@Transactional
public interface DesignRecommendationMapper {
    int insertSelective(DesignRecommendation record);

    int updateByPrimaryKeySelective(DesignRecommendation record);
  
    int deleteByPrimaryKey(Integer id);
        
    DesignRecommendation selectByPrimaryKey(Integer id);
    
    int selectCount(DesignRecommendationSearch designRecommendationSearch);
    int selectTotal(DesignProgramDiy Dpd);
    
	List<DesignRecommendation> selectPaginatedList(
			DesignRecommendationSearch designRecommendationSearch);
			
    List<DesignRecommendation> selectList(DesignRecommendation designRecommendation);
    
    DesignProgramDiy getDesignProgramDiyByCode(DesignProgramDiy Dpd);
    DesignProgramDiy getSampleroomSourceyByCode(DesignProgramDiy Dpd);
    RecommendationSpaceResult getSpaceRender(Integer id);
    List<DesignProgramDiy> dropDownBoxList(DesignProgramDiy Dpd);
    DesignProgramDiy getDesignTrendsDetails(DesignProgramDiy Dpd);
    
	/**
	 * 其他
	 * 
	 */
}
