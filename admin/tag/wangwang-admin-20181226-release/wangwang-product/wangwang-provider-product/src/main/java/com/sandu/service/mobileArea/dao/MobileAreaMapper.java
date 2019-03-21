package com.sandu.service.mobileArea.dao;

import com.sandu.api.mobileArea.model.MobileArea;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * mobileArea
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Jul-31 14:14
 */
@Repository
public interface MobileAreaMapper {
    int insert(MobileArea mobileArea);

    int updateByPrimaryKey(MobileArea mobileArea);

    int deleteByPrimaryKey(@Param("mobileAreaIds") Set<Integer> mobileAreaIds);

    MobileArea selectByPrimaryKey(@Param("mobileAreaId") int mobileAreaId);

    List<MobileArea> selectByMobilePrefix(@Param("mobilePrefix")String mobilePrefix);
}
