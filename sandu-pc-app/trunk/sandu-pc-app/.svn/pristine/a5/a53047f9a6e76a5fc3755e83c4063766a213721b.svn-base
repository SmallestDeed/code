package com.sandu.panorama.dao;

import java.util.List;

import com.sandu.cityunion.model.UnionSpecialOffer;
import com.sandu.panorama.model.input.UnionSpecialOfferSearch;
import com.sandu.panorama.model.output.UnionSpecialOfferVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**   
 * @Title: UnionSpecialOfferMapper.java 
 * @Package com.nork.cityunion.dao
 * @Description:同城联盟-联盟优惠活动表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:34
 * @version V1.0   
 */
@Repository
@Transactional
public interface UnionSpecialOfferMapper {
    int insertSelective(UnionSpecialOffer record);

    int updateByPrimaryKeySelective(UnionSpecialOffer record);
  
    int deleteByPrimaryKey(Integer id);
        
    UnionSpecialOffer selectByPrimaryKey(Integer id);
    
    int selectCount(UnionSpecialOfferSearch unionSpecialOfferSearch);
    
/*	List<UnionSpecialOffer> selectPaginatedList(
            UnionSpecialOfferSearch unionSpecialOfferSearch);*/
			
    List<UnionSpecialOffer> selectList(UnionSpecialOffer unionSpecialOffer);
    
	/**
	 * 其他
	 * 
	 */

    int findSpecialOfferCount(@Param("userId") Integer userId, @Param("start") Integer start, @Param("limit") Integer limit);

    List<UnionSpecialOffer> findSpecialOfferList(@Param("userId") Integer userId, @Param("start") Integer start, @Param("limit") Integer limit);

    List<UnionSpecialOfferVo> selectUnionSpecialOfferVoListBySearch(UnionSpecialOfferSearch specialOfferSearch);
}
