package com.nork.common.objectconvert.cityUnion;

import com.nork.cityunion.model.UnionStorefront;
import com.nork.cityunion.model.vo.UnionStorefrontVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxc on 2018/1/17 0017.
 */
public class UnionStorefrontObject {

    /**
     * 获取联盟店面集合 (转换店面Volist)
     * @param storefrontList
     * @return
     */
    public static List<UnionStorefrontVO> parseToStorefrontVOListByStorefrontList(List<UnionStorefront> storefrontList) {

        List<UnionStorefrontVO> storefrontVOList = new ArrayList<>();
        if (storefrontList == null || storefrontList.size() == 0) {
            return storefrontVOList;
        }
        for (UnionStorefront storefront : storefrontList) {
            UnionStorefrontVO storefrontVO = new UnionStorefrontVO();
            storefrontVO.setStorefrontId(storefront.getId());
            storefrontVO.setName(storefront.getName());
            storefrontVO.setPhone(storefront.getPhone());
            storefrontVO.setContact(storefront.getContact());
            storefrontVO.setAddress(storefront.getAddress());
            storefrontVO.setIsDisplayed(storefront.getIsDisplayed());
            storefrontVOList.add(storefrontVO);
        }
        return storefrontVOList;
    }

    /**
     * 获取联盟店面单个信息 (转换店面Vo对象)
     * @param storefront
     * @return
     */
    public static UnionStorefrontVO parseToStorefrontVOByStorefront(UnionStorefront storefront) {

        UnionStorefrontVO storefrontVO = null;
        if (null != storefront) {
            storefrontVO = new UnionStorefrontVO();
            storefrontVO.setStorefrontId(storefront.getId());
            storefrontVO.setName(storefront.getName());
            storefrontVO.setPhone(storefront.getPhone());
            storefrontVO.setContact(storefront.getContact());
            storefrontVO.setAddress(storefront.getAddress());
            storefrontVO.setIsDisplayed(storefront.getIsDisplayed());
        }
        return storefrontVO;
    }

}
