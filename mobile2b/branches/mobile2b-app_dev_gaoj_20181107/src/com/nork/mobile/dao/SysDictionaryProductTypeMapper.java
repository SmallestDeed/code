package com.nork.mobile.dao;

import com.nork.design.model.ProductsCost;
import com.nork.design.model.ProductsCostType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:39 2018/5/23 0023
 * @Modified By:
 */
@Repository
public interface SysDictionaryProductTypeMapper {

    //获取数据字典产品总类别
    List<ProductsCostType> getProductTotalType();

    //获取数据字典小类别
    List<ProductsCost> getProductCost();
}
