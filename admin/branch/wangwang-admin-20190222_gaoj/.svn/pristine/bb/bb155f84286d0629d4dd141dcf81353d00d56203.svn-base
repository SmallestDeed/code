package com.sandu.api.category.service;

import com.sandu.api.category.model.Category;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Sandu
 * @date 2017/12/16
 */
public interface CategoryService {
    /**
     * 添加
     * @param category
     * @return
     */
    int saveCategory(Category category);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteCategoryById(long id);

    /**
     * 更新
     * @param category
     */
    void updateCategory(Category category);
    /**
     * 根据父类ID加载子节点(用于加载树形菜单)
     * @param pid
     * @return
     */
    List<Category> getCategoryByPid(int pid);

    /**
     * 根据ID加载节点详情
     * @param id
     * @return
     */
    Category getCategoryInfoById(long id);

    /**
     * 过滤查询子节点
     * @param code
     * @param name
     * @param page
     * @param limit
     * @param pid
     * @return
     */
    List<Category> queryCategoryByCodeOrName(String code, String name, int page, int limit, Integer pid,
                                             String orders, String order, String orderWay);

    /**
     * 根据企业ID加载企业分类
     * @param companyId
     * @return
     */
    List<Category> getCategoryByCompanyId(long companyId);



    /**
     * 根据code(集合)查询分类ID(集合)
     * @param codes
     * @return
     */
    List<Integer> getCategoryIdsByCategoryCodes(List<String> codes);

    /**
     * 根据code查询分类详情
     * @param categoryCodes
     * @return
     */
    Category getCategoryByCode(String categoryCodes);

    /**
     * 根据级别code查询所有五级节点ID
     * @param category
     * @return
     */
    List<Integer> getChildCategoryIdByCategory(Category category, int page, int size);

    /**
     *
     * @param startLevel  大于等于startLevel
     * @param endLevel     小于等于endLevel
     * @return
     */
    List<Category> listCategoryNodesByLevel(int startLevel, int endLevel);

    List<Category> getCategorysByCodes(List<String> categoryCodes);

    /**
     * 根据
     * @param categoryIdsret
     * @return
     */
    List<Category> getCategoryByIds(List<Integer> categoryIdsret);

    Map<Integer,String> getCategoryNameAndParentNameByIds(List<Integer> categoryIdsret);

    /**
     * 根据分类ID查询三级节点
     * @param categoryIds
     * @return
     */
    List<Category> getTreeLevelNodeByCategoryIds(List<Integer> categoryIds);

    List<Category> listCategoryInfoByParentCode(String code);
}
