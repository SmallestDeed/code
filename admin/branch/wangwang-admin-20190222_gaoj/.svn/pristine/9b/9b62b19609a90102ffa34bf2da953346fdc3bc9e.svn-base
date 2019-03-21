package com.sandu.service.category.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.sandu.api.category.model.Category;
import com.sandu.api.category.service.CategoryService;
import com.sandu.service.category.dao.CategoryDao;
import com.sandu.util.Commoner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author Sandu
 * @date 2017/12/16
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public int saveCategory(Category category) {
        return categoryDao.saveCategory(category);
    }

    @Override
    public int deleteCategoryById(long id) {
        return categoryDao.deleteCategoryById(id);

    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    @Override
    public List<Category> getCategoryByPid(int pid) {
        return categoryDao.getCategoryByPid(pid);
    }

    @Override
    public Category getCategoryInfoById(long id) {
        return categoryDao.getCategoryInfoById(id);
    }

    @Override
    public List<Category> queryCategoryByCodeOrName(String code, String name, int page, int limit, Integer pid,
                                                    String orders, String order, String orderWay) {
        PageHelper.startPage(page, limit);
        return categoryDao.queryCategoryByCodeOrName(code, name, pid, orders, order, orderWay);
    }

    @Override
    public List<Category> getCategoryByCompanyId(long companyId) {
        return categoryDao.getCategoryByCompanyId(companyId);
    }

    @Override
    public List<Integer> getCategoryIdsByCategoryCodes(List<String> codes) {
        if (codes.isEmpty()) {
            return Collections.emptyList();
        }
        return categoryDao.getCategoryIdsByCategoryCodes(codes);
    }

    @Override
    public Category getCategoryByCode(String code) {
        return categoryDao.getCategoryByCode(code);
    }

    @Override
    public List<Integer> getChildCategoryIdByCategory(Category category, int page, int size) {
        PageHelper.startPage(page, size);
        return categoryDao.getChildCategoryIdByCategory(category);
    }

    @Override
    public List<Category> listCategoryNodesByLevel(int startLevel, int endLevel) {
        return categoryDao.listCategoryNodesByLevel(startLevel, endLevel);
    }

    @Override
    public List<Category> getCategorysByCodes(List<String> categoryCodes) {
        if (categoryCodes.isEmpty()) {
            return Collections.emptyList();
        }
        return categoryDao.getCategorysByCodes(categoryCodes);
    }

    @Override
    public List<Category> getCategoryByIds(List<Integer> categoryIdsret) {
        if (categoryIdsret.isEmpty()) {
            return Collections.emptyList();
        }
        return categoryDao.getCategoryByIds(categoryIdsret);
    }

    @Override
    public Map<Integer, String> getCategoryNameAndParentNameByIds(List<Integer> categoryIdsret) {
        if (categoryIdsret.size() == 0) {
            return Collections.emptyMap();
        }
        categoryIdsret = categoryIdsret.stream().distinct().collect(Collectors.toList());
        List<Map<String, Object>> list = categoryDao.getCategoryNameAndParentNameByIds(categoryIdsret);
        Map<Integer, String> map = new HashMap<>(list.size());
        for (Map<String, Object> tmp : list) {
            if (Commoner.isNotEmpty(tmp.get("id")) && Commoner.isNotEmpty(tmp.get("names"))) {
                map.put(Integer.parseInt(tmp.get("id").toString()), tmp.get("names").toString());
            }
        }
        return map;
    }

    @Override
    public List<Category> getTreeLevelNodeByCategoryIds(List<Integer> categoryIds) {
        if (categoryIds.isEmpty()) {
            return Collections.emptyList();
        }
        return categoryDao.getTreeLevelNodeByCategoryIds(categoryIds);
    }

    @Override
    public List<Category> listCategoryInfoByParentCode(String code) {
        if(StringUtils.isBlank(code)){
            return Collections.emptyList();
        }
        return categoryDao.listCategoryInfoByParentCode(code);
    }
}
