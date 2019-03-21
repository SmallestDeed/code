package com.nork.common.objectconvert.cityUnion;

import com.nork.cityunion.model.UnionSpecialOffer;
import com.nork.cityunion.model.vo.UnionSpecialOfferVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxc on 2018/1/18 0018.
 */
public class UnionSpecialOfferObject {

    /**
     * 获取优惠活动对象 （转换vo对象）
     * @param specialOffer
     * @return
     */
    public static UnionSpecialOfferVO paramToSpecialOfferVOBySpecialOffer(UnionSpecialOffer specialOffer) {

        UnionSpecialOfferVO specialOfferVO = null;
        if (specialOffer != null) {
            specialOfferVO = new UnionSpecialOfferVO();
            specialOfferVO.setSpecialOfferId(specialOffer.getId());
            specialOfferVO.setSpecialOfferName(specialOffer.getSpecialOfferName());
            specialOfferVO.setSpecialOfferContent(specialOffer.getSpecialOfferContent());
        }
        return specialOfferVO;
    }

    /**
     * 获取优惠活动列表 （转换vo list集合）
     * @param specialOfferList
     * @return
     */
    public static List<UnionSpecialOfferVO> paramToSpecialOfferVOListBySpecialOfferList(List<UnionSpecialOffer> specialOfferList) {

        List<UnionSpecialOfferVO> specialOfferVOList = new ArrayList<>();
        if (specialOfferList == null || specialOfferList.size() == 0) {
            return  specialOfferVOList;
        }
        for (UnionSpecialOffer specialOffer : specialOfferList) {
            UnionSpecialOfferVO specialOfferVO = new UnionSpecialOfferVO();
            specialOfferVO.setSpecialOfferId(specialOffer.getId());
            specialOfferVO.setSpecialOfferName(specialOffer.getSpecialOfferName());
            specialOfferVO.setSpecialOfferContent(specialOffer.getSpecialOfferContent());
            specialOfferVOList.add(specialOfferVO);
        }
        return specialOfferVOList;
    }
}
