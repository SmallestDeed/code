package com.sandu.system.service.impl;

import com.sandu.product.dao.SysDictionaryMapper;
import com.sandu.product.model.output.ProductsCost;
import com.sandu.product.model.output.ProductsCostType;
import com.sandu.system.model.SysDictionary;
import com.sandu.system.model.search.SysDictionarySearch;
import com.sandu.system.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 4:57 2018/6/27 0027
 * @Modified By:
 */
@Service("sysDictionaryService")
public class SysDictionaryServiceImpl implements SysDictionaryService{

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    /**
     * 通过type和typeKey查找sysDictionary
     * @author huangsongbo
     * @param type
     * @param valueKey
     * @return
     */
    @Override
    public SysDictionary findOneByTypeAndValueKey(String type, String valueKey) {
        SysDictionarySearch sysDictionarySearch=new SysDictionarySearch();
        sysDictionarySearch.setStart(0);
        sysDictionarySearch.setLimit(1);
        sysDictionarySearch.setType(type);
        sysDictionarySearch.setValuekey(valueKey);
        List<SysDictionary> sysDictionaries=getPaginatedList(sysDictionarySearch);
        if(sysDictionaries!=null&&sysDictionaries.size()>0){
            return sysDictionaries.get(0);
        }
        return null;
    }

    /**
     *    分页获取数据
     * @return   List<SysDictionary>
     */
    @Override
    public List<SysDictionary> getPaginatedList(SysDictionarySearch sysDictionarySearch) {
        return sysDictionaryMapper.selectPaginatedList(sysDictionarySearch);
    }

    @Override
    public List<ProductsCostType> getProductTotalType() {
        return sysDictionaryMapper.getProductTotalType();
    }

    @Override
    public List<ProductsCost> getProductCost() {
        return sysDictionaryMapper.getProductCost();
    }


}
