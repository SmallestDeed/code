package com.sandu.product.service;

import com.sandu.common.model.ResponseEnvelope;
import com.sandu.product.model.CollectionQueryModel;
import com.sandu.product.model.UserProductCollect;
import com.sandu.product.model.UserProductsConllect;
import com.sandu.product.model.search.UserProductCollectSearch;
import com.sandu.user.model.LoginUser;
import com.sandu.user.model.UserSO;

import java.util.HashMap;
import java.util.List;

/**
 * @version V1.0
 * @Title: UserProductCollectService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-我的产品收藏Service
 * @createAuthor pandajun
 * @CreateDate 2015-06-15 18:27:21
 */
public interface UserProductCollectService {
    /**
     * 新增数据
     *
     * @param userProductCollect
     * @return int
     */
    int add(UserProductCollect userProductCollect);

    /**
     * 更新数据
     *
     * @param userProductCollect
     * @return int
     */
    int update(UserProductCollect userProductCollect);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);


    /**
     * 获取数据详情
     *
     * @param id
     * @return UserProductCollect
     */
    UserProductCollect get(Integer id);


    /**
     * 所有数据
     *
     * @param userProductCollect
     * @return List<UserProductCollect>
     */
    List<UserProductCollect> getList(UserProductCollect userProductCollect);


    ResponseEnvelope collectionList(CollectionQueryModel model);


    ResponseEnvelope collecProduct(UserProductCollect userProductCollect, com.nork.common.model.LoginUser loginUser);


    int getUserProductsConllectCount(UserProductsConllect userProductsConllect);


    List<UserProductsConllect> getAllList(UserProductsConllect userProductsConllect);

    /**
     * 获取数据数量
     *
     * @return int
     */
    int getCount(UserProductCollectSearch userProductCollectSearch);

    /**
     * (转移方法)  通过用户id 和 收藏夹Id，将将收藏夹ID  改为 默认Id
     *
     * @param userProductCollectSearch
     */
    void transferCollection(UserProductCollectSearch userProductCollectSearch);

    int getUserProductsConllectCountAll(UserProductsConllect userProductsCollect);

    List<UserProductsConllect> getAllListAll(UserProductsConllect userProductsCollect);

    Integer getCollectCatalogId(Integer id);

	List<Integer> collectionList2(CollectionQueryModel model);

	HashMap<Integer, Integer> getProductFavoriteMap(List<Integer> productIdList, UserSO userSo);

    HashMap<Integer,Integer> getSpuFavoriteMap(List<Integer> productIdList, UserSO userSo);

    ResponseEnvelope collectionList3(CollectionQueryModel model);
}
