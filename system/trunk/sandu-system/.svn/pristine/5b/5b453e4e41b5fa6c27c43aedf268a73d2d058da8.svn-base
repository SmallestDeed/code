package com.sandu.api.shop.service;

import com.sandu.api.shop.input.CompanyShopArticleAdd;
import com.sandu.api.shop.input.CompanyShopArticleQuery;
import com.sandu.api.shop.input.CompanyShopArticleReleaseUpdate;
import com.sandu.api.shop.input.CompanyShopArticleUpdate;
import com.sandu.api.shop.model.CompanyShopArticle;
import com.sandu.api.shop.output.CompanyShopArticleDetailVO;
import com.sandu.api.shop.output.CompanyShopArticleListVO;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 店铺博文-Service层接口
 * @author WangHaiLin
 * @date 2018/8/9  10:14
 */
@Component
public interface CompanyShopArticleService {

    /**
     * 新增
     * @param articleAdd 新增对象
     * @param loginUser 当前用户对象
     * @return long 新增数据Id
     */
    Long add(CompanyShopArticleAdd articleAdd, LoginUser loginUser);

    /**
     * 删除
     * @param id 主键Id
     * @param loginUser 当前登录用户对象
     * @return 删除数量
     */
    int delete(Long id, LoginUser loginUser, Integer shopType);


    /**
     * 更新
     * @param articleUpdate 修改对象
     * @param loginUser 当前登录用户对象
     * @return 更新数量
     */
    int update(CompanyShopArticleUpdate articleUpdate, LoginUser loginUser);


    /**
     * 获取满足条件的数据列表
     * @param query 查询条件
     * @return  集合列表
     */
    List<CompanyShopArticleListVO> findList(CompanyShopArticleQuery query);

    /**
     * 获取满足条件的数据数量
     * @param query 查询条件
     * @return int
     */
    Integer getCount(CompanyShopArticleQuery query);

    /**
     * 查看店铺博文详情
     * @param articleId 店铺博文Id
     * @return CompanyShopArticleDetailVO 博文详情
     */
    CompanyShopArticleDetailVO select(Long articleId);

    /**
     * 发布店铺博文
     * @param update 博文发布入参
     * @param loginUser 当前登录用户
     * @return boolean
     */
    boolean release(CompanyShopArticleReleaseUpdate update, LoginUser loginUser);

    /**
     * 根据店铺Id批量删除
     * @param shopId 店铺Id
     * @param loginUser 当前用户对象
     * @return 删除数量
     */
    int deleteByShopId(Long shopId, LoginUser loginUser);

    /**
     * add by WangHL
     * 通过店铺Id删除属于店铺的博文列表
     * @param shopIds 店铺Id
     *
     * @return list
     */
    Integer deleteArticleByShopId(List<Integer> shopIds,String loginUserName);

    /**
     * add by WangHL
     * 通过店铺Id查找属于店铺的博文数量
     * @param shopIds 店铺Id
     * @return list
     */
    Integer getArticleByShopId(List<Integer> shopIds);

    /**
     * 获取企业店铺ID
     * @param shopId 店铺ID
     * @return
     */
    Integer getMainCompanyShopId(Integer shopId);

    /**
     * 修改主店铺的博文状态
     * @param mainShopId
     * @param shopId
     * @param status
     * @return
     */
    Integer updateMainShopArticleStatus(Integer mainShopId, Integer shopId, Integer status);
}
