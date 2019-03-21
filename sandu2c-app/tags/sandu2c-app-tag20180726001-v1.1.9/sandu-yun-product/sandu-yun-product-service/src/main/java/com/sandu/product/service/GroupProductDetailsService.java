package com.sandu.product.service;

import com.sandu.product.model.GroupProductDetails;

import java.util.List;


/**
 * @version V1.0
 * @Title: GroupProductDetailsService.java
 * @Package com.sandu.product.service
 * @Description:产品模块-产品组合关联表Service
 * @createAuthor pandajun
 * @CreateDate 2016-06-22 20:37:16
 */
public interface GroupProductDetailsService {
    /**
     * 新增数据
     *
     * @param groupProductDetails
     * @return int
     */
    int add(GroupProductDetails groupProductDetails);

    /**
     * 更新数据
     *
     * @param groupProductDetails
     * @return int
     */
    int update(GroupProductDetails groupProductDetails);

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    int delete(Integer id);

    /**
     * 获取数据详情
     *
     * @param id
     * @return GroupProductDetails
     */
    GroupProductDetails get(Integer id);

    /**
     * 所有数据
     *
     * @param groupProductDetails
     * @return List<GroupProductDetails>
     */
    List<GroupProductDetails> getList(GroupProductDetails groupProductDetails);

}
