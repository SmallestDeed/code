package com.sandu.service.dynamic.impl;


import com.sandu.api.dynamic.input.DynamicQuery;
import com.sandu.api.dynamic.model.Dynamic;
import com.sandu.api.dynamic.service.DynamicService;
import com.sandu.service.dynamic.dao.DynamicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Sandu
 */
@Service("dynamicService")
public class DynamicServiceImpl implements DynamicService {
    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    public List<Dynamic> queryAll(DynamicQuery query) {
        List<Dynamic> allList = new ArrayList<>();
        dynamicMapper.updateSullpyModified();
        dynamicMapper.updateShopModified();
        if(query.getType() != null) {
            if(query.getType() == 0) {
                List<Dynamic> supplyList = dynamicMapper.querySupplyList();
                allList.addAll(supplyList);
            }
            if(query.getType() == 1){
                List<Dynamic> shopList = dynamicMapper.queryShopList();
                allList.addAll(shopList);
            }
        }else{
            List<Dynamic> shopList = dynamicMapper.queryShopList();
            List<Dynamic> supplyList = dynamicMapper.querySupplyList();
            allList.addAll(shopList);
            allList.addAll(supplyList);
        }
        for(Dynamic dynamic : allList){
            dynamic.setRecommendedDate(dynamic.getGmtModified());
        }
        Comparator<Dynamic> comparator = (t1, t2) -> t1.getRecommendedDate().compareTo(t2.getRecommendedDate());
        allList.sort(comparator.reversed());
        if(query.getDynamicName() != null) {
            Iterator iterator = allList.iterator();
            while(iterator.hasNext()){
                Dynamic dynamic = (Dynamic) iterator.next();
                if(dynamic.getDynamicName().indexOf(query.getDynamicName())<0) {
                    iterator.remove();
                }
            }
        }
        List<Dynamic> results = allList.subList(0,allList.size()>10?10:allList.size());
        return results;
    }
}
