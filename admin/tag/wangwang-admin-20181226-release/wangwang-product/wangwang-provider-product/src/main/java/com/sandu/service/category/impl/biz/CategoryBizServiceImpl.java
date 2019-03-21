package com.sandu.service.category.impl.biz;

import com.sandu.api.category.model.Category;
import com.sandu.api.category.model.bo.CategoryTreeNode;
import com.sandu.api.category.service.CategoryService;
import com.sandu.api.category.service.biz.CategoryBizService;
import com.sandu.api.storage.service.CacheService;
import com.sandu.constant.Punctuation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.sandu.api.category.model.Category.LEVEL_THIRD;

/**
 * @author Sandu
 */
@Service("categoryBizService")
@Slf4j
public class CategoryBizServiceImpl implements CategoryBizService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CacheService cacheService;

    @Override
    public List<Category> getCategoryByCompanyId(Long companyId) {
        return null;
    }

    @Override
    public List<Integer> queryCategoryIdsByCategoryCode(String categoryCodes) {
        if (StringUtils.isBlank(categoryCodes)) {
            return Collections.emptyList();
        }
        List<Integer> categoryIds;
        if (categoryCodes.contains(Punctuation.COMMA)) {
            //多个节点,五级节点
            //查询categoryId集合
            List<String> codes = Arrays.asList(categoryCodes.split(Punctuation.COMMA));
            categoryIds = categoryService.getCategoryIdsByCategoryCodes(codes);
        } else {
            //查询category
            Category category = categoryService.getCategoryByCode(categoryCodes);
            if (category == null) {
                return Collections.emptyList();
            }
            if (Category.LEVEL_FIFTH.equals(category.getLevel())) {
                //单个五级节点
                categoryIds = new ArrayList<>();
                categoryIds.add(category.getId().intValue());
            } else {
                //非五级节点
                //设置查询条件
                Category queryCategory = setQueryCategoryLevel(category);
                categoryIds = categoryService.getChildCategoryIdByCategory(queryCategory, 1, 30);
            }
        }
        return categoryIds;
    }

    @Override
    public List<CategoryTreeNode> initCategoryTree(Integer filterType, Integer lastLevel) {
        List<CategoryTreeNode> categoryTreeNodes;
        try {
            categoryTreeNodes = cacheService.listAllCategory();
        } catch (Exception e) {
            log.warn("从Redis获取分类数据失败,exception:{}",e.getMessage());
            categoryTreeNodes = initCategoryTreeInDB(lastLevel);
        }
        return categoryTreeNodes;
    }

    private List<CategoryTreeNode> initCategoryTreeInDB(Integer lastLevel) {
        List<Category> nodes = categoryService.listCategoryNodesByLevel(Category.LEVEL_FIRST, lastLevel);
        Map<Integer, List<CategoryTreeNode>> pid2Node = nodes.parallelStream().map(category -> {
            CategoryTreeNode tmp = new CategoryTreeNode();
            BeanUtils.copyProperties(category, tmp);
            return tmp;
        }).collect(Collectors.groupingBy(CategoryTreeNode::getPid, Collectors.toList()));
        pid2Node.entrySet().parallelStream().forEach(item ->
                item.getValue().forEach(node ->
                        node.setChildren(pid2Node.get(node.getId().intValue()))
                )
        );
        return Optional.ofNullable(pid2Node.get(1)).orElseGet(Collections::emptyList);
    }

    @Override
    public List<CategoryTreeNode> listCategoryInfo(String code) {
        List<Category> categoryList = categoryService.getCategoryByIds(queryCategoryIdsByCategoryCode(code));
        return categoryList.stream().filter(item -> item.getLevel().equals(Category.LEVEL_FOURTH))
                .map(item -> {
                    CategoryTreeNode parentNode = new CategoryTreeNode();
                    BeanUtils.copyProperties(item, parentNode);
                    List<CategoryTreeNode> childrenNode = categoryList.stream().filter(cNode -> cNode.getPid().equals(item.getId().intValue()))
                            .map(cNode -> {
                                CategoryTreeNode childrenNodeTmp = new CategoryTreeNode();
                                BeanUtils.copyProperties(cNode, childrenNodeTmp);
                                return childrenNodeTmp;
                            }).collect(Collectors.toList());
                    parentNode.setChildren(childrenNode);
                    return parentNode;
                }).collect(Collectors.toList());
    }

    @Override
    public Category getCategoryByCode(String code) {
        return categoryService.getCategoryByCode(code);
    }

    @Override
    public void syncCache() {
        log.info("start sync category cache");
        cacheService.resetCategoryCache();
        log.info("end sync category cache");
    }

    private Category setQueryCategoryLevel(Category category) {
        Category ret = new Category();
        if (category != null) {
            Integer level = category.getLevel();
            String categoryCode = category.getCode();
            if (level != null && level.equals(Category.LEVEL_ZERO)) {
                ret.setFirstStageCode(categoryCode);
            } else if (level != null && level.equals(Category.LEVEL_FIRST)) {
                ret.setSecondStageCode(categoryCode);
            } else if (level != null && level.equals(Category.LEVEL_SECOND)) {
                ret.setThirdStageCode(categoryCode);
            } else if (level != null && level.equals(LEVEL_THIRD)) {
                ret.setFourthStageCode(categoryCode);
            } else if (level != null && level.equals(Category.LEVEL_FOURTH)) {
                ret.setFifthStageCode(categoryCode);
            }
            return ret;
        }
        return null;
    }
}
