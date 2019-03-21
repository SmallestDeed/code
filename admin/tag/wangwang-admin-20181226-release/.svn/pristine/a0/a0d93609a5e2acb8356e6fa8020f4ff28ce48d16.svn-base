package com.sandu.service.storage.impl;

import com.sandu.api.category.model.Category;
import com.sandu.api.category.model.bo.CategoryTreeNode;
import com.sandu.api.category.service.CategoryService;
import com.sandu.commons.gson.GsonUtil;
import com.sandu.api.storage.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

    private final CategoryService categoryService;
    private final RedisTemplate redisTemplate;

    private final String CATEGORY_CACHE_KEY = "categoryMap";

    @Autowired
    public CacheServiceImpl(CategoryService categoryService, RedisTemplate redisTemplate) {
        this.categoryService = categoryService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<CategoryTreeNode> listAllCategory() {
        log.info("search category in cache");
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(CATEGORY_CACHE_KEY);
        Map<String, String> categoryMap = hashOperations.entries();
        if (categoryMap.isEmpty()) {
            categoryMap = this.resetCategoryCache();
        }
        Map<Integer, List<CategoryTreeNode>> map = new HashMap<>(categoryMap.size());
        categoryMap.forEach((k, v) -> map.put(Integer.valueOf(k), Arrays.asList(GsonUtil.fromJson(v, CategoryTreeNode[].class))));
        map.entrySet().parallelStream().forEach(item ->
                item.getValue().forEach(node ->
                        node.setChildren(map.get(node.getId().intValue()))
                )
        );
        return Optional.ofNullable(map.get(1)).orElseGet(Collections::emptyList);
    }

    @Override
    public Map<String, String> resetCategoryCache() {
        log.info("reset category cache");
        redisTemplate.delete(CATEGORY_CACHE_KEY);
        BoundHashOperations hashOperations = redisTemplate.boundHashOps(CATEGORY_CACHE_KEY);

        List<Category> nodes = categoryService.listCategoryNodesByLevel(Category.LEVEL_FIRST, Category.LEVEL_FIFTH);
        Map<String, String> pid2Node = nodes.parallelStream().map(category -> {
            CategoryTreeNode tmp = new CategoryTreeNode();
            BeanUtils.copyProperties(category, tmp);
            return tmp;
        }).collect(Collectors.groupingBy(item -> item.getPid().toString(),
                Collectors.collectingAndThen(Collectors.toList(), GsonUtil::toJson)));

        hashOperations.putAll(pid2Node);
        return pid2Node;
    }
}
