package com.nork.common.objectconvert.cityUnion;

import com.nork.cityunion.model.UnionGroup;
import com.nork.cityunion.model.UnionStorefront;
import com.nork.cityunion.model.vo.UnionGroupVO;
import com.nork.cityunion.model.vo.UnionStorefrontVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxc on 2018/1/21 0021.
 */
public class UnionGroupObject {

    /**
     * 获取联盟组集合 (转换联盟组Volist)
     * @param groupList
     * @return
     */
    public static List<UnionGroupVO> parseToGroupVOListByGroupList(List<UnionGroup> groupList) {

        List<UnionGroupVO> groupVOList = new ArrayList<>();
        if (groupList == null || groupList.size() == 0) {
            return groupVOList;
        }
        for (UnionGroup unionGroup : groupList) {
            UnionGroupVO groupVO = new UnionGroupVO();
            groupVO.setGroupId(unionGroup.getId());
            groupVO.setGroupName(unionGroup.getGroupName());
            groupVOList.add(groupVO);
        }
        return groupVOList;
    }


    /**
     * 获取联盟组对象 (转换联盟组Vo)
     * @param unionGroup
     * @return
     */
    public static UnionGroupVO parseToGroupVOByGroup(UnionGroup unionGroup) {

        UnionGroupVO unionGroupVO = null;
        if (null != unionGroup) {
            unionGroupVO = new UnionGroupVO();
            unionGroupVO.setGroupId(unionGroup.getId());
            unionGroupVO.setGroupName(unionGroup.getGroupName());
        }
        return unionGroupVO;
    }
}
