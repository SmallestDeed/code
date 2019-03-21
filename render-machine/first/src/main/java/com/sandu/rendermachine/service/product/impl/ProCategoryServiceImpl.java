package com.sandu.rendermachine.service.product.impl;

import com.sandu.rendermachine.common.cache.ProCategoryCacher;
import com.sandu.rendermachine.common.util.Utils;
import com.sandu.rendermachine.dao.product.ProCategoryMapper;
import com.sandu.rendermachine.model.product.ProCategory;
import com.sandu.rendermachine.model.product.SearchProCategorySmall;
import com.sandu.rendermachine.service.product.ProCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 9:45 2018/4/24 0024
 * @Modified By:
 */
@Service("proCategoryService")
public class ProCategoryServiceImpl implements ProCategoryService{

    @Autowired
    private ProCategoryMapper proCategoryMapper;

    @Override
    public ProCategory getAllCategory() {
        List<SearchProCategorySmall> categoryList = new ArrayList<SearchProCategorySmall>();
        ProCategory category = null;
        ProCategory pc = new ProCategory();
        pc.setState("0");
        pc.setOrder(" ordering ");

        List<ProCategory> categoryAllList = null;

        if(Utils.enableRedisCache()){
            categoryAllList = ProCategoryCacher.getAllList(pc);
        }else{
            categoryAllList = this.getListV2(pc);
        }


        if (categoryAllList != null && categoryAllList.size() >= 0) {
            for (ProCategory ppc : categoryAllList) {
                if (ppc.getId().intValue() == 1) {
                    category = ppc;
                    break;
                }
            }
        }
        categoryList = recursionCategory2(category, categoryAllList);
        category.setChildrenNodes(categoryList);
        return category;
    }

    /**
     * 所有数据
     *
     * @param  proCategory
     * @return   List<ProCategory>
     */
    @Override
    public List<ProCategory> getList(ProCategory proCategory) {
        return proCategoryMapper.selectList(proCategory);
    }

    /**
     *    获取数据详情
     *
     * @param id
     * @return  ProCategory
     */
    @Override
    public ProCategory get(Integer id) {
        return proCategoryMapper.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param  proCategory
     * @return   List<ProCategory>
     */
    @Override
    public List<ProCategory> getListV2(ProCategory proCategory) {
        return proCategoryMapper.selectListV2(proCategory);
    }

    public List<SearchProCategorySmall> recursionCategory2(
            ProCategory category, List<ProCategory> categoryAllList) {
        List<SearchProCategorySmall> childrenNodes = category
                .getChildrenNodes();

        ProCategory procate = new ProCategory();
        procate.setPid(category.getId());
        procate.setLongCode(category.getLongCode());

        List<ProCategory> list = new ArrayList<ProCategory>();
        if (categoryAllList != null && categoryAllList.size() > 0) {
            for (ProCategory pc : categoryAllList) {
                if (pc.getPid().intValue() == procate.getPid().intValue()
                        && pc.getLongCode().indexOf(category.getLongCode()) != -1) {
                    list.add(pc);
                }
            }
        } else {

            if(Utils.enableRedisCache()){
                list = ProCategoryCacher.getAllList(procate);
            }else{
                list = this.getList(procate);
            }
        }
        if (list != null && list.size() > 0) {
            if (childrenNodes == null) {
                childrenNodes = new ArrayList<SearchProCategorySmall>();
            }
        }
        SearchProCategorySmall newCategory = null;
        for (ProCategory childrenNode : list) {
            newCategory = new SearchProCategorySmall();
            newCategory.setAa(childrenNode.getId());
            newCategory.setCc(childrenNode.getPid());
            newCategory.setDd(childrenNode.getName());
            newCategory.setBb(childrenNode.getCode());
            newCategory.setEe(childrenNode.getOrdering());
            newCategory.setFf(recursionCategory2(childrenNode,
                    categoryAllList));
            newCategory.setGg(childrenNode.getNameFirstLetter());
            childrenNodes.add(newCategory);
        }
        category.setChildrenNodes(childrenNodes);

        return childrenNodes;
    }
}
