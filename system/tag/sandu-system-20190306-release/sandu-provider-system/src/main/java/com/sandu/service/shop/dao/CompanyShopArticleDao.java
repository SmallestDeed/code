package com.sandu.service.shop.dao;

import com.sandu.api.shop.input.CompanyShopArticleQuery;
import com.sandu.api.shop.model.CompanyShopArticle;
import com.sandu.api.shop.output.CompanyShopArticleListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 店铺博文--dao层接口
 * @author WangHaiLin
 * @date 2018/8/9  11:02
 */
@Repository
public interface CompanyShopArticleDao {

    /**
     * 新增
     * @param article 新增对象
     * @return int 新增结果
     */
    int add(CompanyShopArticle article);

    /**
     * 通过Id查询店铺博文
     * @param id 博文Id
     * @return CompanyShopArticle
     */
    CompanyShopArticle get(@Param("id") Long id);

    /**
     * 修改店铺博文
     * @param article 博文实体
     * @return int 修改结果
     */
    int update(CompanyShopArticle article);

    /**
     * 查询店铺博文列表
     * @param query 查询入参
     * @return list 返回结果
     */
    List<CompanyShopArticle> getList(CompanyShopArticleQuery query);

    /**
     * 查询店铺博文数量
     * @param query 查询条件
     * @return int 数量
     */
    int getCount (CompanyShopArticleQuery query);

    /**
     * 通过店铺Id进行批量删除
     * @param shopId 店铺Id
     * @param modifier 修改人
     * @return int 操作结果
     */
    int deleteByShopId(@Param("shopId") Long shopId,@Param("modifier") String modifier);


    /**
     * add by WangHL
     * 通过店铺Ids删除属于店铺的博文列表
     * @param shopIds 店铺Ids
     * @return list
     */
    Integer deleteArticleByShopId(@Param("shopIds") List<Integer> shopIds,@Param("loginUserName") String loginUserName);

    /**
     * add by WangHL
     * 通过店铺Id查找属于店铺的博文数量
     * @param shopIds 店铺Id
     * @return list
     */
    Integer getArticleByShopId(@Param("shopIds")List<Integer> shopIds);


    Integer getMainCompanyShopId(Integer shopId);

    Long getMainShopArticleId(@Param("articleId") Long articleId,
                              @Param("mainShopId") Integer mainShopId);

    Integer updateMainShopArticleStatus(@Param("mainShopId") Integer mainShopId,
                                        @Param("shopId") Integer shopId,
                                        @Param("status") Integer status);
}
