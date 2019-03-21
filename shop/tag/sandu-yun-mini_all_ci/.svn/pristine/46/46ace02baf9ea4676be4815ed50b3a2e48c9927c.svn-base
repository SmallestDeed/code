package com.sandu.company.dao;

import com.sandu.company.model.CompanyShopArticle;
import com.sandu.company.model.query.ShopArticleQuery;
import com.sandu.company.model.vo.CompanyShopArticleDetailVO;
import com.sandu.company.model.vo.CompanyShopArticleListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 店铺博文--前端展示--Dao层接口
 * @author WangHaiLin
 * @date 2018/8/10  16:02
 */
@Repository
public interface CompanyShopArticleDao {
    /**
     * 查询博文列表
     * @param query 查询条件
     * @return List
     */
    List<CompanyShopArticleListVO> getList(ShopArticleQuery query);

    /**
     * 查询博文数量
     * @param query
     * @return
     */
    int getCount(ShopArticleQuery query);


    /**
     * 查看博文详情
     * @param articleId 博文Id
     * @return CompanyShopArticleDetailVO 博文详情
     */
    CompanyShopArticleDetailVO get(@Param("articleId") Long articleId);


    /**
     * 修改浏览数量(获取一次详情,博文数量加1)
     * @param article 博文实体
     * @return boolean true:成功；false:失败
     */
    int updateBrowseCount(CompanyShopArticle article);

}
