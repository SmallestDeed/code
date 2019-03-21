package com.nork.common.objectconvert.cityUnion;

import com.nork.cityunion.model.UnionGroupDetails;
import com.nork.cityunion.model.vo.UnionGroupDetailsVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxc on 2018/1/21 0021.
 */
public class UnionGroupDetailsObject {

    /**
     * 获取联盟组详情集合 (转换联盟组详情Volist)
     * @param groupDetailsList
     * @return
     */
    public static List<UnionGroupDetailsVO> parseToGroupDetailsVOListByGroupDetailsList(List<UnionGroupDetails> groupDetailsList) {

        List<UnionGroupDetailsVO> groupDetailsVOList = new ArrayList<>();
        if (groupDetailsList == null || groupDetailsList.size() == 0) {
            return groupDetailsVOList;
        }
        for (UnionGroupDetails unionGroupDetails : groupDetailsList) {
            UnionGroupDetailsVO groupDetailsVO = new UnionGroupDetailsVO();
            groupDetailsVO.setGroupDetailsId(unionGroupDetails.getId());
            groupDetailsVO.setName(unionGroupDetails.getName());
            groupDetailsVO.setGroupId(unionGroupDetails.getGroupId());
            groupDetailsVO.setBrandId(unionGroupDetails.getBrandId());
            groupDetailsVO.setContact(unionGroupDetails.getContact());
            groupDetailsVO.setPhone(unionGroupDetails.getPhone());
            groupDetailsVO.setAddress(unionGroupDetails.getAddress());
            groupDetailsVO.setBrandVal(unionGroupDetails.getBrandName());
            groupDetailsVOList.add(groupDetailsVO);
        }
        return groupDetailsVOList;
    }
}
