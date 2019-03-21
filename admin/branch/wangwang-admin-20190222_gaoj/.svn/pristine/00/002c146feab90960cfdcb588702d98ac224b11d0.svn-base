package com.sandu.service.platform.dao;

import com.sandu.api.groupproducct.model.GroupProduct;
import com.sandu.api.platform.model.PlatformGroupRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sandu
 */
@Repository
public interface Platform2cGroupRelDao {
    int insertRel(PlatformGroupRel rel);

    int deleteByGroupIds(List<Integer> groupIds);

    int updateRelWithGroupId(PlatformGroupRel rel);

    List<Integer> getBeAllotGroupIds(List<Integer> groupIds);

    GroupProduct getInfoByGroupId(Integer groupId);
}
