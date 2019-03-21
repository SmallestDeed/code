package com.sandu.product.dao;

import com.sandu.goods.output.GoodsVO;
import com.sandu.product.model.UserProductCollect;
import com.sandu.product.model.UserProductsConllect;
import com.sandu.product.model.search.UserProductCollectSearch;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Title: UserProductCollectMapper.java
 * @Package com.sandu.product.dao
 * @Description:产品模块-我的产品收藏Mapper
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 18:27:21
 */
@Repository
public interface UserProductCollectMapper {
    int insertSelective(UserProductCollect record);

    int updateByPrimaryKeySelective(UserProductCollect record);

    int deleteByPrimaryKey(Integer id);

    Integer cancelConllect(UserProductCollect userProductCollect);

    UserProductCollect selectByPrimaryKey(Integer id);

    int selectCount(UserProductCollectSearch userProductCollectSearch);

    List<UserProductCollect> selectPaginatedList(UserProductCollectSearch userProductCollectSearch);

    List<UserProductCollect> selectList(UserProductCollect userProductCollect);

    UserProductCollect getUserProductConllect(UserProductCollect upc);

    List<UserProductsConllect> getUserProductsConllectList(UserProductsConllect userProductsConllect);

    List<UserProductsConllect> getAllList(UserProductsConllect userProductsConllect);

    int getUserProductsConllectCount(UserProductsConllect userProductsConllect);

    List<UserProductsConllect> selectUserNamelist(UserProductsConllect userProductsConllect);

    void transferCollection(UserProductCollectSearch userProductCollectSearch);

    List getUserProductsCollectByValue(List<Map<Object, Object>> valueList);

    int getUserProductsConllectCountAll(
            UserProductsConllect userProductsCollect);

    List<UserProductsConllect> getAllListAll(
            UserProductsConllect userProductsCollect);

    /**
     * 其他
     *
     */
    /**
     * 批量删除
     */
    int deleteBatch(List<Integer> list);

	Integer selectCollectCatalogId(Integer userId);

	List<Integer> getAllListAll2(UserProductsConllect userProductsConllect);

	List<Integer> selectProductIsFavorite(@Param("productIdList") List<Integer> productIdList,@Param("userId") Integer userId);

    List<Integer> selectSpuIsFavorite(List<Integer> productIdList, Integer userId);

    List<Integer> getSpuIdList(UserProductsConllect userProductsConllect);

    Integer getCollectCount(UserProductsConllect userProductsConllect);

	int getFarivorateCollectCount(@Param("userId")Integer userId);
}
