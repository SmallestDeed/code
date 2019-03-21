package com.sandu.service.groupproduct.dao;

import com.sandu.api.groupproducct.input.GroupQuery;
import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.groupproducct.model.bo.GroupProductListBO;
import com.sandu.base.IdToSomeBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Sandu
 */
@Repository
public interface GroupProductDao {

    int deleteByPrimaryKey(Long id);

    int insert(GroupProduct record);

    int insertSelective(GroupProduct record);

    GroupProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupProduct record);

    int updateByPrimaryKey(GroupProduct record);

    List<GroupProductListBO> queryList(GroupQuery groupQuery);

    int deleteByIds(List<Integer> ids);

    List<GroupProduct> getInfoByIds(List<Integer> notAllotIds);

    List<GroupProductListBO> queryList2b(GroupQuery query);

    List<GroupProductListBO> queryList2c(GroupQuery query);

    int updateGroupSecrecyByIds(@Param("ids") List<Integer> ids, @Param("secrecyFlag") Integer secrecy);

    String getMaxId();

    List<IdToSomeBean> listGroupPutStatusByGroupIds(@Param("ids") List<Integer> collect);
}