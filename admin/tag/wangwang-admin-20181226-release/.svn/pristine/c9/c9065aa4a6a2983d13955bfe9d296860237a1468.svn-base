package com.sandu.service.groupproduct.dao;

import com.sandu.api.groupproducct.model.GroupProductDetails;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface GroupProductDetailsDao {
    int deleteByPrimaryKey(Long id);

    int insert(GroupProductDetails record);

    int insertSelective(GroupProductDetails record);

    GroupProductDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupProductDetails record);

    int updateByPrimaryKey(GroupProductDetails record);

    int insertList(List<GroupProductDetails> list);

    String getProductIdsByGroupId(@Param("id") Integer id);

    int deleteByGroupIds(@Param("ids") List<Integer> ids);

    int alterMainProduct(@Param("id") Integer id, @Param("productBatchId") Integer mainProductId);

    int defaultMainProduct(@Param("id") Integer id);

    Integer getMainProductIdWithGroupId(@Param("groupId") Integer groupId);
}