package com.sandu.service.basewaterjet.dao;

import com.sandu.api.basewaterjet.input.BasewaterjetQuery;
import com.sandu.api.basewaterjet.model.Basewaterjet;
import com.sandu.api.basewaterjet.output.BrandNameVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * base_waterjet
 *
 * @author sandu <dev@sanduspace.cn>
 * @datetime 2018-Nov-09 10:03
 */
@Repository
public interface BasewaterjetMapper {
    int insert(Basewaterjet basewaterjet);

    int updateByPrimaryKey(Basewaterjet basewaterjet);

    int deleteByPrimaryKey(@Param("basewaterjetIds") Set<Integer> basewaterjetIds,@Param("userName") String userName);

    Basewaterjet selectByPrimaryKey(@Param("basewaterjetId") int basewaterjetId);

    Basewaterjet selectInfoByPrimaryKey(@Param("basewaterjetId") int basewaterjetId);

    List<Basewaterjet> findAll(BasewaterjetQuery query);
    int findAllCount(BasewaterjetQuery query);

    List<BrandNameVO> selectBrandNameList(@Param("userId") int userId,@Param("userType")Integer userType);

    /**
     * 上下架
     * @author chenqiang
     * @param basewaterjetIds
     * @return
     * @date 2018/11/10 0010 16:20
     */
    int upperandlowerstatus(@Param("basewaterjetIds") Set<Integer> basewaterjetIds,@Param("templateStatus")Integer templateStatus,@Param("userName") String userName);

    String selectMaxId();
}
