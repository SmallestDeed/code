package com.sandu.api.banner.service;

import com.sandu.api.banner.input.BaseBannerAdAdd;
import com.sandu.api.banner.input.BaseBannerAdUpdate;
import com.sandu.api.banner.input.BaseBannerListQuery;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.BaseBannerPosition;
import com.sandu.api.banner.model.po.BannerPO;
import com.sandu.api.banner.output.BaseBannerVO;
import com.sandu.commons.LoginUser;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基础-广告 Service接口
 * @author WangHaiLin
 * @date 2018/5/16  20:50
 */
@Component
public interface BaseBannerAdService {
    /**
     * 新增广告
     * @param bannerAdd 新增入参
     * @param loginUser 当前登录用户
     * @return int 新增数据Id
     */
    int addBaseBanner(BaseBannerAdAdd bannerAdd, LoginUser loginUser);

    /**
     * 删除广告
     * @param bannerId 被删除的bannerId
     * @param loginUser 当前登录用户
     * @return int 删除操作返回结果
     */
    int deleteBanner(Integer bannerId,LoginUser loginUser);

    /**
     * 修改广告信息
     * @param update 修改操作入参
     * @param loginUser 当前登录用户
     * @return int修改操作结果
     */
    int updateBanner(BaseBannerAdUpdate update,LoginUser loginUser);

    /**
     * 通过BannerId查询广告信息
     * @param bannerId 广告Id
     * @return BaseBannerAd
     */
    BaseBannerAd getBannerById(Integer bannerId);

    /**
     * 条件查询基础-广告列表
     * @param queryList 列表查询入参
     * @return List<BaseBannerAd> 返回结果
     */
    List<BaseBannerVO> getBannerList(BannerPO queryList);

    /**
     * 获取满足条件的数据数量
     * @param bannerPO 入参
     * @return int 满足条件数据数量
     */
    int getCount(BannerPO bannerPO);

    /**
     * 通过PositionId 和 modelId查询广告列表
     * @param query 查询入参
     * @return List<BaseBannerAd> 返回结果集合
     */
    List<BaseBannerAd> getBannerByModelPosition(BaseBannerListQuery query);

    List<BaseBannerAd> getBaseBannerByRefModelId(Integer refModelId);

    /**
     * 修改banner发布状态
     * @param bannerAdId
     * @param state
     * @return
     */
    Integer setBannerState(Integer bannerAdId, Integer state, LoginUser loginUser);

    /**
     * 修改banner排序
     * @param bannerAdId
     * @param sequence
     * @param loginUser
     * @return
     */
    List<BaseBannerAd> setBannerSequence(Integer bannerAdId, Integer sequence, LoginUser loginUser);

    /**
     * 查询banner数量
     * @param positionId
     * @param shopId
     * @return
     */
    Integer getBannerCountByModelPosition(Integer positionId, Integer shopId);
}
