package com.sandu.base.dao;

import com.sandu.banner.model.ResBannerPic;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 广告--图片资源Mapper
 * @author WangHaiLin
 * @date 2018/5/14  14:17
 */
@Repository
public interface ResBannerPicMapper {

    /**
     * 通过图片Id查询图片详细信息
     * @param picId 图片Id
     * @return resBannerPic 查询结果
     */
    ResBannerPic selectPicById(@Param("picId") Integer picId);

}
