package com.sandu.api.shop.service;

import com.sandu.api.shop.input.CompanyShopAdd;
import com.sandu.api.shop.input.CompanyShopQuery;
import com.sandu.api.shop.input.CompanyShopUpdate;
import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.shop.output.CompanyShopDetailsVO;
import com.sandu.api.shop.output.CompanyShopVO;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 企业店铺-service接口
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Component
public interface CompanyShopService {

    /**
     * 新增店铺
     * @param shopAdd 新增对象
     * @param loginUser 当前用户对象
     * @return int 新增数据Id
     */
    int addShop(CompanyShopAdd shopAdd, LoginUser loginUser);

    /**
     * 删除店铺
     * @param shopId 店铺ID
     * @param loginUser 当前用户对象
     * @return 删除数量
     */
    int deleteShop(Integer shopId, LoginUser loginUser);

    /**
     * 更新店铺
     * @param shopUpdate 修改对象
     * @param loginUser 当前用户对象
     * @return 更新数量
     */
    int updateShop(CompanyShopUpdate shopUpdate, LoginUser loginUser);

    /**
     * 更新店铺
     * @param companyShop 修改对象
     * @param loginUser 当前用户对象
     * @return 更新数量
     */
    int updateCompanyShop(CompanyShop companyShop, LoginUser loginUser);


    /**
     * 通过店铺ID查询店铺信息
     * @param shopId 店铺ID
     * @return CompanyShop对象
     */
    CompanyShop getShopById(Integer shopId);

    /**
     * 通过店铺ID查询店铺信息
     * @param shopId 店铺ID
     * @return CompanyShopVO对象
     */
    CompanyShopDetailsVO getShopDetails(Integer shopId);

    /**
     * 获取满足条件的数据数量
     * @param query 查询条件对象
     * @return int 满足条件数据数量
     */
    int getCount(CompanyShopQuery query);

    /**
     * 获取满足条件的数据列表
     * @param query 查询条件对象
     * @return List<CompanyShop> 集合列表
     */
    List<CompanyShopVO> findList(CompanyShopQuery query);

    /**
     * 查询品牌馆信息
     * @param userId 用户ID
     * @param companyId 企业ID
     * @param businessType 店铺类型
     * @return CompanyShop
     */
    CompanyShopDetailsVO findBrandPavilion(Integer userId, Long companyId, Integer businessType);

    /**
     * 验证店铺唯一性
     * @param shopName  店铺名称
     * @param shopId 企业ID
     * @return
     */
    Integer checkShopName(String shopName, Long shopId);

    /**
     * 处理店铺分类（为厂商、经销商类型）
     * @param companyId
     * @param categoryIds
     * @return
     */
    boolean disposeShopCategory(Long companyId, String categoryIds);

    /**
     * 根据企业Id更新店铺分类
     *
     * @param companyShop
     * @return
     */
    int updateShopCategoryByCompanyId(CompanyShop companyShop);

    /**
     * 通过用户id查询用户所属店铺列表
     * @param userId 用户Id
     * @return list
     */
    List<CompanyShop> getShopListByUserId(Long userId);

    /**
     * 通过用户Id删除属于用户的店铺
     * @param userId 用户Id
     * @return
     */
    Integer deleteShopByUserId(Long userId,String loginUserName);

    /**
     * 校验企业店铺是否存在
     * @param companyId
     * @param shopType
     * @return
     */
    Integer checkCompanyShop(Integer companyId, Integer shopType);

    /**
     * 获取店铺下所有发布方案的ID集合
     * @param shopId
     * @param isDeleted
     * @return
     */
    List<Integer> getShopDesignList(Integer shopId, Integer isDeleted);

    /**
     * 设置店铺发布与取消发布
     * 店铺类型：品牌馆、建材家居默认发布到随选网、企业小程序、同城联盟，其他店铺类型发布到：随选网、同城联盟
     * @param shop 店铺信息
     * @param loginUser 当前登录用户
     * @param isRelease 发布状态
     * @return int
     */
    int setRelease(CompanyShop shop,LoginUser loginUser,Integer isRelease);

    List<CompanyShop> selectShopByUserIds(Set<Long> userIds);

    void delCompanyShopByUserIds(Set<Long> userIds,String loginName);

    List<CompanyShop> listShopByCompanyId(Long companyId);

    Integer deleteShopById(List<Integer> listShopId, String loginName);
}
