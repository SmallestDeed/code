package com.sandu.rendermachine.service.product;

import com.sandu.rendermachine.model.product.ProCategory;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 9:44 2018/4/24 0024
 * @Modified By:
 */
public interface ProCategoryService {

    /**
     * 获取所有的大类
     * @return
     */
    ProCategory getAllCategory();

    /**
     * 所有数据
     * @param  proCategory
     * @return   List<ProCategory>
     */
    List<ProCategory> getList(ProCategory proCategory);

    List<ProCategory> getListV2(ProCategory proCategory);

    /**
     * 获取数据详情
     * @param id
     * @return  ProCategory
     */
    public ProCategory get(Integer id);
}
