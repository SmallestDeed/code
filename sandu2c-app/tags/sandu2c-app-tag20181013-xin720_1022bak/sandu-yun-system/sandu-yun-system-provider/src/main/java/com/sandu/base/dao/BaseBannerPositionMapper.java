package com.sandu.base.dao;

import com.sandu.banner.input.BaseBannerPositionQuery;
import com.sandu.banner.model.BaseBannerPosition;
import org.springframework.stereotype.Repository;

/**
 * 广告-位置Mapper
 * @author WangHaiLin
 * @date 2018/5/14  11:52
 */
@Repository
public interface BaseBannerPositionMapper {
    /**
     * 通过type和位置编码查询Banner使用位置
     * @param query 查询入参
     * @return BaseBannerPosition 返回结果
     */
    BaseBannerPosition getPositionByTypeAndCode(BaseBannerPositionQuery query);


}
