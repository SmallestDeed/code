package com.sandu.service.platform.dao;

import com.sandu.api.platform.model.PlatformProductRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**   
 * @author Sandu
 * @Title: Platform2bProductRelMapper.java
 * @Package com.nork.product.dao
 * @Description:产品-2c类型平台-产品关联表Mapper
 * @createAuthor pandajun 
 * @CreateDate 2017-12-28 20:58:57
 * @version V1.0   
 */
@Repository
public interface Platform2cProductRelDao {
    int insertSelective(PlatformProductRel record);

    int updateByPrimaryKeySelective(PlatformProductRel record);

    int updateByPlatformIdAndProductId(PlatformProductRel platformProductRel);

    int deleteByPrimaryKey(Integer id);
        
    PlatformProductRel selectByPrimaryKey(Integer id);
			
    List<PlatformProductRel> selectList(PlatformProductRel platformProductRel);

    int insertList(@Param("platformId") Integer platformId,
                   @Param("platformProductRelList") List<PlatformProductRel> platformProductRelList);

    List<Integer> getIssueProductIdsFromIds(@Param("platformId") Integer platformId,
                                            @Param("productIds") List<Integer> productIds);

    List<Integer> getCancleIssueIdsFromIds(@Param("platformId") Integer platformId,
                                           @Param("notIssue") List<Integer> notIssue);

    void updatePlatformProductRels(@Param("list") List<PlatformProductRel> platformProductRelList);

    PlatformProductRel getByProductIdAndPlatform(@Param("platformId") Integer platformId,
                                                 @Param("productId") Integer productId);

    List<String> getAllIssuePlatfromByProductId(@Param("productId") Integer id);

    List<String> getAllPutPlatfromByProductId(@Param("productId") Integer id);

    int checkProductHasIssueToPlatform(PlatformProductRel platformProductRel);

    List<Integer> getExistProductIds(List<Integer> idsTmp);

    Integer getPlatformIdByProductId(long productId);

    int deleteByProductIds(@Param("ids") List<Integer> ids);

    int insertRelList(List<PlatformProductRel> collect);

    List<Map<String,String>> getBePutawayPlatformByProductIds(List<Integer> productIds);

    int insertRelListIfNotExist(List<PlatformProductRel> insertList);

	int deleteByIdsAndPlatForm(@Param("ids")List<Integer> ids, @Param("platformIds")List<String> platFormIds);

}
