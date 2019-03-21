package com.sandu.service.banner.dao;

import com.sandu.api.banner.model.ResBannerPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 广告-图片资源Dao
 * @author WangHaiLin
 * @date 2018/5/16  20:56
 */
@Repository
public interface ResBannerPicMapper {
    /**
     * 新增广告图片资源
     * @param pic 新增入参
     * @return int 新增数据Id
     */
    int insertResBannerPic(ResBannerPic pic);

    /**
     * 删除图片信息
     * @param pic 图片Id
     * @return int返回结果
     */
    int deleteResBannerPic(ResBannerPic pic);

    /**
     * 修改广告图片信息
     * @param pic 修改入参
     * @return int 修改操作结果
     */
    int updateResBannerPic(ResBannerPic pic);

    /**
     * 通过图片Id查询图片信息
     * @param picId 图片Id
     * @return ResBannerPic
     */
    ResBannerPic getPicById(@Param("picId") Integer picId);


}
