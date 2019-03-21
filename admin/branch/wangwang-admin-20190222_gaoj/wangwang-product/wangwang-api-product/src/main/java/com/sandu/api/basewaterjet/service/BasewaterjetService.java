package com.sandu.api.basewaterjet.service;

import com.sandu.api.basewaterjet.input.BasewaterjetQuery;
import com.sandu.api.basewaterjet.model.Basewaterjet;
import org.springframework.stereotype.Component;
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
@Component
public interface BasewaterjetService {

    /**
     * 插入
     * @param basewaterjet
     * @return
     */
    int insert(Basewaterjet basewaterjet);

    /**
     * 更新
     * @param basewaterjet
     * @return
     */
    int update(Basewaterjet basewaterjet);

    /**
     * 删除
     * @param basewaterjetIds
     * @return
     */
    int delete(Set<Integer> basewaterjetIds,String userName);

    /**
     * 通过ID获取详情
     * @param basewaterjetId
     * @return
     */
     Basewaterjet getById(int basewaterjetId);

    /**
     * 查询全部数据
     * @param query 查询条件
     * @return
     */
    List<Basewaterjet> findAll(BasewaterjetQuery query);
    int findAllCount(BasewaterjetQuery query);

    String getMaxId();
}
