package com.sandu.common.util;


import com.sandu.system.model.SysDictionary;

import java.util.List;

public class DecorateUtil {


    /**
     * 根据具体的装修报价，获得匹配的数据字典中的装修报价区间的value值
     * @param decoratePrice
     * @return
     */
    public static int getMatchPriceRangeOfDecorate(Integer decoratePrice, List<SysDictionary> priceRangeList) {
        if (decoratePrice == null || decoratePrice == 0) {
            throw new RuntimeException("装修报价为空");
        }
        if (priceRangeList == null || priceRangeList.size() == 0) {
            throw new RuntimeException("报价区间为空");
        }
        for (SysDictionary sysDictionary : priceRangeList) {
            //获取区间，截取并获取报价区间集合，判断报价值在哪个区间
            String name = sysDictionary.getName();
            String[] split = name.split("-");
            if (split.length == 2) {
                //区间为两个值
                int min = Integer.parseInt(split[0]);
                int max = Integer.parseInt(split[1]);
                if (decoratePrice>=min && decoratePrice<max) {
                    return sysDictionary.getValue();
                }
            } else {
                //区间为1个值（XXX以上这种格式）
                String substring = split[0].substring(0, (split[0].length() - 2));
                int min = Integer.parseInt(substring);
                if (decoratePrice >= min) {
                    return sysDictionary.getValue();
                }
            }
        }
        throw new RuntimeException("匹配不到报价值，decoratePrice="+decoratePrice+",priceRangeList="+priceRangeList);
    }

}
