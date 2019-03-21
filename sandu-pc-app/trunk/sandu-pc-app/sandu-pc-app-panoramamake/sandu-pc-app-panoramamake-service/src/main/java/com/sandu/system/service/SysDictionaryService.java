package com.sandu.system.service;

import com.sandu.product.model.output.ProductsCost;
import com.sandu.product.model.output.ProductsCostType;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:57 2018/6/27 0027
 * @Modified By:
 */
public interface SysDictionaryService {

    /**
     * 通过type和typeKey查找sysDictionary
     * @author huangsongbo
     * @param type
     * @param valueKey
     * @return
     */
    SysDictionary findOneByTypeAndValueKey(String type, String valueKey);

    /**
     *    分页获取数据
     *
     * @param  sysDictionarytSearch
     * @return   List<SysDictionary>
     */
    List<SysDictionary> getPaginatedList(SysDictionarySearch sysDictionarytSearch);

    //获取数据字典产品总类别
    List<ProductsCostType> getProductTotalType();

    //获取数据字典小类别
    List<ProductsCost> getProductCost();
}
