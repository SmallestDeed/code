package com.sandu.service.category.dao;

import com.sandu.api.category.model.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sandu
 * @date 2017/12/16
 */
@Repository
public interface CategoryDao {

    int deleteCategoryById(@Param("id") long id);

    int saveCategory(Category category);

    void updateCategory(Category category);

    List<Category> getCategoryByPid(@Param("pid") int pid);

    Category getCategoryInfoById(@Param("id") long id);

    List<Category> queryCategoryByCodeOrName(@Param("code") String code,
                                             @Param("name") String name,
                                             @Param("pid") Integer pid,
                                             @Param("orders") String orders,
                                             @Param("order") String order,
                                             @Param("orderWar") String orderWay);

    List<Category> getCategoryByCompanyId(@Param("companyId") long companyId);

    Category getCategoryByCode(@Param("code") String code);

    List<Integer> getCategoryIdsByCategoryCodes(@Param("codes")List<String> codes);

    List<Integer> getChildCategoryIdByCategory(Category category);

    List<Category> listCategoryNodesByLevel(@Param("startLevel") int startLevel, @Param("endLevel") int endLevel);

    List<Category> getCategorysByCodes(List<String> categoryCodes);

    List<Category> getCategoryByIds(List<Integer> categoryIds);

    List<Map<String,Object>> getCategoryNameAndParentNameByIds(List categoryIdsret);

    List<Category> getTreeLevelNodeByCategoryIds(List<Integer> categoryIds);

    List<Category> listCategoryInfoByParentCode(String code);
}
