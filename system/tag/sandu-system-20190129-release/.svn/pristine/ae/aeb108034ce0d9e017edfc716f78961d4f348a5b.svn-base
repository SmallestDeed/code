package com.sandu.service.banner.dao;

import com.sandu.api.banner.input.BaseBannerListQuery;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.po.BannerPO;
import com.sandu.api.banner.output.BaseBannerVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 基础-广告Dao
 * @author WangHaiLin
 * @date 2018/5/16  20:55
 */
@Repository
public interface BaseBannerAdMapper {
    /**
     * 新增广告
     * @param banner 新增入参
     * @return int 新增数据Id
     */
    int insertBanner(BaseBannerAd banner);

    /**
     * 删除广告
     * @param banner 被删除的banner
     * @return int 删除操作返回结果
     */
    int deleteBanner(BaseBannerAd banner);

    /**
     * 修改广告信息
     * @param update 修改操作入参
     * @return int修改操作结果
     */
    int updateBanner(BaseBannerAd update);

    /**
     * 通过BannerId查询广告信息
     * @param bannerId 广告Id
     * @return BaseBannerAd
     */
    BaseBannerAd getBannerById(@Param("bannerId")Integer bannerId);

    /**
     * 条件查询基础-广告列表
     * @param query 列表查询入参
     * @return List<BaseBannerAd> 返回结果
     */
    List<BaseBannerVO> getBannerList(BannerPO query);

    /**
     * 查询满足条件的数据数量
     * @param query 查询入参
     * @return int 返回结果
     */
    int getBannerCount(BannerPO query);

    /**
     * 通过PositionId 和 modelId查询广告列表
     * @param query 查询入参
     * @return List<BaseBannerAd> 返回结果集合
     */
    List<BaseBannerAd> getBannerByModelPosition(BaseBannerListQuery query);


    List<BaseBannerAd> getBannerByRefModelId(@Param("refModelId")Integer refModelId);


    Integer getBannerCountByModelPosition(BaseBannerListQuery query);

    List<BaseBannerAd> getAllBannerByModelPosition(BaseBannerListQuery query);
}
