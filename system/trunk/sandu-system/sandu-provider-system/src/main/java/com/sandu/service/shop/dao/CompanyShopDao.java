package com.sandu.service.shop.dao;

import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.shop.model.po.ShopPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 店铺管理--接口持久层
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Repository
public interface CompanyShopDao {

    /**
     * 插入店铺
     * @param companyShop 新增对象
     * @return int 新增数量
     */
    int insertShop(CompanyShop companyShop);

    /**
     * 删除店铺
     * @param companyShop 删除对象
     * @return 删除数量
     */
    int deleteShop(CompanyShop companyShop);

    /**
     * 更新店铺
     * @param companyShop 修改对象
     * @return 更新数量
     */
    int updateShop(CompanyShop companyShop);

    /**
     * 通过店铺ID查询店铺信息
     * @param shopId 店铺ID
     * @return CompanyShop对象
     */
    CompanyShop getShopById(Integer shopId);

    /**
     * 获取满足条件的数据数量
     * @param query 查询条件对象
     * @return int 满足条件数据数量
     */
    int findCount(ShopPO query);

    /**
     * 获取满足条件的数据列表
     * @param query 查询条件对象
     * @return List<CompanyShop> 集合列表
     */
    List<CompanyShop> findList(ShopPO query);

    /**
     * 查询品牌馆信息
     * @param userId
     * @param companyId
     * @param businessType
     * @return
     */
    CompanyShop findBrandPavilion(@Param("userId") Integer userId, @Param("companyId") Long companyId, @Param("businessType") Integer businessType);

    /**
     * 验证店铺唯一性
     * @param shopName  店铺名称
     * @param shopId 企业ID
     * @return
     */
    Integer checkShopName(@Param("shopName") String shopName, @Param("shopId") Long shopId);

    /**
     * 根据企业Id更新店铺分类
     *
     * @param companyShop
     * @return
     */
    int updateShopCategoryByCompanyId(CompanyShop companyShop);

    /**
     * add by WangHL
     * 通过用户ID查询属于用户的店铺列表
     * @param userId 用户Id
     * @return list
     */
    List<CompanyShop> getShopListByUserId(@Param("userId") Long userId);

    /**
     * add by WangHL
     * 通过用户Id删除属于用户的店铺
     * @param userId 用户Id
     * @return
     */
    Integer deleteShopByUserId(@Param("userId") Long userId,@Param("loginUserName") String loginUserName);

    /**
     * 检验企业店铺是否存在
     * @param companyId
     * @param shopType
     * @return
     */
    Integer checkCompanyShop(@Param("companyId") Integer companyId, @Param("shopType") Integer shopType);

    /**
     * add by WangHL
     * 设置店铺发布与取消
     * @param shop 店铺信息
     * @return int 修改结果
     */
    int setRelease(CompanyShop shop);

    /**
     * 获取店铺下的所有发布方案
     * @param shopId
     * @param isDeleted
     * @return
     */
    List<Integer> getShopDesignList(@Param("shopId") Integer shopId,
                                    @Param("isDeleted") Integer isDeleted);

    List<CompanyShop> selectShopByUserIds(@Param("userIds")Set<Long> userIds);

    void delCompanyShopByUserIds(@Param("userIds")Set<Long> userIds, @Param("loginName") String loginName);

    List<CompanyShop> listShopByCompanyId(Long companyId);

    Integer deleteShopById(@Param("list") List<Integer> listShopId,
                           @Param("loginName") String loginName);
}
