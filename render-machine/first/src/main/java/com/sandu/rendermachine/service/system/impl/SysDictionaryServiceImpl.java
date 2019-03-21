package com.sandu.rendermachine.service.system.impl;

import com.sandu.rendermachine.common.cache.SysDictionaryCacher;
import com.sandu.rendermachine.dao.system.SysDictionaryMapper;
import com.sandu.rendermachine.model.system.SysDicitonaryOptimize;
import com.sandu.rendermachine.model.system.SysDictionary;
import com.sandu.rendermachine.model.system.SysDictionarySearch;
import com.sandu.rendermachine.service.system.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 上午 11:20 2018/5/14 0014
 * @Modified By:
 */
public class SysDictionaryServiceImpl implements SysDictionaryService {

    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

    @Override
    public List<SysDicitonaryOptimize> getListOptimize(SysDictionarySearch sysDictionarySearch) {

        List<SysDicitonaryOptimize> list = sysDictionaryMapper.getListOptimize(sysDictionarySearch);
        // 先获取所有大类
        List<SysDictionary> productTypeList = this.findAllByType("productType");
        //获取大类下的所有小类
        SysDictionaryCacher.getAllSmallTypeByType(list, productTypeList);

        return list;
    }

}
