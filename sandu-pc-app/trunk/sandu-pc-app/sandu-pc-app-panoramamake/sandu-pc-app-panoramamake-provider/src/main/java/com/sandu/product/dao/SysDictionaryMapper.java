package com.sandu.product.dao;

import com.sandu.product.model.output.ProductsCost;
import com.sandu.product.model.output.ProductsCostType;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 5:05 2018/6/27 0027
 * @Modified By:
 */
@Repository
public interface SysDictionaryMapper {

    List<SysDictionary> selectPaginatedList(SysDictionarySearch sysDictionarySearch);

    //获取数据字典产品总类别
    List<ProductsCostType> getProductTotalType();

    //获取数据字典小类别
    List<ProductsCost> getProductCost();
}
