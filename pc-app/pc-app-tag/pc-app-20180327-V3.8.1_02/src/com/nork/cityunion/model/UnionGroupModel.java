package com.nork.cityunion.model;

import com.nork.cityunion.model.vo.UnionGroupDetailsVO;
import com.nork.common.model.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kono on 2018/1/26 0026.
 */
public class UnionGroupModel extends Mapper implements Serializable {
    private static final long serialVersionUID = 1L;
    /**  联盟组Id  **/
    private Integer groupId;
    /**  组名  **/
    private String groupName;
    /**  用户ID  **/
    private Integer userId;
    /** 组合明细 **/
    private List<UnionGroupDetailsVO> unionGroupDetails;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<UnionGroupDetailsVO> getUnionGroupDetails() {
        return unionGroupDetails;
    }

    public void setUnionGroupDetails(List<UnionGroupDetailsVO> unionGroupDetails) {
        this.unionGroupDetails = unionGroupDetails;
    }
}
