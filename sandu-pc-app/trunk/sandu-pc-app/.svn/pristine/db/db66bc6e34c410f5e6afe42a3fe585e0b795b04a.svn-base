package com.sandu.living.dao;

import com.sandu.living.model.input.BaseLivingSearch;
import com.sandu.living.model.output.BaseLivingVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseLivingMapper {

    /**
     * 获取小区数量
     * @param baseLivingSearch
     * @return
     */
    int count(BaseLivingSearch baseLivingSearch);

    /**
     * 获取小区列表
     * @param baseLivingSearch
     * @return
     */
    List<BaseLivingVo> list(BaseLivingSearch baseLivingSearch);

}
