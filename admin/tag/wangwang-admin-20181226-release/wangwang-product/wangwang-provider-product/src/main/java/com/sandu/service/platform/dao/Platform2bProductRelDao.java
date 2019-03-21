package com.sandu.service.platform.dao;

import com.sandu.api.platform.model.PlatformProductRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 * @version V1.0
 * @Title: Platform2bProductRelMapper.java
 * @Package com.nork.product.dao
 * @Description:产品-2b类型平台-产品关联表Mapper
 * @createAuthor pandajun
 * @CreateDate 2017-12-28 20:58:57
 */
@Repository
public interface Platform2bProductRelDao {
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

	int deleteByIdsAndPlatForm(@Param("ids") List<Integer> ids, @Param("platformIds") List<String> platformIds);

	int insertRelListIfNotExist(List<PlatformProductRel> insertList);


}
