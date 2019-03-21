package com.sandu.api.category.service.biz;

import com.sandu.api.category.model.Category;
import com.sandu.api.category.model.bo.CategoryTreeNode;

import java.util.List;

/**
 *
 * @author Sandu
 * @date 2017/12/16
 */
public interface CategoryBizService {
    /**
     * 根据公司加载品牌下的类目
     * @param companyId
     * @return
     */
    List<Category> getCategoryByCompanyId(Long companyId);

    /**
     * 查询此类目及以下所有分类的ID集合
     * @param code
     * @return
     */
    List<Integer> queryCategoryIdsByCategoryCode(String code);

    /**
     * 初始化分类树(3级)
     * @return
     */
    List<CategoryTreeNode> initCategoryTree(Integer filterType,Integer lastLevel);

    List<CategoryTreeNode> listCategoryInfo(String code);

    Category getCategoryByCode(String code);

    void syncCache();
}
